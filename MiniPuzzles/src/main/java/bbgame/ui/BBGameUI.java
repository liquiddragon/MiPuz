package bbgame.ui;

import bbgame.logic.BBEngine;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * This class implements BBGame main GUI.
 */
public class BBGameUI implements ActionListener {

    private final String BBID = "BB";
    private final String BBZERO = "0";
    private final String BBONE = "1";
    private final JPanel gamePanel;
    private final BBEngine engine;
    private JPanel gameUI;
    List<JToggleButton> bbToggles;

    /**
     * This is BBGameUI default constructor.
     *
     * @param gameDisplay panel containing the game
     * @param engine game engine for running the logic
     */
    public BBGameUI(JPanel gameDisplay, BBEngine engine) {
        this.gamePanel = gameDisplay;
        this.engine = engine;
    }

    /**
     * This method creates main display and sets the game on the run.
     */
    public void runGame() {
        gamePanel.setLayout(new BorderLayout());

        createGameUI();

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    /**
     * This method constructs part of the main display.
     */
    private void createGameUI() {
        long newGuess = engine.obtainNewGuess();
        
        JLabel bbGoal = new JLabel("Target: " + newGuess);
        gamePanel.add(bbGoal, BorderLayout.LINE_START);

        JPanel bbPanel = createBBPanel();
        gamePanel.add(bbPanel, BorderLayout.CENTER);
    }

    /**
     * This method constructs main display button objects.
     *
     * @return panel containing main display buttons
     */
    private JPanel createBBPanel() {
        Long maxGuessValue = engine.getGameLevel().getLimit();

        JPanel bbPanel = new JPanel(new GridBagLayout());

        bbToggles = new ArrayList<>();

        int i = 0;
        while (maxGuessValue > 1) {
            JToggleButton gameButton = new JToggleButton(BBZERO, false);
            
            gameButton.addActionListener(this);
            gameButton.setActionCommand(BBID + Integer.toString(i++));
            
            bbToggles.add(gameButton);
            bbPanel.add(gameButton);
            
            maxGuessValue >>= 1;
        }

        return bbPanel;
    }

    /**
     * This method handles main game GUI actions.
     *
     * @param event to be handled
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().startsWith(BBID) == true) {
            udpateDisplay(event);

            String bbGuess = obtainGuess(event);

            if (engine.checkGuess(bbGuess) == BBEngine.CheckResult.CORRECT) {
                // Temporary testing dialog for letting player to know that the game has ended
                JOptionPane.showMessageDialog(null, "Game over!", "MiPuz", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * This method updates main display buttons based on event.
     *
     * @param event to be handled
     */
    private void udpateDisplay(ActionEvent event) {
        for (JToggleButton bbToggle : bbToggles) {
            if (bbToggle.getActionCommand().equals(event.getActionCommand())) {
                if (bbToggle.isSelected()) {
                    bbToggle.setText(BBONE);
                } else {
                    bbToggle.setText(BBZERO);
                }
            }
        }
    }

    /**
     * This method obtains a string formed by buttons.
     *
     * @param event to be handled
     * @return String containing user's guess
     */
    private String obtainGuess(ActionEvent event) {
        StringBuilder bbGuess = new StringBuilder();
        for (JToggleButton bbToggle : bbToggles) {
            if (bbToggle.isSelected()) {
                bbGuess.append(BBONE);
            } else {
                bbGuess.append(BBZERO);
            }
        }
        return bbGuess.toString();
    }
}
