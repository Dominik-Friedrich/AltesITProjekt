package jumpandrun;

import java.awt.Rectangle;

import javax.swing.JFrame;

//Autor: Julian
public class MainMenuButton extends GameButton {

	public MainMenuButton(int xPos, int yPos, int tempWidth, int tempHeight, Id showID, Handler tempHandler,
			JFrame tempFrame) {
		super(xPos, yPos, tempWidth, tempHeight, showID, tempHandler, tempFrame);
		super.setbtnTextureID(8);

	}

	// Wechsel ins Hauptmenü
	@Override
	public void action() {
		if (btnActive) {
			handler.setgameRenderState(0);
		}
	}

	@Override
	public Rectangle getBounds() {

		return null;
	}

}