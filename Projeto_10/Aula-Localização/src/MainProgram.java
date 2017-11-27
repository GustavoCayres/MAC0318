import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainProgram extends JPanel implements KeyListener, WindowListener {
	Histogram hist;
	Robot robot;
	Map map;
	double min, max; 
	int numbersegments;

	private DiscreteSpace bel;
	
	/*
	Edite as variáveis, modificando com os valores específicos do mapa
	*/
	static private int BOX_DEPTH = 37; // profundidade da caixa
	static private int WALL_DISTANCE = 80; // distância do sonar à parede
	static private int LENGHTMAP = 240; // comprimento máximo do mapa
	static private int DISCRET_SIZE = 120; // número de células da discretização
	static private double CELL_SIZE = LENGHTMAP/DISCRET_SIZE;
	
	public MainProgram(double mapsize, int numbersegments, Robot robot, Map map) {
		this.robot = robot;
		max = mapsize;
		min = 0;
		this.map = map;
		this.numbersegments = numbersegments;
		JFrame frame = new JFrame("Mapa MAC0318");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		hist = new Histogram();
		frame.setSize(800, 400);
		frame.setVisible(true);

		frame.setFocusable(true);
		frame.requestFocusInWindow();
		frame.addKeyListener(this);
		frame.addWindowListener(this);

		frame.add(hist);

		initializeBelief (); 
	}

	private void initializeBelief () { 
		bel = new DiscreteSpace();

		for (int i = 0; i < DISCRET_SIZE; i++) {
			bel.add(1.0);
		}

		bel.normalize();

		/*
			Insira o código de inicialização da crença do robô quanto a sua localização no eixo-x

			Exemplo:
				Uma distribuição uniforme considerando todas as células possíveis, indica que o robô não tem uma predição inicial de sua
				localização. 
		*/
		printHistogram ();
	}

	private void correction (double distance) { 
		/*
			Insira o código de atualização da crença do robô dada uma leitura 'distance' do sonar
		*/
		printHistogram ();
	}
	
	void prediction (double delta) {
		DiscreteSpace tmp = new DiscreteSpace();

		for (int i = 0; i < DISCRET_SIZE; i++) {
			tmp.add(0.0);
		}

		for (int i = 0; i < DISCRET_SIZE; i++) {
			Double value = bel.get(i);
			for (int j = 0; j < DISCRET_SIZE; j++) {
				double dist = (j - i) * CELL_SIZE;
				tmp.set(j, tmp.get(j) + value * pdf(dist, delta, 0.1 * delta));

			}
		} 
	
		for (int i = 0; i < DISCRET_SIZE; i++) {
			bel.set(i, tmp.get(i));
		}

		bel.normalize();
		printHistogram ();
	}
	
    public static double pdf(double x) {
        return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI); // return pdf(x) = standard Gaussian pdf
    }

    public static double pdf(double x, double mu, double sigma) { 
        return pdf((x - mu) / sigma) / sigma; // return pdf(x, mu, signma) = Gaussian pdf with mean mu and stddev sigma
    }
	
	void printHistogram () {
		hist.print(min, max, bel, map);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		char input = e.getKeyChar();
		switch (input) {
		case 'm': // envia comando de movimento ao robô de uma distância 'dist' inserida pelo usuário
			double dist = askDouble("Distancia (cm)");
			robot.move(dist);
			prediction(dist);
			break;
		case 'r': // reset
			initializeBelief();
			break;
		}
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE: // barra de espaco para leitura do sonar 
			robot.read(this);
			break;
		case KeyEvent.VK_UP: // seta cima, mover para frente em 10 cm 
			robot.move(10);
			prediction(10);
			break;
		case KeyEvent.VK_DOWN: // seta baixo, mover para trás em 10 cm
			robot.move(-10);
			prediction(-10);
			break;
		}
	}
	
	private double askDouble(String s) {
		try {
			String rs = JOptionPane.showInputDialog(s);
			return Double.parseDouble(rs);
		} catch (Exception e) {
		}
		return 0;
	}


	@Override
	public void keyReleased(KeyEvent arg0) {		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {	
		char input = arg0.getKeyChar();
		switch (input) {
		case 'i':
			hist.zoomIn();
			break;
		case 'o':
			hist.zoomOut();
			break;
		default:
			break;
		}
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.err.println("Fechando...");
		if (robot == null)
			return;
		robot.disconnect();		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {		
	}
	

	public static void main(String[] args) {
		Map map  = new Map();

		map.add(45, 76);
		map.add(121, 152);
		map.add(225, 256);
		
		Robot robot =  new Robot("NXT07"); // altere para o nome do brick
		if (robot.connect() == false) return;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainProgram(LENGHTMAP, DISCRET_SIZE, robot, map);
			}
		});
	}

	public void robotData(int distance) {
		System.out.println("Distance: "+ distance);
		correction(distance);
	}
}
