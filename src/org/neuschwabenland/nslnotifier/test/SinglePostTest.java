package org.neuschwabenland.nslnotifier.test;

import org.neuschwabenland.nslnotifier.json.NSLJSONInterface;

public class SinglePostTest {
	public static void main(String args[]) {
		NSLJSONInterface fetcher = new NSLJSONInterface();

		try {
			System.out.println("Content of /up/283 : "
					+ fetcher.getSinglePost(
							8, 283));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
