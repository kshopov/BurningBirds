package tk.tapet.android.game.birds.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import tk.tapet.android.game.birds.BurningBirdsMain;
import tk.tapet.android.game.birds.model.Bird;
import tk.tapet.android.game.birds.model.Cloud;
import tk.tapet.android.game.birds.model.Drill;
import tk.tapet.android.game.birds.utils.ActionResolver;
import tk.tapet.android.game.birds.utils.BackgroundManager;
import tk.tapet.android.game.birds.utils.Score;
import tk.tapet.android.game.birds.utils.ScreenshotFactory;

public class GameScreen implements Screen, InputProcessor, TextInputListener {

	private static final int GAME_OVER_BIRD_VELOCITY_Y = 500;

	private ActionResolver actionResolver = null;

	private BitmapFont font = null;
	
	private SpriteBatch batch = null;

	private TextureAtlas birdsAtlas = new TextureAtlas(Gdx.files.internal("animations/birds_pack.pack"));
	private TextureAtlas drillAtlas = new TextureAtlas(Gdx.files.internal("animations/drill_pack.pack"));
	private TextureAtlas buttonsAtlas = new TextureAtlas(Gdx.files.internal("animations/button_pack.pack"));

	private FrameBuffer particleBuffer = null;

	private TextureRegion particleRegion = new TextureRegion();

	private Music fireMusic = null;
	private Music gameOverMusic = null;
	private Music gameOverBirdSound = null;
	private Sound orangeBirdSound = null;
	private Sound yellowBirdSound = null;
	private Sound blueBirdSound = null;
	private Sound levelChangeSound = null;
	private Sound smashBirdSound = null;

	private ParticleEffect fireEffectBotOne = null;
	private ParticleEffect fireEffectBotTwo = null;
	private ParticleEffect fireEffectTopOne = null;
	private ParticleEffect fireEffectTopTwo = null;

	private Animation blueBirdAnimation = null;
	private Animation redBirdAnimation = null;
	private Animation yellowBirdAnimation = null;
	private Animation gameOverBirdAnimation = null;
	private Animation starBirdAnimation = null;
	private Animation chickenLegsAnimation = null;
	private Animation drillTopAnimation = null;
	private Animation drillBotAnimation = null;
	private Animation burnedBirdAnimation = null;

	private Drill drillTop = null;
	private Drill drillBot = null;

	private Sprite buttonFireSprite = null;
	private Sprite buttonUp = null;
	private Sprite buttonDown = null;
	private Sprite buttonShare = null;
	private Sprite buttonPlayServices = null;
	private Sprite buttonGPlusShare = null;
	private Sprite boxChickenLegs = null;
	private Sprite fuelBar = null;
	private Sprite gameOverSprite = null;
	private Sprite buttonPause = null;
	private Sprite buttonSound = null;
	private Sprite buttonReload = null;

	private Cloud cloud1 = null;
	private Cloud cloud2 = null;
	private Cloud cloud3 = null;

	private ArrayList<Bird> birdsArray = new ArrayList<Bird>();
	private Bird gameOverBird = null;

	private BackgroundManager bm = null;

	private int width = 0;
	private int height = 0;
	private int highScore = 0;
	private int lastUpdatePoints = 0;
	private long bornTime = Bird.INITIAL_BORN_TIME;
	private long currentTime = 0;
	private float stateTime = 0f;
	private float fuelMeterHeight = 0f;
	private float fuelMeterInitialHeight = 0f;
	private int firePointer = -1;
	private int drillPointer = -1;
	private boolean isGameOver = false;
	private boolean pause = false;
	private Random rand = new Random();

	public TextureRegion[] blueBirdTextureRegion = {
			birdsAtlas.findRegion("00blue_bird"),
			birdsAtlas.findRegion("01blue_bird"),
			birdsAtlas.findRegion("02blue_bird"),
			birdsAtlas.findRegion("03blue_bird"),
			birdsAtlas.findRegion("04blue_bird"),
			birdsAtlas.findRegion("05blue_bird")
	};

	private TextureRegion[] redBirdTextureRegion = {
			birdsAtlas.findRegion("06red_bird"),
			birdsAtlas.findRegion("07red_bird"),
			birdsAtlas.findRegion("08red_bird"),
			birdsAtlas.findRegion("09red_bird"),
			birdsAtlas.findRegion("10red_bird"),
			birdsAtlas.findRegion("11red_bird")
	};

	private TextureRegion[] yellowBirdTextureRegion = {
			birdsAtlas.findRegion("12yellow_bird"),
			birdsAtlas.findRegion("13yellow_bird"),
			birdsAtlas.findRegion("14yellow_bird"),
			birdsAtlas.findRegion("15yellow_bird"),
			birdsAtlas.findRegion("16yellow_bird"),
			birdsAtlas.findRegion("17yellow_bird")
	};

