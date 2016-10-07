package userinterface;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import model.teile.FontTeil;

public class FontChooser extends JPanel implements ActionListener {

    private static final long serialVersionUID = -6348005779423355381L;

    private JComboBox box;

    private JComboBox sizeBox;

    private float size;

    private Font font;

    public FontChooser() {
        super();
        sizeBox = new JComboBox();

        int sizes[] = { 4, 6, 8, 10, 12, 14, 16, 18, 20, 24 };
        for (int i = 0; i < sizes.length; i++) {
            sizeBox.addItem("" + sizes[i]);
        }
        sizeBox.setSelectedItem("18");

        Font fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAllFonts();
        box = new JComboBox();
        for (int i = 0; i < fonts.length; i++) {
            box.addItem(new FontChooserItem(fonts[i]));
        }
        box.setSelectedIndex(0);

        add(box);
        add(sizeBox);

        updateFont();

        box.addActionListener(this);
        sizeBox.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        updateFont();
        firePropertyChange("font", "old", "new");
    }

    private void updateFont() {
        font = ((FontChooserItem) box.getSelectedItem()).getFont();
        size = Float.parseFloat((String) sizeBox.getSelectedItem());
    }

    public Font getChooseFont() {
        return font;
    }

    public float getFontSize() {
        return size;
    }

    public void setChooseFont(FontTeil f) {
        if (f != null) {
            box.setSelectedItem(new FontChooserItem(f.getFont()));
            sizeBox.setSelectedItem("" + ((int) f.getFontSize()));
            box.invalidate();
            sizeBox.invalidate();
            updateFont();
        }
    }

}
