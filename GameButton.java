package jumpandrun;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

//Autor: Dominik
public abstract class GameButton extends GameObject {
	protected Handler handler;

	private PointerInfo pcMouse;
	private Point mouse;
	private int mouseX, mouseY;

	protected JFrame frame;
	private Point framePoint;
	private int frameX, frameY;

	protected ImageIcon[] image = { new ImageIcon("resources/Buttons/LevelMenuButton.png"),
									new ImageIcon("resources/Buttons/LevelMenuButtonHover.png"), 
									new ImageIcon("resources/Buttons/QuitButton.png"),
									new ImageIcon("resources/Buttons/QuitButtonHover.png"), 
									new ImageIcon("resources/Buttons/ResumeButton.png"),
									new ImageIcon("resources/Buttons/ResumeButtonHover.png"), 
									new ImageIcon("resources/Buttons/RestartButton.png"),
									new ImageIcon("resources/Buttons/RestartButtonHover.png"),
									new ImageIcon("resources/Buttons/MainMenuButton.png"),
									new ImageIcon("resources/Buttons/MainMenuButtonHover.png"),
									new ImageIcon("resources/Buttons/Level1Button.png"),
									new ImageIcon("resources/Buttons/Level1ButtonHover.png"), 
									new ImageIcon("resources/Buttons/Level2Button.png"),
									new ImageIcon("resources/Buttons/Level2ButtonHover.png"),};
	private Image btnTexture;
	private int btnTextureID;
	protected boolean btnActive = false;

	public GameButton(int xPos, int yPos, int tempWidth, int tempHeight, Id showID, Handler tempHandler,
			JFrame tempFrame) {
		super(xPos, yPos, null, showID);
		handler = tempHandler;
		frame = tempFrame;

		width = tempWidth;
		height = tempHeight;
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		// Wenn der Button Angezeigt wird, soll er auch Aktiv sein
		btnActive = true;

		// Standard Button Textur
		btnTexture = image[btnTextureID].getImage();

		// Computer Maus Position abfragen
		pcMouse = MouseInfo.getPointerInfo();
		mouse = pcMouse.getLocation();
		mouseX = (int) mouse.getX();
		mouseY = (int) mouse.getY();

		// Position vom JFrame auf dem Bildschirm abfragen
		framePoint = frame.getLocation();
		frameX = (int) framePoint.getX() + 3; // Offset wegen Fensterleiste
		frameY = (int) framePoint.getY() + 26; // 3px in X-Richtung, 26px in Y-Richtung

		// Abfragen, ob Maus innerhalb des Button Objektes ist
		if (mouseX >= (xPos + frameX) && mouseX <= (xPos + frameX + width)) {
			if (mouseY >= (yPos + frameY) && mouseY <= (yPos + frameY + height)) {
				btnTexture = image[btnTextureID + 1].getImage();
			}
		}

		g.drawImage(btnTexture, xPos, yPos, width, height, null);
	}

	public void setbtnTextureID(int id) {
		btnTextureID = id;
	}

	public boolean getbtnActive() {
		return btnActive;
	}

	public void setbtnActive(boolean active) {
		btnActive = active;
	}

	public abstract void action();
}