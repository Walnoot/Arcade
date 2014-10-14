package walnoot.arcade;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class ArcadeGame extends ApplicationAdapter {
	private GameWorld gameWorld;
	
	private float unprocessedSeconds;
	
	private WorldRenderer worldRenderer;
	
	@Override
	public void create() {
		setup();
		
		worldRenderer = new WorldRenderer();
	}
	
	private void setup() {
		gameWorld = new GameWorld();
		PlayerEntity playerEntity = new PlayerEntity();
		gameWorld.addEntity(playerEntity);
		gameWorld.addEntity(new EnemyEntity(playerEntity), 10, 0);
		gameWorld.addEntity(new EnemyEntity(playerEntity), 10, 5);
		gameWorld.addEntity(new EnemyEntity(playerEntity), 10, -5);
	}
	
	@Override
	public void render() {
		if (Gdx.input.isKeyJustPressed(Keys.R) || Gdx.input.isKeyJustPressed(Keys.MENU)) setup();
		
		unprocessedSeconds += Gdx.graphics.getDeltaTime();
		while (unprocessedSeconds > Time.DELTA) {
			unprocessedSeconds -= Time.DELTA;
			
			gameWorld.update();
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		worldRenderer.render(gameWorld);
	}
	
	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}
	
	@Override
	public void dispose() {
		gameWorld.dispose();
	}
}
