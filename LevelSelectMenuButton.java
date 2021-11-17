package jumpandrun;

import java.awt.Rectangle;

import javax.swing.JFrame;

//Autor: Julian
public class LevelSelectMenuButton extends GameButton {

	public LevelSelectMenuButton(int xPos, int yPos, int tempWidth, int tempHeight, Id showID, Handler tempHandler,
			JFrame tempFrame) {
		super(xPos, yPos, tempWidth, tempHeight, showID, tempHandler, tempFrame);
		super.setbtnTextureID(0);
	}

	// Wechsel ins Levelauswahlmenü
	@Override
	public void action() {
		if (btnActive) {
			handler.setgameRenderState(1);
			handler.setmapMade(false);
		}
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
}