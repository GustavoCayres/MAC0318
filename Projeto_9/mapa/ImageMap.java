import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ImageMap extends JPanel {
    private double zoom = 2.0; // pixel per cm
    private double grid = 10.0; // cm
    private double centerx = 0.0;
    private double centery = 0.0; // cm
    private Point mousePt;
    private ArrayList<Point3D> lista_pontos;
    private ArrayList<Color> lista_cores;
    private int visual_method = 0;
    private boolean line = false;
    private boolean image = false;
    private BufferedImage img = null;
    
	public ImageMap() {
		lista_pontos = new ArrayList<Point3D>();
		lista_cores = new ArrayList<Color>();
		setBackground(Color.BLACK);
		addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                // TODO Auto-generated method stub
                if(e.getWheelRotation()<0){
                    if (zoom < 15.0)
                        zoom *= 1.1;
                    repaint();
                }
                //Zoom out
                if(e.getWheelRotation()>0){
                    if (zoom > 1.0)
                        zoom /= 1.1;
                    repaint();
                }
            }
        });
		addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // TODO Auto-generated method stub
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                centerx += e.getX() - mousePt.x;
                centery += e.getY() - mousePt.y;
                mousePt = e.getPoint();
                repaint();
            }
        });

        try {
            img = ImageIO.read(new File("PistaBUG2.png"));
        } catch ( IOException exc ) {
            
        }
	}
    private void drawModel (Graphics g) {
        int width = (int) (getWidth()+2*centerx);
        int height = (int) (getHeight()+2*centery);
        int count = 0;
        int x_tmp = 0, y_tmp = 0;

    	for (Point3D p : lista_pontos) {
    	    g.setColor(lista_cores.get(count));
    		int x = width/2+(int)(p.x*zoom);
    		int y = height/2+(int)(p.y*zoom)*-1;
    		
			if (visual_method == 0) {
				g.fillOval(
						x-(int)(zoom/2.0*1.5),
						y-(int)(zoom/2.0*1.5),
						(int)(zoom*0.5),
						(int)(zoom*0.5)
				);
			} else if (visual_method == 1) {
                g.drawLine(
                    width/2+(int)(p.x*zoom),
                    height/2-(int)(p.y*zoom), 
                    width/2+(int)(p.x*zoom+Math.sin(p.z)*zoom),
                    height/2-(int)(p.y*zoom-Math.cos(p.z)*zoom)
                );

               g.drawLine(
                    width/2+(int)(p.x*zoom+zoom*Math.sin(p.z)),
                    height/2-(int)(p.y*zoom-zoom*Math.cos(p.z)),
                    width/2+(int)(p.x*zoom+0.6*zoom*Math.sin(Math.PI/8+p.z)),
                    height/2-(int)(p.y*zoom-0.6*zoom*Math.cos(Math.PI/8+p.z))
                );
			} else if (visual_method == 2) {
				g.fillOval(
						x-(int)(zoom/2.0*1.5),
						y-(int)(zoom/2.0*1.5),
						(int)(zoom*1.5),
						(int)(zoom*1.5)
				);
                g.setColor(Color.BLACK);
                g.drawLine(
                    width/2+(int)(p.x*zoom),
                    height/2-(int)(p.y*zoom), 
                    width/2+(int)(p.x*zoom+Math.sin(p.z)*zoom),
                    height/2-(int)(p.y*zoom-Math.cos(p.z)*zoom)
                );
			}

	    	if (line && count != 0) {
	    		g.setColor(Color.LIGHT_GRAY);
	    		g.drawLine(x_tmp, y_tmp, x, y);
	    	}

	    	x_tmp = x;
	    	y_tmp = y;
	    	count++;
		}
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        int width = (int) (getWidth());
        int height = (int) (getHeight());
        int width2 = (int) (getWidth()+2*centerx);
        int height2 = (int) (getHeight()+2*centery);
        super.paintComponent(g);
    
        g.setColor(new Color(20, 20, 20));
        
        int initial_x = height2/2;
        while (initial_x < width) {
        	initial_x += grid*zoom;
        	g.drawLine(0, initial_x, width, initial_x); 
        }
        initial_x = height2/2;
        while (initial_x > 0) {
        	initial_x -= grid*zoom;
        	g.drawLine(0, initial_x, width, initial_x); 
        }
        int initial_y = width2/2;
        while (initial_y < width) {
        	initial_y += grid*zoom;
            g.drawLine(initial_y, 0, initial_y, height);
        }
        initial_y = width2/2;
        while (initial_y > 0) {
        	initial_y -= grid*zoom;
            g.drawLine(initial_y, 0, initial_y, height);
        }

        g.setColor(Color.ORANGE);
        g.drawLine(width2/2, 0, width2/2, height);
        g.drawLine(0, height2/2, width, height2/2);

        double dx, dy;
        dx = 0;
        dy = -73.8;
        dx *= zoom;
        dy *= zoom;
        if (image == true) {
            g.drawImage(img,
                (int)(width2/2+dx),
                (int)(height2/2+dy),
                (int)(width2/2+95.5*zoom+dx),
                (int)(height2/2+73.8*zoom+dy),
                0, 0,
                img.getWidth(null), img.getHeight(null),
                null
            );
        }
        
        drawModel(g);
    }
    
    /**
     * Adiciona um ponto ao mapa
     * @param p ponto
     */

    public float measurementProbability(Double expected,
                                         Double measured,
                                         Double w_object,
                                         Double w_others,
                                         Double w_lim,
                                         Double variance){
        Double p_object = Math.exp((-Math.pow((measured - expected),2))/(2 * variance))/Math.sqrt(2 * Math.PI * variance);
        Double p_others = 1/256.0;
        Double p_lim;
        if (measured != 255) {
            p_lim = 0.0;
        } else {
            p_lim = 1.0;
        }

        return (float) (w_object * p_object + w_others * p_others + w_lim * p_lim);
    }

    public void addPoint(Point3D p, Double expected, Double measured) {
    	lista_pontos.add(p);
    	lista_cores.add(Color.getHSBColor((float) (30 * measurementProbability(expected, measured, 0.33266622284045777, 0.6126025162026004, 0.05473126095694189, 12.566004300171572)), 1, (float) 0.6));
    	repaint();
	}
    
    public void showLine () {
    	line = !line;
    	repaint();
    }


    public void showImg () {
        image = !image;
        repaint();
    }
    
    public void setVisual (int method) {
    	visual_method = method;
    	repaint();
    }
    

    public void save () {
    	Integer name = new Integer((int) (Math.random()*1000000));
    	BufferedImage imagebuf = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    	Graphics g = imagebuf.createGraphics();
    	g.fillRect(0, 0, imagebuf.getWidth(), imagebuf.getHeight());
    	print(g);
    	try {
			ImageIO.write(imagebuf, "png",  new File(name.toString()+".png"));
			JOptionPane.showMessageDialog(null, "Image saved.");
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Image not saved.");
		}
    }
}
