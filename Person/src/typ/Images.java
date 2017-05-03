package typ;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

//图片工厂类  用来产生图片
public class Images {
	public static int WALLWIDTH;// 功能块的宽度
	public static int WALLHEIGHT;// 功能块的高度
	public static int PERSONWIDTH; // 小人的宽度
	public static int PERSONHEIGHT;// 小人的高度
	public static int MISSILEWIDTH;// 小人的高度
	public static int MISSILEHEIGHT;// 小人的高度

	public static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] Images = null;
	private static Map<String, Image> imgs = new HashMap<String, Image>();
	// 通过反射获取图片
	static {
		Images = new Image[] {

				tk.getImage(Images.class.getClassLoader().getResource(
						"Imgs/1.jpg")),
				tk.getImage(Images.class.getClassLoader().getResource(
						"Imgs/2.jpg")),
				tk.getImage(Images.class.getClassLoader().getResource(
						"Imgs/3.jpg")),
				tk.getImage(Images.class.getClassLoader().getResource(
						"Imgs/4.jpg")),
				tk.getImage(Images.class.getClassLoader().getResource(
						"Imgs/5.gif")),
				tk.getImage(Images.class.getClassLoader().getResource(
						"Imgs/6.gif")),
				tk.getImage(Images.class.getClassLoader().getResource(
						"Imgs/7.jpg")),
				tk.getImage(Images.class.getClassLoader().getResource(
						"Imgs/8.jpg")),
				tk.getImage(Images.class.getClassLoader().getResource(
						"Imgs/9.jpg")),
				tk.getImage(Images.class.getClassLoader().getResource(
						"Imgs/10.jpg")),
				tk.getImage(Images.class.getClassLoader().getResource(
						"Imgs/11.gif")),
				tk.getImage(Images.class.getClassLoader().getResource(
						"Imgs/missile.png")) };
		imgs.put("基本信息", Images[0]);
		imgs.put("兴趣", Images[1]);
		imgs.put("我的性格", Images[2]);
		imgs.put("我的目标", Images[3]);
		imgs.put("向左跑的人", Images[4]);
		imgs.put("向右跑的人", Images[5]);
		imgs.put("窗口背景", Images[6]);
		imgs.put("兴趣介绍", Images[7]);
		imgs.put("性格介绍", Images[8]);
		imgs.put("目标介绍", Images[9]);
		imgs.put("死神", Images[10]);
		imgs.put("子弹", Images[11]);
	}

	public static Image getImgs(String s) {
		PERSONWIDTH = Images[4].getWidth(null);// 读取功能块的宽度
		PERSONHEIGHT = Images[4].getHeight(null);// 读取功能块的高度
		WALLWIDTH = Images[0].getWidth(null);// 读取小人的宽度
		WALLHEIGHT = Images[0].getHeight(null);// 读取小人的高度
		MISSILEWIDTH=Images[11].getWidth(null);
		MISSILEHEIGHT=Images[11].getHeight(null);

		return imgs.get(s);
	}

}
