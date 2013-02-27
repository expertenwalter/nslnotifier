package org.neuschwabenland.nslnotifier.json;

public class Mode4Message {
	public long id;
	public int boardid;
	public long parentid;
	public String name;
	public String tripcode;
	public String email;
	public String subject;
	public String message;
	public String timestamp;
	public String stickied;
	public String locked;
	public String posterauthority;
	public String file;
	public String file_type;
	public String file_original;
	public String file_size_formatted;
	public String image_w;
	public String image_h;
	public String thumb_w;
	public String thumb_h;

	public static NSLPost toNSLPost(Mode4Message messages[]) {
		NSLPost post = new NSLPost();

		post.id = messages[0].id;
		post.boardid = messages[0].boardid;
		post.parentid = messages[0].parentid;
		post.name = messages[0].name;
		post.tripcode = messages[0].tripcode;
		post.email = messages[0].email;
		post.subject = messages[0].subject;
		post.message = messages[0].message;
		post.timestamp = messages[0].timestamp;
		post.stickied = messages[0].stickied;
		post.locked = messages[0].locked;
		post.posterauthority = messages[0].posterauthority;

		// Instantiate arrays
		post.file = new String[messages.length];
		post.file_type = new String[messages.length];
		post.file_original = new String[messages.length];
		post.file_size_formatted = new String[messages.length];
		post.image_w = new String[messages.length];
		post.image_h = new String[messages.length];
		post.thumb_w = new String[messages.length];
		post.thumb_h = new String[messages.length];

		// Fill arrays
		for (int i = 0; i < messages.length; i++) {
			post.file[i] = messages[i].file;
			post.file_type[i] = messages[i].file_type;
			post.file_original[i] = messages[i].file_original;
			post.file_size_formatted[i] = messages[i].file_size_formatted;
			post.image_w[i] = messages[i].image_w;
			post.image_h[i] = messages[i].image_h;
			post.thumb_w[i] = messages[i].thumb_w;
			post.thumb_h[i] = messages[i].thumb_h;
		}

		return post;
	}
}
