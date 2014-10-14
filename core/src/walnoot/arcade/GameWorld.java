package walnoot.arcade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class GameWorld implements QueryCallback {
	public static final float RADIUS = 80f;
	
	private World world = new World(new Vector2(), true);
	private Array<Body> tempBodies = new Array<Body>();//temp array to iterate over bodies
	
	private final GameInput input = new GameInput();
	
	private Array<Entity> queryArray = new Array<Entity>();
	
	private PlayerEntity player;
	
	public GameWorld() {
		Gdx.input.setInputProcessor(input);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		Body body = world.createBody(bodyDef);
		ChainShape border = new ChainShape();
		
		float[] vertices = new float[2 * 50];
		
		for (int i = 0; i < 50; i++) {
			float alpha = (i / 50f) * MathUtils.PI2;
			vertices[i * 2] = (float) (Math.cos(alpha) * RADIUS);
			vertices[i * 2 + 1] = (float) (Math.sin(alpha) * RADIUS);
		}
		
		border.createLoop(vertices);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = border;
		fixtureDef.restitution = .5f;
		body.createFixture(fixtureDef);
	}
	
	public void update() {
		world.getBodies(tempBodies);
		for (Body body : tempBodies) {
			Entity e = (Entity) body.getUserData();
			if (e != null) e.update();
		}
		
		world.step(Time.DELTA, 8, 3);
	}
	
	public void addEntity(Entity e) {
		addEntity(e, 0f, 0f);
	}
	
	public void addEntity(Entity e, float x, float y) {
		e.world = this;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DynamicBody;
		Body body = world.createBody(bodyDef);
		body.setUserData(e);
		e.body = body;
		
		Shape shape = e.createShape();
		Fixture fixture = body.createFixture(shape, 1f);
		fixture.setUserData(e);
		
		shape.dispose();
		
		if (e instanceof PlayerEntity) player = (PlayerEntity) e;
	}
	
	public Array<Entity> getEntities(float lowerX, float lowerY, float upperX, float upperY) {
		queryArray.clear();
		world.QueryAABB(this, lowerX, lowerY, upperX, upperY);
		
		return queryArray;
	}
	
	@Override
	public boolean reportFixture(Fixture fixture) {
		Entity entity = (Entity) fixture.getUserData();
		if (entity != null && !queryArray.contains(entity, true)) {
			queryArray.add(entity);
		}
		
		return true;
	}
	
	public World getWorld() {
		return world;
	}
	
	public GameInput getInput() {
		return input;
	}
	
	public PlayerEntity getPlayer() {
		return player;
	}
	
	public void dispose() {
		world.dispose();
		Gdx.input.setInputProcessor(null);
	}
}
