package tk.tapet.android.game.birds.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ActorTween extends Image {
	
	private float velocityX = 0;
	private float velocityY = 0;
	
	private boolean isFinished = false;
	
	public ActorTween(Texture texture) {
		super(texture);
	}
	
	public float getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}

	public float getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}
	
	public boolean isAnimationFinished() {
		return isFinished;
	}
	
	public void setIsFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public void update(float deltaTime) {
		setX(getX() + velocityX * deltaTime);
		setY(getY() + velocityY * deltaTime);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
}
