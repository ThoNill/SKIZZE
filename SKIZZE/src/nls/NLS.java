package nls;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * Natural Language Support
 * 
 * @author Thomas Nill
 *
 */
public class NLS {
    private static final Logger LOG = LogManager.getLogger(NLS.class);
    private static ResourceBundle res = null;

    public static void init() {
        try {

            res = ResourceBundle.getBundle("sk", Locale.GERMAN,
                    NLS.class.getClassLoader());
        } catch (Exception ex) {
            LOG.error("Exception in init", ex);
        }
    }

    public static String getText(String name) {
        String s = name;
        try {
            s = res.getString(name);
        } catch (Exception ex) {
            s = "<" + name + "> fehlt";
            LOG.error("Exception in getText", ex);
        }
        return s;
    }

}
