package tk.tapet.android.game.birds.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bird extends BaseModel {
	
	public static final int BIRD_Y_VELOCITY = 70;
	
	public static final int INITIAL_BORN_TIME = 2000; // bird born time in ms

	public static final int BLUE_BIRD_SPEED_X_VELOCITY = 300;

	public static final int YELLOW_BIRD_SPEED_X_VELOCITY = 250;

	public static final int RED_BIRD_SPEED_X_VELOCITY = 200;
	
	public static final int BIRD_ROTATION = 1;
	
	private int points = 0;
	private float lastY = 0f;
	private float lastX = 0f;
	private int accel = 0;
	private float stateTime = 0;
	
	private boolean isBurned = false;
	private boolean wasStar = false;
	private boolean hasEscaped = false;
	private boolean isLegs = false;
	
	public Bird(Vector2 position, Vector2 velocity, Vector2 accel, Animation animation, int points) {
		super(position, velocity, accel, animation);
		this.points = points;
		this.wasStar = false;
		this.lastY = position.y;
		this.lastX = position.x;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public boolean isBurned() {
		return isBurned;
	}
	
	public void draw(SpriteBatch batch, float stateTime) {
		if(!isBurned) {
			batch.draw(animation.getKeyFrame(stateTime),
				getPosition().x, getPosition().y);
		} else {
			batch.draw(animation.getKeyFrame(stateTime),
					getPosition().x, getPosition().y,
					0, 0,
					animation.getKeyFrame(stateTime).getRegionWidth(),
					animation.getKeyFrame(stateTime).getRegionHeight(),
					1.0f, 1.0f, rotation -= BIRD_ROTATION);
		}
	}
	
	public void update(float deltaTime) {
		stateTime += deltaTime;

		position.x += velocity.x * deltaTime;

		if (isBurned && !isLegs()) {
			accel++;
			position.y -= Math.abs(velocity.y) * accel * deltaTime;
		} else {
			position.y += velocity.y * deltaTime;

			if (position.y - lastY > 100) {
				lastY = position.y;
				velocity.y = -velocity.y;
			} else if (position.y - lastY < -100) {
				lastY = position.y;
				velocity.y = Math.abs(velocity.y);
			}
		}
	}

	public void updateLaughBird(float deltaTime) {
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;

		if ((int) getPosition().y >= -100) {
			setVelocityY(getVelocity().y + 50);
			setVelocityX(getVelocity().x + 100);
		}
	}

	public boolean hasEscaped() {
		return hasEscaped;
	}

	public boolean checkBurnable(int drillX, int drillTopY, int drillBotY) {
		if ((int) position.x - 50 >= drillX || (int) position.x + 60 >= drillX) {
			if (isInRange(drillTopY, drillBotY) && (int) position.x < drillX + 70) {
					return true;
			} else {
				hasEscaped = true;
			} 
		}
			
		return false;
	}
	
	public boolean isInRange(int drillTopY, int drillBotY) {
		if ((int)position.y < drillTopY && (int) position.y > (drillBotY  - 80)) {
			return true;
		}
		
		return false;
	}

	public void star() {
		wasStar = true;
	}

	public boolean wasStar() {
		return wasStar;
	}

	public boolean isLegs() {
		return isLegs;
	}

	public void legs() {
		this.isLegs = true;
	}

	public void burn() {
		accel = 0;
		isBurned = true;
	}

}
