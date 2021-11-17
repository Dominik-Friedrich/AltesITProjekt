package jumpandrun;

import java.awt.Rectangle;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

//Autor: Julian
public class QuitButton extends GameButton {

	public QuitButton(int xPos, int yPos, int tempWidth, int tempHeight, Id showID, Handler tempHandler,
			JFrame tempFrame) {
		super(xPos, yPos, tempWidth, tempHeight, showID, tempHandler, tempFrame);
		super.setbtnTextureID(2);
	}

	@Override
	public void action() {

		// Fenster schlieﬂen
		if (btnActive) {
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
}