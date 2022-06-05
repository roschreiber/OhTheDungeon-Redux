package otd.util;

import java.io.File;

public class FileUtils {
	public static String getExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return name.substring(lastIndexOf + 1);
	}

	public static boolean deleteDirectory(File directoryToBeDeleted) {
		if (!directoryToBeDeleted.exists())
			return true;
		File[] allContents = directoryToBeDeleted.listFiles();
		boolean res = true;
		if (allContents != null) {
			for (File file : allContents) {
				res &= deleteDirectory(file);
			}
		}
		res &= directoryToBeDeleted.delete();
		return res;
	}
}
