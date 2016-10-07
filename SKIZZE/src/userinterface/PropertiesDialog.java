package userinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PropertiesDialog extends JDialog implements ActionListener {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1395920727706706139L;

    JTextField ehost = null;

    JTextField eport = null;

    AppProperties prop;

    public PropertiesDialog(JFrame frame, AppProperties prop)
            throws HeadlessException {
        super(frame, "Eigenschaften");
        this.prop = prop;
        setLayout(new GridLayout(1, 0));

        ehost = new JTextField(10); // PropertyEditorManager.findEditor(String.class);
        eport = new JTextField(10); // PropertyEditorManager.findEditor(Integer.class);

        updateDialog();

        JPanel c = new JPanel(new BorderLayout());
        JPanel p = new JPanel(new GridLayout(2, 0));
        JPanel p1 = new JPanel(new GridLayout(0, 2));

        p1.add(new JLabel("Host"));
        p1.add(ehost);

        JPanel p2 = new JPanel(new GridLayout(0, 2));

        p2.add(new JLabel("Port"));
        p2.add(eport);

        p.add(p1);
        p.add(p2);

        c.add(BorderLayout.CENTER, p);

        JButton ende = new JButton();
        ende.setText("Ok");
        c.add(BorderLayout.SOUTH, ende);
        ende.addActionListener(this);

        getContentPane().add(c);
        setSize(new Dimension(200, 100));
    }

    public void updateProperties() {
        prop.setHost(ehost.getText());
        prop.setPort(Integer.parseInt(eport.getText()));
    }

    public void updateDialog() {
        ehost.setText(prop.getHost());
        eport.setText("" + prop.getPort());
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        updateProperties();
        setVisible(false);
    };

}
