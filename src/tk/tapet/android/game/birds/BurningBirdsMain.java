package tk.tapet.android.game.birds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import tk.tapet.android.game.birds.screens.SplashScreen;
import tk.tapet.android.game.birds.utils.ActionResolver;
import tk.tapet.android.game.birds.utils.Assets;
import tk.tapet.android.game.birds.utils.IPlayServices;

public class BurningBirdsMain extends Game {

	public static IPlayServices googleServices = null;

	//used by all screens
	public SpriteBatch batcher = null;

	private ActionResolver actionResolver = null;

	public BurningBirdsMain(ActionResolver actionResolver, IPlayServices playServices) {
		this.actionResolver = actionResolver;
		googleServices = playServices;
	}

	@Override
	public void create () {
		batcher = new SpriteBatch();
		Assets.load();
		setScreen(new SplashScreen(this, actionResolver));
//		setScreen(new GameScreen(this, actionResolver));
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void pause() {
		super.pause();
	}
	
	@Override
	public void resume() {
		super.resume();
	}
}
