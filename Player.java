package jumpandrun;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

//Autor: Dominik
public class Player extends GameObject {
	private Handler handler;
	private int i = 1;
	private int ground = 1000;
	private boolean jump = false;
	private boolean godmode = false;
	
	private int textureIndex = 0;
	private int texture = 0;
	private int lastyPos;
	private Image player;
	private ImageIcon image[] = { new ImageIcon("resources/Dosenfred.png"),
			new ImageIcon("resources/DosenfredRunRight.png"), new ImageIcon("resources/DosenfredRunRight2.png"),
			new ImageIcon("resources/DosenfredRunLeft.png"), new ImageIcon("resources/DosenfredRunLeft2.png"),
			new ImageIcon("resources/DosenfredJumpRight.png"), new ImageIcon("resources/DosenfredJumpLeft.png"), };

	public Player(int xPos, int yPos, Id id, Handler tempHandler) {
		super(xPos, yPos, id, Id.MAINGAME);
		handler = tempHandler;
		velY = 10;
		// An die Graphik des Spielers angepasst
		width = 39;
		height = 123;
	}

	public void tick() {
		// letzte Position zwischenspeichern für Sprung
		lastyPos = yPos;

		// Spieler Position
		xPos += velX;
		yPos += velY;

		// Map bewegen
		if (xPos >= Game.width / 2) {
			xPos = (Game.width / 2 - 1);
			handler.setmapvelX(-10);
		} else {
			handler.setmapvelX(0);
		}

		// Abfragen, ob Spieler mit einem anderen Objekt kollidiert
		collision();

		// Nicht Links aus dem Bildschirm hinauslaufen
		if (xPos <= 0) {
			xPos = 0;
		}

		// Nicht Herunterfallen
		if (yPos >= ground) {
			yPos = ground;
		}

		// Übergang zum Todesbildschirm, wenn man bei einem Loch herunterfällt
		if (yPos > 720) {
			handler.setgameRenderState(3);
		}

		// Sprungsequenz
		if (jump) {
			if (i >= 20) {
				velY = 10;
				i = 0;
			}
			i++;
		} else {
			i = 0;
		}

		// Wenn Spieler auf dem Boden steht das Springen wieder ermöglich
		// Wenn er am fallen ist, soll er nicht springen können
		if (yPos == lastyPos) {
			jump = false;
		} else {
			jump = true;
		}

		// Arbiträrer Wert, um den Spieler zum fallen zu bringen, bis er am
		// Boden-Hinternis stoppt
		ground = 1000;

	}

	public void collision() {
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);
			switch (tempObject.getId()) {
			case OBSTACLE:
				// Abfragen, ob Spieler mit einem Hindernis kollidiert;
				if (getBounds().intersects(tempObject.getBounds())) {

					// Abfragen, ob Spieler über dem Hindernis war
					if (tempObject.getyPos() >= yPos + height - 10) {

						// Boden wird verändert, damit der Spieler auf dem Hindernis stehen bleibt
						ground = tempObject.getyPos() - height;
					} else {

						// Abfragen ob Spieler von links in das Hindernis läuft
						if (tempObject.getxPos() > xPos) {

							// Position des Spielers auf einen Punkt kurz vor dem Hindernis setzen
							xPos = tempObject.getxPos() - width;

							// Else -> Spieler kommt von rechts
						} else {

							// Position des Spielers auf einen Punkt kurz hinter dem Hindernis setzen
							xPos = tempObject.getxPos() + tempObject.getwidth();
						}
					}
				}
				break;

			// Ähnliche Abfrage wie mit Hindernissen, nur mit Gegner Objekten
			case ENEMY:
				if (!godmode) {
					if (getBounds().intersects(tempObject.getBounds())) {

						if (tempObject.getyPos() >= yPos + height - 30) {
							handler.objects.remove(tempObject);
							i = 10;
							jump = true;
							velY = -7;
						} else {
							handler.setgameRenderState(3);
						}
					}
				}
			default:
				break;
			}
		}
	}

	@Override
	public void setvelY(int vel) {
		velY = vel;
		if (jump) {
			i = 1;
		}
	}
	
	public void setgodmode(boolean gm) {
		godmode = gm;
	}
	
	public boolean getgodmode() {
		return godmode;
	}

	public boolean getJump() {
		return jump;
	}

	public void setJump(boolean tempJump) {
		jump = tempJump;
	}

	public void render(Graphics g) {
		if (!handler.getpaused()) {

			// Animation durch Texturenwechsel erzeugen
			if (texture >= 20) {
				texture = 0;
				if (textureIndex > 0) {
					textureIndex = 0;
				} else {
					textureIndex = 1;
				}
			}
			texture++;
			// Bewegt nach Rechts
			if (velX > 0) {

				// Auf dem Boden
				if (!jump) {
					player = image[textureIndex + 1].getImage();

					// Springt nach Rechts
				} else {
					player = image[5].getImage();
				}

				// Bewegt sich nach links
			} else if (velX < 0) {

				// Auf dem Boden
				if (!jump) {
					player = image[textureIndex + 3].getImage();

					// Springt nach links
				} else {
					player = image[6].getImage();
				}

				// Steht still
			} else {
				player = image[0].getImage();
			}
		}
		g.drawImage(player, xPos, yPos, width, height, null);
		
		if(godmode) {
			Image godmode = new ImageIcon("resources/Buttons/godmode.png").getImage();
			g.drawImage(godmode, 10, 10, null);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(xPos, yPos, width, height);
	}
}