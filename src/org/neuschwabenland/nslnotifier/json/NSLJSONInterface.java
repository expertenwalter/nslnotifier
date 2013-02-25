package org.neuschwabenland.nslnotifier.json;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class NSLJSONInterface {
	private static final String JSON_BASEURL = "https://www.neuschwabenland.org/json.php";

	public enum BoardEntry {
		BOARD_B(3, "b"), BOARD_C(6, "c"), BOARD_UP(8, "up"), BOARD_M(10, "m"), BOARD_NSL(
				12, "NSL"), BOARD_CHAOS(23, "chaos"), BOARD_NEWS(33, "news");

		private int id;
		private String name;

		private BoardEntry(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}

	public int getBoardPostCount(BoardEntry board) throws Exception {
		// Fetch JSON document via HTTP
		URL url = new URL(JSON_BASEURL + "?mo=2&bo=" + board.getId());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new Exception("Could not fetch " + JSON_BASEURL
					+ "?json.php?mo=2&bo=" + board.getId() + ": HTTP Error '"
					+ conn.getResponseCode() + "'");
		}

		StringBuffer buf = new StringBuffer();
		InputStream in = conn.getInputStream();
		int inchar;

		while ((inchar = in.read()) != -1) {
			buf.append((char) inchar);
		}
		
		System.out.println(buf.toString());

		// Parse JSON
		Gson parser = new Gson();
		String answer = parser.fromJson(buf.toString(), String.class);
		
		return Integer.parseInt(answer.substring(1, answer.length() - 1));
	}
}
