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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import tk.tapet.android.game.birds.BurningBirdsMain;
import tk.tapet.android.game.birds.utils.Score;

public class ScoreScreen implements Screen {

	private Stage stage = null;
	private ImageButton gPlayButton = null;
	private Table table = null;
	public  Image highScoreBackground= null;
	private ImageButton buttonBack = null;
	private Skin buttonSkin = null;
	private Skin skin = null;
	private TextureAtlas buttonsAtlas = new TextureAtlas(Gdx.files.internal("animations/button_pack.pack"));
	
	private BurningBirdsMain game = null;
	
	public ScoreScreen(BurningBirdsMain game) {
		this.game = game;
	}

	@Override
	public void show() {
		Score.loadHighScores();
		
		stage = new Stage(new StretchViewport(SplashScreen.VIRTUAL_WIDTH, SplashScreen.VIRTUAL_HEIGHT));
		stage.getViewport().apply();
		
		highScoreBackground = new Image(new Texture("img/scores_background.jpg"));
		highScoreBackground.setSize(SplashScreen.VIRTUAL_WIDTH, SplashScreen.VIRTUAL_HEIGHT);
		
		buttonSkin = new Skin(buttonsAtlas);
		skin = new Skin(Gdx.files.internal("data/menu_skin.json"));
		
		gPlayButton = new ImageButton(buttonSkin.getDrawable("gplay_games_controller"));
		gPlayButton.setPosition(SplashScreen.VIRTUAL_WIDTH - 120, SplashScreen.VIRTUAL_HEIGHT - 100);
		gPlayButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!BurningBirdsMain.googleServices.isSignedIn()) {
					BurningBirdsMain.googleServices.signIn();
				} else {
					BurningBirdsMain.googleServices.showScores();
				}
			}
		});
		
		buttonBack = new ImageButton(buttonSkin.getDrawable("buttonback"));
		buttonBack.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.moveBy(stage.getWidth(), 0, .5f), Actions.run(new Runnable() {
					@Override
					public void run() {
//						((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game, null));
						game.setScreen(new MainMenuScreen(game, null));
					}
				})));
			}
		});
		
		table = new Table();
		table.setFillParent(true);
		table.row();
		for (int i = 0; i < 5; i++) {
			if (Score.highScores[i] > 0) {
				Label nameLabel = new Label((i + 1)+ ". " +
						Score.highScoresNames[i] + " - " + Score.highScores[i], skin);
				table.add(nameLabel).align(Align.left);
				table.row();
			}
		}
		
		table.row();
		table.add(buttonBack).align(Align.bottom);
		
		stage.addActor(highScoreBackground);
		stage.addActor(table);
		stage.addActor(gPlayButton);
		stage.addAction(Actions.sequence(
				Actions.moveTo(-Gdx.graphics.getWidth(), 0),
				Actions.moveTo(0, 0, .5f)));
		
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
		game.batcher.begin();
		game.batcher.end();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
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
	}

}
