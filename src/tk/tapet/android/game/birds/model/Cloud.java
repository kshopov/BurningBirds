package tk.tapet.android.game.birds.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cloud extends Sprite {
	
	private float velocityX = 20.f;
	private float velocityY = 0;
	
	public Cloud(Texture cloud) {
		super(cloud);
	}
	
	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}
	
	public float getVelocityX() {
		return velocityX;
	}
	
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}
	
	public float getVelocityY() {
		return velocityY;
	}
	
	@Override
	public void draw(Batch batch) {
		super.draw(batch);
	}
	
	public void update(float deltaTime, SpriteBatch batcher) {
		setX(getX() + velocityX * deltaTime);
		setY(getY() + velocityY * deltaTime);
	}

}
