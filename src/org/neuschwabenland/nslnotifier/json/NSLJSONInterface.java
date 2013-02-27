package org.neuschwabenland.nslnotifier.json;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.neuschwabenland.nslnotifier.json.Mode2Message;

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

	private String fetchJSON(String jsonurl) throws Exception {
		// Fetch JSON document via HTTP
		URL url = new URL(jsonurl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new Exception("Could not fetch " + jsonurl + ": HTTP Error '"
					+ conn.getResponseCode() + "'");
		}

		StringBuffer buf = new StringBuffer();
		InputStream in = conn.getInputStream();
		int inchar;

		while ((inchar = in.read()) != -1) {
			buf.append((char) inchar);
		}

		return buf.toString();
	}

	public int getBoardPostCount(BoardEntry board) throws Exception {

		String json = fetchJSON(JSON_BASEURL + "?mo=2&bo=" + board.getId());

		// Parse JSON
		Gson parser = new Gson();

		Type collectionType = new TypeToken<Collection<Mode2Message>>() {
		}.getType();
		Collection<Mode2Message> msgcollection = parser.fromJson(
				json.toString(), collectionType);

		Iterator<Mode2Message> iterator = msgcollection.iterator();
		String scount = iterator.next().getCount();

		return Integer.parseInt(scount.substring(1, scount.length() - 1));
	}

	public int getThreadPostCount(BoardEntry board, long threadid)
			throws Exception {

		String json = fetchJSON(JSON_BASEURL + "?mo=3&bo=" + board.getId()
				+ "&po=" + threadid);

		// Parse JSON
		Gson parser = new Gson();

		Type collectionType = new TypeToken<Collection<Mode3Message>>() {
		}.getType();
		Collection<Mode3Message> msgcollection = parser.fromJson(
				json.toString(), collectionType);

		Iterator<Mode3Message> iterator = msgcollection.iterator();
		String scount = iterator.next().getCount();

		return Integer.parseInt(scount);
	}

	public NSLPost getSinglePost(BoardEntry board, long postid)
			throws Exception {

		String json = fetchJSON(JSON_BASEURL + "?mo=4&bo=" + board.getId()
				+ "&po=" + postid);

		System.out.println(json);

		// Parse JSON
		Gson parser = new Gson();

		Type collectionType = new TypeToken<Collection<Mode4Message>>() {
		}.getType();
		Collection<Mode4Message> msgcollection = parser.fromJson(
				json.toString(), collectionType);

		Iterator<Mode4Message> iterator = msgcollection.iterator();
		Mode4Message rawmsg = iterator.next();

		NSLPost post = Mode4Message.toNSLPost((Mode4Message[]) msgcollection
				.toArray(new Mode4Message[0]));
		return post;
	}
}
