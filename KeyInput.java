package jumpandrun;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Autor: Dominik
public class KeyInput implements KeyListener {
	private Handler handler;
	private Player player;
	private boolean holda;
	private boolean holdd;

	public KeyInput(Handler tempHandler, Player player) {
		handler = tempHandler;
		this.player = player;
	}

	// Abfragen für den Tastatur-Input
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			if (!player.getJump()) {
				player.setJump(true);
				player.setvelY(-7);
			}
			break;

		// Pausemenü anzeigen
		case KeyEvent.VK_P:
		case KeyEvent.VK_ESCAPE:
			if (handler.getgameRenderState() == 2 && handler.getpaused() == false) {
				handler.setpaused(true);
			} else {
				handler.setpaused(false);
			}
			break;

		// Spieler nach Rechts bewegen
		case KeyEvent.VK_D:
			player.setvelX(7);
			holdd = true;
			break;

		// Spieler nach Links bewegen
		case KeyEvent.VK_A:
			player.setvelX(-7);
			holda = true;
			break;
		
		case KeyEvent.VK_G:
			if(player.getgodmode()) {
				player.setgodmode(false);
			} else
				player.setgodmode(true);
		default:
			break;
		}
	}

	// Für einen flüssigen Übergang von links nach rechts
	// und rechts nach links Bewegungen
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_D:

			if (holda) {
				player.setvelX(-7);
			} else {
				player.setvelX(0);
			}
			holdd = false;
			break;
			
		case KeyEvent.VK_A:

			if (holdd) {
				player.setvelX(7);
			} else {
				player.setvelX(0);
			}
			holda = false;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
	}
}