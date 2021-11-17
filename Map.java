package jumpandrun;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

//Autor: Dominik
public class Map {
	private Handler handler;
	private int mapX = Game.width + 100;
	private int velX = 0;
	// Jedem Gegnerobjekt eine eindeutige Id zufügen, um später in einer Abfrage den
	// Fall, dass ein Gegner mit sich selbst kollidiert
	// auszuschließen
	private int enemyKey = 0;

	public Map(Handler tempHandler, int level) {
		handler = tempHandler;
		newMap(level);
	}

	public void setvelX(int tempvelX) {
		velX = tempvelX;
	}

	public void newMap(int newLevel) {
		FileInputStream fstream = null;
		BufferedReader br;
		String line;

		// Auslesen aus der zugehörigen Map.txt Datei
		try {
			fstream = new FileInputStream("maps/map" + newLevel + ".txt");
		} catch (Exception e) {
			System.out.println("Hallo, ein Hund");
		}
		try

		{
			br = new BufferedReader(new InputStreamReader(fstream));
			line = br.readLine();
			System.out.println(line);

			while ((line = br.readLine()) != null) {
				convertLine(line);
			}

			fstream.close();
		} catch (Exception e) {
		}
	}

	// Verarbeitung der Ausgelesenen Textzeilen
	public void convertLine(String line) {
		String lineSplit[] = new String[10];
		lineSplit = line.split(", ");

		switch (lineSplit[0]) {
		case "obstacle":
			handler.objects.add(new Obstacle(Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]),
					Integer.parseInt(lineSplit[3]), Integer.parseInt(lineSplit[4]), Integer.parseInt(lineSplit[5]),
					Id.OBSTACLE, Boolean.parseBoolean(lineSplit[6]), handler));
			break;

		case "background":
			handler.objects.add(new Background(Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]),
					Integer.parseInt(lineSplit[3]), Id.BACKGROUND, Id.MAINGAME, handler));
			break;

		case "enemy":
			switch (lineSplit[1]) {
			case "xminesreaper":
				handler.objects.add(new XMinesReaper(Integer.parseInt(lineSplit[2]), Integer.parseInt(lineSplit[3]),
						Integer.parseInt(lineSplit[4]), Integer.parseInt(lineSplit[5]), Id.ENEMY, enemyKey, this,
						handler));
				enemyKey++;
				break;

			case "yminesreaper":
				handler.objects.add(new YMinesReaper(Integer.parseInt(lineSplit[2]), Integer.parseInt(lineSplit[3]),
						Integer.parseInt(lineSplit[4]), Integer.parseInt(lineSplit[5]), Integer.parseInt(lineSplit[6]),
						Id.ENEMY, enemyKey, this, handler));
				enemyKey++;
				break;

			case "wyvern":
				handler.objects.add(new Wyvern(Integer.parseInt(lineSplit[2]), Integer.parseInt(lineSplit[3]), Id.ENEMY,
						enemyKey, this, handler));
				enemyKey++;
				break;
			}
			break;

		default:
			if (lineSplit[0].contains("-") == false) {
				System.out.println("Unverarbeitete Zeile bei Map.convertLine()");
			}
			break;
		}
	}

	// Allen Levelrelevanten Objekten die Geschwindigkeit der Map zufügen
	public void tick() {
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);
			if ((tempObject.getId() == Id.OBSTACLE || tempObject.getId() == Id.BACKGROUND)
					&& tempObject.getshowID() == Id.MAINGAME) {
				tempObject.setvelX(velX);
			}
		}
	}

	public int getvelX() {
		return velX;
	}

	public int getmapX() {
		return mapX;
	}
}