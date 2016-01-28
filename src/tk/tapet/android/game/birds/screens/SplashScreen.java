package tk.tapet.android.game.birds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import tk.tapet.android.game.birds.BurningBirdsMain;
import tk.tapet.android.game.birds.utils.ActionResolver;
import tk.tapet.android.game.birds.utils.ActorTween;
import tk.tapet.android.game.birds.utils.AnimatedImage;
import tk.tapet.android.game.birds.utils.Assets;
import tk.tapet.android.game.birds.utils.Score;

public class SplashScreen implements Screen, InputProcessor {
	
	public static final int VIRTUAL_WIDTH = 1280;
	public static final int VIRTUAL_HEIGHT = 720;
	
	private static final long SPLASH_SCREEN_TIME = 8000;
	
	private Image background = null;
	private BurningBirdsMain game = null;
	private Stage stage = null;
	private ActorTween wantedSignboardImage = null;
	private ActorTween flyingText = null;
	private AnimatedImage burningTextImageAnim = null;
	private ParticleEffect featherLarge = null;
	private ParticleEffect featherSmall = null;

	private Sound boomSound = null;
	private Sound fireSound = null;
	private Sound signboardSound = null;
	
	private ActionResolver actionResolver = null;
	
	private TextureAtlas atlas = new TextureAtlas("animations/fire_text_pack.pack");
	private TextureRegion[] burnTextTextureRegion = {
			atlas.findRegion("frame0"),	
			atlas.findRegion("frame1"),	
			atlas.findRegion("frame2"),	
			atlas.findRegion("frame3"),	
			atlas.findRegion("frame4"),	
	};
	
	public SplashScreen(BurningBirdsMain game, ActionResolver actionResolver) {
		this.game = game;
		this.actionResolver = actionResolver;
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		
		currentTime = System.currentTimeMillis();
		
		if (Score.isSoundenabled()) {
			Assets.gameMusic.play();
		}
		
		boomSound = Gdx.audio.newSound(Gdx.files.internal("music/boom.mp3"));
		fireSound = Gdx.audio.newSound(Gdx.files.internal("music/izgarqne.wav"));
		signboardSound = Gdx.audio.newSound(Gdx.files.internal("music/tabela.wav"));
		
		stage = new Stage(new StretchViewport(SplashScreen.VIRTUAL_WIDTH, SplashScreen.VIRTUAL_HEIGHT));
		stage.getViewport().apply();
		
		featherLarge = new ParticleEffect();
		featherLarge.load(Gdx.files.internal("effects/large_feather_particle.p"), Gdx.files.internal("img"));

		featherSmall = new ParticleEffect();
		featherSmall.load(Gdx.files.internal("effects/small_feather_particle.p"), Gdx.files.internal("img"));
		
		Animation burningTextAnimation = new Animation(.1f, burnTextTextureRegion);
		burningTextAnimation.setPlayMode(PlayMode.LOOP);
		
		burningTextImageAnim = new AnimatedImage(burningTextAnimation);
		burningTextImageAnim.setX(VIRTUAL_WIDTH - burningTextImageAnim.getWidth());
		burningTextImageAnim.setHeight(VIRTUAL_HEIGHT);
		
		background = new Image(new Texture("img/splash_background.jpg"));
		background.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		
		wantedSignboardImage = new ActorTween(new Texture(Gdx.files.internal("img/wanted_signboard.png")));
		wantedSignboardImage.setOrigin(wantedSignboardImage.getWidth() / 2.0f, 0);
		wantedSignboardImage.setPosition(VIRTUAL_WIDTH / 2 - wantedSignboardImage.getWidth() / 2, VIRTUAL_HEIGHT);
		
		flyingText = new ActorTween(new Texture(Gdx.files.internal("img/flaing.png")));
		flyingText.setPosition(VIRTUAL_WIDTH, 0);
		flyingText.setVelocityX(-1500);
		
		stage.addActor(background);
		stage.addActor(wantedSignboardImage);
		stage.addActor(flyingText);
		
	}
	
	private int soundCount = 0;
	private long currentTime = 0;
	@Override
	public void render(float delta) {
		System.out.println("DT: " + delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();

		game.batcher.begin();
		
		featherLarge.draw(game.batcher, delta);
		featherSmall.draw(game.batcher, delta);
		
		if (flyingText.getX() - 50 <= 0 && soundCount == 0) {
			if (Score.isSoundenabled()) {
				boomSound.play();
			}
			flyingText.setX(50f);
			flyingText.setVelocityX(0);
			flyingText.setIsFinished(true);
			soundCount++;
		}
		
		if (flyingText.isAnimationFinished()) {
			if (Score.isSoundenabled() && soundCount == 1) {
				signboardSound.play();
				soundCount++;
			}
			wantedSignboardImage.setVelocityY(-800f);
			if (wantedSignboardImage.getY() <= 50) {
				wantedSignboardImage.setIsFinished(true);
				wantedSignboardImage.setVelocityY(0);
			}
		}
		
		if (wantedSignboardImage.isAnimationFinished()) {
			stage.addActor(burningTextImageAnim);
			if (Score.isSoundenabled() && soundCount == 2) {
				fireSound.play();
				soundCount++;
			}
		}

		if (System.currentTimeMillis() - currentTime > SPLASH_SCREEN_TIME ) {
			currentTime = System.currentTimeMillis();
//			((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game, actionResolver));
			game.setScreen(new MainMenuScreen(game, actionResolver));
		}
 		
		flyingText.update(delta);
		flyingText.draw(game.batcher, 0f);
		
		wantedSignboardImage.update(delta);
		wantedSignboardImage.draw(game.batcher, 0);
		
		game.batcher.end();
		
	}
	
	@Override
	public void resize(int width, int height) {
		featherLarge.setPosition(0, height + 100);
		featherLarge.start();

		featherSmall.setPosition(0, height + 100);
		featherSmall.start();
		
		stage.getViewport().update(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
	
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		featherLarge.dispose();
		featherSmall.dispose();
		stage.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		game.setScreen(new MainMenuScreen(game, actionResolver));
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
