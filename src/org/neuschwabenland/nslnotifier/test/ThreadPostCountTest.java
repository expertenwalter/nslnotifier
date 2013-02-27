package org.neuschwabenland.nslnotifier.test;

import org.neuschwabenland.nslnotifier.json.NSLJSONInterface;

public class ThreadPostCountTest {
	public static void main(String args[]) {
		NSLJSONInterface fetcher = new NSLJSONInterface();

		try {
			System.out.println("Posts in /up/61 : "
					+ fetcher.getThreadPostCount(
							8, 61));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
