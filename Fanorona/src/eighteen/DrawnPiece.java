package eighteen;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javax.swing.*;
//using model code from here http://harryjoy.com/2011/08/21/different-button-shapes-in-swing/
public class DrawnPiece extends JButton{
	private static final long serialVersionUID = 5422176987616663895L;
	public int xLoc;
	public int yLoc;
	public Color pColor;
	public boolean selected;
	Shape shape;
	public DrawnPiece(int x, int y, Color c) {
		super();
		xLoc = x;
		yLoc = y;
		pColor = c;
		selected = false;
		setPreferredSize(new Dimension(45, 45));
		setContentAreaFilled(false);
	}
	
	protected void paintComponent(Graphics g) {
		/* 
		 * If the piece is gray, don't draw it.
		 */
		if(pColor != Color.GRAY) {
			super.paintComponent(g);
			Dimension size = this.getSize();
			int d = Math.min(size.width, size.height) - 4;
			int x = (size.width - d) / 2;
			int y = (size.height - d) / 2;
			g.setColor(pColor);
			// Is a possibility for a piece to move to
			if(pColor == Color.YELLOW) {
				Point2D center = new Point2D.Float(x + d/2, y + d/2);
				float[] dist = {0.3f, .6f};
				Color[] colors = {Color.YELLOW, Color.LIGHT_GRAY};
				RadialGradientPaint p = new RadialGradientPaint(center, d/2, dist, colors);
				((Graphics2D)g).setPaint(p);
				g.fillOval(x + (int)(d/4), y + (int)(d/4), (int)(d*.5), (int)(d*.5));
			}
			// It's either black or white
			else {
				// If it was clicked on by the player, highlight it
				if(selected) {
					Point2D center = new Point2D.Float(x + d/2, y + d/2);
					float[] dist = {0.0f, 0.9f};
					Color[] colors = {Color.GREEN, pColor};
					RadialGradientPaint p = new RadialGradientPaint(center, d/2, dist, colors);
					((Graphics2D)g).setPaint(p);
				}
				// Not clicked on by user
				else
					g.setColor(pColor);
				g.fillOval(x, y, d, d);
			}
		}
	}
	
	// Puts a black border on all the pieces if the location contains a piece
	protected void paintBorder(Graphics g) {
		if(pColor != Color.GRAY && pColor != Color.YELLOW) { 
			g.setColor(Color.BLACK);
			Dimension size = this.getSize();
			int d = Math.min(size.width, size.height) - 4;
			int x = (size.width - d) / 2;
			int y = (size.height - d) / 2;
			g.drawOval(x, y, d, d);
		}
	}
	
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds()))
		{
			shape = new Ellipse2D.Float(0,0,getWidth(),getHeight());
		}
		return shape.contains(x,y);
	}
}
