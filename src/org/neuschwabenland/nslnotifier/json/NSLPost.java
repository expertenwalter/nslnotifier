package org.neuschwabenland.nslnotifier.json;

public class NSLPost {
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
	public String file[];
	public String file_type[];
	public String file_original[];
	public String file_size_formatted[];
	public String image_w[];
	public String image_h[];
	public String thumb_w[];
	public String thumb_h[];

	private String arrayToString(Object[] array) {
		if (array.length < 1)
			return "";

		StringBuffer buf = new StringBuffer();
		buf.append("[ " + array[0]);

		for (int i = 1; i < array.length; i++) {
			buf.append(" " + array[i]);
		}

		buf.append(" ]");

		return buf.toString();
	}

	public String toString() {
		return "ID: " + this.id + " BoardID: " + this.boardid + " ParentID: "
				+ this.parentid + " Name: " + this.name + " Tripcode: "
				+ this.tripcode + " E-Mail: " + this.email + " Subject: "
				+ this.subject + " Message: \"" + this.message + "\""
				+ " Timestamp: " + this.timestamp + " Stickied: "
				+ this.stickied + " Locked: " + this.locked
				+ " Posterauthority: " + this.posterauthority + " File: "
				+ arrayToString(this.file) + " File_type: "
				+ arrayToString(this.file_type) + " File Original: "
				+ arrayToString(this.file_original) + " File_size_formatted: "
				+ arrayToString(this.file_size_formatted) + " Image_w: "
				+ arrayToString(this.image_w) + " Image_h: "
				+ arrayToString(this.image_h) + " thumb_w: "
				+ arrayToString(this.thumb_w) + " thumb_h: "
				+ arrayToString(this.thumb_h);
	}
}
