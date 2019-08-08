package game.Player;

import game.Enemy.Enemy;
import game.EnemyAirShip.EnemyAirShip;
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
    public int damage;

    public PlayerBulletUp() {
        renderer = new Renderer("assets/images/RegAttackUp.png", 4, false);
        velocity.set(0, -5);
        hitBox = new BoxCollider(this, Settings.PLAYER_BULLET_HEIGHT, Settings.PLAYER_BULLET_WIDTH);
        damage = 1;
    }

    @Override
    public void run() {
        super.run();
        this.checkEnemyAirShip();
        this.deactiveIfNeeded();
    }

    private void deactiveIfNeeded() {
        if(position.x > Settings.GAME_WIDTH + 100) {
            this.deactive();
        }
    }

    private void checkEnemyAirShip() {
        EnemyAirShip enemyairship = GameObject.findIntersects(EnemyAirShip.class, this.hitBox);
        if(enemyairship != null) {
            this.deactive();
            enemyairship.takeDamage(this.damage);
            if(!enemyairship.active) {
                Settings.score += 10;
            }
        }
    }
}
