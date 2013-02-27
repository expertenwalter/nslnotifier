package org.neuschwabenland.nslnotifier.test;

import org.neuschwabenland.nslnotifier.json.NSLJSONInterface;

public class BoardPostCountTest {
	public static void main(String args[]) {
		NSLJSONInterface fetcher = new NSLJSONInterface();

		try {
			System.out
					.println("Posts on /b/ : "
							+ fetcher
									.getBoardPostCount(3));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
