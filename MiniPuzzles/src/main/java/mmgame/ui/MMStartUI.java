/**
 * MMGame start up UI.
 */
package mmgame.ui;

import framework.utilities.RelativeLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Hashtable; // This collection is marked as obsolete but mandatory for JSlider custom labels
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import mmgame.event.MMStateEvent;
import mmgame.event.MMStateListener;
import mmgame.event.MMStates;
import mmgame.logic.MMEngine;

/**
 * MMGame starting GUI.
 */
public class MMStartUI implements ActionListener, ChangeListener {

    private final String promptSelectLevel = "Select level";
    private final String cmdPlay = "Play";
    private final String cmdMenu = "Menu";
    private final JPanel gamePanel;
    private final MMStateListener mmStateListener;
    private MMEngine.GameLevel gameLevel;

    /**
     * Construct MMStart GUI.
     *
     * @param gameDisplay panel containing the game
     * @param mmStateListener this GUI event listener
     */
    public MMStartUI(JPanel gameDisplay, MMStateListener mmStateListener) {
        gamePanel = gameDisplay;
        this.mmStateListener = mmStateListener;
        gameLevel = MMEngine.GameLevel.EASY;
    }

    /**
     * Create game starting GUI and set it running.
     */
    public void askGameLevel() {
        createAskGameLevelUI();
        udpateMainGamePanel();
    }

    /**
     * Remove game starting GUI from game panel.
     */
    public void removeStartUI() {
        gamePanel.removeAll();
        udpateMainGamePanel();
    }

    /**
     * Return player selected game level.
     *
     * @return GameLevel selected
     */
    public MMEngine.GameLevel getSelection() {
        return gameLevel;
    }

    /**
     * Helper method for constructing main starting game GUI.
     */
    private void createAskGameLevelUI() {
        gamePanel.setLayout(new BorderLayout());

        JPanel topPanel = createTopPanel();
        gamePanel.add(topPanel, BorderLayout.PAGE_START);

        JPanel selectionUI = createLevelSelectionPanel();
        gamePanel.add(selectionUI, BorderLayout.CENTER);

        JPanel buttonPanel = createCmdButtons();
        gamePanel.add(buttonPanel, BorderLayout.PAGE_END);
    }

    /**
     * Create top panel for start GUI.
     *
     * @return JPanel containing top panel
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new RelativeLayout(RelativeLayout.X_AXIS));

        topPanel.add(Box.createGlue(), new Float(1));
        JLabel title = new JLabel(promptSelectLevel);
        topPanel.add(title, (float) 0.4);
        topPanel.add(Box.createGlue(), new Float(1));

        return topPanel;
    }

    /**
     * Create start GUI command buttons.
     *
     * @return panel containing command buttons
     */
    private JPanel createCmdButtons() {
        JPanel buttonPanel = new JPanel();

        JButton play = createCommandButton(cmdPlay, KeyEvent.VK_P);
        buttonPanel.add(play);
        JButton menu = createCommandButton(cmdMenu, KeyEvent.VK_M);
        buttonPanel.add(menu);

        return buttonPanel;
    }

    /**
     * Helper method for creating command buttons.
     *
     * @param command name of the command to display
     * @param cmdKey shortcut key for the command
     * @return JButton created command
     */
    private JButton createCommandButton(String command, int cmdKey) {
        JButton cmdButton = new JButton(command);

        cmdButton.setActionCommand(command);
        cmdButton.setMnemonic(cmdKey);
        cmdButton.addActionListener(this);

        return cmdButton;
    }

    /**
     * Creates game level slider for main GUI.
     *
     * @return panel contains game level selection method
     */
    private JPanel createLevelSelectionPanel() {
        JPanel selectionUI = new JPanel();

        JSlider levelSelection = new JSlider(JSlider.HORIZONTAL,
                MMEngine.GameLevel.EASY.getLevelValue(),
                MMEngine.GameLevel.HARD.getLevelValue(),
                MMEngine.GameLevel.EASY.getLevelValue());
        levelSelection.setPaintTicks(true);
        levelSelection.addChangeListener(this);

        Hashtable<Integer, JLabel> levelLabels = createSliderLabels(levelSelection);
        levelSelection.setLabelTable(levelLabels);
        levelSelection.setPaintLabels(true);

        selectionUI.add(levelSelection);

        return selectionUI;
    }

    /**
     * Helper method for creating custom labels for the slider.
     *
     * @param levelSelection
     */
    private Hashtable<Integer, JLabel> createSliderLabels(JSlider levelSelection) {
        Hashtable<Integer, JLabel> levelLabels = new Hashtable<>();
        levelLabels.put(MMEngine.GameLevel.EASY.getLevelValue(),
                new JLabel(MMEngine.GameLevel.EASY.toString()));
        levelLabels.put(MMEngine.GameLevel.MEDIUM.getLevelValue(),
                new JLabel(MMEngine.GameLevel.MEDIUM.toString()));
        levelLabels.put(MMEngine.GameLevel.HARD.getLevelValue(),
                new JLabel(MMEngine.GameLevel.HARD.toString()));
        return levelLabels;
    }

    /**
     * Starting game GUI actions handler.
     *
     * @param event to be handled
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case cmdPlay:
                sendEvent(MMStates.State.RUN);
                break;
            case cmdMenu:
                sendEvent(MMStates.State.RETURN_TO_MENU);
                break;
        }
    }

    /**
     * Helper method for handling starting game GUI slider actions.
     *
     * @param event to be handled
     */
    @Override
    public void stateChanged(ChangeEvent event) {
        JSlider source = (JSlider) event.getSource();
        if (!source.getValueIsAdjusting()) {
            if (source.getValue() == MMEngine.GameLevel.EASY.getLevelValue()) {
                gameLevel = MMEngine.GameLevel.EASY;
            } else if (source.getValue() == MMEngine.GameLevel.MEDIUM.getLevelValue()) {
                gameLevel = MMEngine.GameLevel.MEDIUM;
            } else {
                gameLevel = MMEngine.GameLevel.HARD;
            }
        }
    }

    /**
     * Helper method for sending MMGame state event.
     *
     * @param state to be sent to listener
     */
    private void sendEvent(MMStates.State state) {
        MMStateEvent stateEvent = new MMStateEvent(this, state);
        mmStateListener.mmStateReceived(stateEvent);
    }

    /**
     * Request update to the main game panel.
     */
    private void udpateMainGamePanel() {
        gamePanel.revalidate();
        gamePanel.repaint();
    }
}
