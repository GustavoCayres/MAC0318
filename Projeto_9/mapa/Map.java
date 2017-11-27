import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Map extends JPanel {
	private ImageMap map;
	
	public Map () {
		JFrame frame = new JFrame("Mapa MAC0318");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        this.map = new ImageMap();
		frame.add(this.map);
		frame.setSize(800, 800);
		frame.setVisible(true);
		
		frame.setFocusable(true);
		frame.requestFocusInWindow();
		
		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				char input = e.getKeyChar();
				if (input == '1') map.setVisual(0);
				else if (input == '2') map.setVisual(1);
				else if (input == '3') map.setVisual(2);
				else if (input == 'l') map.showLine();
				else if (input == 'i') map.showImg();
				else if (input == 's') map.save();
			}
		});
		String text = "1,2,3 - Change view mode.\n";
		text += "s - Save image.\n";
		text += "l - Show trace.\n";
		text += "i - Show map image.\n";
		JOptionPane.showMessageDialog(null, text);
	}

    public void addPoint(Point3D p, Double expected, Double measured) {
    	map.addPoint(p, expected, measured);
	}

	public static void main(String[] args) {
		Map map = new Map();
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String line;

		try {
			while ((line = stdin.readLine()) != null){
				String[] exploded = line.split(",");
				double px = Double.valueOf(exploded[0]).doubleValue();
				double py = Double.valueOf(exploded[1]).doubleValue();
				double pheading = Double.valueOf(exploded[2]).doubleValue();
				double sensor_ang = Math.toRadians(Double.valueOf(exploded[3]).doubleValue() + 90);
				double measured = Double.valueOf(exploded[4]).doubleValue();
				double expected = Double.valueOf(exploded[5]).doubleValue();
		        double dx = Math.cos(sensor_ang) * measured;
		        double dy = Math.sin(sensor_ang) * measured;
                if (measured != 255) {
				    map.addPoint(new Point3D(dx,
				                         dy,
                                         pheading),
                                         expected,
                                         measured);
                }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
