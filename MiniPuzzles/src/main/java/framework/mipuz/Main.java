/**
 * Container for main method.
 */
package framework.mipuz;

import java.awt.EventQueue;
import framework.mipuz.ui.MiPuzUI;

/**
 * Main method container.
 */
public class Main {

    /**
     * Start the framework GUI.
     * 
     * @param args command line parameters
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new MiPuzUI());
    }
}
