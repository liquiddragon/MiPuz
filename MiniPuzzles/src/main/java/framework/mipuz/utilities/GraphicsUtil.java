package framework.mipuz.utilities;

import java.awt.Image;
import javax.swing.ImageIcon;

public class GraphicsUtil {

    /**
     * This method scales given ImageIcon to new dimensions using smooth
     * resampling algorithm. If either newX or newY is negative the scaling
     * substitutes that value with appropriate value to maintain the aspect
     * ratio of the original image. Using zero (0) value in either newX or
     * newY is considered as an error and null is returned.
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
}
