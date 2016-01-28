package tk.tapet.android.game.birds.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Score {
	
	public static String[] highScoresNames = new String[5];
	public static int[] highScores = new int[5];
	
	private final static String highScoresFile = "highscoresfile";
	private final static String soundEnabledFile = "soundenabledfile";
	public static final String PLAYER_KEY = "player";
	public static final String SCORE_KEY = "score";
	
	
	private static final String SOUND_ENABLED = "soundEnabled";
	
	public static boolean isSoundenabled() {
		boolean soundEnabled = true;
		
		Preferences soundPrefs = Gdx.app.getPreferences(soundEnabledFile);
		if (soundPrefs.contains(SOUND_ENABLED)) {
			soundEnabled = soundPrefs.getBoolean(SOUND_ENABLED);
		}
		
		return soundEnabled;
	}
	
	public static void saveSoundEnabled(boolean isSoundEnabled) {
		Preferences soundPrefs = Gdx.app.getPreferences(soundEnabledFile);
		soundPrefs.putBoolean(SOUND_ENABLED, isSoundEnabled);
		soundPrefs.flush();
	}

	public static void saveHighScores () {
		Preferences highScorePrefs = Gdx.app.getPreferences(highScoresFile);

		for (int i = 0; i < 5; i++) {
			highScorePrefs.putString(PLAYER_KEY + i, "" + highScoresNames[i]);
			highScorePrefs.putInteger(SCORE_KEY + i, highScores[i]);
		}
		
		highScorePrefs.flush();
	}
	
	public static void loadHighScores () {
		Preferences highScorePrefs = Gdx.app.getPreferences(highScoresFile);
		
		for (int i = 0; i < 5; i++) {
			highScoresNames[i] = highScorePrefs.getString(PLAYER_KEY + i);
			highScores[i] = highScorePrefs.getInteger(SCORE_KEY + i);
		}
	}

	public static void addScore (String name, int score) {
		for (int i = 0; i < 5; i++) {
			if (highScores[i] < score) {
				for (int j = 4; j > i; j--) {
					highScores[j] = highScores[j - 1];
					highScoresNames[j] = highScoresNames[j-1];
				}
				highScores[i] = score;
				highScoresNames[i] = name;
				
				break;
			}
		}
	}
}