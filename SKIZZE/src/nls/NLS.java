package nls;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * 
 * Natural Language Support
 * 
 * @author Thomas Nill
 *
 */
public class NLS {
	private static ResourceBundle res = null;

	public static void init() {
		try {

			res = ResourceBundle.getBundle("sk", Locale.GERMAN, NLS.class
					.getClassLoader());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getText(String name) {
		String s = name;
		try {
			s = res.getString(name);
		} catch (Exception ex) {
			s = "<" + name + "> fehlt";
			//ex.printStackTrace();
		}
		return s;
	}

}
