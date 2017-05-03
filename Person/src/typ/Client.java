package typ;

import java.awt.Color;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 这个类的作用是该小程序的主窗口
 *
 * @author 唐岳平
 *
 */
public class Client extends Frame {
	public static final int GAME_WIDTH = 1280;// 窗口宽度
	public static final int GAME_HEIGHT = 720;// 窗口高度
	boolean isInfo = false, isInterest = false, isCharacter = false,
			isGoal = false;// 判断是否碰撞的量

	private Person person = new Person(380, 300, Direction.STOP, this);// 加入小人对象实例
	private Wall wall = new Wall(100, 70);// 加入墙对象实例
	 Image offScreenImage = null;//
		List<Missile> missiles = new ArrayList<Missile>();

	// 双缓冲刷新方法，消除闪烁问题
	public void update(Graphics g) {

		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics goffScreen = offScreenImage.getGraphics();
		Color c = goffScreen.getColor();
		goffScreen.setColor(new Color(252, 252, 243));
		goffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		goffScreen.setColor(c);
		paint(goffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	// 绘画 类比：paint是画家,Graphics对象是画笔
	public void paint(Graphics g) {
		g.setFont(new Font("宋体", Font.BOLD, 12));
		g.drawImage(Images.getImgs("窗口背景"), 0, 0, null);
		g.drawString("简单自我介绍(可使用上下左右方向键控制小人！)", 10, 40);
		g.drawString("温馨提示：若要获取信息，请控制小人到该区域！", 10, 55);
		person.draw(g);
		wall.draw(g);
		person.konckWall(wall);

		if (isInfo) {
			g.setColor(new Color(242, 239, 188));
			g.fill3DRect(370, 112, 380, 380, true);
			g.setColor(new Color(0, 0, 0));
			g.setFont(new Font("宋体", Font.BOLD, 30));
			g.drawString("基本信息:", 380, 170);
			g.drawString("姓名：唐岳平", 380, 210);
			g.drawString("性别：男", 380, 250);
			g.drawString("身高：168", 380, 290);
			g.drawString("生日：1996年2月19日", 380, 330);
			g.drawString("星座：白羊座", 380, 370);
		}
		if (isInterest) {
			g.drawImage(Images.getImgs("兴趣介绍"), 370, 232, null);
		}
		if (isCharacter) {
			g.drawImage(Images.getImgs("性格介绍"), 370, 232, null);
		}
		if (isGoal) {
			g.drawImage(Images.getImgs("目标介绍"), 370, 232, null);
		}

		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.draw(g);
			m.hitPerson(person);
			m.hitWall(wall);
		}
	}

	// 窗口配置方法
	void lauchFrame() {
		setLocation(200, 200);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("唐岳平");
		this.setResizable(false);
		this.setBackground(new Color(252, 252, 243));
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent w) {
				System.exit(0);
			}
		});
		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start();
		new Audio(0);
	}



	// 重画的线程
	private class PaintThread implements Runnable {
		public void run() {
			while (true) {

				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	// 键盘监听
	private class KeyMonitor extends KeyAdapter {
		public void keyReleased(KeyEvent k) {
			person.keyReleased(k);
		}

		public void keyPressed(KeyEvent k) {
			person.keyPressed(k);
		}
	}



}
