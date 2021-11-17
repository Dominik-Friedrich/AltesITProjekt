package jumpandrun;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

//Autor: Dominik
public class Obstacle extends GameObject {
	private Handler handler;
	private int textureIndex;
	private boolean endmarker = false;

	public Obstacle(int xPos, int yPos, int tempWidth, int tempHeight, int textureIndex, Id id, Handler tempHandler) {
		super(xPos, yPos, id, Id.MAINGAME);
		handler = tempHandler;
		width = tempWidth;
		height = tempHeight;
		this.textureIndex = textureIndex;
	}

	// Überladener Konstruktor, für den Fall, dass ein Hinderniss nicht entfernt
	// werden soll (persistent)
	public Obstacle(int xPos, int yPos, int tempWidth, int tempHeight, int textureIndex, Id id, boolean tempEndmarker,
			Handler tempHandler) {
		super(xPos, yPos, id, Id.MAINGAME);
		handler = tempHandler;
		width = tempWidth;
		height = tempHeight;
		this.textureIndex = textureIndex;
		this.endmarker = tempEndmarker;
	}

	public void tick() {
		// Hindernis Position aktualisieren
		xPos += velX;

		// Hindernisse entfernen, sobald sie zu weit links liegen
		if (xPos <= -1000) {
			handler.objects.remove(this);
		}
	}

	public void render(Graphics g) {

		// Texturen für Testzwecke
		switch (textureIndex) {

		case 2:
			g.setColor(Color.magenta);
			g.drawRect(xPos, yPos, width, height);
			break;

		case 3:
			g.setColor(Color.pink);
			g.fillRect(xPos, yPos, width, height);
			break;
		
		default:
			break;
		}
	}

	@Override
	public boolean getEndmark() {
		return endmarker;
	}

	public Rectangle getBounds() {
		return new Rectangle(xPos, yPos, width, height);
	}
}