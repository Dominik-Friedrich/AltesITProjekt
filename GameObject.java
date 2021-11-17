package jumpandrun;

import java.awt.Graphics;
import java.awt.Rectangle;

//Autor: Dominik
// Oberklasse der meisten Spielobjekte
public abstract class GameObject {
	protected int xPos, yPos, velX, velY;
	protected int width = 100;
	protected int height = 100;
	protected Id id;
	protected Id showID;

	public GameObject(int x, int y, Id tempId, Id tempshowID) {
		xPos = x;
		yPos = y;
		id = tempId;
		showID = tempshowID;
	}
	
	public void setxPos(int x) {
		xPos = x;
	}

	public void setyPos(int y) {
		yPos = y;
	}

	public int getxPos() {
		return xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setId(Id tempId) {
		id = tempId;
	}

	public Id getId() {
		return id;
	}

	public void setvelX(int vel) {
		velX = vel;
	}

	public void setvelY(int vel) {
		velY = vel;
	}

	public int getvelX() {
		return velX;
	}

	public int getvelY() {
		return velY;
	}

	public void setwidth(int width) {
		this.width = width;
	}

	public void setheight(int height) {
		this.height = height;
	}

	public int getwidth() {
		return width;
	}

	public int getheight() {
		return height;
	}

	public Id getshowID() {
		return showID;
	}
	
	public void setshowID(Id id) {
		showID = id;
	}
	
	public boolean getEndmark() {
		return false;
	}
	
	//Fehlerabfang
	public int getKey() {
		System.out.println("Wrong use of getKey()");
		return 0;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract Rectangle getBounds();
}