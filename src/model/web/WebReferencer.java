package model.web;

import java.lang.reflect.Method;

public class WebReferencer {

	final public static String UNIPROT_WEBSITE = "https://www.uniprot.org/uniprot/";
	final public static String NCBI_WEBSITE = "https://www.ncbi.nlm.nih.gov/entrez/viewer.fcgi?db=protein&val=";
	final public static String SGD_WEBSITE = "http://www.yeastgenome.org/cgi-bin/locus.fpl?dbid=";
	final public static String TAIR_WEBSITE = " http://www.arabidopsis.org/servlets/TairObject?type=locus&name=";

	private static String url;

	public static void openURL(String dbType, String id) throws Exception {

		url = getUrlFromDBType(dbType, id);
		if (isMacOS()) {
			Class fileMgr = Class.forName("com.apple.eio.FileManager");
			Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });

			openURL.invoke(null, new Object[] { url });
		} else if (isWindows())
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
		else {
			String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
			String browser = null;

			for (int i = 0; i < browsers.length; i++)
				if (Runtime.getRuntime().exec(new String[] { "which", browsers[i] }).waitFor() == 0) {
					browser = browsers[i];
					break;
				}
			if (browser == null)
				throw new Exception("Error while running external browser");
			else
				Runtime.getRuntime().exec(browser + " " + url);
		}
	}

	private static String getUrlFromDBType(String dbType, String id) {

		if (dbType.equals("UniProt"))
			return UNIPROT_WEBSITE + id;
		else if (dbType.equals("NCBI"))
			return NCBI_WEBSITE + id;
		else if (dbType.equals("SGD"))
			return SGD_WEBSITE + id;
		else if (dbType.equals("TAIR"))
			return TAIR_WEBSITE + id;
		else
			return null;
	}

	/**
	 * 
	 * @return
	 */
	public static boolean isMacOS() {
		String os = null;
		return ((os = getOsName(true)) != null && os.startsWith("mac"));
	}

	/**
	 * 
	 * @return
	 */
	public static boolean isWindows() {
		String os = null;
		return ((os = getOsName(true)) != null && os.startsWith("windows"));
	}

	/**
	 * 
	 * @param lowerCase
	 * @return
	 */
	public static String getOsName(boolean lowerCase) {
		String os = null;

		if ((os = System.getProperty("os.name")) != null) {
			if (lowerCase)
				os = os.toLowerCase();
		}
		return (os);
	}
}
