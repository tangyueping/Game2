package typ;

import java.awt.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class Missile {

	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	private int x, y;
	private boolean live = true;
	Direction dir;
	Client c;

	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;

	}

	public Missile(int x, int y, Direction dir, Client c) {
		this(x, y, dir);
		this.c = c;

	}

	public void draw(Graphics g) {
		if (!live) {
			c.missiles.remove(this);
			return;
		}
		g.drawImage(Images.getImgs("子弹"), x, y, null);

		move();

	}

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
		}
		if (x < 0 || y < 0 || x > c.GAME_WIDTH
				|| y > c.GAME_HEIGHT) {
			this.live = false;

		}
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, Images.WALLWIDTH,
				Images.MISSILEHEIGHT);
	}

	public boolean hitPerson(Person t) {
		if (this.live && t.isLive() && this.getRect().intersects(t.getRect())
				) {
			t.setLive(false);
			this.live = false;
			return true;

		}
		return false;
	}




	public void hitWall(Wall w) {
		if (this.getRect().intersects(w.getRect1())) {
			this.live=false;
		}
		if (this.getRect().intersects(w.getRect2())) {
			this.live=false;
		}
		if (this.getRect().intersects(w.getRect3())) {
			this.live=false;
		}
		if (this.getRect().intersects(w.getRect4())) {
			this.live=false;
		}
	}

}
