package game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static game.SwingConsole.*;

public class SnakeGame extends JFrame {

	
	public static final int ORIGIN = 30;	//基底座標
	
	private static final Color BACKGROUND_COLOR = Color.white;
	private static final Color LINE_COLOR = new Color(200,200,200);
	
	private static final int DEFAULT_SPEED	= 500;
	
	private GameDraw drawer;	
	
	private GameMap map;		
	private Snake snake;		
	private Food food;			
	
	private int width;
	private int height;
	private int speed;
	private int score;
	
	private boolean isOver = true;
	
	//遊戲執行迴圈
	public void loop() {
		while(!isOver) {
			
			if(snake.checkFood(food.getBody())) {
				//判斷食物是否在蛇的眼前
				snake.addHead(food.getBody().x, food.getBody().y);
				resetFood();
				levelUp();
				
			} else if(snake.checkCollide(width, height)) {
				//判斷是否撞到牆或身體
				isOver = true;
				gameOver();
				break;
			} else
				snake.walk();
			
			//迴圈延遲時間
			try {
				TimeUnit.MILLISECONDS.sleep(speed);
			} catch (InterruptedException e) {
				System.exit(0);
			}
		}
	}
	
	//重新開始
	public void restart() {
		map = new GameMap(width, height, LINE_COLOR);
		snake = new Snake();
		food = new Food();
		drawer.restart(map, snake, food);
		
		speed = DEFAULT_SPEED;
		score = 0;
		isOver = false;
		
		resetFood();
		
		Runnable r = () -> loop();
		new Thread(r).start();
	}
	
	//重新設定食物位置
	private void resetFood() {
		int newX, newY;
		Random rand = new Random();
		while(true) {
			newX = rand.nextInt(width/Block.SIZE);
			newY = rand.nextInt(height/Block.SIZE);
			if(!snake.checkIsBody(newX, newY))
				break;
		}
		food.reset(newX, newY);
	}
	
	//提升速度
	private void levelUp() {
		if(speed > 30) 
			speed /= 1.1;
		score++;
	}
	
	//遊戲結束顯示文字
	private void gameOver() {
		drawer.addDraw(new Drawable() {
			public void update(Graphics g) {
				g.setColor(Color.BLUE);
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
				g.drawString("Game Over!", width/2 - 200, height/2-50);
				
				g.setColor(Color.CYAN);
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
				g.drawString("Press Space", width/2 - 120, height/2);
			}
		});
	}
	
	//設定鍵盤監聽器
	private void addListeners() {
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				try {
				if(!isOver)
					switch(arg0.getKeyCode()) {
					case 37 : snake.turn(snake.LEFT);	break;
					case 38 : snake.turn(snake.UP);		break;
					case 39 : snake.turn(snake.RIGHT);	break;
					case 40 : snake.turn(snake.DOWN);	break;
					}
				else if(arg0.getKeyCode() == 32)
					restart();
				} catch(Exception e) {
					System.err.println(e);
				}
			}
		});
	}
	
	//建構遊戲
	public SnakeGame(int width, int height) {
		this.width = width;
		this.height = height;
		this.setBackground(BACKGROUND_COLOR);
		this.setLayout(null);
		
		drawer = new GameDraw(width, height);
		drawer.setLocation(ORIGIN, ORIGIN);
		add(drawer);
		
		addListeners();
		restart();
	}
	
	public static void main(String[] args) {
		SnakeGame game = new SnakeGame(500, 300);
		run(game, 580, 400);
	}
}
