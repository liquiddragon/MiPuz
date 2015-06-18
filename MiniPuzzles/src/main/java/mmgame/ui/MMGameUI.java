/**
 * This is MMGame main game UI.
 */
package mmgame.ui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import mmgame.event.MMStateListener;
import mmgame.logic.MMEngine;
import static framework.utilities.GraphicsUtil.scaleImageIcon;
import static framework.utilities.GraphicsUtil.getGray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import mmgame.event.MMStateEvent;
import mmgame.event.MMStates;
import mmgame.event.MMTimerEvent;
import mmgame.event.MMTimerListener;

/**
 * This class implements MMGame main game GUI.
 */
public class MMGameUI implements ActionListener, MMTimerListener {

    private final String resPrefix = "/MMGame/";
    private final int imageSize = 64;
    private final int gridXSize = 3;
    private final int gridYSize = 6;
    private final int delayBetweenBlinks = 500;
    private final int startingDelay = 1000;

    private enum GT {

        BLUE(0), BLUERED(1), PINK(2), RED(3), ORANGE(4), GREENYELLOW(5),
        GREEN(6), BLUEGREEN(7), LON(8), RON(9), OFF(10);

        private final int value;

        private GT(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    private final JPanel gamePanel;
    private final MMStateListener mmStateListener;
    private final MMEngine engine;
    private final List<ImageIcon> images;
    private Map<GT, GameTile> gameButtons;
    private List<Integer> selections;
    private int lastShownCount;
    private Timer blinkers;
    private Timer blinkersDelay;
    private int blinkIndex;

    /**
     * Create MMGame main UI.
     *
     * @param gameDisplay panel containing the game
     * @param engine game engine for running the logic
     * @param mmStateListener this GUI event listener
     */
    public MMGameUI(JPanel gameDisplay, MMEngine engine, MMStateListener mmStateListener) {
        gamePanel = gameDisplay;
        this.engine = engine;
        this.mmStateListener = mmStateListener;
        images = new ArrayList();
    }

    /**
     * Create main display and sets the game running.
     */
    public void runGame() {
        createButtons();
        createGameUI();
        udpateMainGamePanel();

        selections = new ArrayList<>();
        blinkers = createTimer(delayBetweenBlinks);
        blinkersDelay = createTimer(startingDelay);
    }

    /**
     * Create internal non-repeating timer.
     *
     * @param length timer length in milliseconds
     * @return Timer created
     */
    private Timer createTimer(int length) {
        Timer timer = new Timer(length, this);
        timer.setRepeats(false);

        return timer;
    }

    /**
     * Removes game GUI elements.
     */
    public void removeGameUI() {
        gamePanel.removeAll();
        udpateMainGamePanel();
    }

    /**
     * Construct the main display.
     */
    private void createGameUI() {
        gamePanel.setLayout(new GridLayout(gridXSize, gridYSize));

        gamePanel.add(gameButtons.get(GT.BLUE));
        gamePanel.add(gameButtons.get(GT.BLUERED));
        gamePanel.add(gameButtons.get(GT.PINK));
        gamePanel.add(gameButtons.get(GT.RED));
        gamePanel.add(gameButtons.get(GT.ORANGE));
        gamePanel.add(gameButtons.get(GT.GREENYELLOW));
        gamePanel.add(gameButtons.get(GT.GREEN));
        gamePanel.add(Box.createGlue());
        gamePanel.add(Box.createGlue());
        gamePanel.add(Box.createGlue());
        gamePanel.add(Box.createGlue());
        gamePanel.add(gameButtons.get(GT.BLUEGREEN));
        gamePanel.add(gameButtons.get(GT.LON));
        gamePanel.add(Box.createGlue());

        JButton go = new JButton("Go");
        go.addActionListener(this);
        go.setMnemonic(KeyEvent.VK_G);
        gamePanel.add(go);

        gamePanel.add(Box.createGlue());
        gamePanel.add(Box.createGlue());
        gamePanel.add(gameButtons.get(GT.RON));
    }

    /**
     * Request update to the main game panel.
     */
    private void udpateMainGamePanel() {
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    /**
     * Create game tiles for GUI.
     */
    private void createButtons() {
        loadImagesIntoList();

        createButtonsIntoMap();

        for (GameTile gt : gameButtons.values()) {
            gt.addActionListener(this);
        }
    }

    /**
     * Load, adjust and store game tile images into array.
     */
    private void loadImagesIntoList() {
        images.add(loadResource("Blue.png", imageSize, imageSize));
        images.add(loadResource("BlueRedTint.png", imageSize, imageSize));
        images.add(loadResource("Pink.png", imageSize, imageSize));
        images.add(loadResource("Red.png", imageSize, imageSize));
        images.add(loadResource("Orange.png", imageSize, imageSize));
        images.add(loadResource("GreenYellow.png", imageSize, imageSize));
        images.add(loadResource("Green.png", imageSize, imageSize));
        images.add(loadResource("BlueGreen.png", imageSize, imageSize));
        images.add(loadResource("Off.png", imageSize, imageSize));
        images.add(loadResource("Off.png", imageSize, imageSize));
        images.add(loadResource("On.png", imageSize, imageSize));
    }

    /**
     * Set game tile buttons in easy accessible map.
     */
    private void createButtonsIntoMap() {
        gameButtons = new HashMap<>();
        gameButtons.put(GT.BLUE, createGameTile(GT.BLUE.getValue(), GT.BLUE.getValue(), "0", KeyEvent.VK_1));
        gameButtons.put(GT.BLUERED, createGameTile(GT.BLUERED.getValue(), GT.BLUERED.getValue(), "1", KeyEvent.VK_2));
        gameButtons.put(GT.PINK, createGameTile(GT.PINK.getValue(), GT.PINK.getValue(), "2", KeyEvent.VK_3));
        gameButtons.put(GT.RED, createGameTile(GT.RED.getValue(), GT.RED.getValue(), "3", KeyEvent.VK_4));
        gameButtons.put(GT.ORANGE, createGameTile(GT.ORANGE.getValue(), GT.ORANGE.getValue(), "4", KeyEvent.VK_5));
        gameButtons.put(GT.GREENYELLOW, createGameTile(GT.GREENYELLOW.getValue(), GT.GREENYELLOW.getValue(), "5", KeyEvent.VK_6));
        gameButtons.put(GT.GREEN, createGameTile(GT.GREEN.getValue(), GT.GREEN.getValue(), "6", KeyEvent.VK_Q));
        gameButtons.put(GT.BLUEGREEN, createGameTile(GT.BLUEGREEN.getValue(), GT.BLUEGREEN.getValue(), "7", KeyEvent.VK_Y));
        gameButtons.put(GT.LON, createGameTile(GT.LON.getValue(), GT.OFF.getValue(), "8", KeyEvent.VK_A));
        gameButtons.put(GT.RON, createGameTile(GT.RON.getValue(), GT.OFF.getValue(), "9", KeyEvent.VK_H));
    }

    /**
     * Helper method to load image resource and adjust its size.
     *
     * @param imageName image name to be loaded
     * @param sizeX resized X dimension
     * @param sizeY resized Y dimension
     * @return ImageIcon with requested image
     */
    private ImageIcon loadResource(String imageName, int sizeX, int sizeY) {
        ImageIcon icon = new ImageIcon(getClass().getResource(resPrefix + imageName));

        return scaleImageIcon(icon, sizeX, sizeY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            executeGame((JButton) e.getSource());
        } else if (e.getSource() instanceof Timer) {
            //System.out.print("Timer action...");
            if (e.getSource() == blinkersDelay) {
                blinkersDelay.stop();
            }
            this.mmTimerReceived(new MMTimerEvent(this, 2));
        } else {
            gameTileAction((GameTile) e.getSource());
        }
    }

    /**
     * Helper method to get game running after player presses Go button.
     *
     * @param button that was pressed
     */
    private void executeGame(JButton button) {
        engine.startNewGame();

        lastShownCount = 1;
        blinkIndex = 0;
        blinkTiles();

        button.setVisible(false);
    }

    /**
     * GameTile action handler.
     *
     * @param gt GameTile pressed
     */
    private void gameTileAction(GameTile gt) {
        selections.add(Integer.parseInt(gt.getActionCommand()));
        //System.out.println("\nSelection: " + Integer.parseInt(gt.getActionCommand()));
        gt.setSelected(false);

        if (checkSelections() == true) {
            if (selections.size() == lastShownCount) {
                if (lastShownCount < engine.getGameLevel().getCount()) {
                    startNextRound(false);
                } else {
                    handleGameOver();
                }
            }
        } else if (selections.size() == lastShownCount) {
            startNextRound(true);
        }
    }

    /**
     * Helper method for handling game over.
     */
    private void handleGameOver() {
        JOptionPane.showMessageDialog(gamePanel, "Congratulations", "MMGame", JOptionPane.PLAIN_MESSAGE);

        MMStateEvent stateEvent = new MMStateEvent(this, MMStates.State.GAME_OVER);
        mmStateListener.mmStateReceived(stateEvent);
    }

    /**
     * Helper method to start next round or repeat previous one.
     *
     * @param repeat if true previous round is repeated
     */
    private void startNextRound(boolean repeat) {
        if (!repeat) {
            lastShownCount++;
        }
        blinkIndex = 0;
        blinkersDelay.restart();
        selections.clear();
    }

    /**
     * Helper method for checking if user entered input was correct.
     *
     * @return true if input was correct, otherwise false
     */
    private boolean checkSelections() {
        boolean correct = false;

        if (engine.checkGuess(selections, selections.size()) == MMEngine.CheckResult.CORRECT) {
            correct = true;
        }

        return correct;
    }

    /**
     * Helper method for creating GameTile.
     *
     * @param normalIndex index for normal image
     * @param selectedIndex index for selected image
     * @param actionCommand action command string attached to this GameTile
     * @param keyEvent shortcut key for this GameTile
     * @return GameTile created
     */
    private GameTile createGameTile(int normalIndex, int selectedIndex, String actionCommand, int keyEvent) {
        ImageIcon otherImage;
        if (normalIndex == selectedIndex) {
            otherImage = getGray(images.get(normalIndex));
        } else {
            otherImage = images.get(selectedIndex);
        }
        GameTile gt = new GameTile(images.get(normalIndex), otherImage, this);

        gt.setActionCommand(actionCommand);
        gt.setMnemonic(keyEvent);

        return gt;
    }

    /**
     * Helper method to blink tiles to visualise them to player.
     */
    private void blinkTiles() {
        List<Integer> numbers = engine.obtainNumbers(lastShownCount);

        if (blinkIndex < lastShownCount) {
            GameTile gt = gameButtons.get(GT.values()[numbers.get(blinkIndex)]);
            //System.out.println("\nBlink: " + numbers.get(blinkIndex));
            gt.blink();
            blinkIndex++;
        }
    }

    /**
     * Timer event handler for GameTile and game GUI events.
     *
     * @param event MMTimerEvent
     */
    @Override
    public void mmTimerReceived(MMTimerEvent event) {
        switch (event.getID()) {
            case 1:
                blinkers.restart();
                //System.out.print("Event 1..." + System.currentTimeMillis() + "...");
                break;

            case 2:
                if (blinkers.isRunning()) {
                    blinkers.stop();
                }
                //System.out.print("Event 2..." + System.currentTimeMillis() + "...");
                blinkTiles();
                break;
        }
    }
}
