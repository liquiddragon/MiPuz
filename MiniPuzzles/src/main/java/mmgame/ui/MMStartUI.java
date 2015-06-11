package mmgame.ui;

import framework.utilities.RelativeLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Hashtable; // This collection is obsolete but mandatory for JSlider custom labels
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
 * This class implements MMGame starting GUI.
 */
public class MMStartUI implements ActionListener, ChangeListener {

    private final String promptSelectLevel = "Select level";
    private final String cmdPlay = "Play";
    private final String cmdMenu = "Menu";
    private final JPanel gamePanel;
    private final MMStateListener mmStateListener;
    private MMEngine.GameLevel gameLevel;

    /**
     * This is MMStartUI default constructor.
     *
     * @param gameDisplay panel containing the game
     * @param mmStateListener this GUI event listener
     */
    public MMStartUI(JPanel gameDisplay, MMStateListener mmStateListener) {
        gamePanel = gameDisplay;
        this.mmStateListener = mmStateListener;
    }

    /**
     * This method creates game starting GUI and sets it running.
     */
    public void askGameLevel() {
        createAskGameLevelUI();
        udpateMainGamePanel();
    }

    /**
     * This method removes game starting GUI from game panel.
     */
    public void removeStartUI() {
        gamePanel.removeAll();
        udpateMainGamePanel();
    }

    /**
     * This method constructs main starting game GUI.
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
     * This method creates top panel for start GUI.
     *
     * @return JPanel containing top panel
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new RelativeLayout(RelativeLayout.X_AXIS));

        topPanel.add(Box.createGlue(), new Float(1));
        JLabel title = new JLabel(promptSelectLevel);
        topPanel.add(title, (float) 0.2);
        topPanel.add(Box.createGlue(), new Float(1));

        return topPanel;
    }

    /**
     * This method creates start GUI command buttons.
     *
     * @return panel containing command buttons
     */
    private JPanel createCmdButtons() {
        JPanel buttonPanel = new JPanel();

        // Removed as functionality does not exists yet
        //JButton play = createCommandButton(cmdPlay, KeyEvent.VK_P);
        //buttonPanel.add(play);
        JButton menu = createCommandButton(cmdMenu, KeyEvent.VK_M);
        buttonPanel.add(menu);

        return buttonPanel;
    }

    /**
     * This is helper method for creating command buttons.
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
     * This method creates game level selection panel for main GUI.
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
     * This method creates custom labels for the slider.
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
     * This method handles starting game GUI actions.
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
     * This method handles starting game GUI slider actions.
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
     * This method sends MMGame state event.
     *
     * @param state to be sent to listener
     */
    private void sendEvent(MMStates.State state) {
        MMStateEvent stateEvent = new MMStateEvent(this, state);
        mmStateListener.mmStateReceived(stateEvent);
    }

    /**
     * This method request update to the main game panel.
     */
    private void udpateMainGamePanel() {
        gamePanel.revalidate();
        gamePanel.repaint();
    }
}
