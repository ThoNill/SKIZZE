package parts;

import io.Datenbank;
import io.IOManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Skizze;
import nls.NLS;
import userinterface.SkizzenChooser;

/**
 * Verbindung zur Datenbank
 * 
 * @author Thomas Nill
 *
 */
public class DBAppPart extends AppPart implements ActionListener {

    private SkizzenChooser skitzzenChooser;

    private JButton safeButton = null;

    private JButton deleteButton = null;

    public DBAppPart(AppControler appc) {
        super(appc, "db");
    }

    @Override
    public void addComponents() {
        if (Datenbank.getContainer() == null)
            return;

        JPanel p = new JPanel();
        skitzzenChooser = new SkizzenChooser();
        skitzzenChooser.addActionListener(this);
        p.add(skitzzenChooser);

        safeButton = new JButton();
        safeButton.setText(NLS.getText("safe"));
        safeButton.setActionCommand("safe");
        safeButton.addActionListener(this);

        p.add(safeButton);

        deleteButton = new JButton();
        deleteButton.setText(NLS.getText("delete"));
        deleteButton.setActionCommand("delete");
        deleteButton.addActionListener(this);

        p.add(deleteButton);

        getAppc().getUi().getInternToolBar().add(p);

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == skitzzenChooser.getSkitzen()) {
            String name = skitzzenChooser.getSkitzenname();
            if (name != null && !"".equals(name)) {
                IOManager iom = new IOManager();
                Skizze s = iom.getSkitze(name);
                if (s != null) {
                    getAppc().setModel(s);
                }
            }
        } else {
            if ("safe".equals(arg0.getActionCommand())) {
                safe();
            }
            if ("delete".equals(arg0.getActionCommand())) {
                String name = skitzzenChooser.getSkitzenname();
                if (name != null && !"".equals(name.trim())) {
                    IOManager iom = new IOManager();
                    Skizze model = getAppc().getModel();
                    iom.delete(model);
                    Skizze neues_model = new Skizze();
                    getAppc().setModel(neues_model);
                }
                skitzzenChooser.fill();
            }
            ;
        }
        ;
    }

    @Override
    public void close() {
        Datenbank.stop();
    }

    @Override
    public boolean isDirty() {
        return getAppc().getModel().isDirty();
    }

    @Override
    public boolean safe() {
        String name = skitzzenChooser.getSkitzenname();

        if (name != null && !"".equals(name.trim())) {
            Skizze model = getAppc().getModel();
            IOManager iom = new IOManager();

            if (!name.equals(model.getName())) {
                int res = JOptionPane.YES_OPTION;
                if (!"".equals(model.getName().trim())) {
                    res = JOptionPane.showConfirmDialog(null,
                            NLS.getText("umbenennen"),
                            NLS.getText("Information"),

                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE);
                }

                if (res == JOptionPane.YES_OPTION) {
                    model.setName(name);
                    iom.safe(model);
                } else {
                    Skizze neues_model = new Skizze();
                    neues_model.copy(model);
                    neues_model.setName(name);
                    iom.safe(neues_model);
                    getAppc().setModel(neues_model);
                }
                skitzzenChooser.fill();
                skitzzenChooser.setSkitzenname(name);
            } else {
                iom.safe(model);
            }
        } else {
            JOptionPane
                    .showMessageDialog(null, NLS.getText("bitteNameEingeben"),
                            NLS.getText("Information"),
                            JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
}
