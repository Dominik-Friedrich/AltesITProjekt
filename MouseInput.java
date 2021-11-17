package jumpandrun;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//Autor: Dominik
public class MouseInput implements MouseListener {
	private Handler handler;

	public MouseInput(Handler tempHandler) {
		handler = tempHandler;
	}

	// Abfragen f�r den Maus-Input
	@Override
	public void mouseClicked(MouseEvent e) {
		
		// Abfrage, ob ein Mausklick �ber einem Button erfolgte
		for (int i = 0; i < handler.buttons.size(); i++) {
			GameButton tempButton = handler.buttons.get(i);
			if (e.getX() >= tempButton.getxPos() && e.getX() <= tempButton.getxPos() + tempButton.getwidth()) {
				if (e.getY() >= tempButton.getyPos() && e.getY() <= tempButton.getyPos() + tempButton.getheight()) {

					// Die Aktion des zugeh�rigen Buttons ausf�hren
					tempButton.action();
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}