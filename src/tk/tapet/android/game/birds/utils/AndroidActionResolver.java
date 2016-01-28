package tk.tapet.android.game.birds.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;


public class AndroidActionResolver implements ActionResolver {

    private Handler uiThread;
    private Context context;

    public AndroidActionResolver(Context context) {
        uiThread = new Handler();
        this.context = context;
    }

    @Override
    public void showFacebookIntent(final String screenshotPath) {
        uiThread.post(new Runnable() {
            @Override
            public void run() {
            	Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            	Uri screenshotUri = Uri.parse(screenshotPath);
            	shareIntent.setType("*/*");
            	shareIntent.putExtra(Intent.EXTRA_TEXT, "some text");
            	shareIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                context.startActivity(Intent.createChooser(shareIntent, "BurningBirds"));
            }
        });
    }
}
