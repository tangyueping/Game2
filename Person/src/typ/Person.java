package typ;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;



public class Person {
	private static final int XSPEED = 9;
	private static final int YSPEED = 9;
	private boolean live=true;
	public static Random r=new Random();


	private int x, y;
	private boolean bL = false, bR = false, bU = false, bD = false;
	private Direction dir,ptdir;
	Client c;
	Images img;


	public Person(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	public Person(int x, int y, Direction dir, Client c) {
		this(x, y, dir);
		this.c = c;
	}

	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}

	//小人的移动方法
	void move() {
		switch (dir) {
			case U:
				y = y - YSPEED;
				break;
			case D:
				y = y + YSPEED;
				break;
			case L:
				x = x - XSPEED;
				break;
			case R:
				x = x + XSPEED;
				break;
			case UL:
				y = y - YSPEED;
				x = x - XSPEED;
				break;
			case UR:
				y = y - YSPEED;
				x = x + XSPEED;
				break;
			case DL:
				y = y + YSPEED;
				x = x - XSPEED;
				break;
			case DR:
				y = y + YSPEED;
				x = x + XSPEED;
				break;
			case STOP:
				break;
		}

		//边界
		if (x < -5)
			x = -5;
		if (y < 17)
			y = 17;
		if (x > c.GAME_WIDTH- img.PERSONWIDTH)
			x = c.GAME_WIDTH - img.PERSONWIDTH;
		if (y > c.GAME_HEIGHT- img.PERSONHEIGHT)
			y = c.GAME_HEIGHT - img.PERSONHEIGHT;



	}
	//描绘有关小人，以图片形式画出
	public void draw(Graphics g) {
		if(live){
			if(dir==Direction.R||dir==Direction.UR||dir==Direction.DR)
				g.drawImage(Images.getImgs("向右跑的人"), x, y, null);
			else  g.drawImage(Images.getImgs("向左跑的人"), x, y, null);
		}
		g.drawImage(Images.getImgs("死神"), 350, 70, null);
		move();
		enemy();

	}

	void enemy() {
		Direction[] dirs = Direction.values();
		int rn = r.nextInt(9);
		ptdir = dirs[rn];
		if (r.nextInt(80) > 20) {
			this.fire();
		}

	}

	// 开炮
	public  Missile fire() {

		if (!live)
			return null;
		int x = 450;
		int y = 150;
		Missile m = new Missile(x, y, ptdir, this.c);
		c.missiles.add(m);
		return m;
	}


	// 设定按下键盘情况的方法
	public void keyPressed(KeyEvent k) {
		int key = k.getKeyCode();
		switch (key) {
			case KeyEvent.VK_UP:
				bU = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;
			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			case KeyEvent.VK_F2:
				if (!live) {
					this.live = true;
				}
				break;
		}
		selectDir();

	}

	// 设定松开键盘情况的方法
	public void keyReleased(KeyEvent k) {
		int key = k.getKeyCode();
		switch (key) {
			case KeyEvent.VK_UP:
				bU = false;
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				break;
			case KeyEvent.VK_LEFT:
				bL = false;
				break;
			case KeyEvent.VK_RIGHT:
				bR = false;
				break;
		}
		selectDir();
	}



	//方向选择的方法
	private void selectDir() {
		if (!bL && bU && !bR && !bD)
			dir = Direction.U;
		else if (!bL && !bU && !bR && bD)
			dir = Direction.D;
		else if (bL && !bU && !bR && !bD)
			dir = Direction.L;
		else if (!bL && !bU && bR && !bD)
			dir = Direction.R;
		else if (!bL && bU && bR && !bD)
			dir = Direction.UR;
		else if (bL && bU && !bR && !bD)
			dir = Direction.UL;
		else if (!bL && !bU && bR && bD)
			dir = Direction.DR;
		else if (bL && !bU && !bR && bD)
			dir = Direction.DL;
		else if (!bL && !bU && !bR && !bD)
			dir = Direction.STOP;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, img.PERSONWIDTH, img.PERSONHEIGHT);

	}
	//产生碰撞的事件方法
	public void konckWall(Wall w) {

		if (this.getRect().intersects(w.getRect1())) c.isInfo=true;
		else c.isInfo=false;

		if (this.getRect().intersects(w.getRect2())) c.isInterest=true;
		else c.isInterest=false;

		if (this.getRect().intersects(w.getRect3())) c.isCharacter=true;
		else c.isCharacter=false;

		if (this.getRect().intersects(w.getRect4())) c.isGoal=true;
		else c.isGoal=false;

	}

}
