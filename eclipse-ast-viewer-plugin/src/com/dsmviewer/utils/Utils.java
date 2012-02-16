package com.dsmviewer.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

/**
 * @author <a href="mailto:Daniil.Yaroslavtsev@gmail.com"> Daniil Yaroslavtsev</a>
 */
public final class Utils {

	private Utils() {
	}

	public static String extractStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

	public static String extractFileName(URL url) {
		String filePath = url.getFile();
		int fileNameBeginIndex = filePath.lastIndexOf('/') + 1;
		return filePath.substring(fileNameBeginIndex);
	}

}