package game;

import java.awt.Color;
import java.awt.Graphics;

public class Food implements Drawable {
	
	private static final Color DEFAULT_COLOR = Color.RED;
	
	private Block body;
	
	public Block getBody() {
		return body;
	}
	
	public void reset(int x, int y) {
		body = new Block(x, y, DEFAULT_COLOR);
	}
	
	public void update(Graphics g) {
		body.update(g);
	}
}
