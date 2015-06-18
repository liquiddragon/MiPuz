/**
 * This is BBGame main game UI.
 */
package bbgame.ui;

import bbgame.event.BBStateEvent;
import bbgame.event.BBStateListener;
import bbgame.event.BBStates;
import bbgame.logic.BBEngine;
import framework.utilities.GBC;
import framework.utilities.RelativeLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * This class implements BBGame main game GUI.
 */
public class BBGameUI implements ActionListener {

    private final String bbID = "BB";
    private final String tBBZero = "0";
    private final String tBBOne = "1";
    private final String gameOverID = "GO";
    private final String showHelpID = "SH";
    private final String tHelp = "Help";
    private final String panelTopID = "TOP";
    private final String panelMainID = "MAIN";
    private final String panelBottomID = "BOTTOM";
    private final String tCurrent = "Current: ";
    private final String tTarget = "Target: ";
    private final String tGameOver = "Game over";
    private final int rowLength = 16;
    private final int[] shortCutKeys;
    private final JPanel gamePanel;
    private final BBEngine engine;
    private final BBStateListener bbStateListener;
    Map<String, JPanel> uiPanels;
    List<JToggleButton> bbToggles;
    private JButton showHelp;
    private long currentGuess;
    private boolean hintsOn;

    /**
     * Create BBGame main UI.
     *
     * @param gameDisplay panel containing the game
     * @param engine game engine for running the logic
     * @param bbStateListener this GUI event listener
     */
    public BBGameUI(JPanel gameDisplay, BBEngine engine, BBStateListener bbStateListener) {
        shortCutKeys = new int[]{KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3,
            KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7,
            KeyEvent.VK_8, KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E,
            KeyEvent.VK_R, KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_U,
            KeyEvent.VK_I, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D,
            KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_J,
            KeyEvent.VK_K, KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C,
            KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_N, KeyEvent.VK_M,
            KeyEvent.VK_COMMA};
        gamePanel = gameDisplay;
        this.engine = engine;
        this.bbStateListener = bbStateListener;
        uiPanels = new HashMap<>();
        hintsOn = false;
    }

    /**
     * Create main display and sets the game running.
     */
    public void runGame() {
        createGameUI();
        udpateMainGamePanel();
    }

    /**
     * Removes game GUI elements.
     */
    public void removeGameUI() {
        gamePanel.removeAll();
        udpateMainGamePanel();
    }

    /**
     * Construct the main display from its sub-components.
     */
    private void createGameUI() {
        gamePanel.setLayout(new BorderLayout());

        long newGuess = engine.obtainNewGuess();
        currentGuess = 0;

        gamePanel.add(createTopPanel(newGuess), BorderLayout.PAGE_START);
        gamePanel.add(createBBPanel(), BorderLayout.CENTER);
        gamePanel.add(createBottomPanel(), BorderLayout.PAGE_END);

    }

    /**
     * Create top panel for game GUI.
     *
     * @param guess value tried to obtain
     * @return JPanel containing top panel
     */
    private JPanel createTopPanel(long guess) {
        JPanel topPanel = new JPanel(new RelativeLayout(RelativeLayout.X_AXIS));

        topPanel.add(Box.createGlue(), new Float(1));
        JLabel bbGoal = new JLabel(tTarget + guess);
        topPanel.add(bbGoal, (float) 0.4);
        topPanel.add(Box.createGlue(), new Float(1));

        uiPanels.put(panelTopID, topPanel);

        return topPanel;
    }

