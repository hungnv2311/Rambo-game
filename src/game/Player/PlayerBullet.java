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

public class PlayerBullet extends GameObject {
    public PlayerBullet() {
        renderer = new Renderer("assets/images/RegAttack.png", 4, false);
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

    public void rotate(boolean is_rotate) {
        if(is_rotate) {
            renderer = new Renderer("assets/images/RegAttackUp.png");
            this.hitBox = new BoxCollider(this, Settings.PLAYER_BULLET_HEIGHT, Settings.PLAYER_BULLET_WIDTH);
        }

    }
}
