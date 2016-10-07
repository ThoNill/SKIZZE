package parts;

import gui.SkizzeComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.Skizze;
import nls.NLS;
import userinterface.UserInterface;

/**
 * 
 * Eine Teil der Anwendung, die mit einen ApplicationController zusammenarbeitet
 * 
 * @author Thomas Nill
 *
 */

public class AppPart implements ActionListener {
    private String name;
    private AppControler appc;

    public AppPart(AppControler appc, String name) {
        this.name = name;
        this.appc = appc;
    }

    public AppControler getAppc() {
        return appc;
    }

    public UserInterface getUi() {
        return appc.getUi();
    }

    public SkizzeComponent getSkitze() {
        return getUi().getSkitze();
    }

    public Skizze getSModel() {
        return appc.getModel();
    }

    public void addComponents() {
        addMenu(getName());
    }

    public void addMenu(String name) {
        JMenuBar mb = appc.getUi().getInternMenuBar();
        JMenuItem item = new JMenuItem();
        item.setActionCommand(name);
        item.setText(NLS.getText(name));
        item.addActionListener(this);
        mb.add(item);
    }

    public String getName() {
        return name;
    }

    public void close() {
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }

    public boolean safe() {
        return true;
    }

    public boolean isDirty() {
        return false;
    }

}
