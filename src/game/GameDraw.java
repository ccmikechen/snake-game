package game;

import javax.swing.*;

import java.awt.Graphics;
import java.util.*;

public class GameDraw extends JPanel {
	
	public List<Drawable> drawList = new ArrayList<Drawable>();
	
	public GameDraw(int width, int height) {
		this.setSize(width+1, height+1);
	}
	
	public void addDraw(Drawable d) {
		drawList.add(d);
	}
	
	public void restart(Drawable... list) {
		drawList.clear();
		drawList.addAll(Arrays.asList(list));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawList.forEach(d -> d.update(g));
		repaint();
	}
}
