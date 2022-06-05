package otd.util;

import java.util.List;

import otd.lib.org.davidmoten.text.utils.WordWrap;

public class Wrap {
	public static List<String> wordWrap(String text, int width) {
		return WordWrap.from(text).maxWidth(width).breakWords(true).wrapToList();
	}
}
