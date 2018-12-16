package model.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/*
 * T.R. Funkcje pomocnicze zwiazane z jakims zadaniem (np.obsluga plikow) dobrze jest gromadzic w osobnych klasach pelniacych role bibliotek  
 */
public class FileTools {

	public FileTools() {
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static String getExtension(String path) {
		String extension = "";
		int i = path.lastIndexOf(".");
		if (i > 0) {
			extension = path.substring(i + 1);
		}
		// System.out.println("Extension:" + extension);
		return extension;
	}

	/**
	 * 
	 * @param filepath
	 * @return
	 */
	public static String removeExtension(String filepath) {
		String newFilepath = null;
		int index = -1;

		if ((index = filepath.lastIndexOf(".")) != -1)
			newFilepath = filepath.substring(0, index);
		else
			newFilepath = filepath;

		return (newFilepath);
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getLineSeparator(String srcPath) throws IOException {
		File file = new File(srcPath);
		char current;
		String lineSeparator = "";

		FileInputStream fis = new FileInputStream(file);
		try {
			while (fis.available() > 0) {
				current = (char) fis.read();
				if ((current == '\n') || (current == '\r')) {
					lineSeparator += current;
					if (fis.available() > 0) {
						char next = (char) fis.read();
						if ((next != current) && ((next == '\r') || (next == '\n'))) {
							lineSeparator += next;
						}
					}
					return lineSeparator;
				}
			}
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return null;
	}

}
