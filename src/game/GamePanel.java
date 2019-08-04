package game;

import game.Enemy.Enemy;
import game.Enemy.EnemySummoner;
import game.Player.Item.ItemSummoner;
import game.Player.Player;
import tklibs.SpriteUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    Player player;
    Background background;
    double fps;

    public GamePanel(){
        background = new Background();
        player = new Player();
        new EnemySummoner();
        new ItemSummoner();
    }

    /**
     * auto bên called by program
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        GameObject.renderAll(g);

        this.drawMenu(g);
    }

    static Font font = new Font("Verdana", Font.BOLD, 32);
    static BufferedImage enemyImage = SpriteUtils.loadImage("assets/images/enemies/level0/pink/1.png");
    static BufferedImage heartImage = SpriteUtils.loadImage("assets/images/enemies/level0/blue/2.png");

    private void drawMenu(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(
                Settings.BACKGROUND_WIDTH,
                0,
                Settings.GAME_WIDTH - Settings.BACKGROUND_WIDTH,
                Settings.GAME_HEIGHT
        );

        g.setColor(Color.RED);
        g.drawString(fps + "", 750, 50);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawImage(enemyImage, 550, 175, null);
        g.drawString(Settings.score + "", 600, 200);

        if (player.hp < 3) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.GREEN);
        }

        g.drawImage(heartImage, 550, 225, null);
        g.drawString(player.hp + "", 600, 250);
    }

    public void runAll() {
        GameObject.runAll();
    }

    public void gameLoop(){
        long lastTime = System.currentTimeMillis();
        while (true) {
            long currentTime = System.currentTimeMillis();    // long giong int nhung co the luu chuoi dai hon
            if(currentTime - lastTime >= 1000/60){
                fps = 1000 / (currentTime-lastTime );
                this.repaint();      // ~ recall paint()
                this.runAll();       // chay logic cho game
                lastTime = currentTime;
            }

        }
    }
}
