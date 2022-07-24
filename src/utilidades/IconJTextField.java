package utilidades;

import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class IconJTextField extends JTextField 
{
    private static final int ICON_SPACING = 4;
    private Border mBorder;
    private Icon mIcon;

    @Override
    public void setBorder(Border border)
    {
        mBorder = border;

        if (mIcon == null)
        {
            super.setBorder(border);
        } 

        else 
        {
            Border margin = BorderFactory.createEmptyBorder(0, mIcon.getIconWidth() + ICON_SPACING, 0, 0);
            Border compoud = BorderFactory.createCompoundBorder(border, margin);
            super.setBorder(compoud);
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) 
    {
        super.paintComponent(graphics);

        if (mIcon != null)
        {
            Insets iconInsets = mBorder.getBorderInsets(this);
            mIcon.paintIcon(this, graphics, iconInsets.left, iconInsets.top);
        }
    }

    public void setIcon(Icon icon) 
    {
        mIcon = icon;
        resetBorder();
    }

    private void resetBorder() 
    {
        setBorder(mBorder);
    }
}
