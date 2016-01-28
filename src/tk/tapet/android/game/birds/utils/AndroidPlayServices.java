package tk.tapet.android.game.birds.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import tk.tapet.android.game.birds.R;
import tk.tapet.android.game.birds.R.string;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.games.Games;

public class AndroidPlayServices implements IPlayServices {
	
	public static final int MAX_SIGN_IN_ATTEMPTS = 0;
	
	private final static int REQUEST_CODE_UNUSED = 9002;
	
	private Activity activity = null;
	private GameHelper gameHelper = null;
	
	public AndroidPlayServices(Activity activity, GameHelper gameHelper) {
		this.activity = activity;
		this.gameHelper = gameHelper;
	}

    @Override
    public void signIn() {
    	try {
	    	activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
    	} catch (Exception e) {
    		Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
    	}
    }

    @Override
    public void signOut() {
    	try {
    		activity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					gameHelper.signOut();
				}
			});
    	} catch (Exception e) {
    		Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
    	}
    }

    @Override
    public void rateGame() {
    	String str ="https://play.google.com/store/apps/details?id=tk.tapet.android.game.birds";
    	activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    }

    @Override
    public void submitScore(long score) {
    	if (isSignedIn()) {
    		Games.Leaderboards.submitScore(gameHelper.getApiClient(), activity.getString(R.string.leaderboard_id), score);
    		activity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), activity.getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
    	}
    }

    @Override
    public void showScores() {
    	if (isSignedIn() == true){
    		activity.startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), activity.getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
    	}
    }

    @Override
    public boolean isSignedIn() {
    	return gameHelper.isSignedIn();
    }

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
	}

	@Override
	public void getLeaderboardGPGS() {
		
	}

	@Override
	public void getAchievementsGPGS() {
		
	}
}
