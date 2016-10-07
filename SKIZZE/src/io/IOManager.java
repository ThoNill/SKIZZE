package io;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Skizze;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

/**
 * 
 * Manipulationen der Datenbank
 * 
 * @author Thomas Nill
 *
 */
public class IOManager {
    private static final Logger LOG = LogManager.getLogger(IOManager.class);

    public List<String> getSkitzen() {
        List<String> v = new ArrayList<String>();

        ObjectContainer db = Datenbank.getContainer();

        Query query = db.query();
        query.constrain(Skizze.class);
        ObjectSet<Skizze> result = query.execute();
        Iterator<Skizze> i = result.iterator();
        while (i.hasNext()) {
            Skizze s = i.next();
            v.add(s.getName());
        }
        return v;
    }

    public Skizze getSkitze(String name) {
        ObjectContainer db = Datenbank.getContainer();
        if (db == null)
            return null;

        Query query = db.query();
        query.constrain(Skizze.class);
        query.descend("name").constrain(name);
        ObjectSet<Skizze> result = query.execute();
        Iterator<Skizze> i = result.iterator();
        if (i.hasNext()) {
            Skizze s = i.next();
            db.activate(s.getTeile(), 10);
            s.cloneTeile();
            s.setDirty(false);
            return s;
        }
        return null;
    };

    public void safe(Skizze s) {
        ObjectContainer db = Datenbank.getContainer();
        if (db == null)
            return;

        s.cloneTeile();
        try {
            db.store(s);
            db.commit();
        } catch (Exception ex) {
            LOG.error("Ausnahme ", ex);
            db.rollback();
        }
        s.setDirty(false);
    }

    public void delete(Skizze s) {
        ObjectContainer db = Datenbank.getContainer();
        if (db == null)
            return;

        try {
            db.delete(s);
            db.commit();
        } catch (Exception ex) {
            LOG.error("Ausnahme ", ex);
            db.rollback();
        }

    }
}
