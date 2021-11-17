package jumpandrun;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

//Autor: Julian + Dominik
// Eine Variante des MinesReaper, dieser bewegt sich
// nur vertikal in x-Richtung
public class XMinesReaper extends GameObject {
	private int bound1, bound2;
	private Map map;
	private Handler handler;
	private int key;
	private int basevelX;
	
	private int i = 0;
	private int textureIndex;
	private Image minesreaper;
	private ImageIcon[] image = { new ImageIcon("resources/MinesReaper1.png"), new ImageIcon("resources/MinesReaper2.png"),
			new ImageIcon("resources/MinesReaper3.png"), new ImageIcon("resources/MinesReaper4.png") };
	
	public XMinesReaper(int x, int y, int boundary1, int boundary2, Id tempId, int tempKey, Map map,
			Handler tempHandler) {
		super(x, y, tempId, Id.MAINGAME);
		xPos = x;
		yPos = y;
		basevelX = -4;
		this.map = map;
		handler = tempHandler;
		bound1 = xPos + boundary1;
		bound2 = xPos + boundary2;
		key = tempKey;
		width = (int)(0.4 * 129);
		height = (int)(0.4 * 86);
	}

	// Aktualisieren der Werte
	@Override
	public void tick() {
		velX = basevelX + map.getvelX();

		xPos += velX;
		yPos += velY;

		// Grenzen der Bewegung mit der Map Bewegen
		bound1 += map.getvelX();
		bound2 += map.getvelX();

		if (xPos < bound1) {
			basevelX = -basevelX;
		} else if (xPos > bound2) {
			basevelX = -basevelX;
		}
		collision();
		velX = basevelX + map.getvelX();
	}

	public void collision() {
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);
			switch (tempObject.getId()) {
			case OBSTACLE:
				if (getBounds().intersects(tempObject.getBounds())) {

					if (tempObject.getxPos() > xPos) {
						xPos = tempObject.getxPos() - width;

					} else {
						xPos = tempObject.getxPos() + tempObject.getwidth();
					}
					basevelX = -basevelX;
				}
				break;

			case ENEMY:
				if (getBounds().intersects(tempObject.getBounds()) && key != tempObject.getKey()) {
					basevelX = -basevelX;
				}
				break;

			default:
				break;
			}
		}
	}

	@Override
	public int getKey() {
		return key;
	}

	@Override
	public void render(Graphics g) {
		if(i > 35) {
			textureIndex++;
			i = 0;
		}
		i++;
		
		if(textureIndex >= 4) {
			textureIndex = 0;
		}
		minesreaper = image[textureIndex].getImage();
		g.drawImage(minesreaper, xPos, yPos, width, height, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(xPos, yPos, width, height);
	}
}