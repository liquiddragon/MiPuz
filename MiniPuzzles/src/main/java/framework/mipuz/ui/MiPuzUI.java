package framework.mipuz.ui;

import framework.mipuz.game.GameInfo;
import framework.mipuz.logic.Engine;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

public class MiPuzUI implements Runnable, ActionListener {

    private JFrame mainFrame;
    private final Engine engine;
    private JList gamesList;

    public MiPuzUI() {
        this.engine = new Engine();
    }

    @Override
    public void run() {
        creatMainWindow();
    }

    /**
     * This method creates application main window.
     */
    private void creatMainWindow() {

        // Create main window frame
        this.mainFrame = new JFrame("MiniPuzzles");
        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.setSize(getInitialSize());

        // Create main window panel that contains menu and game area
        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());

        createMenu(pane);
        createMainPanel(pane);

        this.mainFrame.getContentPane().add(pane);
        // Commented out for now for development purposes.
        //this.mainFrame.pack();
        this.mainFrame.setVisible(true);
    }

    /**
     * Create main menu panel.
     *
     * @param pane Main window panel that will contain main menu
     */
    private void createMenu(JPanel pane) {
        // Create menu panel
        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());

        // Game info list construction
        JScrollPane listScroller = createGameInfoList();
        menu.add(listScroller, BorderLayout.CENTER);

        // Play game button construction
        JButton playGame = new JButton("Play game");
        playGame.addActionListener(this);
        menu.add(playGame, BorderLayout.PAGE_END);

        // Add menu panel to main window panel
        // TO DO: Replace hard coded values with constants
        pane.add(menu, new GBC(0, 0).setAnchor(GBC.FIRST_LINE_END).setFill(GBC.BOTH).setWeight(0.1, 1.0));
    }

    /**
     * This method creates JList containing available games.
     *
     * @return JScrollPane contained JList of available games
     */
    private JScrollPane createGameInfoList() {
        DefaultListModel listModel = createGameInfoListModel();

        gamesList = new JList(listModel);
        gamesList.setCellRenderer(new JLabelCellRenderer());
        gamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gamesList.setVisibleRowCount(4);
        JScrollPane listScroller = new JScrollPane(gamesList);

        return listScroller;
    }

    /**
     * This method creates list model from available games to be included to
     * JList.
     *
     * @return ListModel containing available games
     */
    private DefaultListModel createGameInfoListModel() {
        List<GameInfo> gis = createGameInfoArray();

        DefaultListModel listModel = new DefaultListModel();
        for (GameInfo gi : gis) {
            listModel.addElement(gi);
        }

        return listModel;
    }

    /**
     * This method obtains available games and creates list from them.
     *
     * @return List of available games
     */
    private List<GameInfo> createGameInfoArray() {
        List<GameInfo> gis = new ArrayList<>();

        Iterator gamesItr = this.engine.listGameInfos();
        while (gamesItr.hasNext()) {
            GameInfo gi = (GameInfo) gamesItr.next();
            gis.add(gi);
        }
        return gis;
    }

    /**
     * This method creates main panel that will contain game to be played.
     *
     * @param pane Main window panel that will contain main panel
     */
    private void createMainPanel(JPanel pane) {
        // Create main panel
        JPanel gameDisplay = new JPanel();

        // Add main panel to main window panel
        // TO DO: Replace hard coded values with constants
        pane.add(gameDisplay, new GBC(1, 0).setAnchor(GBC.FIRST_LINE_START).setFill(GBC.BOTH).setWeight(0.9, 1.0));
    }

    /**
     * This is a helper method for obtaining default windows size for the GUI.
     *
     * @return Dimension object containing default windows size
     */
    private Dimension getInitialSize() {
        Toolkit kit = Toolkit.getDefaultToolkit();

        Dimension frameSize = new Dimension();
        frameSize.setSize(kit.getScreenSize().getWidth() / 2, kit.getScreenSize().getHeight() / 2);

        return frameSize;
    }

    /**
     * This is a test action listener method at the moment to ensured that GUI
     * part works as expected.
     *
     * @param ae ActionEvent received
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Play game")) {
            if (gamesList.getSelectedIndex() >= 0) {
                // Display selected item provided that Play game button was
                // pressed and something was selected from the list
                GameInfo gi = (GameInfo) gamesList.getModel().getElementAt(gamesList.getSelectedIndex());
                JOptionPane.showMessageDialog(mainFrame, "Selected: " + gi.getShortName(), "MiPuz", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
