import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WordCounter {
	private String urlStr;
	private String content;

	public WordCounter(String urlStr) {
		this.urlStr = urlStr;
	}

	private String fetchContent() throws IOException {
		URL url = new URL(this.urlStr);
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");

		InputStream in = conn.getInputStream();
		InputStreamReader inReader = new InputStreamReader(in, "utf-8");

		BufferedReader br = new BufferedReader(inReader);

		String retVal = "";

		String line = null;

		while ((line = br.readLine()) != null) {
			retVal = retVal + line + "\n";
		}
		//

		return retVal;

	}

	public int countKeyword(String keyword) throws IOException {
		if (content == null) {
			content = fetchContent(); // 回傳網頁內容
		}

		// To do a case-insensitive search, we turn the whole content and keyword into
		// upper-case:
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();

		int retVal = 0; // 關鍵字次數
		int i = 0; // 從哪裡開始數

		while (content.indexOf(keyword, i) != -1) {// 之後還有關鍵字的話...
			retVal++;
			i = content.indexOf(keyword, i) + keyword.length();
		}

		return retVal;
	}
}