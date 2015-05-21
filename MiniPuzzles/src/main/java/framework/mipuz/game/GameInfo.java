package framework.mipuz.game;

import javax.swing.ImageIcon;

/**
 * This class contains information of a game that framework uses to provide
 * information to end user of the game on its interface. All games are expected
 * to provide name and description of the game but game icon is optional
 * although recommended.
 */
public class GameInfo {

    private String shortName;
    private String description;
    private ImageIcon icon;

    /**
     * The constructor to setup all information of game.
     *
     * @param gname Short game name
     * @param gdescription Description of the game
     * @param gicon Games icon used in framework UI
     */
    public GameInfo(String gname, String gdescription, ImageIcon gicon) {
        this.shortName = gname;
        this.description = gdescription;
        this.icon = gicon;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
