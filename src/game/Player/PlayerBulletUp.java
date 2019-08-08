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
import java.util.Set;

public class PlayerBulletUp extends GameObject {
    public PlayerBulletUp() {
        renderer = new Renderer("assets/images/RegAttackUp.png", 4, false);
        velocity.set(0, -5);
        hitBox = new BoxCollider(this, Settings.PLAYER_BULLET_HEIGHT, Settings.PLAYER_BULLET_WIDTH);
    }

    @Override
    public void run() {
        super.run();
        this.checkEnemy();
        this.deactiveIfNeeded();
    }

    private void deactiveIfNeeded() {
        if(position.x > Settings.GAME_WIDTH + 100) {
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
