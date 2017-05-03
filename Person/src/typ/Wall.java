package typ;

import java.awt.*;

public class Wall {
	private int x, y;
	private Images img;
	public Wall(int x, int y) {
		this.x = x;
		this.y = y;
	}
	//加入功能块：以图片的形式画出
	public void draw(Graphics g) {
		g.drawImage(Images.getImgs("基本信息"), x, y, null);
		g.drawImage(Images.getImgs("兴趣"), x, y + (img.PERSONHEIGHT + img.WALLHEIGHT), null);
		g.drawImage(Images.getImgs("我的性格"), x, y + (img.PERSONHEIGHT + img.WALLHEIGHT) * 2, null);
		g.drawImage(Images.getImgs("我的目标"), x, y + (img.PERSONHEIGHT + img.WALLHEIGHT) * 3, null);
	}
	//获得能产生碰撞的位置大小
	public Rectangle getRect1() {
		return new Rectangle(x, y, img.WALLWIDTH, img.WALLHEIGHT);
	}
	public Rectangle getRect2() {
		return new Rectangle(x, y + (50 + img.WALLHEIGHT), img.WALLWIDTH, img.WALLHEIGHT);
	}
	public Rectangle getRect3() {
		return new Rectangle(x, y + (50 + img.WALLHEIGHT) * 2, img.WALLWIDTH, img.WALLHEIGHT);
	}
	public Rectangle getRect4() {
		return new Rectangle(x, y + (50 + img.WALLHEIGHT) * 3, img.WALLWIDTH, img.WALLHEIGHT);
	}
}
