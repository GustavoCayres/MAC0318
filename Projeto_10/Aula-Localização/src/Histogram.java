import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

public class Histogram extends JPanel implements MouseListener{
	Map map;
	double min, max;
	DiscreteSpace space;
	static double axispower = 1;
	boolean showpoint = false;
	int p_x, p_y;
	double zoom =1.0;
    
	public Histogram() {
		super();
		addMouseListener(this);
	}

	public void print(double min, double max, DiscreteSpace space, Map map) {
		this.map = map;
		this.min = min;
		this.max = max;
		this.space = space;
		repaint();
	}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int axiszise = 60;
        int w = (int) (getWidth())-axiszise;
        int h = (int) (getHeight());
        double mult = w/(max-min);
        
        g.setColor(Color.BLACK);
        g.drawLine(w, 0, w, h);
        for (double i = 1; i >= 0; i -= 0.125) {
        		int y = (int)(h*(1-Math.pow(i, axispower)));
        		g.drawLine(w, y, w+10, y);
        }
        for (double i = 1; i >= 0; i -= 0.125) {
	    		int y = (int)(h*(1-Math.pow(i, axispower)));
	    		g.drawString(Double.toString(i*zoom), w+12, y+5);
	    }
        
        if (map != null) {
	        paintMap(g, h, mult);
        }
        
        if (space != null) {
        		paintSpace(g, h, w);
        }
        
        if (showpoint == true) {
        		g.setColor(Color.GRAY);
        		g.fillRect(p_x, p_y, 200, 30);
            g.setColor(Color.BLACK);
            double x = Math.round((double)p_x/mult*100.0)/100.0;
            double y = Math.pow(1-((double)p_y/h/zoom), 1.0/axispower);
            y = Math.round(y*10000.0)/10000.0;
        		g.drawString("x:     "+x, p_x+6, p_y+11);
        		g.drawString("p(x): "+y, p_x+6, p_y+26);
        }
    }

	private void paintSpace(Graphics g, int h, int w) {
		double unit = ((double)w)/space.size();
		for (int i = 0; i < space.size(); i++) {
				int x = (int)(i*unit);
				int y = (int)(h*(1-Math.pow(space.get(i)/zoom, axispower)));
				int width = (int) (unit);
				int height = h-y;
				g.setColor(Color.RED);
				g.fillRect(x, y, width, height);
				g.setColor(Color.BLACK);
				g.drawRect(x, y, width, height);
		}
	}

	private void paintMap(Graphics g, int h, double mult) {
		g.setColor(Color.GREEN);
		for (Double[] data: map) {
			int x = (int) (data[0]*mult);
			int y = 0;
			int width = (int) ((data[1]-data[0])*mult);
			g.fillRect(x, y, width, h);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		showpoint = !showpoint;
		p_x = arg0.getX();
		p_y = arg0.getY();
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {	
	}

	@Override
	public void mouseExited(MouseEvent arg0) {		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	public void zoomIn() {
		if (zoom > 1.0/16)
			zoom /= 2.0;
		repaint();
	}

	public void zoomOut() {
		if (zoom < 0.9)
			zoom *= 2.0;
		repaint();
	}

}
