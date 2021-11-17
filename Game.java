package jumpandrun;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

//Autor: Dominik
public class Game extends Canvas implements Runnable {
	private Thread thread;
	private Handler handler;
	private JFrame frame;

	public static final int height = 720;
	public static final int width = 1280;
	private boolean running = true;

	public static void main(String args[]) {
		new Game();
	}

	public Game() {
		handler = new Handler();

		// Fenster initialisieren
		frame = new JFrame("Platformer");
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.toFront();
		frame.add(this);
		frame.setLocationRelativeTo(null);

		// Initialisierung einiger Objekte,
		// Hauptsächlich Buttons und Hintergründe der verschiedenen Menüs
		
		// Spieler als erstes Objekt in die Liste einfügen
		handler.objects.add(new Player(50, 350, Id.PLAYER, handler));
		Player player = (Player) handler.objects.get(0);

		// Main Menue
		handler.objects.add(new Background(0, 0, Id.BACKGROUND, Id.MAINMENU, handler));
		handler.buttons.add(new LevelSelectMenuButton(430, 400, 180, 42, Id.MAINMENU, handler, frame));
		handler.buttons.add(new QuitButton(630, 400, 180, 42, Id.MAINMENU, handler, frame));

		// LevelSelect Menue
		handler.objects.add(new Background(0, 0, Id.BACKGROUND, Id.LEVELSELECT, handler));
		handler.buttons.add(new LevelButton(100, 80, 200, 87, 1, Id.LEVELSELECT, handler, frame));
		handler.buttons.add(new LevelButton(320, 80, 200, 87, 2, Id.LEVELSELECT, handler, frame));

		// Pausemenue
		handler.objects.add(new Background(0, 0, Id.BACKGROUND, Id.PAUSEMENU, handler));
		handler.buttons.add(new MainMenuButton(width / 2 - 90, height/2 - 125, 180, 42, Id.PAUSEMENU, handler, frame));
		handler.buttons.add(new ResumeButton(width / 2 - 90, height/2 - 73, 180, 42, Id.PAUSEMENU, handler, frame));
		handler.buttons.add(new MapResetButton(width / 2 - 90, height/2 - 21, 180, 42, Id.PAUSEMENU, handler, frame));
		handler.buttons.add(new QuitButton(width / 2 - 90, height/2 + 31, 180, 42, Id.PAUSEMENU, handler, frame));

		// Todesbildschirm
		handler.objects.add(new Background(0, 0, Id.BACKGROUND, Id.DEATHSCREEN, handler));
		handler.buttons.add(new MainMenuButton(width / 2 - 90, 420, 180, 42, Id.DEATHSCREEN, handler, frame));
		handler.buttons.add(new MapResetButton(width / 2 - 90, 472, 180, 42, Id.DEATHSCREEN, handler, frame));
		handler.buttons.add(new QuitButton(width / 2 - 90, 524, 180, 42, Id.DEATHSCREEN, handler, frame));
		
		// Todesbildschirm
		handler.objects.add(new Background(0, 0, Id.BACKGROUND, Id.VICTORYSCREEN, handler));
		handler.buttons.add(new MainMenuButton(width / 2 - 90, 420, 180, 42, Id.VICTORYSCREEN, handler, frame));
		handler.buttons.add(new MapResetButton(width / 2 - 90, 472, 180, 42, Id.VICTORYSCREEN, handler, frame));
		handler.buttons.add(new QuitButton(width / 2 - 90, 524, 180, 42, Id.VICTORYSCREEN, handler, frame));

		// Maus und Tasten Abfragen
		this.addKeyListener(new KeyInput(handler, player));
		this.addMouseListener(new MouseInput(handler));

		this.start();
	}

	@Override
	// Sogenannte "Game Loop": 
	// Macht das Aktualisieren des Spiels abhängig von der Systemzeit
	// und nicht von der Prozessorgeschwindigkeit
	// "amountOfTicks" entspricht der Anzahl an Updates pro Sekunde
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				handler.tick();
				delta--;
			}
			if (running)
				render();

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
		stop();
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Graphikausgabe
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		handler.render(g);

		g.dispose();
		bs.show();
	}
}

//Danke an Christian Fischer für die Unterstützung