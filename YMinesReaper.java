package jumpandrun;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

//Autor: Julian
// Eine Variante des MinesReaper, dieser bewegt sich
// nur horizontal in y-Richtung
public class YMinesReaper extends GameObject {
	private int bound1, bound2;
	private Map map;
	private Handler handler;
	private int key;
	
	private int i = 0;
	private int textureIndex;
	private Image minesreaper;
	private ImageIcon[] image = { new ImageIcon("resources/MinesReaper1.png"), new ImageIcon("resources/MinesReaper2.png"),
			new ImageIcon("resources/MinesReaper3.png"), new ImageIcon("resources/MinesReaper4.png") };

	public YMinesReaper(int x, int y, int boundary1, int boundary2, int direction ,Id tempId, int tempKey, Map map,
			Handler tempHandler) {
		super(x, y, tempId, Id.MAINGAME);
		xPos = x;
		yPos = y;
		velY = direction;
		this.map = map;
		handler = tempHandler;
		bound1 = yPos + boundary1;
		bound2 = yPos + boundary2;
		key = tempKey;
		width = (int)(0.4 * 129);
		height = (int)(0.4 * 86);
	}

	// Aktualisieren der Werte
	@Override
	public void tick() {
		velX += map.getvelX();

		xPos += velX;
		yPos += velY;

		if (yPos < bound1) {
			velY = -velY;
		} else if (yPos > bound2) {
			velY = -velY;
		}
		collision();
		velX -= map.getvelX();
	}

	public void collision() {
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);
			switch (tempObject.getId()) {
			case OBSTACLE:
				if (getBounds().intersects(tempObject.getBounds())) {
					if(tempObject.getyPos() > yPos) {
						yPos = tempObject.getyPos() - height;
					}
					velY = -velY;
				}
				break;

			case ENEMY:
				if (getBounds().intersects(tempObject.getBounds()) && key != tempObject.getKey()) {
					velY = -velY;
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
