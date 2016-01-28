package tk.tapet.android.game.birds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import tk.tapet.android.game.birds.BurningBirdsMain;
import tk.tapet.android.game.birds.utils.ActionResolver;
import tk.tapet.android.game.birds.utils.Assets;
import tk.tapet.android.game.birds.utils.Score;

public class MainMenuScreen implements Screen {
	
	private Skin skin = null;
	private Image img = null;
	private Stage stage = null;
	private BurningBirdsMain game = null;
	private ImageButton playButton = null;
	private ImageButton exitButton = null;
	private ImageButton highScoresButton = null;
	private ImageButton musicOnButton = null;
	private ImageButton musicOffButton = null;
	private TextureAtlas buttonsAtlas = new TextureAtlas(Gdx.files.internal("animations/button_pack.pack"));

	private ActionResolver actionResolver = null;
	
	public MainMenuScreen(BurningBirdsMain game, ActionResolver actionResolver) {
		this.game = game;
		this.actionResolver = actionResolver;
	}

	@Override
	public void show() {
		img = new Image(new Texture(Gdx.files.internal("img/main_menu_screen_background.jpg")));
		img.setSize(SplashScreen.VIRTUAL_WIDTH, SplashScreen.VIRTUAL_HEIGHT);
		
		stage = new Stage(new StretchViewport(SplashScreen.VIRTUAL_WIDTH, SplashScreen.VIRTUAL_HEIGHT));
		
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin(buttonsAtlas);
		
		playButton = new ImageButton(skin.getDrawable("buttonplay"));
		exitButton = new ImageButton(skin.getDrawable("buttonexit"));
		highScoresButton = new ImageButton(skin.getDrawable("buttonhighscore"));
		musicOffButton = new ImageButton(skin.getDrawable("buttonmusicoff"));
		musicOnButton = new ImageButton(skin.getDrawable("buttonmusicon"));
		
		exitButton.setTransform(true);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		playButton.setTransform(true);
		playButton.setRotation(15.0f);
		playButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.moveBy(-stage.getWidth(), 0, .5f), Actions.run(new Runnable() {
					
					@Override
					public void run() {
						if (Score.isSoundenabled()) {
							Assets.buttonsSound.play();
							Assets.gameMusic.pause();
						}
//						((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(game, actionResolver));
						game.setScreen(new GameScreen(game, actionResolver));
					}
				})));
			}
		});
		
		highScoresButton.setTransform(true);
		highScoresButton.setRotation(-10.0f);
		highScoresButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.moveBy(stage.getWidth(), 0, 1), Actions.run(new Runnable() {

					@Override
					public void run() {
						if (Score.isSoundenabled())
							Assets.buttonsSound.play();
						
//						((Game) Gdx.app.getApplicationListener()).setScreen(new ScoreScreen(game));
						game.setScreen(new ScoreScreen(game));
					}
				})));
			}
		});
		
		musicOnButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				musicOnButton.remove();
				stage.addActor(musicOffButton);
				Assets.gameMusic.pause();
				Score.saveSoundEnabled(false);
			}
		});
		
		musicOffButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				musicOffButton.remove();
				stage.addActor(musicOnButton);
				Assets.gameMusic.play();
				Score.saveSoundEnabled(true);
			}
		});
		
		stage.addActor(img);
		stage.addActor(exitButton);
		stage.addActor(playButton);
		stage.addActor(highScoresButton);
		
		if (Score.isSoundenabled()) {
			stage.addActor(musicOnButton);
			Assets.gameMusic.play();
		} else {
			stage.addActor(musicOffButton);
			Assets.gameMusic.stop();
		}
	}

	@Override
	public void render(float delta) {
		 Gdx.gl.glClearColor(0, 0, 0, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	     stage.act();
	     stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		playButton.setPosition( SplashScreen.VIRTUAL_WIDTH - playButton.getWidth(), 0);
		exitButton.setPosition(20, 20);
		highScoresButton.setPosition(SplashScreen.VIRTUAL_WIDTH / 2 - exitButton.getWidth() / 1.5f, SplashScreen.VIRTUAL_HEIGHT / 2);
		musicOnButton.setPosition(0, SplashScreen.VIRTUAL_HEIGHT - musicOnButton.getHeight());
		musicOffButton.setPosition(0, SplashScreen.VIRTUAL_HEIGHT - musicOffButton.getHeight());
		
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
		stage.dispose();
	}

}
