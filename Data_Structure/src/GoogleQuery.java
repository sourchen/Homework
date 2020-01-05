import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.net.URL;

import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

public class GoogleQuery

{
	public String searchKeyword;

	public String url;

	public String content;

	public ArrayList<GoogleReturn> list = new ArrayList<GoogleReturn>(); // 存放GoogleReturn(title,url)

	public GoogleQuery(String searchKeyword)

	{

		this.searchKeyword = searchKeyword;

		this.url = "http://www.google.com/search?q=" + searchKeyword + "&oe=utf8&num=10";

	}

	private String fetchContent() throws IOException

	{
		String retVal = "";

		URL u = new URL(url);

		URLConnection conn = u.openConnection();

		conn.setRequestProperty("User-agent", "Chrome/7.0.517.44");

		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");

		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while ((line = bufReader.readLine()) != null) {
			retVal += line;

		}
		return retVal;
	}

	public HashMap<String, String> query() throws IOException

	{

		if (content == null)

		{

			content = fetchContent();

		}

		HashMap<String, String> retVal = new HashMap<String, String>();

		Document doc = Jsoup.parse(content);
		// System.out.println(doc.text()); // output: NCCU - Google 搜尋 Google Google...
		Elements lis = doc.select("div");
		lis = lis.select(".ZINbbc");
		// System.out.println(lis.size());// output: 11

		for (Element li : lis) {
			try {

				// System.out.println(li.select(".BNeawe").get(0).text()); // 國立政治大學
				// System.out.println(li.select("a").get(0).attr("href"));
				// //url?q=https://www.nccu.edu.tw/...
				String title = li.select(".BNeawe").get(0).text(); // output:國立政治大學
				String url = li.select("a").get(0).attr("href"); // output:url?q=https://www.nccu.edu.tw/...

				url = url.substring((url.indexOf("http"))); // 將"url?q="刪除
				if (url.contains("https://")) {
					StringBuilder newURL = new StringBuilder(url); // 將「http"s"」刪除，才跑得動
					newURL = newURL.replace(4, 5, "");
					url = newURL.toString();
				}
				list.add(new GoogleReturn(title, url)); // 存到list裡面

//				for (int i = 0; i < block.size(); i++)
//					System.out.println(block.get(i).text());

//				System.out.println(block.get(1).text());
//				System.out.println(block.get(2).text());
//
//				String title = block.get(1).text();
//				String citeUrl = block.get(2).text();

//				System.out.println(title + " " + citeUrl);

//				retVal.put(title, citeUrl);

			} catch (IndexOutOfBoundsException e) {

			}
		}

		return retVal;

	}

}