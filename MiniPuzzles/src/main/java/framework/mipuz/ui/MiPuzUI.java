/**
 * Framework GUI.
 */
package framework.mipuz.ui;

import framework.utilities.GBC;
import framework.mipuz.game.GameEnd;
import framework.mipuz.game.GameInfo;
import framework.mipuz.game.GameParameters;
import framework.mipuz.logic.Engine;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

/**
 * This class implements framework main GUI.
 */
public class MiPuzUI implements Runnable, ActionListener, GameEnd {

    private final String cmdPlayGame = "Play game";
    private final String gameTitle = "MiniPuzzles";
    private JFrame mainFrame;
    private final Engine engine;
    private JList<?> gamesList;
    private JPanel gameDisplay;
    private JPanel gameMenu;

    /**
     * Game framework default constructor.
     */
    public MiPuzUI() {
        engine = new Engine();
    }

    /**
     * Get framework running.
     */
    @Override
    public void run() {
        creatMainWindow();
    }

    /**
     * Create application main window.
     */
    private void creatMainWindow() {

        // Create main window frame
        mainFrame = new JFrame(gameTitle);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(getInitialSize());

        // Create main window panel that contains menu and game area
        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());

        createMenu(pane);
        createMainPanel(pane);

        mainFrame.getContentPane().add(pane);
        mainFrame.setVisible(true);
    }

    /**
     * Create main menu panel.
     *
     * @param pane Main window panel that will contain main menu
     */
    private void createMenu(JPanel pane) {
        // Create menu panel
        gameMenu = new JPanel();
        gameMenu.setLayout(new BorderLayout());

        // Game info list construction
        JScrollPane listScroller = createGameInfoList();
        gameMenu.add(listScroller, BorderLayout.CENTER);

        // Play game button construction
        JButton playGame = new JButton(cmdPlayGame);
        playGame.setMnemonic(KeyEvent.VK_P);
        playGame.addActionListener(this);
        gameMenu.add(playGame, BorderLayout.PAGE_END);

        // Add menu panel to main window panel
        pane.add(gameMenu, new GBC(0, 0).setAnchor(GBC.LAST_LINE_START).setFill(GBC.BOTH).setWeight(0.1, 1.0));
    }

    /**
     * Create JList containing available games.
     *
     * @return JScrollPane contained JList of available games
     */
    private JScrollPane createGameInfoList() {
        List<GameInfo> gis = createGameInfoArray();
        gamesList = new JList<>(gis.toArray());
        gamesList.setCellRenderer(new JLabelCellRenderer());
        gamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gamesList.setVisibleRowCount(4);
        JScrollPane listScroller = new JScrollPane(gamesList);

        return listScroller;
    }

    /**
     * Obtain available games and creates list from them.
     *
     * @return List of available games
     */
    private List<GameInfo> createGameInfoArray() {
        List<GameInfo> gis = new ArrayList<>();

        Iterator gamesItr = engine.listGameInfos();
        while (gamesItr.hasNext()) {
            GameInfo gi = (GameInfo) gamesItr.next();
            gis.add(gi);
        }
        return gis;
    }

    /**
     * Create main panel that will contain game to be played.
     *
     * @param pane Main window panel that will contain main panel
     */
    private void createMainPanel(JPanel pane) {
        // Create main panel
        gameDisplay = new JPanel();

        // Add main panel to main window panel
        pane.add(gameDisplay, new GBC(1, 0).setAnchor(GBC.FIRST_LINE_START).setFill(GBC.BOTH).setWeight(0.9, 1.0));
    }

    /**
     * Helper method for obtaining default windows size for the GUI.
     *
     * @return Dimension object containing default windows size
     */
    private Dimension getInitialSize() {
        Toolkit kit = Toolkit.getDefaultToolkit();

        Dimension frameSize = new Dimension();
        frameSize.setSize(kit.getScreenSize().getWidth() * 0.75,
                kit.getScreenSize().getHeight() / 2);

        return frameSize;
    }

    /**
     * Action listener method for handling GUI events.
     *
     * @param ae ActionEvent received
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals(cmdPlayGame)) {
            if (gamesList.getSelectedIndex() >= 0) {
                gameMenu.setVisible(false);

                GameParameters gparams = new GameParameters();
                gparams.setGameDisplay(gameDisplay);
                gparams.setGameEnd(this);
                GameInfo gi = (GameInfo) gamesList.getModel().getElementAt(gamesList.getSelectedIndex());
                engine.playGame(gi, gparams);
            }
        }
    }

    /**
     * Return control to main framework from a game.
     */
    @Override
    public void finished() {
        gameMenu.setVisible(true);
    }
}