	private TextureRegion[] burnedBirdTextureRegion = {
			birdsAtlas.findRegion("18burned_bird"),
			birdsAtlas.findRegion("19burned_bird"),
			birdsAtlas.findRegion("20burned_bird"),
			birdsAtlas.findRegion("21burned_bird"),
			birdsAtlas.findRegion("22burned_bird"),
			birdsAtlas.findRegion("23burned_bird"),
			birdsAtlas.findRegion("24burned_bird")
	};

	private TextureRegion[] starBirdTextureRegion = {
			birdsAtlas.findRegion("25star_bird"),
			birdsAtlas.findRegion("26star_bird"),
			birdsAtlas.findRegion("27star_bird")
	};

	private TextureRegion[] gameOverBirdTextureRegion = {
			birdsAtlas.findRegion("laughbird0"),
			birdsAtlas.findRegion("laughbird1")
	};

	private TextureRegion[] buttonsTextureRegion = {
			buttonsAtlas.findRegion("buttondown"),
			buttonsAtlas.findRegion("buttonup"),
			buttonsAtlas.findRegion("buttonfire"),
			buttonsAtlas.findRegion("sharebutton"),
			buttonsAtlas.findRegion("boxchickenlegs"),
			buttonsAtlas.findRegion("gplusshare"),
			buttonsAtlas.findRegion("gplay_games_controller"),
			buttonsAtlas.findRegion("buttonpause"),
			buttonsAtlas.findRegion("buttonmusicon"),
			buttonsAtlas.findRegion("buttonmusicoff"),
			buttonsAtlas.findRegion("reload")
	};

	private TextureRegion[] chickenLegsTextureRegion = {
			birdsAtlas.findRegion("legs0"),
			birdsAtlas.findRegion("legs1"),
			birdsAtlas.findRegion("legs2"),
			birdsAtlas.findRegion("legs3")
	};
	
	private TextureRegion[] numberTextureRegion = {
			buttonsAtlas.findRegion("0"),
			buttonsAtlas.findRegion("1"),
			buttonsAtlas.findRegion("2"),
			buttonsAtlas.findRegion("3"),
			buttonsAtlas.findRegion("4"),
			buttonsAtlas.findRegion("5"),
			buttonsAtlas.findRegion("6"),
			buttonsAtlas.findRegion("7"),
			buttonsAtlas.findRegion("8"),
			buttonsAtlas.findRegion("9"),
	};
	
	private Sprite[] numbersSprite = new Sprite[10];

	private TextureRegion[] drillTopTextureRegion = {
			drillAtlas.findRegion("drill_top0"),
			//drillAtlas.findRegion("drill_top1")
	};

	private TextureRegion[] drillBotTextureRegion = {
			drillAtlas.findRegion("drill_bot0"),
			//drillAtlas.findRegion("drill_bot1")
	};

	private OrthographicCamera camera = null;
	private Viewport stretchViewport = null;

	private ShapeRenderer shapeRenderer = new ShapeRenderer();

	private boolean projectionMatrixSet = false;
	private BurningBirdsMain game = null;

	public GameScreen(BurningBirdsMain game, ActionResolver actionResolver) {
		this.actionResolver = actionResolver;
		this.game = game;
	}

