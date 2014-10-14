package walnoot.arcade;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;

public class WorldRenderer {
	private final Vector2 tmp = new Vector2();
	
	private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	
	private OrthographicCamera camera = new OrthographicCamera();
	
	public void render(GameWorld world) {
		PlayerEntity player = world.getPlayer();
		
		camera.position.set(player.body.getPosition(), 0f);
		camera.update();
		
		debugRenderer.render(world.getWorld(), camera.combined);
		
		float x = camera.position.x;
		float y = camera.position.y;
		float width = camera.viewportWidth * camera.zoom / 2;
		float height = camera.viewportHeight * camera.zoom / 2;
		
		Array<Entity> entities = world.getEntities(x - width, y - height, x + width, y + height);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		
		shapeRenderer.identity();
		
		shapeRenderer.setColor(Color.DARK_GRAY);
		for (int i = (int) Math.floor(x - width); i < Math.floor(x + width) + 1f; i++) {
			if (i % 4 == 0) {
				for (int j = (int) Math.floor(y - height); j < Math.floor(y + height) + 1f; j++) {
					if (j % 4 == 0) shapeRenderer.box(i, j, 0f, 0.1f, 0.1f, 0f);
				}
			}
		}
		
		shapeRenderer.setColor(Color.WHITE);
		
		//		Vector2 intersect = PlayerEntity.intersectPoint;
		//		if (intersect != null) shapeRenderer.circle(intersect.x, intersect.y, 1f, 10);
		
		//		shapeRenderer.circle(0f, 0f, GameWorld.RADIUS);
		
		for (Entity e : entities) {
			shapeRenderer.identity();
			
			if (e instanceof EnemyEntity) {
				Vector2 targetPos = ((EnemyEntity) e).getTargetPos();
				if (targetPos != null) shapeRenderer.circle(targetPos.x, targetPos.y, 0.5f);
			}
			
			shapeRenderer.translate(e.getX(), e.getY(), 0f);
			
			if (e instanceof EnemyEntity) {
				float targetAngle = ((EnemyEntity) e).getTargetAngle();
				tmp.set(0f, 1f).rotateRad(-targetAngle);
				shapeRenderer.line(0f, 0f, tmp.x, tmp.y);
			}
			
			shapeRenderer.rotate(0f, 0f, 1f, e.getRotation() * MathUtils.radiansToDegrees);
			
			if (e instanceof EngineEntity) {
				if (((EngineEntity) e).moveRight) shapeRenderer.triangle(-1f, -1f, -0.75f,
						-1.5f * MathUtils.random(1.0f, 1.3f), -0.5f, -1f);
				if (((EngineEntity) e).moveLeft) shapeRenderer.triangle(1f, -1f, 0.75f,
						-1.5f * MathUtils.random(1.0f, 1.3f), 0.5f, -1f);
			}
			
			if (e instanceof PlayerEntity) {
				if (player.isFiring()) {
					Vector2 point = e.body.getLocalPoint(player.getIntersectPoint());
					shapeRenderer.line(0f, 1f, point.x, point.y);
				}
			}
		}
		shapeRenderer.end();
	}
	
	public void resize(int width, int height) {
		camera.viewportWidth = 2f * width / height;
		camera.viewportHeight = 2f;
		camera.zoom = 16f;
	}
}
