/**
 * MiPuz graphic utilities package.
 */
package framework.utilities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import javax.swing.ImageIcon;

/**
 * Graphics utility methods.
 */
public class GraphicsUtil {

    /**
     * Scale given ImageIcon to new dimensions using smooth resampling
     * algorithm. If either newX or newY is negative the scaling substitutes
     * that value with appropriate value to maintain the aspect ratio of the
     * original image. Using zero (0) value in either newX or newY is considered
     * as an error and null is returned.
     *
     * @param original ImageIcon to be scaled. This is not modified.
     * @param newX Scaled image new X size
     * @param newY Scaled image new Y size
     * @return New scaled ImageIcon object or null, if either newX or newY is
     * zero
     */
    public static ImageIcon scaleImageIcon(ImageIcon original, int newX, int newY) {
        if (newX == 0 || newY == 0) {
            return null;
        }

        Image orgImage = original.getImage();
        Image scaledImage = orgImage.getScaledInstance(newY, newY, Image.SCALE_SMOOTH);

        return new ImageIcon(scaledImage);
    }

    /**
     * Convert given ImageIcon image into gray version of original image.
     *
     * See <a href="http://stackoverflow.com/questions/12226198/program-not-accessing-method-paintcomponent-of-extended-jpanel-class/12228640#12228640">
     * convert image to gray</a>
     *
     * @param icon ImageIcon to be converted
     * @return converted gray ImageIcon
     */
    public static ImageIcon getGray(ImageIcon icon) {
        final int w = icon.getIconWidth();
        final int h = icon.getIconHeight();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage image = gc.createCompatibleImage(w, h);
        Graphics2D g2d = image.createGraphics();
        g2d.setPaint(new Color(0x00f0f0f0));
        g2d.fillRect(0, 0, w, h);
        icon.paintIcon(null, g2d, 0, 0);
        BufferedImage gray = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        ColorConvertOp op = new ColorConvertOp(
                image.getColorModel().getColorSpace(),
                gray.getColorModel().getColorSpace(), null);
        op.filter(image, gray);
        
        return new ImageIcon(gray);
    }
}
