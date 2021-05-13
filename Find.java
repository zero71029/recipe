package tw.org.iii.recipe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Find {

	public static void main(String[] args) {
		System.out.println("hello Find................");
		String newEn = "杏鮑菇";
		String UUU = "https://icook.tw/search/" + newEn;

		try {
			URL url = new URL(UUU);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			JSONArray root = new JSONArray();
			JSONObject row = null;
			FindDetail fd = new FindDetail();
			while ((line = reader.readLine()) != null) {
				if (line.contains("class=\"browse-recipe-link\"")) {
					row = new JSONObject();
//        	  System.out.println(line.indexOf("href="));
					System.out.println("網址:  https://icook.tw"
							+ line.substring(line.indexOf("href=") + 6, line.indexOf(">", 50) - 1));
					fd.getDetail("https://icook.tw"+line.substring(line.indexOf("href=") + 6, line.indexOf(">", 50) - 1));
					System.out.println("");
					System.out.println("");
					System.out.println("=======================================================================");
					row.put("address", line.substring(line.indexOf("href=") + 6, line.indexOf(">", 50) - 1));
				}

				if (line.contains("data-srcset=\"\" src=\"")) {
//        	  System.out.println(line.indexOf("https://imageproxy.icook.network/"));
					System.out.println("圖片:" + line.substring(line.indexOf("https://imageproxy.icook.network/"),
							line.indexOf("data-srcset", 50) - 2));
					row.put("img", line.substring(line.indexOf("https://imageproxy.icook.network/"),
							line.indexOf("data-srcset", 50) - 2));
				}

				if (line.contains("<h2 data-title=")) {
					System.out.println("菜名:" + line.substring(27, line.indexOf(" c", 27)));

					row.put("name", line.substring(27, line.indexOf(" c", 27)));
					root.put(row);
				}

			}
			System.out.println(root);
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("找不到資料");
			e.printStackTrace();
		}
	}

}
