import java.io.BufferedReader;
import java.util.ArrayList;

public class GoogleReturn {
	public String title;
	public String url;

	public GoogleReturn(String title, String url) {
		this.title = title;
		this.url = url;
	}

	@Override
	public String toString() {
		return "[" + title + "," + url + "]";
	}

}
