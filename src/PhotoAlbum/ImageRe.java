package PhotoAlbum;

import java.io.File;
import java.io.Serializable;

public class ImageRe implements Serializable{
	File file;
	String title;
	String annotation;
	public ImageRe(File file, String title, String annotation) {
		super();
		this.file = file;
		this.title = title;
		this.annotation = annotation;
	}
	public File getFile() {
		return file;
	}
	public void setPath(File file) {
		this.file = file;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAnnotation() {
		return annotation;
	}
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
}
