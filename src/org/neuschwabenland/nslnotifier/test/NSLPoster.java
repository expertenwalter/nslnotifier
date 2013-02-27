package org.neuschwabenland.nslnotifier.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.neuschwabenland.nslnotifier.json.NSLPost;

public class NSLPoster {
	public static void post(String boardName, NSLPost post) {

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost poster = new HttpPost(
					"http://neuschwabenland.org/board.php");

			// Fill form
			MultipartEntity entity = new MultipartEntity();
			entity.addPart(new FormBodyPart("board", new StringBody(boardName)));
			entity.addPart(new FormBodyPart("subject", new StringBody(
					post.subject, Charset.defaultCharset())));
			entity.addPart(new FormBodyPart("message", new StringBody(
					post.message, Charset.defaultCharset())));
			entity.addPart(new FormBodyPart("embed", new StringBody(
					"xVp_oSS7yLw")));
			entity.addPart(new FormBodyPart("embedtype", new StringBody(
					"youtube")));
			entity.addPart(new FormBodyPart("postpassword", new StringBody(
					"abcdefgh")));

			// Add files
			if (post.file != null)
				for (int i = 0; i < post.file.length; i++)
					entity.addPart(new FormBodyPart("imagefile[0]",
							new FileBody(new File(post.file[i]))));

			// Do Request
			poster.setEntity(entity);

			HttpResponse response = httpclient.execute(poster);
			HttpEntity responseentity = response.getEntity();

			// If the response does not enclose an entity, there is no need
			// to worry about connection release
			if (entity != null) {
				InputStream instream = responseentity.getContent();
				try {

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(instream));
					// do something useful with the response
					String s;
					while ((s = reader.readLine()) != null)
						System.out.println(s);
				} catch (IOException ex) {
					throw ex;
				} catch (RuntimeException ex) {
					poster.abort();
					throw ex;
				} finally {
					instream.close();
				}
				httpclient.getConnectionManager().shutdown();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void main(String args[]) {
		NSLPost post = new NSLPost();
		post.subject = "Test ü";
		post.message = "Test Test Test";

		post("m", post);
	}
}
