package bbgame.ui;

import bbgame.logic.BBEngine;
import bbgame.BBStateEvent;
import bbgame.BBStateListener;
import bbgame.BBStates;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * This class implements BBGame starting GUI.
 */
public class BBStartUI implements ActionListener {

    private final String cmdPlay = "Play";
    private final String cmdMenu = "Menu";
    private final String promptSelectLevel = "Select level";
    private final JPanel gamePanel;
    private BBEngine.GameLevel gameLevel;
    private JPanel askGLUI;
    private final BBStateListener bbStateListener;

    /**
     * This is BBStartUI default constructor.
     *
     * @param gameDisplay panel containing the game
     * @param bbStateListener this GUI event listener
     */
    public BBStartUI(JPanel gameDisplay, BBStateListener bbStateListener) {
        this.gamePanel = gameDisplay;
        this.bbStateListener = bbStateListener;
    }

    /**
     * This method creates game starting GUI and sets it running.
     */
    public void askGameLevel() {
        askGLUI = createAskGameLevelUI();

        gamePanel.add(askGLUI);
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    /**
     * This method removes game starting GUI from game panel.
     */
    public void removeStartUI() {
        gamePanel.remove(askGLUI);
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    /**
     * This method returns player selected game level.
     *
     * @return GameLevel selected
     */
    public BBEngine.GameLevel getSelection() {
        return gameLevel;
    }

    /**
     * This method constructs main starting game GUI.
     *
     * @return main panel containing the GUI
     */
    private JPanel createAskGameLevelUI() {
        JPanel askUI = new JPanel(new BorderLayout());

        JLabel title = new JLabel(promptSelectLevel);
        askUI.add(title, BorderLayout.PAGE_START);

        JPanel selectionUI = createLevelSelectionPanel();
        askUI.add(selectionUI, BorderLayout.CENTER);

        JPanel buttonPanel = createCmdButtons();
        askUI.add(buttonPanel, BorderLayout.PAGE_END);

        return askUI;
    }

    /**
     * This method creates start GUI command buttons.
     *
     * @return panel containing command buttons
     */
    private JPanel createCmdButtons() {
        JPanel buttonPanel = new JPanel();

        JButton play = new JButton(cmdPlay);
        play.setActionCommand(cmdPlay);
        play.addActionListener(this);
        buttonPanel.add(play);

        JButton menu = new JButton(cmdMenu);
        menu.setActionCommand(cmdMenu);
        menu.addActionListener(this);
        buttonPanel.add(menu);

        return buttonPanel;
    }

    /**
     * This method creates game level selection buttons for main GUI.
     *
     * @return panel contains game level selection buttons
     */
    private JPanel createLevelSelectionPanel() {
        JPanel selectionUI = new JPanel();
        ButtonGroup glChoices = new ButtonGroup();

        JRadioButton easyGL = constructGameLevelRadioButton(
                BBEngine.GameLevel.EASY.toString(), KeyEvent.VK_E, true);
        gameLevel = BBEngine.GameLevel.EASY;
        JRadioButton mediumGL = constructGameLevelRadioButton(
                BBEngine.GameLevel.MEDIUM.toString(), KeyEvent.VK_M, false);
        JRadioButton hardGL = constructGameLevelRadioButton(
                BBEngine.GameLevel.HARD.toString(), KeyEvent.VK_H, false);

        glChoices.add(easyGL);
        glChoices.add(mediumGL);
        glChoices.add(hardGL);

        selectionUI.add(easyGL);
        selectionUI.add(mediumGL);
        selectionUI.add(hardGL);

        return selectionUI;
    }

    /**
     * This method is private helper routine to create JRadioButtons.
     *
     * @param name of the button and action command
     * @param mnemonic for keyboard access
     * @param selected default state of JRadioButton
     * @return
     */
    private JRadioButton constructGameLevelRadioButton(String name, int mnemonic, boolean selected) {
        JRadioButton radioButton = new JRadioButton(name);

        radioButton.setMnemonic(mnemonic);
        radioButton.setActionCommand(name);
        radioButton.addActionListener(this);
        radioButton.setSelected(selected);

        return radioButton;
    }

    /**
     * This method handles staring game GUI actions.
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
     * This method sends BBGame state event.
     *
     * @param state to be sent to listener
     */
    private void sendEvent(BBStates.State state) {
        BBStateEvent stateEvent = new BBStateEvent(this, state);
        bbStateListener.bbStateReceived(stateEvent);
    }
}
