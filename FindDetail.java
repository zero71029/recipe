package tw.org.iii.recipe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class FindDetail {

	public void getDetail(String UUU) {
		System.out.println("hello FindDetail................");
//		JspContext context =  getJspContext();
//		JspWriter out = context.getOut();
//		String UUU = "https://icook.tw/recipes/372470";
		ArrayList<String> cn = new ArrayList();
		try {
			URL url = new URL(UUU);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			JSONArray root = new JSONArray();
			JSONObject row = null;
//			JSONObject ingredients = new JSONObject();
			while ((line = reader.readLine()) != null) {
				if (line.contains("recipe-step-cover ratio-container ratio-container-4-3 glightbox")) {
					row = new JSONObject();
//          	   	  System.out.println(line.indexOf("href="));
					System.out.println("步驟 : "+line.substring(line.indexOf("description: ") + 13,
							line.indexOf("\" data-track-id", 50) - 1));
					row.put("address", line.substring(line.indexOf("description: ") + 13,
							line.indexOf("\" data-track-id", 50) - 1));
					System.out.println("步驟圖片 : "+line.substring(line.indexOf("href=") + 6,
							line.indexOf(">", 50) - 1));
					row.put("img", line.substring(line.indexOf("href=") + 6,
							line.indexOf(">", 50) - 1));
				root.put(row);
				}

				if (line.contains("data-track-id=\"recipe-ingredient\"")) {
					System.out.println(line.substring(line.indexOf("data-name=") + 11,
							line.indexOf("\" href=\"", 50) - 1));
//					ingredients.put("ingredients",line.substring(line.indexOf("data-name=") + 11,
//							line.indexOf("\" href=\"", 50) - 1));
				}

			}
//			root.put(ingredients);
				System.out.println(root);
//				System.out.println(ingredients);
				reader.close();
			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("找不到資料");
			e.printStackTrace();
		}
	}
}
