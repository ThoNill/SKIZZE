package io;

import model.Skizze;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;

/**
 * 
 * Die db4j Datenbank Anbindung
 * 
 * @author Thomas Nill
 *
 */
public class Datenbank {
    private static final Logger LOG = LogManager.getLogger(Datenbank.class);

    static final String APFILENAME = "skitzen.dat";

    static ObjectContainer db = null;

    public static void stop() {
        if (db != null) {
            db.close();
        }
        db = null;
    };

    public static ObjectContainer getContainer() {
        if (db == null) {
            Db4o.configure().objectClass(Skizze.class)
                    .maximumActivationDepth(1);
            int i = 0;
            while (i < 10) {
                try {
                    db = Db4o.openFile("skitzen" + i + ".dat");
                    return db;
                } catch (Exception ex) {
                    LOG.error("can not open DB", ex);
                    i++;
                }
            }
        }
        return db;
    }
}
