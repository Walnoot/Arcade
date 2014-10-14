package walnoot.arcade;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.IntArray;

public class GameInput extends InputAdapter {
	private IntArray pointers = new IntArray();
	
	public boolean moveLeft() {
		if (Gdx.app.getType() == ApplicationType.Android) {
			for (int i = 0; i < pointers.size; i++) {
				int pointer = pointers.get(i);
				
				if (Gdx.input.getX(pointer) < Gdx.graphics.getWidth() / 2) return true;
			}
		}
		
		if (Gdx.app.getType() == ApplicationType.Desktop || Gdx.app.getType() == ApplicationType.WebGL) {
			return Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isButtonPressed(Buttons.LEFT);
		}
		
		return false;
	}
	
	public boolean moveRight() {
		if (Gdx.app.getType() == ApplicationType.Android) {
			for (int i = 0; i < pointers.size; i++) {
				int pointer = pointers.get(i);
				
				if (Gdx.input.getX(pointer) > Gdx.graphics.getWidth() / 2) return true;
			}
		}
		
		if (Gdx.app.getType() == ApplicationType.Desktop || Gdx.app.getType() == ApplicationType.WebGL) {
			return Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isButtonPressed(Buttons.RIGHT);
		}
		
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		pointers.add(pointer);
		
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		pointers.removeValue(pointer);
		
		return false;
	}
}
