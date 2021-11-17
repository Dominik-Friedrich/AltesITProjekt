package jumpandrun;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

//Autor: Julian + Dominik
public class Wyvern extends GameObject {
	private Map map;
	private Handler handler;
	private int key;
	private int ground;
	private int baseVelX;
	private int textureIndex = 0;
	private int texture = 0;
	private Image wyvern;
	private ImageIcon[] image = { new ImageIcon("resources/Sprite2.png"), new ImageIcon("resources/Sprite3.png"),
			new ImageIcon("resources/Sprite3.png"), new ImageIcon("resources/Sprite2.png") };

	public Wyvern(int x, int y, Id tempId, int tempKey, Map map, Handler tempHandler) {
		super(x, y, tempId, Id.MAINGAME);
		xPos = x;
		yPos = y;
		velY = 7;
		this.map = map;
		handler = tempHandler;
		key = tempKey;
		width = (int)(1.5 * 51);
		height = (int)(1.5 * 35);
		ground = 3000;
		baseVelX = -5;
	}

	// Aktualisieren der Werte
	// Große Ähnlichkeit mit Player.tick() Methode
	@Override
	public void tick() {
		// Die Wyverns sollen sich erst richtig bewegen,
		// wenn sie kurz vor dem Anzeigebildschirm sind
		// wenn nicht, werden sie von der Map "mitgezogen", bis sie es sind
		if (xPos > Game.width + 20) {
			xPos += map.getvelX();
			
		} else {
			velX = baseVelX + map.getvelX();

			xPos += velX;
			yPos += velY;

			collision();

			if (yPos >= ground) {
				yPos = ground;
			}

			velX = baseVelX - map.getvelX();

			if (xPos < -1000 || yPos > 2000) {
				handler.objects.remove(this);
			}

			ground = 3000;
		}
	}

	public void collision() {

		// Vergleichbar mit player.collision()
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);
			switch (tempObject.getId()) {
			case OBSTACLE:
				if (getBounds().intersects(tempObject.getBounds())) {

					if (tempObject.getyPos() >= yPos + height - 10) {
						ground = tempObject.getyPos() - height;
					} else {
						if (tempObject.getxPos() > xPos) {
							xPos = tempObject.getxPos() - width;

						} else {
							xPos = tempObject.getxPos() + tempObject.getwidth();
						}
						baseVelX = -baseVelX;
					}
				}
				break;

			case ENEMY:
				// Ausschließen, dass ein Gegner mit sich selber kollidiert
				if (getBounds().intersects(tempObject.getBounds()) && key != tempObject.getKey()) {
					baseVelX = -baseVelX;
				}
				break;

			default:
				break;
			}
		}
	}

	public int getKey() {
		return key;
	}

	@Override
	public void render(Graphics g) {
		if (!handler.getpaused()) {
			// Animation durch Texturenwechsel
			// theoretisch funktionieren
			// praktisch fehlen noch Animationstexturen
//			if (texture >= 15) {
//				texture = 0;
//				if (textureIndex > 0) {
//					textureIndex = 0;
//				} else {
//					textureIndex = 1;
//				}
//			}
//			texture++;

			// Rechts- und Links-Bewegungen
			if (velX - map.getvelX() < 0) {
				wyvern = image[0 + textureIndex].getImage();
			} else {
				wyvern = image[2 + textureIndex].getImage();
			}
		}
		g.drawImage(wyvern, xPos, yPos, width, height, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(xPos, yPos, width, height);
	}
}