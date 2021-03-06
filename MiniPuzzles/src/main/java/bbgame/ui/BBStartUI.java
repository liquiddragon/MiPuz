/**
 * BBGame start up UI.
 */
package bbgame.ui;

import bbgame.logic.BBEngine;
import bbgame.event.BBStateEvent;
import bbgame.event.BBStateListener;
import bbgame.event.BBStates;
import framework.utilities.RelativeLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * BBGame starting GUI.
 */
public class BBStartUI implements ActionListener {

    private final String cmdPlay = "Play";
    private final String cmdMenu = "Menu";
    private final String promptSelectLevel = "Select level";
    private final JPanel gamePanel;
    private final BBStateListener bbStateListener;
    private BBEngine.GameLevel gameLevel;

    /**
     * Construct BBStart GUI.
     *
     * @param gameDisplay panel containing the game
     * @param bbStateListener this GUI event listener
     */
    public BBStartUI(JPanel gameDisplay, BBStateListener bbStateListener) {
        gamePanel = gameDisplay;
        this.bbStateListener = bbStateListener;
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
    public BBEngine.GameLevel getSelection() {
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

        JButton menu = createCommandButton(cmdMenu, KeyEvent.VK_E);
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
     * Create game level selection buttons for main GUI.
     *
     * @return panel contains game level selection buttons
     */
    private JPanel createLevelSelectionPanel() {
        JPanel selectionUI = new JPanel();
        ButtonGroup glChoices = new ButtonGroup();

        JRadioButton easyGL = constructGameLevelRadioButton(
                BBEngine.GameLevel.EASY.toString(), KeyEvent.VK_E, true);
        gameLevel = BBEngine.GameLevel.EASY;
        glChoices.add(easyGL);
        selectionUI.add(easyGL);

        JRadioButton mediumGL = constructGameLevelRadioButton(
                BBEngine.GameLevel.MEDIUM.toString(), KeyEvent.VK_M, false);
        glChoices.add(mediumGL);
        selectionUI.add(mediumGL);

        JRadioButton hardGL = constructGameLevelRadioButton(
                BBEngine.GameLevel.HARD.toString(), KeyEvent.VK_H, false);
        glChoices.add(hardGL);
        selectionUI.add(hardGL);

        return selectionUI;
    }

    /**
     * Helper method for creating JRadioButtons used on GUI.
     *
     * @param name of the button and action command
     * @param mnemonic for keyboard access
     * @param selected default state of JRadioButton
     * @return
     */
    private JRadioButton constructGameLevelRadioButton(String name, int mnemonic,
            boolean selected) {
        JRadioButton radioButton = new JRadioButton(name);

        radioButton.setMnemonic(mnemonic);
        radioButton.setActionCommand(name);
        radioButton.addActionListener(this);
        radioButton.setSelected(selected);

        return radioButton;
    }

    /**
     * Starting game GUI actions handler.
     *
     * @param event to be handled
     */
    @Override
    public void actionPerformed(ActionEvent event) {

        if (event.getActionCommand().equals(cmdPlay)) {
            sendEvent(BBStates.State.RUN);
        } else if (event.getActionCommand().equals(cmdMenu)) {
            sendEvent(BBStates.State.RETURN_TO_MENU);
        } else {
            switch (BBEngine.GameLevel.valueOf(event.getActionCommand())) {
                case EASY:
                    gameLevel = BBEngine.GameLevel.EASY;
                    break;

                case MEDIUM:
                    gameLevel = BBEngine.GameLevel.MEDIUM;
                    break;

                case HARD:
                    gameLevel = BBEngine.GameLevel.HARD;
                    break;
            }
        }
    }

    /**
     * Helper method for sending BBGame state event.
     *
     * @param state to be sent to listener
     */
    private void sendEvent(BBStates.State state) {
        BBStateEvent stateEvent = new BBStateEvent(this, state);
        bbStateListener.bbStateReceived(stateEvent);
    }

    /**
     * Request update to the main game panel.
     */
    private void udpateMainGamePanel() {
        gamePanel.revalidate();
        gamePanel.repaint();
    }
}
