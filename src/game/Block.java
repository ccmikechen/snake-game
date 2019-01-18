package game;

import java.awt.Color;
import java.awt.Graphics;

public class Block implements Drawable {
	public static final int SIZE = 20;
	
	public int x;
	public int y;
	private Color color;
	
	public Block(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public void update(Graphics g) {
		g.setColor(color);
		
		int rx = x * SIZE;
		int ry = y * SIZE;
		g.fillRect(rx + 1, ry + 1, SIZE - 1, SIZE - 1);
	}
}
