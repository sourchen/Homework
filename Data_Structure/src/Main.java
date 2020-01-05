import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

public class Main {
	public static void main(String[] args) throws IOException {

		// EX: input(3 NCCU 0.2 Fang 0.3 Data 0.4)
		// 1. 抓多個關鍵字
		System.out.println("Please type: ");
		Scanner scanner = new Scanner(System.in);
		ArrayList<Keyword> keywords = new ArrayList<Keyword>();
		GoogleQuery google;
		WebPage rootPage;
		WebTree tree;
		String input = "";
		while (scanner.hasNextLine()) {
			int numOfKeywords = scanner.nextInt();

			for (int i = 0; i < numOfKeywords; i++) {
				String name = scanner.next();
				double weight = scanner.nextDouble();
				Keyword k = new Keyword(name, weight);
				keywords.add(k);
			}
	
			// 2. 丟關鍵字去Google搜尋
			/**
			 * version1:關鍵字「各別搜尋
			 * 
			 * try { for (int i = 0; i < keywords.size(); i++) { Keyword input =
			 * keywords.get(i); google = new GoogleQuery(input.name); google.query(); //
			 * 結果存到list
			 * 
			 * for (int j = 0; j < google.list.size(); j++) { rootPage = new
			 * WebPage(google.list.get(j).url, google.list.get(j).title); //
			 * WebPage(url,name) rootPage.setScore(keywords); // 每個網頁的總分 }
			 * 
			 * } } catch (IOException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */

			/** version2:關鍵字「一起」搜尋 */
			try {
				for (int i = 1; i < keywords.size(); i++) {
					input = keywords.get(0).name;
					input = input + "+" + keywords.get(i).name;
				}

				google = new GoogleQuery(input);
				google.query(); // 結果存到list

				// 3. 計算「每個回傳網頁」的「輸入關鍵字」次數
				// step 1. 將「每個回傳網頁」轉成Webpage，再建立Webpage tree（主網頁）
				// step 2. 將Webpage tree加入「child」(子網頁) （進階）
				for (int i = 0; i < google.list.size(); i++) {
					rootPage = new WebPage(google.list.get(i).url, google.list.get(i).title); // WebPage(url,name)
					tree = new WebTree(rootPage);

					// step 3. 利用輸入關鍵字加權，計算tree（主網頁）的分數總和
					// 1. compute the score of children nodes // 2. setNode score of startNode
					tree.setPostOrderScore(keywords);
					tree.eularPrintTree(); // print tree
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// step 4. 依照網頁分數，排序先後順序
		}
		scanner.close();

	}
}