package typ;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JFrame {
	private static int score = 0; //分数
	private GameBoard gameBoard;
	final Font[] fonts = { new Font("Helvetica Neue", Font.BOLD, 48), new Font("Helvetica Neue", Font.BOLD, 42),
			new Font("Helvetica Neue", Font.BOLD, 36), new Font("Helvetica Neue", Font.BOLD, 30),
			new Font("Helvetica Neue", Font.BOLD, 24) };

	private JLabel ltitle;
	private JLabel lsctip;
	private JLabel lscore;
	private JLabel lgatip;



	public Window() {
		// Container:一般 Abstract Window Toolkit(AWT) 容器对象
		// 一个可包含其他 AWT 组件的组件。
		this.setLayout(null);// 设置此容器的布局管理器
	}

	public void initView() {
		ltitle = new JLabel("2048", JLabel.CENTER);
		ltitle.setFont(new Font("", Font.BOLD, 50));
		ltitle.setForeground(new Color(0x776e65));
		ltitle.setBounds(0, 0, 120, 60);

		lsctip = new JLabel("SCORE", JLabel.CENTER);
		lsctip.setFont(new Font("", Font.BOLD, 16));
		lsctip.setForeground(new Color(0xeee4da));
		lsctip.setOpaque(true);//只有设置为true背景色才生效
		lsctip.setBackground(new Color(0xbbada0));
		lsctip.setBounds(290, 5, 100, 25);

		lscore = new JLabel("0", JLabel.CENTER);
		lscore.setFont(new Font("Helvetica Neue", Font.BOLD, 22));
		lscore.setForeground(Color.WHITE);
		lscore.setOpaque(true);
		lscore.setBackground(new Color(0xbbada0));
		lscore.setBounds(290, 30, 100, 25);

		lgatip = new JLabel("按方向键可以控制方块的移动，按ESC键可以重新开始游戏。", JLabel.LEFT);
		lgatip.setFont(new Font("Helvetica Neue", Font.ITALIC, 13));
		lgatip.setForeground(new Color(0x776e65));
		lgatip.setBounds(10, 60, 390, 30);
		// 游戏面板组件
		gameBoard = new GameBoard();
		gameBoard.setPreferredSize(new Dimension(400, 400));
		gameBoard.setBackground(new Color(0xbbada0));
		gameBoard.setBounds(0, 100, 400, 400);// 移动组件并调整其大小。
		gameBoard.setFocusable(true);// 将此 Component 的焦点状态设置为指定值。
		// 把组件加入窗体
		this.add(gameBoard);
		this.add(ltitle);
		this.add(lsctip);
		this.add(lscore);
		this.add(lgatip);

		this.setTitle("2048:获胜即得唐岳平的有关信息");
		//setPreferredSize:
		this.getContentPane().setPreferredSize(new Dimension(400, 500) );
		//JFrame直接调用setBackground设置背景色不生效
		this.getContentPane().setBackground(new Color(0xfaf8ef));
		//
		this.setResizable(false);
		this.pack();//获得最佳大小

	}

	class GameBoard extends JPanel implements KeyListener {
		private static final int GAP_TILE = 16;// 瓦片之间间隙
		private static final int ARC_TILE = 16;// 瓦片圆角弧度
		private static final int SIZE_TILE = 80;// 瓦片的大小
		private Tile[][] tiles = new Tile[4][4];
		private boolean isOver;
		private boolean isMove;
		private boolean isAward;

		public GameBoard() {
			initGame();
			addKeyListener(this);

		}
		 //如果你玩到了2048，则跳出个人简介窗口
		public void victoryAward(){
          for(int i=0;i<4;i++){
			  for (int j=0;j<4;j++){
			  	if (tiles[i][j].value>=2048){
			  		isAward=true;
					Client c = new Client();
					c.lauchFrame();
					c.setFocusable(true);
					c.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent w) {
							c.setVisible(false);
						}
					});
				}
			  }
		  }

		}

		private void initGame() {
			// 初始化地图
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					tiles[i][j] = new Tile();
				}
			}
			// 生成2个瓦片
			createTile();
			createTile();
			// 初始化参数
			isMove = false;
			isOver = false;

		}

		private void createTile() {
			// 获取当前空白的瓦片，并加入列表 ppp
			List<Tile> list = getBlankTiles();
			if (!list.isEmpty()) {
				Random r = new Random();
				int index = r.nextInt(list.size());
				Tile tile = list.get(index);
				// 初始化瓦片的值为2或4 ppp
				tile.value = r.nextInt(100) > 50 ? 4 : 2;

			}

		}

		// 获取当前空白的瓦片，加入列表返回 ppp
		private List<Tile> getBlankTiles() {
			List<Tile> list = new ArrayList<Tile>();
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (tiles[i][j].value == 0) {
						list.add(tiles[i][j]);
					}
				}
			}

			return list;
		}

		public void paint(Graphics g) {
			super.paint(g);
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					drawTile(g, i, j);
				}
			}
		if(!isAward)	victoryAward();
		}

		private void drawTile(Graphics gg, int i, int j) {
			Graphics2D g = (Graphics2D) gg;
			//消除线段的锯齿状边缘
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			//  笔划规范化控制提示键 笔划规范化控制提示值——几何形状应当规范化，以提高均匀性或直线间隔和整体美观。
//			g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
			Tile tile = tiles[i][j];
			// 绘制瓦片背景
			g.setColor(tile.getBackground());
			//注意：横坐标用j计算,纵坐标用i计算
			g.fillRoundRect(GAP_TILE + (GAP_TILE + SIZE_TILE) * j ,
					GAP_TILE + (GAP_TILE + SIZE_TILE) * i ,
					SIZE_TILE , SIZE_TILE , ARC_TILE, ARC_TILE);
			// 绘制瓦片文字
			g.setColor(tile.getForeground());
			Font font = tile.getTileFont();
			g.setFont(font);
			// 类定义字体规格对象，该对象封装将在特定屏幕上呈现特定字体的有关信息。
			FontMetrics fms = getFontMetrics(font);
			String value = String.valueOf(tile.value);
			// 注意：横坐标用j计算,纵坐标用i计算
			g.drawString(value, GAP_TILE + (GAP_TILE + SIZE_TILE) * j + (SIZE_TILE - fms.stringWidth(value)) / 2,
					GAP_TILE + (GAP_TILE + SIZE_TILE) * i + (SIZE_TILE - fms.getAscent() - fms.getDescent()) / 2
							+ fms.getAscent());
			tiles[i][j].ismerge = false;

		}

		private void doMove(Tile src, Tile dst) {
			dst.swap(src);
			src.clear();
			isMove = true;

		}

		private void doMerge(Tile src, Tile dst) {
			dst.value = dst.value << 1;
			dst.ismerge = true;
			src.clear();
			score += dst.value;
			isMove = true;

		}

		public void keyPressed(KeyEvent e) {
			boolean moved = false;
			switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					initGame();
					break;
				case KeyEvent.VK_LEFT:
					moved = moveLeft();
					inovkerCreateTile();
					checkGameOver(moved);
					break;
				case KeyEvent.VK_RIGHT:
					moved = moveRight();
					inovkerCreateTile();
					checkGameOver(moved);
					break;
				case KeyEvent.VK_UP:
					moved = moveUp();
					inovkerCreateTile();
					checkGameOver(moved);
					break;
				case KeyEvent.VK_DOWN:
					moved = moveDown();
					inovkerCreateTile();
					checkGameOver(moved);
					break;
			}
			repaint();

		}

		private void checkGameOver(boolean moved) {
			lscore.setText(score + "");//ppp
			if (!getBlankTiles().isEmpty()) {
				return;
			}

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (tiles[i][j].value == tiles[i][j + 1].value || tiles[i][j].value == tiles[i + 1][j].value) {
						isOver = false;
						return;
					}

				}

			}
			isOver = true;
		}

		private void inovkerCreateTile() {
			if (isMove) {
				createTile();
				isMove = false;
			}

		}

		public void keyTyped(KeyEvent e) {

		}

		public void keyReleased(KeyEvent e) {

		}

		private boolean moveLeft() {
			isMove = false;
			for (int i = 0; i < 4; i++) {
				for (int j = 1; j < 4; j++) {
					int k = j;

					while (k > 0 && tiles[i][k].value != 0 && !tiles[i][k - 1].ismerge) {
						if (tiles[i][k - 1].value == 0) {
							doMove(tiles[i][k], tiles[i][k - 1]);
						} else if (tiles[i][k - 1].value == tiles[i][k].value) {
							doMerge(tiles[i][k], tiles[i][k - 1]);
							break;
						} else {
							break;
						}
						k--;
					}
				}
			}

			return isMove;
		}

		private boolean moveRight() {
			isMove = false;
			for (int i = 0; i < 4; i++) {
				for (int j = 2; j > -1; j--) {
					int k = j;
					// 当前移动瓦片不能到达边界，不能为空白瓦片，前方瓦片不能是合成瓦片
					while (k < 3 && tiles[i][k].value != 0 && !tiles[i][k + 1].ismerge) {
						if (tiles[i][k + 1].value == 0) {
							doMove(tiles[i][k], tiles[i][k + 1]);
						} else if (tiles[i][k + 1].value == tiles[i][k].value) {
							doMerge(tiles[i][k], tiles[i][k + 1]);
							break;
						} else {
							break;
						}
						k++;
					}
				}
			}
			return isMove;
		}

		private boolean moveUp() {
			isMove = false;
			for (int j = 0; j < 4; j++) {
				for (int i = 1; i < 4; i++) {
					int k = i;
					// 当前移动瓦片不能到达边界，不能为空白瓦片，前方瓦片不能是合成瓦片
					while (k > 0 && tiles[k][j].value != 0 && !tiles[k - 1][j].ismerge) {
						if (tiles[k - 1][j].value == 0) {
							doMove(tiles[k][j], tiles[k - 1][j]);
						} else if (tiles[k - 1][j].value == tiles[k][j].value) {
							doMerge(tiles[k][j], tiles[k - 1][j]);
							break;
						} else {
							break;
						}
						k--;
					}
				}
			}
			return isMove;
		}

		private boolean moveDown() {
			isMove = false;
			for (int j = 0; j < 4; j++) {
				for (int i = 2; i > -1; i--) {
					int k = i;
					// 当前移动瓦片不能到达边界，不能为空白瓦片，前方瓦片不能是合成瓦片
					while (k < 3 && tiles[k][j].value != 0 && !tiles[k + 1][j].ismerge) {
						if (tiles[k + 1][j].value == 0) {
							doMove(tiles[k][j], tiles[k + 1][j]);
						} else if (tiles[k + 1][j].value == tiles[k][j].value) {
							doMerge(tiles[k][j], tiles[k + 1][j]);
							break;
						} else {
							break;
						}
						k++;
					}
				}
			}
			return isMove;
		}

	}

	class Tile {
		public int value;// 显示的数字
		public boolean ismerge;// 是否是合并的

		public Tile() {
			clear();
		}

		private void clear() {
			value = 0;
			ismerge = false;

		}

		public void swap(Tile t) {
			this.value = t.value;
			this.ismerge = t.ismerge;
		}

		// 前景
		public Color getForeground() {
			switch (value) {
				case 0:
					return new Color(0xcdc1b4);
				case 2:
				case 4:
					return new Color(0x776e65);
				default:
					return new Color(0xf9f6f2);
			}
		}

		// 背景
		public Color getBackground() {
			switch (value) {
				case 0:
					return new Color(0xcdc1b4);
				case 2:
					return new Color(0xeee4da);
				case 4:
					return new Color(0xede0c8);
				case 8:
					return new Color(0xf2b179);
				case 16:
					return new Color(0xf59563);
				case 32:
					return new Color(0xf67c5f);
				case 64:
					return new Color(0xf65e3b);
				case 128:
					return new Color(0xedcf72);
				case 256:
					return new Color(0xedcc61);
				case 512:
					return new Color(0xedc850);
				case 1024:
					return new Color(0xedc53f);
				case 2048:
					return new Color(0xedc22e);
				case 4096:
					return new Color(0x65da92);
				case 8192:
					return new Color(0x5abc65);
				case 16384:
					return new Color(0x248c51);
				default:
					return new Color(0x248c51);
			}
		}

		public Font getTileFont() {
			int index = value < 100 ? 1 : value < 1000 ? 2 : value < 10000 ? 3 : 4;
			return fonts[index];

		}

	}
}