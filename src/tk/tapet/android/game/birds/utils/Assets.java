package tk.tapet.android.game.birds.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Assets {
	
	public static Music gameMusic = null;
	public static Sound buttonsSound = null;
	
	
	public static Image loadTexutre(String file) {
		return new Image(new Texture(Gdx.files.internal(file)));
	}
	
	public static void load() {
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/muzika.wav"));
		gameMusic.setLooping(true);
		buttonsSound = Gdx.audio.newSound(Gdx.files.internal("music/buttons_sound.wav"));
	}
	
}
