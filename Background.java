package jumpandrun;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

// Autor: Dominik
public class Background extends GameObject {
	private int mapTextureId;
	private Image background;
	private ImageIcon[] image = { new ImageIcon("resources/Backgrounds/mainmenu.png"),
			new ImageIcon("resources/Backgrounds/levelselect.png"),
			new ImageIcon("resources/Backgrounds/pausefilter.png"),
			new ImageIcon("resources/Backgrounds/deathscreen.png"),
			new ImageIcon("resources/Backgrounds/victoryscreen.png"),
			new ImageIcon("resources/Backgrounds/backgroundlevel1.png"),
			new ImageIcon("resources/Backgrounds/backgroundlevel2.png"), };

	public Background(int xPos, int yPos, Id id, Id showID, Handler tempHandler) {
		super(xPos, yPos, id, showID);
//		handler = tempHandler;
		width = Game.width;
		height = Game.height;
	}

	public Background(int xPos, int yPos, int mapTextureId, Id id, Id showID, Handler tempHandler) {
		super(xPos, yPos, id, showID);
//		handler = tempHandler;
		width = Game.width;
		height = Game.height;
		this.mapTextureId = mapTextureId;

	}

	public void tick() {
		xPos += velX;
	}

	public void render(Graphics g) {
		// Richtige Textur des Hintergrundes anzeigen
		switch (showID) {
		case MAINMENU:
			background = image[0].getImage();
			break;

		case LEVELSELECT:
			background = image[1].getImage();
			break;

		case PAUSEMENU:
			background = image[2].getImage();
			break;

		case DEATHSCREEN:
			background = image[3].getImage();
			break;

		case VICTORYSCREEN:
			background = image[4].getImage();
			break;

		case MAINGAME:
			background = image[4 + mapTextureId].getImage();
			break;

		default:
			break;
		}

		g.drawImage(background, xPos, yPos, null);
	}

	public Rectangle getBounds() {
		return null;
	}
}