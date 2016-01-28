package tk.tapet.android.game.birds.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Drill extends BaseModel {
	
	public Drill(Vector2 position, Vector2 velocity, Vector2 accel,
			Animation animation) {
		super(position, velocity, accel, animation);
	}
	
	public void draw(SpriteBatch batch, float stateTime) {
		if (isPlaying) {
			batch.draw(animation.getKeyFrame(stateTime), getPosition().x, getPosition().y);
		}
	}
	
	public void updateTop(boolean isDraggedTop) {
		if (isDraggedTop && position.y < 640) {
			position.y += velocity.y;
		} else if (position.y > 380  ) {
			position.y -= velocity.y;
		}
	}
	
	public void updateBot(boolean isDraggedTop) {
		if (isDraggedTop && position.y < -570) {
			position.y += velocity.y;
		} else if (position.y > -850) {
			position.y -= velocity.y;
		}
	}
}
