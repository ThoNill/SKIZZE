package userinterface;

import gui.SkizzeComponent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;

import model.Skizze;
import nls.NLS;

public class UserInterface extends JFrame {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4524893663935649413L;

    SkizzeComponent s = null;

    JLabel message = null;

    JMenuBar menuBar = null;

    JToolBar toolBar = null;

    public UserInterface() throws HeadlessException {
        super(NLS.getText("Skitze"));
        menuBar = new JMenuBar();
        toolBar = new JToolBar();
        s = new SkizzeComponent();
        s.setFocusable(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        s.setPreferredSize(dim);

        JScrollPane scrollPane = new JScrollPane(s);
        scrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel sbar = generateStatusbar();
        JPanel p = new JPanel(new BorderLayout());
        p.add(BorderLayout.NORTH, toolBar);
        p.add(BorderLayout.CENTER, scrollPane);
        p.add(BorderLayout.SOUTH, sbar);
        this.setJMenuBar(menuBar);
        this.getContentPane().add(p);
    }

    private JPanel generateStatusbar() {

        message = new JLabel();

        JPanel bar = new JPanel(new BorderLayout());
        bar.setBorder(new BevelBorder(BevelBorder.RAISED));
        bar.add(BorderLayout.WEST, new JLabel(NLS.getText("Meldung")));
        bar.add(BorderLayout.CENTER, message);
        return bar;
    }

    public void setMessage(String text) {
        message.setText(text);
    }

    public JMenuBar getInternMenuBar() {
        return menuBar;
    }

    public JToolBar getInternToolBar() {
        return toolBar;
    }

    public void focusSkitze() {
        if (!s.hasFocus())
            s.requestFocus();
    }

    public SkizzeComponent getSkitze() {
        return s;
    }

    public void setModel(Skizze model) {
        s.setModel(model);
    }

    public SkizzeComponent getSkitzeComponentr() {
        return s;
    }
}
