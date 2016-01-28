package tk.tapet.android.game.birds;

import android.content.Intent;
import android.os.Bundle;
import tk.tapet.android.game.birds.utils.ActionResolver;
import tk.tapet.android.game.birds.utils.AndroidActionResolver;
import tk.tapet.android.game.birds.utils.AndroidPlayServices;
import tk.tapet.android.game.birds.utils.GameHelper;
import tk.tapet.android.game.birds.utils.GameHelper.GameHelperListener;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

	private ActionResolver actionResolver = null;
	private AndroidPlayServices playServices = null;
	
	private GameHelper gameHelper = null;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);
		
		AndroidGameHelperListener gameHelperListener = new AndroidGameHelperListener();
		
		gameHelper.setMaxAutoSignInAttempts(AndroidPlayServices.MAX_SIGN_IN_ATTEMPTS);
		gameHelper.setup(gameHelperListener);

		actionResolver = new AndroidActionResolver(this);
		
		playServices = new AndroidPlayServices(this, gameHelper);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new BurningBirdsMain(actionResolver, playServices), config);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		gameHelper.onStart(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		gameHelper.onStop();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	private class AndroidGameHelperListener implements GameHelperListener {

		@Override
		public void onSignInFailed() {
			
		}

		@Override
		public void onSignInSucceeded() {
			
		}
		
	}

}
