package typ;

import java.io.IOException;

import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Audio implements Runnable {
	private String url="music/";
	private int type=0;

	public Audio(int type){
		this.type=type;
		//音乐选项
		switch(type){
			case 0:
				url=url+"1.wav";//开场音乐
				break;
			case 1:
				url=url+"fire.wav";//开火音乐
				break;
			case 2:
				url=url+"hit.wav";//打墙音乐
				break;
			case 3:
				url=url+"blast.wav";//爆炸音乐
				break;
		}
		new Thread(this).start();

	}
	@Override
	public void run() {
		AudioStream ais = null;
		InputStream is = null;
		try {
			if (type == 0) { // 背景音乐循环
				while (true) {
					is = ClassLoader.getSystemResourceAsStream(url);
					ais = new AudioStream(is);
					AudioPlayer.player.start(ais);
					Thread.sleep(106000);
				}
			} else { // 音效只播放一次
				is = ClassLoader.getSystemResourceAsStream(url);
				ais = new AudioStream(is);
				AudioPlayer.player.start(ais);
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) { // 关闭输入流
					is.close();
				}
				if (ais != null) { // 关闭音频流
					ais.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
