import java.io.IOException;
import java.util.ArrayList;

import com.sun.org.apache.xpath.internal.operations.Plus;

public class WebPage {
	public String url;
	public String name;
	public WordCounter counter;
	public double score;

	public WebPage(String url, String name) {
		this.url = url;
		this.name = name;
		this.counter = new WordCounter(url);
	}

	public void setScore(ArrayList<Keyword> keywords) throws IOException {
		score = 0;
		int websitePlus = 1; // 指定網站加分
		int keywordPlus = counter.countKeyword("唇膏") // 指定關鍵字加分
				+ counter.countKeyword("口紅") + counter.countKeyword("唇彩") + counter.countKeyword("底妝")
				+ counter.countKeyword("眼妝") + counter.countKeyword("腮紅") + counter.countKeyword("彩妝")
				+ counter.countKeyword("眼影") + counter.countKeyword("粉餅") + counter.countKeyword("遮瑕")
				+ counter.countKeyword("奶茶色");

		for (Keyword k : keywords) {
			// score += counter.countKeyword(k.name) * k.weight; // 每個網頁的總分＝每個關鍵字次數＊加權分數
			score += counter.countKeyword(k.name) * k.weight; // score = 每個關鍵字次數＊加權分數
		}
		if (this.url.contains("http://www.urcosme.com/") || this.url.contains("http://www.elle.com/tw/")
				|| this.url.contains("http://www.popdaily.com.tw/explore/beauty")
				|| this.url.contains("http://www.popdaily.com.tw/article/tag/beauty")
				|| this.url.contains("http://girlstyle.com/tw/category/beauty-美妝")
				|| this.url.contains("http://www.vogue.com.tw/beauty")
				|| this.url.contains("http://www.marieclaire.com.tw/beauty")) {
			websitePlus = 500;
		}

		score += websitePlus * score + keywordPlus * 100; // 每個網頁的總分＝網站加分＊（每個關鍵字次數＊加權分數）＋ 指定關鍵字次數＊100

	}

}