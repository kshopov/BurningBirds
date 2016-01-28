package tk.tapet.android.game.birds.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class BaseModel {
	
	protected Animation animation = null;

	protected Vector2 position;
	protected Vector2 velocity;
	protected Vector2 accel;
	
	protected int rotation;
	
	protected boolean isPlaying = true;
	
	protected BaseModel(Vector2 position, Vector2 velocity, Vector2 accel, Animation animation) {
		this.position = position;
		this.velocity = velocity;
		this.accel = accel;
		this.animation = animation;
	}
	
	public Animation getAnimation() {
		return animation;
	}
	
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public Vector2 getPosition() {
		return position;
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public void setVelocityX(float velocityX) {
		velocity.x = velocityX;
	}

	public void setVelocityY(float velocityY) {
		velocity.y = velocityY;
	}

	public Vector2 getAccel() {
		return accel;
	}
	
	public void setIsPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	
	public boolean getIsPlaying() {
		return isPlaying;
	}
	
	public int getRotation() {
		return rotation;
	}
	
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
}
