package jumpandrun;

import java.awt.Rectangle;

import javax.swing.JFrame;

//Autor: Julian
public class MapResetButton extends GameButton {

	public MapResetButton(int xPos, int yPos, int tempWidth, int tempHeight, Id showID, Handler tempHandler,
			JFrame tempFrame) {
		super(xPos, yPos, tempWidth, tempHeight, showID, tempHandler, tempFrame);
		super.setbtnTextureID(6);
	}

	// Zurücksetzen der Map und diesselbe Map neuerzeugen
	@Override
	public void action() {
		if (btnActive) {
			handler.reset();
			handler.newMap(handler.getcurrentLevel());
			handler.setgameRenderState(2);
			handler.setpaused(false);
		}
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
}