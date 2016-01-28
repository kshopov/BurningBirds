package tk.tapet.android.game.birds.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;

import tk.tapet.android.game.birds.utils.ActionResolver;

public class GameOverScreen implements Screen {

	private ActionResolver actionResolver;

	private Sound gameOverAudio = null;

	private Game game = null;
	private int score = 0;
	
	public GameOverScreen(Game game, int highScore, ActionResolver actionResolver) {
		this.score = highScore;
		this.game = game;
		this.actionResolver = actionResolver;
	}

	@Override
	public void show() {
		gameOverAudio = Gdx.audio.newSound(Gdx.files.internal("music/gameover.wav"));
		gameOverAudio.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {
		
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
