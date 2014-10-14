package walnoot.arcade;

import com.badlogic.gdx.math.Vector2;

public abstract class EngineEntity extends Entity {
	public static final float FORCE = 5f;
	
	private final Vector2 tmp1 = new Vector2();
	private final Vector2 tmp2 = new Vector2();
	
	public boolean moveLeft, moveRight;
	
	public void update() {
		if (moveLeft) applyThrustForce(1f);
		if (moveRight) applyThrustForce(-1f);
		
		moveLeft = false;
		moveRight = false;
	}
	
	public void applyThrustForce(float x) {
		Vector2 point = body.getWorldPoint(tmp1.set(x, 0f));
		
		Vector2 force = tmp2.set(0f, FORCE).rotateRad(body.getAngle());
		body.applyForce(force, point, true);
	}
}
