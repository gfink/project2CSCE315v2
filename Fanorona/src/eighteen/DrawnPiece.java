package eighteen;
import java.awt.*;
import java.awt.geom.Ellipse2D;

import javax.swing.*;
//using model code from here http://harryjoy.com/2011/08/21/different-button-shapes-in-swing/
public class DrawnPiece extends JButton{
	private static final long serialVersionUID = 5422176987616663895L;
	public int xLoc;
	public int yLoc;
	public Color pColor;
	Shape shape;
	public DrawnPiece(int x, int y, Color c)
	{
		super();
		xLoc = x;
		yLoc = y;
		pColor = c;
		setPreferredSize(new Dimension(45, 45));
		setContentAreaFilled(false);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Dimension size = this.getSize();
		int d = Math.min(size.width, size.height) - 4;
		int x = (size.width - d) / 2;
		int y = (size.height - d) / 2;
		g.setColor(pColor);
		g.fillOval(x, y, d, d);
	}
	
	protected void paintBorder(Graphics g)
	{
		g.setColor(Color.BLACK);
		Dimension size = this.getSize();
		int d = Math.min(size.width, size.height) - 4;
		int x = (size.width - d) / 2;
		int y = (size.height - d) / 2;
		g.drawOval(x, y, d, d);
	}
	
	public boolean contains(int x, int y)
	{
		if (shape == null || !shape.getBounds().equals(getBounds()))
		{
			shape = new Ellipse2D.Float(0,0,getWidth(),getHeight());
		}
		return shape.contains(x,y);
	}
}