	@Override
	public void show() {
		/*if (Gdx.app.getType() == ApplicationType.WebGL) {
            if (!Gdx.graphics.isFullscreen()) Gdx.graphics.setDisplayMode(Gdx.graphics.getDisplayModes()[0]);
        }*/
		
		Gdx.input.setInputProcessor(this);

		camera = new OrthographicCamera();

		stretchViewport = new StretchViewport(SplashScreen.VIRTUAL_WIDTH,
				SplashScreen.VIRTUAL_HEIGHT, camera);
		stretchViewport.apply();

		batch = new SpriteBatch();
		
		fireMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gorelki.wav"));
		fireMusic.setLooping(true);
		
		gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gameover.wav"));
		orangeBirdSound = Gdx.audio.newSound(Gdx.files.internal("music/orangebird.wav"));
		yellowBirdSound = Gdx.audio.newSound(Gdx.files.internal("music/yellowbird.wav"));
		blueBirdSound = Gdx.audio.newSound(Gdx.files.internal("music/bluebird.wav"));
		gameOverBirdSound = Gdx.audio.newMusic(Gdx.files.internal("music/prismivane.wav"));
		levelChangeSound = Gdx.audio.newSound(Gdx.files.internal("music/iuhuuu.wav"));
		smashBirdSound = Gdx.audio.newSound(Gdx.files.internal("music/padashto_pile.wav"));

		font = new BitmapFont(Gdx.files.internal("data/font.fnt"));

		drillBotAnimation = new Animation(.3f, drillBotTextureRegion);
		drillBotAnimation.setPlayMode(PlayMode.LOOP);
		drillBot = new Drill(new Vector2(SplashScreen.VIRTUAL_WIDTH / 2, -drillBotTextureRegion[0].getRegionHeight() * 0.7f),
				new Vector2(0, 5), new Vector2(0, 0), drillBotAnimation);

		drillTopAnimation = new Animation(.3f, drillTopTextureRegion);
		drillTopAnimation.setPlayMode(PlayMode.LOOP);
		drillTop = new Drill(new Vector2(SplashScreen.VIRTUAL_WIDTH / 2, SplashScreen.VIRTUAL_HEIGHT - drillTopTextureRegion[0].getRegionHeight() / 5),
				new Vector2(0, 5), new Vector2(0, 0), drillTopAnimation);

		blueBirdAnimation = new Animation(.3f, blueBirdTextureRegion);
		blueBirdAnimation.setPlayMode(PlayMode.LOOP);
		redBirdAnimation = new Animation(.2f, redBirdTextureRegion);
		redBirdAnimation.setPlayMode(PlayMode.LOOP);
		yellowBirdAnimation = new Animation(.25f, yellowBirdTextureRegion);
		yellowBirdAnimation.setPlayMode(PlayMode.LOOP);
		starBirdAnimation = new Animation(.15f, starBirdTextureRegion);
		starBirdAnimation.setPlayMode(PlayMode.LOOP);
		chickenLegsAnimation = new Animation(.2f, chickenLegsTextureRegion);
		chickenLegsAnimation.setPlayMode(PlayMode.NORMAL);
		burnedBirdAnimation = new Animation(.1f, burnedBirdTextureRegion);
		burnedBirdAnimation.setPlayMode(PlayMode.LOOP);

		gameOverBirdAnimation = new Animation(.1f, gameOverBirdTextureRegion);
		gameOverBirdAnimation.setPlayMode(PlayMode.LOOP);
		gameOverBird = new Bird(new Vector2(200, -gameOverBirdTextureRegion[0].getRegionHeight()), new Vector2(0, 0), null, gameOverBirdAnimation, 0);


		buttonUp = new Sprite(buttonsTextureRegion[1]);
		buttonUp.setPosition(buttonUp.getWidth() / 2, SplashScreen.VIRTUAL_HEIGHT / 2.0f);

		buttonDown = new Sprite(buttonsTextureRegion[0]);
		buttonDown.setPosition(buttonDown.getWidth() / 2, buttonUp.getY() - buttonDown.getRegionHeight() * 1.3f);

		buttonFireSprite = new Sprite(buttonsTextureRegion[2]);
		buttonFireSprite.setPosition(SplashScreen.VIRTUAL_WIDTH - buttonFireSprite.getWidth() * 2,
				buttonDown.getY());
		
		
		boxChickenLegs = new Sprite(buttonsTextureRegion[4]);
		boxChickenLegs.setPosition(SplashScreen.VIRTUAL_WIDTH - boxChickenLegs.getRegionWidth(), 0);

		gameOverSprite = new Sprite(new Texture(Gdx.files.internal("img/gameover.png")));
		gameOverSprite.setPosition(SplashScreen.VIRTUAL_WIDTH / 2 - gameOverSprite.getRegionWidth() / 2,
				SplashScreen.VIRTUAL_HEIGHT - gameOverSprite.getRegionHeight() * 1.5f);

		buttonShare = new Sprite(buttonsTextureRegion[3]);
		buttonShare.setPosition(SplashScreen.VIRTUAL_WIDTH - buttonShare.getWidth(), SplashScreen.VIRTUAL_HEIGHT - buttonShare.getHeight());
		
		buttonGPlusShare = new Sprite(buttonsTextureRegion[5]);
		buttonGPlusShare.setPosition(SplashScreen.VIRTUAL_WIDTH - buttonShare.getWidth() * 2, buttonShare.getY());
		
		buttonPlayServices = new Sprite(buttonsTextureRegion[6]);
		buttonPlayServices.setPosition(buttonGPlusShare.getX() - buttonPlayServices.getWidth(), buttonShare.getY());
		
		buttonPause = new Sprite(buttonsTextureRegion[7]);
		buttonPause.setPosition(SplashScreen.VIRTUAL_WIDTH - buttonPause.getWidth()- 10, SplashScreen.VIRTUAL_HEIGHT - buttonPause.getHeight());
		buttonPause.setScale(1f, 0.5f);
		
		buttonReload = new Sprite(buttonsTextureRegion[10]);
		buttonReload.setPosition(gameOverSprite.getX() + buttonReload.getWidth(), buttonFireSprite.getY() + 15);
		
		if (Score.isSoundenabled()) {
			buttonSound = new Sprite(buttonsTextureRegion[8]);
		} else {
			buttonSound = new Sprite(buttonsTextureRegion[9]);
		}
		buttonSound.setPosition(10, SplashScreen.VIRTUAL_HEIGHT - buttonSound.getHeight() - 10);
		
		for (int i = 0; i < numberTextureRegion.length; i++) {
			numbersSprite[i] = new Sprite(numberTextureRegion[i]);
		}

		fuelBar = new Sprite(new Texture(Gdx.files.internal("img/fuel_bar_img.png")));
		fuelBar.setPosition(SplashScreen.VIRTUAL_WIDTH - fuelBar.getWidth() * 2,
				SplashScreen.VIRTUAL_HEIGHT - fuelBar.getHeight() * 1.5f);

		fuelMeterInitialHeight = fuelMeterHeight = fuelBar.getHeight() - 85;

		cloud1 = new Cloud(new Texture(Gdx.files.internal("img/cloud1.png")));
		cloud1.setPosition(0, SplashScreen.VIRTUAL_HEIGHT - cloud1.getHeight());

		cloud2 = new Cloud(new Texture(Gdx.files.internal("img/cloud2.png")));
		cloud2.setPosition(SplashScreen.VIRTUAL_WIDTH / 2,
				SplashScreen.VIRTUAL_HEIGHT - cloud2.getHeight());

		cloud3 = new Cloud(new Texture(Gdx.files.internal("img/cloud2.png")));
		cloud3.setPosition(SplashScreen.VIRTUAL_WIDTH / 3,
				SplashScreen.VIRTUAL_HEIGHT - cloud3.getHeight() * 2.5f);
		cloud3.setVelocityX(40.0f);

		fireEffectBotOne = new ParticleEffect();
		fireEffectBotOne.load(Gdx.files.internal("effects/drill_bot.p"), Gdx.files.internal("img"));
		fireEffectBotOne.setPosition((int) drillBot.getPosition().x + 20, drillBot.getPosition().y +
				drillBotTextureRegion[0].getRegionHeight() - 55);
		fireEffectBotOne.start();

		fireEffectBotTwo = new ParticleEffect();
		fireEffectBotTwo.load(Gdx.files.internal("effects/drill_bot.p"), Gdx.files.internal("img"));
		fireEffectBotTwo.setPosition((int) drillBot.getPosition().x + 65, drillBot.getPosition().y + drillBotTextureRegion[0].getRegionHeight() - 55);
		fireEffectBotTwo.start();

		fireEffectTopOne = new ParticleEffect();
		fireEffectTopOne.load(Gdx.files.internal("effects/drill_top.p"), Gdx.files.internal("img"));
		fireEffectTopOne.setPosition((int) drillTop.getPosition().x + 20, (int) drillTop.getPosition().y + 55);
		fireEffectTopOne.start();

		fireEffectTopTwo = new ParticleEffect();
		fireEffectTopTwo.load(Gdx.files.internal("effects/drill_top.p"), Gdx.files.internal("img"));
		fireEffectTopTwo.setPosition((int) drillTop.getPosition().x + 65, (int) drillTop.getPosition().y + 55);
		fireEffectTopTwo.start();

		particleBuffer = new FrameBuffer(Format.RGBA8888, SplashScreen.VIRTUAL_WIDTH, SplashScreen.VIRTUAL_HEIGHT, false);

		bm = new BackgroundManager();
		
		currentTime = System.currentTimeMillis();
		
	}

