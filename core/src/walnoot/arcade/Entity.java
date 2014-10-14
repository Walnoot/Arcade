package walnoot.arcade;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public abstract class Entity {
	public Body body;
	public GameWorld world;
	
	public abstract void update();
	
	public Shape createShape() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(.8f, 1f);
		
		return shape;
	}
	
	public float getX() {
		return body.getPosition().x;
	}
	
	public float getY() {
		return body.getPosition().y;
	}
	
	public float getRotation() {
		return body.getAngle();
	}
}
