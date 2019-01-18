package game;

import java.awt.Color;
import java.awt.Graphics;

public class GameMap implements Drawable {

	private int width;
	private int height;
	private Color color;
	
	public GameMap(int width, int height, Color color) {
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public void update(Graphics g) {
		g.setColor(color);
		
		//Áa½u
		for(int i = 0; i <= width; i += Block.SIZE)
			g.drawLine(i, 0, i, height);
		
		//¾î½u
		for(int i = 0; i <= height; i += Block.SIZE)
			g.drawLine(0, i, width, i);
	}
}