    /**
     * Create bottom panel for game GUI.
     *
     * @return JPanel containing bottom panel
     */
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new RelativeLayout(RelativeLayout.X_AXIS));

        bottomPanel.add(Box.createGlue(), new Float(1));

        showHelp = new JButton(tHelp);
        showHelp.setActionCommand(showHelpID);
        showHelp.setMnemonic(KeyEvent.VK_0);
        showHelp.addActionListener(this);

        bottomPanel.add(showHelp, (float) 0.6);
        bottomPanel.add(Box.createGlue(), new Float(1));

        uiPanels.put(panelBottomID, bottomPanel);

        return bottomPanel;
    }

    /**
     * Construct main display button objects.
     *
     * @return panel containing main display buttons
     */
    private JPanel createBBPanel() {
        JPanel bbPanel = new JPanel(new GridBagLayout());
        bbToggles = new ArrayList<>();

        int column = 0;
        int row = 0;
        int keyIndex = 0;
        int i = engine.getGameLevel().getBits();
        while (i > 0) {
            JToggleButton gameButton = createGameButton(i--, keyIndex++);
            bbToggles.add(gameButton);
            bbPanel.add(gameButton, new GBC(column, row).setFill(GBC.HORIZONTAL).setWeight(1.0, 1.0));

            column++;
            if (column % rowLength == 0) {
                row++;
                column = 0;
            }
        }
        uiPanels.put(panelMainID, bbPanel);

        return bbPanel;
    }

    /**
     * Helper method to create JToggleButton for game GUI.
     *
     * @param i button index
     * @param keyIndex index to short cut keys table
     * @return JToggleButton created button
     */
    private JToggleButton createGameButton(int i, int keyIndex) {
        JToggleButton gameButton = new JToggleButton(tBBZero, false);

        gameButton.addActionListener(this);
        gameButton.setActionCommand(bbID + Integer.toString(i));
        gameButton.setMnemonic(shortCutKeys[keyIndex]);

        return gameButton;
    }

    /**
     * Handler for the main game GUI actions.
     *
     * @param event to be handled
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().startsWith(bbID) == true) {
            handleGameCommands(event);
        } else {
            switch (event.getActionCommand()) {
                case gameOverID:
                    BBStateEvent stateEvent = new BBStateEvent(this, BBStates.State.GAME_OVER);
                    bbStateListener.bbStateReceived(stateEvent);
                    break;

                case showHelpID:
                    handleHelpCommand();
                    break;
            }
        }
    }

    /**
     * Helper method to handle help command and displaying hints.
     */
    private void handleHelpCommand() {
        hintsOn = !hintsOn;
        if (hintsOn) {
            showHelp.setText(tCurrent + currentGuess);
        } else {
            showHelp.setText(tHelp);
        }
    }

    /**
     * Helper method for handling game related commands and updating display
     * accordingly.
     *
     * @param event to be handled
     */
    private void handleGameCommands(ActionEvent event) {
        udpateDisplay(event);

        String bbGuess = obtainGuess(event);

        if (engine.checkGuess(bbGuess) == BBEngine.CheckResult.CORRECT) {
            displayGameOver();
        }
    }

    /**
     * Updates main display buttons based on event.
     *
     * @param event to be handled
     */
    private void udpateDisplay(ActionEvent event) {
        for (JToggleButton bbToggle : bbToggles) {
            if (bbToggle.getActionCommand().equals(event.getActionCommand())) {
                long value = obtainButtonValue(bbToggle);

                if (bbToggle.isSelected()) {
                    currentGuess += value;
                    bbToggle.setText(tBBOne);
                } else {
                    currentGuess -= value;
                    bbToggle.setText(tBBZero);
                }

                if (hintsOn) {
                    showHelp.setText(tCurrent + currentGuess);
                }
            }
        }
    }

    /**
     * Helper method for obtaining value based on selected button.
     *
     * @param bbToggle button pressed
     * @return value for selected button
     */
    private long obtainButtonValue(JToggleButton bbToggle) {
        String buttonValue;

        buttonValue = new String(bbToggle.getActionCommand().substring(bbID.length()));

        long value = 0;
        try {
            value = Integer.parseInt(buttonValue);
            value = 1L << (value - 1L);
        } catch (NumberFormatException e) {
            System.out.println("Internal error: " + e.toString());
        }

        return value;
    }

    /**
     * Helper method for obtaining a string formed by buttons.
     *
     * @param event to be handled
     * @return String containing user's guess
     */
    private String obtainGuess(ActionEvent event) {
        StringBuilder bbGuess = new StringBuilder();
        for (JToggleButton bbToggle : bbToggles) {
            if (bbToggle.isSelected()) {
                bbGuess.append(tBBOne);
            } else {
                bbGuess.append(tBBZero);
            }
        }
        return bbGuess.toString();
    }

    /**
     * Helper method for handling Game Over message displaying and related
     * action creation.
     */
    private void displayGameOver() {
        JPanel bottomPanel = uiPanels.get(panelBottomID);
        bottomPanel.removeAll();

        bottomPanel.add(Box.createGlue(), new Float(1));

        JButton gameOver = new JButton(tGameOver);
        gameOver.setActionCommand(gameOverID);
        gameOver.setMnemonic(KeyEvent.VK_O);
        gameOver.addActionListener(this);

        bottomPanel.add(gameOver, (float) 0.4);
        bottomPanel.add(Box.createGlue(), new Float(1));

        udpateMainGamePanel();
    }

    /**
     * Request update to the main game panel.
     */
    private void udpateMainGamePanel() {
        gamePanel.revalidate();
        gamePanel.repaint();
    }
}
