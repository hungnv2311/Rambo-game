package game.Player;

import game.Enemy.Enemy;
import game.GameObject;
import game.Physic.BoxCollider;
import game.Renderer.Renderer;
import game.Settings;
import game.Vector2D;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerBullet extends GameObject {
    public PlayerBullet() {
//        image = SpriteUtils.loadImage("assets/images/player-bullets/a/1.png");
        renderer = new Renderer("assets/images/player-bullets/a", 4, false);
//        velocity = new Vector2D(0, -5);
        velocity.set(0, -5);
        hitBox = new BoxCollider(this, Settings.PLAYER_BULLET_WIDTH,Settings.PLAYER_BULLET_HEIGHT);
    }

    @Override
    public void run() {
        super.run();
        this.checkEnemy();
        this.deactiveIfNeeded();
    }

    private void deactiveIfNeeded() {
        if(position.y < -50) {
            this.deactive();
        }
    }

    private void checkEnemy() {
        Enemy enemy = GameObject.findIntersects(Enemy.class, this.hitBox);
        if (enemy != null) {
            this.deactive();
//            enemy.takeDamage(damge);
            if(!enemy.active) {
                Settings.score++;
            }
            enemy.deactive();

        }
    }
}
