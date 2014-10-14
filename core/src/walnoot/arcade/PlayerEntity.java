package walnoot.arcade;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class PlayerEntity extends EngineEntity implements RayCastCallback {
	private static final float RANGE = 100f;
	
	private final Vector2 tmp1 = new Vector2();
	private final Vector2 tmp2 = new Vector2();
	
	private Vector2 intersectPoint = new Vector2();
	
	private float lowestFraction;
	private boolean firing;
	
	@Override
	public void update() {
		super.update();
		
		moveLeft = world.getInput().moveLeft();
		moveRight = world.getInput().moveRight();
		
		//		boolean previousHitting = isFiring();
		
		firing = false;
		lowestFraction = Float.MAX_VALUE;
		//		body.getWorld().rayCast(this, body.getPosition(),
		//				tmp2.set(0f, RANGE).rotateRad(Util.wrap(getRotation(), MathUtils.PI2)));
		
		Vector2 start = tmp1.set(body.getWorldPoint(tmp1.set(0f, 0f)));
		Vector2 end = tmp2.set(body.getWorldPoint(tmp2.set(0f, RANGE)));
		
		System.out.println(start);
		System.out.println(end);
		System.out.println();
		body.getWorld().rayCast(this, start, end);
		
		//		if (previousHitting != isFiring()) System.out.println("firing : " + isFiring());
		//		if (intersectPoint != null && intersectPoint.dst(body.getPosition()) < 5f) System.out.println("crap");
	}
	
	@Override
	public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
		if (fixture.getUserData() == null) {
			return -1f;
		} else {
			if (fraction < lowestFraction) {
				intersectPoint.set(point);
				firing = true;
				lowestFraction = fraction;
			}
			
			return 1f;
		}
	}
	
	public boolean isFiring() {
		return firing;
	}
	
	public Vector2 getIntersectPoint() {
		return intersectPoint;
	}
}
