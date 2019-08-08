package game;

import game.Enemy.Enemy;
import game.Enemy.EnemySummoner;
import game.EnemyAirShip.EnemyAirShipSummoner;
import game.Player.Item.ItemSummoner;
import game.Player.Player;
import tklibs.SpriteUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    Player player;
    Background background;
    Background2 background2;
    double fps;

    public GamePanel(){
        background = new Background();
        background2 = new Background2();
        player = new Player();
        new EnemySummoner();
        new EnemyAirShipSummoner();
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

    static Font font1 = new Font("Verdana", Font.PLAIN, 24);
    static Font font2 = new Font("Verdana", Font.BOLD, 24);
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
        g.setFont(font1);
        g.drawString(fps + "", Settings.GAME_WIDTH - 100, 35);

        g.setColor(Color.WHITE);
        g.setFont(font2);
        g.drawImage(enemyImage, 50, 35, null);
        g.drawString(Settings.score + "", 150, 35);

        if (player.hp < 3) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.GREEN);
        }

        g.drawImage(heartImage, 250, 35, null);
        g.drawString(player.hp + "", 400, 35);
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
