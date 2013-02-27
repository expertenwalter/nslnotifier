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

	/**
	 * Remote procedure call via HTTP
	 * 
	 * @param jsonurl URL
	 * @return JSON Object
	 * @throws Exception
	 */
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

	/**
	 * Fetch the post count for a whole board
	 * 
	 * @param board
	 *            Board ID
	 * @return Post count
	 * @throws Exception
	 *             on Error
	 */
	public int getBoardPostCount(int boardid) throws Exception {

		String json = fetchJSON(JSON_BASEURL + "?mo=2&bo=" + boardid);

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

	/**
	 * Fetch the post count for a single thread
	 * 
	 * @param board
	 *            Board ID
	 * @param threadid
	 *            Thread ID
	 * @return Post count
	 * @throws Exception
	 *             on Error
	 */
	public int getThreadPostCount(int boardid, long threadid)
			throws Exception {

		String json = fetchJSON(JSON_BASEURL + "?mo=3&bo=" + boardid
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

	/**
	 * Get the content of a single post
	 * 
	 * @param board
	 *            Board ID
	 * @param postid
	 *            Post id
	 * @return Post object
	 * @throws Exception
	 *             on error
	 */
	public NSLPost getSinglePost(int boardid, long postid)
			throws Exception {

		String json = fetchJSON(JSON_BASEURL + "?mo=4&bo=" + boardid
				+ "&po=" + postid);

		// Parse JSON
		Gson parser = new Gson();

		Type collectionType = new TypeToken<Collection<Mode4Message>>() {
		}.getType();
		Collection<Mode4Message> msgcollection = parser.fromJson(
				json.toString(), collectionType);

		NSLPost post = Mode4Message.toNSLPost((Mode4Message[]) msgcollection
				.toArray(new Mode4Message[0]));
		return post;
	}

	/**
	 * Get the list of boards and their IDs
	 * 
	 * @throws Exception
	 *             on error
	 */
	public Collection<Mode6Message> getBoards() throws Exception {

		String json = fetchJSON(JSON_BASEURL + "?mo=6");

		// Parse JSON
		Gson parser = new Gson();

		Type collectionType = new TypeToken<Collection<Mode6Message>>() {
		}.getType();
		Collection<Mode6Message> msgcollection = parser.fromJson(
				json.toString(), collectionType);

		return msgcollection;
	}
}
