package jumpandrun;

import java.awt.Rectangle;

import javax.swing.JFrame;

//Autor: Julian
public class ResumeButton extends GameButton {

	public ResumeButton(int xPos, int yPos, int tempWidth, int tempHeight, Id showID, Handler tempHandler,
			JFrame tempFrame) {
		super(xPos, yPos, tempWidth, tempHeight, showID, tempHandler, tempFrame);
		super.setbtnTextureID(4);
	}

	@Override
	public void action() {

		// Pausemenue schlieﬂen, um das Spiel fortzusetzen
		if (btnActive) {
			handler.setpaused(false);
		}
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
}