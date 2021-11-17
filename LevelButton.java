package jumpandrun;

import java.awt.Rectangle;

import javax.swing.JFrame;

//Autor: Julian + Dominik
public class LevelButton extends GameButton {
	private int level;

	public LevelButton(int xPos, int yPos, int tempWidth, int tempHeight, int templevel, Id showID, Handler tempHandler,
			JFrame tempFrame) {
		super(xPos, yPos, tempWidth, tempHeight, showID, tempHandler, tempFrame);
		level = templevel;
		super.setbtnTextureID(8 + 2 * level);
	}

	// Auswählen des Levels, dem der Button zugewiesen ist
	@Override
	public void action() {
		if (btnActive) {
			btnActive = false;
			handler.reset();
			handler.newMap(level);
			handler.setpaused(false);
			handler.setgameRenderState(2);
		}
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}