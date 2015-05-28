package framework.mipuz.ui;

import framework.mipuz.game.GameInfo;
import static framework.mipuz.utilities.GraphicsUtil.scaleImageIcon;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Custom cell renderer class for presenting GameInfo objects in list.
 */
public class JLabelCellRenderer extends JLabel implements ListCellRenderer<Object> {
    
    public JLabelCellRenderer() {
        setOpaque(true);
    }

    /**
     * Custom list cell renderer that display GameInfo object icon and short
     * name in JLabel.
     *
     * @param list JList being painted
     * @param value Element in list being handled
     * @param index Cell index
     * @param isSelected True if the specified cell was selected
     * @param cellHasFocus True if the specified cell has the focus
     * @return A component used to paint the specified value
     */
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        
        if (value instanceof GameInfo) {
            GameInfo gi = (GameInfo) value;
            setText(gi.getShortName());
            setToolTipText(gi.getDescription());
            setIcon(scaleImageIcon(gi.getIcon(), 32, 32));
            
            handleSelection(isSelected, list);
            handleFocus(cellHasFocus, list);
        }
        
        return this;
    }

    /**
     * This method handles cell painting part regarding to given cell selection
     * state.
     *
     * @param isSelected True if the specified cell was selected
     * @param list JList being painted
     */
    private void handleSelection(boolean isSelected, JList<?> list) {
        Color background;
        Color foreground;
        
        if (isSelected) {
            background = new Color(list.getSelectionBackground().getRGB());
            foreground = new Color(list.getSelectionForeground().getRGB());
        } else {
            background = new Color(list.getBackground().getRGB());
            foreground = new Color(list.getForeground().getRGB());
        }
        
        setBackground(background);
        setForeground(foreground);
        
    }

    /**
     * This method handles cell painting part regarding to given cell focus
     * state.
     *
     * @param cellHasFocus True if the specified cell has the focus
     * @param list JList being painted
     */
    private void handleFocus(boolean cellHasFocus, JList<?> list) {
        if (cellHasFocus) {
            Color foreground = new Color(list.getForeground().getRGB());
            setBorder(BorderFactory.createLineBorder(foreground));
        } else {
            setBorder(BorderFactory.createEmptyBorder());
        }
    }
}