	@Override
	public void render(float delta) {
		stateTime += delta;
		camera.update();

		addBirdToArray();

		particleBuffer.bind();

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		// particles draw
		if (fuelMeterHeight > 0) {
			fireEffectBotOne.draw(batch, delta);
			fireEffectBotTwo.draw(batch, delta);
			fireEffectTopOne.draw(batch, delta);
			fireEffectTopTwo.draw(batch, delta);
		}
		
		if (!isGameOver && !pause) {
			if (firePointer >= 0) {
				fuelMeterHeight -= 0.2;
				if (fuelMeterHeight <= 0) {
					//isGameOver = true;
					//gameOverBird.setVelocityY(GAME_OVER_BIRD_VELOCITY_Y);
					fuelMeterHeight = 0;
				}
			} else {
				fuelMeterHeight += 0.1;
				if (fuelMeterHeight > fuelMeterInitialHeight)
					fuelMeterHeight = fuelMeterInitialHeight;
			}
		}

		particleRegion.setRegion(particleBuffer.getColorBufferTexture());
		particleRegion.flip(false, true);

		batch.end();

		FrameBuffer.unbind();

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		bm.draw(batch);
		bm.update(delta);

		if (cloud1.getX() > SplashScreen.VIRTUAL_WIDTH) {
			cloud1.setPosition(-cloud1.getWidth(), SplashScreen.VIRTUAL_HEIGHT
					- cloud1.getHeight());
		}

		if (cloud2.getX() > SplashScreen.VIRTUAL_WIDTH) {
			cloud2.setPosition(-cloud2.getWidth(), SplashScreen.VIRTUAL_HEIGHT
					- cloud2.getHeight());
		}

		if (cloud3.getX() > SplashScreen.VIRTUAL_WIDTH) {
			cloud3.setPosition(-cloud3.getWidth(), SplashScreen.VIRTUAL_HEIGHT
					- cloud3.getHeight() * 3);
		}
		
		cloud1.draw(batch);
		cloud2.draw(batch);
		cloud3.draw(batch);

		if (!pause) {
			cloud1.update(delta, batch);
			cloud2.update(delta, batch);
			cloud3.update(delta, batch);
		}
		
		font.draw(batch, "Score: " + highScore,
				boxChickenLegs.getX() - 200,
				boxChickenLegs.getY() + font.getCapHeight() * 2);

		drillTop.draw(batch, stateTime);
		drillBot.draw(batch, stateTime);

		if (drillPointer != -1 && !pause) {
			updateDrill();
		}

		buttonFireSprite.draw(batch);
		buttonUp.draw(batch);
		buttonDown.draw(batch);
		
		if (!isGameOver) {
			buttonPause.draw(batch);
			if (isButtonTouched(buttonPause)) {
				if (pause) {
					pause = false;
				} else {
					pause = true;
				}
			}
		}

		buttonSound.draw(batch);
		if (isButtonTouched(buttonSound)) {
			if (Score.isSoundenabled()) {
				buttonSound.set(new Sprite(buttonsTextureRegion[9]));
				Score.saveSoundEnabled(false);
			} else {
				buttonSound.set(new Sprite(buttonsTextureRegion[8]));
				Score.saveSoundEnabled(true);
			}
			buttonSound.setPosition(0, SplashScreen.VIRTUAL_HEIGHT - buttonSound.getHeight());
		}
		
		batch.end();
		if(!projectionMatrixSet){
			shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
			projectionMatrixSet = true;
	    }
		shapeRenderer.begin(ShapeType.Filled);
//        shapeRenderer.setColor(1-fuelMeterHeight/fuelMeterInitialHeight,fuelMeterHeight/fuelMeterInitialHeight,0,1);
		if (fuelMeterHeight > fuelMeterInitialHeight / 2)
			shapeRenderer.setColor(Color.GREEN);
		else if (fuelMeterHeight > fuelMeterInitialHeight / 5)
			shapeRenderer.setColor(Color.ORANGE);
		else
			shapeRenderer.setColor(Color.RED);
		
		shapeRenderer.rect(fuelBar.getX() + 10, fuelBar.getY() + 42, fuelBar.getWidth() - 25, fuelMeterHeight);
        shapeRenderer.end();
        batch.begin();
		
		fuelBar.draw(batch);

		for (int i = 0; i < birdsArray.size(); i++) {
			Bird bird = birdsArray.get(i);
			if (!pause)
				bird.update(delta);
			bird.draw(batch, stateTime);

			if (!bird.isBurned()) {
				boolean burnable = bird.checkBurnable(
						(int)drillBot.getPosition().x,
						(int)drillTop.getPosition().y,
						(int)(drillBot.getPosition().y + drillBotTextureRegion[0].getRegionHeight()));

				if (firePointer != -1 && burnable && !isGameOver && fuelMeterHeight > 0) {
					if (Score.isSoundenabled()) {
						switch (bird.getPoints()) {
							case 10:
								orangeBirdSound.play();
								break;
							case 15:
								blueBirdSound.play();
								break;
							case 20:
								yellowBirdSound.play();
								break;
						}
					}
					bird.burn();
					bird.setRotation(5);
					bird.setVelocity(new Vector2(birdsArray.get(i).getVelocity().x, 5));
					bird.setStateTime(0f);
					bird.setAnimation(burnedBirdAnimation);

					if (highScore - lastUpdatePoints  > 200) {
						lastUpdatePoints = highScore;
						bm.changeBackground();
						if (Score.isSoundenabled())
							levelChangeSound.play();
						bornTime -= 50;
					}

				} else if (bird.hasEscaped()) {
					bird.setVelocity(new Vector2(3000, 0));
					if (bird.getPosition().x > SplashScreen.VIRTUAL_WIDTH && !isGameOver) {
						isGameOver = true;
						gameOverBird.setVelocityY(GAME_OVER_BIRD_VELOCITY_Y);
					}
				}
			} else { //if burned
				if (bird.getAnimation().isAnimationFinished(bird.getStateTime())) {
					if (!bird.wasStar()) {
						bird.star();
						bird.setStateTime(0f);
						bird.setAnimation(starBirdAnimation);
					}
					//
				}
				
				if (bird.wasStar() && bird.isLegs()){
					bird.setVelocityY(bird.getVelocity().y - 20);
				}

				if (bird.getPosition().y <= chickenLegsTextureRegion[0].getRegionHeight()) {
					if (!bird.isLegs() && Score.isSoundenabled()){
						smashBirdSound.play();
					}
					
					bird.star();
					bird.legs();
					bird.setStateTime(0f);
					bird.setAnimation(chickenLegsAnimation);
					bird.setPosition(new Vector2(bird.getPosition().x, chickenLegsTextureRegion[0].getRegionHeight() + 10));
					bird.setVelocityY(500f);
				}
				
				if (bird.getPosition().x >= SplashScreen.VIRTUAL_WIDTH - chickenLegsTextureRegion[0].getRegionHeight()
						&& bird.isLegs()) {
					highScore += birdsArray.get(i).getPoints();
					
					if(BurningBirdsMain.googleServices.isSignedIn()) {
						if (highScore >= 100)
							BurningBirdsMain.googleServices.unlockAchievementGPGS("CgkIub6F35QWEAIQBA");
						if (highScore >= 300)
							BurningBirdsMain.googleServices.unlockAchievementGPGS("CgkIub6F35QWEAIQBQ");
						if (highScore >= 600)
							BurningBirdsMain.googleServices.unlockAchievementGPGS("CgkIub6F35QWEAIQBg");
						if (highScore >= 900)
							BurningBirdsMain.googleServices.unlockAchievementGPGS("CgkIub6F35QWEAIQBw");
						if (highScore >= 1500)
							BurningBirdsMain.googleServices.unlockAchievementGPGS("CgkIub6F35QWEAIQCA");
					}
					
					birdsArray.remove(i);
				}
			}
		}

		boxChickenLegs.draw(batch);
		
		int w = particleRegion.getRegionWidth();
		int h = particleRegion.getRegionHeight();

		batch.draw(particleRegion,
				0.0f, 0.0f,
				0.0f, 0.0f,
				w, h,
				(float)SplashScreen.VIRTUAL_WIDTH / width, (float) SplashScreen.VIRTUAL_HEIGHT / height,
				0.0f);

		if (isGameOver) {
			updateGameOver(delta);
		}

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;

		setSmallFire();
		
		stretchViewport.update(width, height);
		camera.position.set(camera.viewportWidth / 2,
				camera.viewportHeight / 2, 0);
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
		birdsAtlas.dispose();
		buttonsAtlas.dispose();
		fireEffectBotOne.dispose();
		cloud1.getTexture().dispose();
		cloud2.getTexture().dispose();
		cloud3.getTexture().dispose();
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

	private Vector3 drillWorldCoordinates = new Vector3();
	private Vector3 buttonsWorldCoordinates = new Vector3();

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		convertToWorldCoordinates(screenX, screenY, buttonsWorldCoordinates);
		
		//if touch is on the left half of the screen -> move drill
		if (screenX < width / 2) {
			convertToWorldCoordinates(screenX, screenY, drillWorldCoordinates);
			drillPointer = pointer;
		}

		//if touch is on the right half of the screen -> activate drill
		if (screenX > width / 2) {
			firePointer = pointer;
			setLargeFire();
			if (Score.isSoundenabled()) {
				fireMusic.play();
			}
		}
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (pointer == firePointer) {
 			firePointer = -1;
			setSmallFire();
 			fireMusic.pause();
 		} else if (pointer == drillPointer){
 			drillPointer  = -1;
 		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (screenX < width / 2) {
			convertToWorldCoordinates(screenX, screenY, drillWorldCoordinates);
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		drillPointer  = 0;
		convertToWorldCoordinates(screenX, screenY, drillWorldCoordinates);

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	@Override
	public void input(String text) {
		this.username = text;
		
		if (!BurningBirdsMain.googleServices.isSignedIn()) {
			BurningBirdsMain.googleServices.signIn();
		}
	}

	@Override
	public void canceled() {
		username = "anonymous";
	}

	private int countSound = 0;
	private boolean isHighScore = false;
	private String username;

	private void updateGameOver(float delta) {
		gameOverSprite.draw(batch);

		if (countSound == 0) {
			countSound++;
			if (Score.isSoundenabled())
				gameOverMusic.play();
		}
		
		if (!gameOverMusic.isPlaying() && countSound == 1) {
			countSound++;

			if (Score.isSoundenabled())
				gameOverBirdSound.play();
		}

		if (!gameOverMusic.isPlaying()) {
			gameOverBird.updateLaughBird(delta);
			gameOverBird.draw(batch, stateTime);
		}
		
		
		createHighscore();
		if (highScore > Score.highScores[4] && countSound == 2 && !gameOverBirdSound.isPlaying()) {
			Gdx.input.getTextInput(this, "New Highscore", "", "Enter your name");
			isHighScore = true;
			countSound++;
		}
		
		if (highscoreSpriteArray != null) {
			for (int i = 0; i < highscoreSpriteArray.length; i++) {
				highscoreSpriteArray[i].draw(batch);
			}
		}
		
		if (countSound == 3) {
			if (isHighScore && username != null) {
				isHighScore = false;
				Score.addScore(username, highScore);
				Score.saveHighScores();
				countSound++;
			}
		}

		buttonShare.draw(batch);
		//buttonGPlusShare.draw(batch);
		buttonPlayServices.draw(batch);
		buttonReload.draw(batch);
		
		if (isButtonTouched(buttonShare)) {
			String screenshotPath = ScreenshotFactory.saveScreenshot();

			if (actionResolver != null) {
				Gdx.app.log("not null", "not null");
				actionResolver.showFacebookIntent(screenshotPath);
			}
		} else if (isButtonTouched(buttonGPlusShare)) {
			Gdx.app.log("gplusshare", "gplusshare");
		} else if (isButtonTouched(buttonReload)) {
			game.setScreen(new GameScreen(this.game, actionResolver));
		} else if (isButtonTouched(buttonPlayServices)) {
			if (!BurningBirdsMain.googleServices.isSignedIn()) {
				BurningBirdsMain.googleServices.signIn();
			} else {
				BurningBirdsMain.googleServices.submitScore(highScore);
				BurningBirdsMain.googleServices.showScores();
			}
		}
	}
	
	private void updateDrill() {
		if (drillWorldCoordinates.y > buttonDown.getY() + buttonDown.getHeight() /*&& worldCoordinates.x < SplashScreen.VIRTUAL_WIDTH / 2*/) {
			drillTop.updateTop(true);
			drillBot.updateBot(true);
		} 
		
		if (drillWorldCoordinates.y < buttonUp.getY() /*&& worldCoordinates.x < SplashScreen.VIRTUAL_WIDTH / 2*/) {
			drillTop.updateTop(false);
			drillBot.updateBot(false);
		}

		fireEffectBotOne.setPosition((int) drillBot.getPosition().x + 20,
				drillBot.getPosition().y + drillBotTextureRegion[0].getRegionHeight() - 55);
		fireEffectBotTwo.setPosition((int) drillBot.getPosition().x + 65,
				drillBot.getPosition().y + drillBotTextureRegion[0].getRegionHeight() - 55);
		fireEffectTopOne.setPosition((int) drillTop.getPosition().x + 20,
				(int) drillTop.getPosition().y + 55);
		fireEffectTopTwo.setPosition((int) drillTop.getPosition().x + 65,
				(int) drillTop.getPosition().y + 55);
	}

	private Vector2 setBirdPosition() {
		Vector2 position = new Vector2();
		position.x = -blueBirdTextureRegion[0].getRegionWidth() - 10;
		position.y = rand.nextInt(SplashScreen.VIRTUAL_HEIGHT - 260) + 130;
		
		return position;
	}

	private void addBirdToArray() {
		if (System.currentTimeMillis() - currentTime > bornTime && !pause) {
			currentTime = System.currentTimeMillis();
			int birdModel = rand.nextInt(3);
			switch (birdModel) {
				case 0:
					birdsArray.add(new Bird(setBirdPosition(), new Vector2(
							Bird.RED_BIRD_SPEED_X_VELOCITY, Bird.BIRD_Y_VELOCITY),
							null, redBirdAnimation, 10));
					break;
				case 1:
					birdsArray.add(new Bird(setBirdPosition(), new Vector2(
							Bird.BLUE_BIRD_SPEED_X_VELOCITY, Bird.BIRD_Y_VELOCITY),
							null, blueBirdAnimation, 15));
					break;
				case 2:
					birdsArray.add(new Bird(setBirdPosition(),
							new Vector2(Bird.YELLOW_BIRD_SPEED_X_VELOCITY,
									Bird.BIRD_Y_VELOCITY), null,
							yellowBirdAnimation, 20));
					break;

				default:
					break;
			}
		}
	}
	
	private void setSmallFire() {
		fireEffectTopOne.findEmitter("Untitled").duration = 0;
		fireEffectTopOne.getEmitters().get(0).getEmission().setHighMax(80);
		fireEffectTopOne.getEmitters().get(0).getEmission().setHighMin(80);
		fireEffectTopOne.getEmitters().get(0).getLife().setHighMax(100);
		fireEffectTopOne.getEmitters().get(0).getLife().setHighMin(100);
		
		fireEffectTopTwo.findEmitter("Untitled").duration = 0;
		fireEffectTopTwo.getEmitters().get(0).getEmission().setHighMax(80);
		fireEffectTopTwo.getEmitters().get(0).getEmission().setHighMin(80);
		fireEffectTopTwo.getEmitters().get(0).getLife().setHighMax(100);
		fireEffectTopTwo.getEmitters().get(0).getLife().setHighMin(100);
		
		fireEffectBotOne.findEmitter("Untitled").duration = 0;
		fireEffectBotOne.getEmitters().get(0).getEmission().setHighMax(80);
		fireEffectBotOne.getEmitters().get(0).getEmission().setHighMin(80);
		fireEffectBotOne.getEmitters().get(0).getLife().setHighMax(100);
		fireEffectBotOne.getEmitters().get(0).getLife().setHighMin(100);
		
		fireEffectBotTwo.findEmitter("Untitled").duration = 0;
		fireEffectBotTwo.getEmitters().get(0).getEmission().setHighMax(80);
		fireEffectBotTwo.getEmitters().get(0).getEmission().setHighMin(80);
		fireEffectBotTwo.getEmitters().get(0).getLife().setHighMax(100);
		fireEffectBotTwo.getEmitters().get(0).getLife().setHighMin(100);
	}
	
	private void setLargeFire() {
		fireEffectTopOne.findEmitter("Untitled").duration = 0;
		fireEffectTopOne.getEmitters().get(0).getEmission().setHighMax(250);
		fireEffectTopOne.getEmitters().get(0).getEmission().setHighMin(250);
		fireEffectTopOne.getEmitters().get(0).getLife().setHighMax(700);
		fireEffectTopOne.getEmitters().get(0).getLife().setHighMin(500);
		
		fireEffectTopTwo.findEmitter("Untitled").duration = 0;
		fireEffectTopTwo.getEmitters().get(0).getEmission().setHighMax(250);
		fireEffectTopTwo.getEmitters().get(0).getEmission().setHighMin(250);
		fireEffectTopTwo.getEmitters().get(0).getLife().setHighMax(700);
		fireEffectTopTwo.getEmitters().get(0).getLife().setHighMin(500);
		
		
		fireEffectBotOne.findEmitter("Untitled").duration = 0;
		fireEffectBotOne.getEmitters().get(0).getEmission().setHighMax(250);
		fireEffectBotOne.getEmitters().get(0).getEmission().setHighMin(250);
		fireEffectBotOne.getEmitters().get(0).getLife().setHighMax(700);
		fireEffectBotOne.getEmitters().get(0).getLife().setHighMin(500);
		
		fireEffectBotTwo.findEmitter("Untitled").duration = 0;
		fireEffectBotTwo.getEmitters().get(0).getEmission().setHighMax(250);
		fireEffectBotTwo.getEmitters().get(0).getEmission().setHighMin(250);
		fireEffectBotTwo.getEmitters().get(0).getLife().setHighMax(700);
		fireEffectBotTwo.getEmitters().get(0).getLife().setHighMin(500);
	}

	private void convertToWorldCoordinates(int screenX, int screenY, Vector3 convert) {
		convert.x = screenX;
		convert.y  = screenY;
		camera.unproject(convert);
	}
	
	private boolean isButtonTouched(Sprite button) {
		boolean isPressed = false;
		
		if ((int) buttonsWorldCoordinates.x > (int) button.getX() && (int) buttonsWorldCoordinates.x < (button.getX() + button.getRegionWidth())
				&& (int) buttonsWorldCoordinates.y > (int) button.getY()
				&& (int) buttonsWorldCoordinates.y < (int) (button.getY() + button.getRegionHeight())) {
			isPressed = true;
			buttonsWorldCoordinates.x = 0;
			buttonsWorldCoordinates.y = 0;
		}
		
		return isPressed;
	}
	
	private Sprite[] highscoreSpriteArray;
	private void createHighscore() {
		String scoreString = Integer.toString(highScore);
		highscoreSpriteArray = new Sprite[scoreString.length()];
		
		for (int i = 0; i < scoreString.length(); i++) {
			switch (scoreString.charAt(i)) {
				case '0':
					highscoreSpriteArray[i] = new Sprite(numbersSprite[0]);
					break;
				case '1':
					highscoreSpriteArray[i] = new Sprite(numbersSprite[1]);
					break;
				case '2':
					highscoreSpriteArray[i] = new Sprite(numbersSprite[2]);
					break;
				case '3':
					highscoreSpriteArray[i] = new Sprite(numbersSprite[3]);
					break;
				case '4':
					highscoreSpriteArray[i] = new Sprite(numbersSprite[4]);
					break;
				case '5':
					highscoreSpriteArray[i] = new Sprite(numbersSprite[5]);
					break;
				case '6':
					highscoreSpriteArray[i] = new Sprite(numbersSprite[6]);
					break;
				case '7':
					highscoreSpriteArray[i] = new Sprite(numbersSprite[7]);
					break;
				case '8':
					highscoreSpriteArray[i] = new Sprite(numbersSprite[8]);
					break;
				case '9':
					highscoreSpriteArray[i] = new Sprite(numbersSprite[9]);
					break;
				default:
					break;
			}
		}
		
		float posX = gameOverSprite.getX() + gameOverSprite.getWidth() / 2;
		for(int i = 0; i < highscoreSpriteArray.length; i++) {
			highscoreSpriteArray[i].setPosition(posX - highscoreSpriteArray[i].getRegionWidth(), gameOverSprite.getY() - highscoreSpriteArray[i].getRegionHeight() * 1.5f);
			posX = posX + highscoreSpriteArray[i].getRegionWidth();
		}
			
	}

}