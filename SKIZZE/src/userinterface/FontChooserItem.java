package userinterface;

import java.awt.Font;

public class FontChooserItem {

    private Font font;

    public FontChooserItem(Font f) {
        this.font = f;
    }

    @Override
    public String toString() {
        return font.getFontName();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((font == null) ? 0 : font.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FontChooserItem other = (FontChooserItem) obj;
        if (font == null) {
            if (other.font != null)
                return false;
        } else if (!font.equals(other.font))
            return false;
        return true;
    }

    public Font getFont() {
        return font;
    }
}
