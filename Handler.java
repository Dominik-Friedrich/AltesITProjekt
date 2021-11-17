package jumpandrun;

import java.awt.Graphics;
import java.util.LinkedList;

//Autor: Dominik
public class Handler {
	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	public LinkedList<GameButton> buttons = new LinkedList<GameButton>();

	private Map map;
	private int currentLevel;
	private boolean mapMade = false;
	private boolean paused = false;

	private int gameRenderState = 0;
	// 0: Hauptmenue
	// 1: Levelauswahl
	// 2: Hauptspiel
	// 3: TodesBildschirm
	// 4: SiegesBildschirm

	public void tick() {

		if (gameRenderState == 2 && paused == false) {
			if (mapMade) {
				map.tick();
			}
			// Jedes Objekt updaten
			// Buttons Update irrelevant, da deren tick() Methode nichts macht
			for (int i = 0; i <= objects.size() - 1; i++) {
				GameObject tempObject = objects.get(i);
				if (tempObject.getshowID() == Id.MAINGAME) {
					tempObject.tick();
				}
			}
		}
	}

	public void render(Graphics g) {
		switch (gameRenderState) {
		case 0: // Hauptmenue
			for (int i = 0; i <= objects.size() - 1; i++) {
				GameObject tempObject = objects.get(i);
				if (tempObject.getshowID() == Id.MAINMENU && tempObject.getId() == Id.BACKGROUND) {
					tempObject.render(g);
				}
			}

			for (int i = 0; i <= buttons.size() - 1; i++) {
				GameButton tempButton = buttons.get(i);
				if (tempButton.getshowID() == Id.MAINMENU) {
					tempButton.render(g);
				} else {
					tempButton.setbtnActive(false);
				}
			}
			break;

		case 1: // Levelauswahl
			for (int i = 0; i <= objects.size() - 1; i++) {
				GameObject tempObject = objects.get(i);
				if (tempObject.getshowID() == Id.LEVELSELECT && tempObject.getId() == Id.BACKGROUND) {
					tempObject.render(g);
				}
			}

			for (int i = 0; i <= buttons.size() - 1; i++) {
				GameButton tempButton = buttons.get(i);
				if (tempButton.getshowID() == Id.LEVELSELECT) {
					tempButton.render(g);
				} else {
					tempButton.setbtnActive(false);
				}
			}
			break;

		case 2: // Hauptspiel
			for (int i = 0; i <= objects.size() - 1; i++) {
				GameObject tempObject = objects.get(i);
				if ((tempObject.getId() == Id.BACKGROUND || tempObject.getId() == Id.ENEMY)
						&& tempObject.getshowID() == Id.MAINGAME) {
					tempObject.render(g);
				}
			}
			for (int i = 1; i <= objects.size() - 1; i++) {
				GameObject tempObject = objects.get(i);
				if (tempObject.getId() == Id.OBSTACLE || tempObject.getId() == Id.ENEMY) {
					if(tempObject.getEndmark()) {
						if(objects.get(0).getxPos() > tempObject.getxPos() + tempObject.getwidth()) {
							setgameRenderState(4);
						}
					}
					tempObject.render(g);
				}
			}

			// Alle Buttons auﬂer Pausemenue deaktivieren
			for (int i = 0; i <= buttons.size() - 1; i++) {
				GameButton tempButton = buttons.get(i);
				if (tempButton.getshowID() != Id.PAUSEMENU) {
					tempButton.setbtnActive(false);
				}
			}
			// Spieler ist das erste Objekt in der Liste, er soll als letztes angezeigt
			// werden
			objects.get(0).render(g);

			// Im Pausemenue
			if (paused) {

				// Erst Transparenter Hintergrund anzeigen
				for (int i = 1; i <= objects.size() - 1; i++) {
					GameObject tempObject = objects.get(i);
					if (tempObject.getshowID() == Id.PAUSEMENU && tempObject.getId() == Id.BACKGROUND) {
						tempObject.render(g);
					}
				}

				// danach die Buttons anzeigen und aktivieren
				for (int i = 1; i <= buttons.size() - 1; i++) {
					GameButton tempButton = buttons.get(i);
					if (tempButton.getshowID() == Id.PAUSEMENU) {
						tempButton.render(g);
					}
				}

				// Pausemenue Buttons deaktivieren, damit diese deaktiviert werden, sobald pause
				// == false ist
			} else {
				for (int i = 1; i <= buttons.size() - 1; i++) {
					GameButton tempButton = buttons.get(i);
					if (tempButton.getshowID() == Id.PAUSEMENU) {
						tempButton.setbtnActive(false);
					}
				}
			}
			break;

		case 3: // TodesBildschirm
			for (int i = 1; i <= objects.size() - 1; i++) {
				GameObject tempObject = objects.get(i);
				if (tempObject.getshowID() == Id.DEATHSCREEN && tempObject.getId() == Id.BACKGROUND) {
					tempObject.render(g);
				}
			}

			for (int i = 1; i <= buttons.size() - 1; i++) {
				GameButton tempButton = buttons.get(i);
				if (tempButton.getshowID() == Id.DEATHSCREEN) {
					tempButton.render(g);
				}
			}

			break;
			
		case 4: // SiegesBildschirm
			for (int i = 1; i <= objects.size() - 1; i++) {
				GameObject tempObject = objects.get(i);
				if (tempObject.getshowID() == Id.VICTORYSCREEN && tempObject.getId() == Id.BACKGROUND) {
					tempObject.render(g);
				}
			}

			for (int i = 1; i <= buttons.size() - 1; i++) {
				GameButton tempButton = buttons.get(i);
				if (tempButton.getshowID() == Id.VICTORYSCREEN) {
					tempButton.render(g);
				}
			}
			break;

		default: // Fehlerabfang
			setgameRenderState(0);
			break;
		}
	}

	// Erneuern bzw resetten der Map
	public void reset() {
		for (int i = 0; i <= objects.size() - 1; i++) {
			GameObject tempObject = objects.get(i);
			if (tempObject.getshowID() == Id.MAINGAME) {
				if (tempObject.getId() == Id.PLAYER) {
					tempObject.setxPos(50);
					tempObject.setyPos(350);
				} else {
					objects.remove(i);
					i--;
				}
			}
		}
	}

	// Neue Map erstellen
	public void newMap(int level) {
		currentLevel = level;
		if (mapMade) {
			map.newMap(level);
		} else {
			map = new Map(this, level);
			mapMade = true;
		}
	}

	public int getcurrentLevel() {
		return currentLevel;
	}

	public void setmapMade(boolean mapMade) {
		this.mapMade = mapMade;
	}

	public void setmapvelX(int velX) {
		if (mapMade) {
			map.setvelX(velX);
		}
	}

	public int getgameRenderState() {
		return gameRenderState;
	}

	public boolean getpaused() {
		return paused;
	}

	public void setpaused(boolean newPaused) {
		paused = newPaused;
	}

	public void setgameRenderState(int newRenderState) {
		if (newRenderState >= 0 && newRenderState <= 4) {
			gameRenderState = newRenderState;
		} else {
			System.out.println("Faulty gameRenderState");
		}
	}
}