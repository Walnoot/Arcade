package walnoot.arcade;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class EnemyEntity extends EngineEntity {
	private static final float MAX_SPEED = 15f;
	
	private final Vector2 tmp1 = new Vector2();
	private final Vector2 tmp2 = new Vector2();
	private final Vector2 tmp3 = new Vector2();
	
	private PlayerEntity player;
	private Vector2 targetPos = new Vector2();
	private float targetAngle;
	
	public EnemyEntity(PlayerEntity player) {
		this.player = player;
	}
	
	@Override
	public void update() {
		super.update();
		
		Vector2 dir = tmp2.set(player.body.getPosition()).sub(body.getPosition()).nor();
		
		float timeToHit = body.getPosition().dst(player.body.getPosition()) / body.getLinearVelocity().dot(dir);
		
		//		System.out.println(timeToHit);
		//		System.out.println(body.getLinearVelocity().len());
		
		timeToHit = MathUtils.clamp(timeToHit, 0f, 2f);
		
		//target where the player will be
		targetPos.set(player.body.getPosition()).add(tmp3.set(player.body.getLinearVelocity()).scl(timeToHit));
		
		targetAngle = tmp1.set(targetPos).sub(body.getPosition()).sub(body.getLinearVelocity()).rotate90(1).angleRad();
		
		targetAngle = Util.wrap(targetAngle - body.getAngle(), MathUtils.PI2);
		
		if (Util.epsilonEquals(targetAngle, MathUtils.PI, 0.2f)
				&& body.getLinearVelocity().len2() < MAX_SPEED * MAX_SPEED) {
			moveLeft = true;
			moveRight = true;
		}
		
		float targetAngularVelocity = (targetAngle - MathUtils.PI) * 3f;
		
		if (body.getAngularVelocity() > targetAngularVelocity) moveRight = true;
		else moveLeft = true;
	}
	
	public Vector2 getTargetPos() {
		return targetPos;
	}
	
	public float getTargetAngle() {
		return targetAngle;
	}
}
