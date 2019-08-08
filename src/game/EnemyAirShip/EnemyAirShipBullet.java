package game.EnemyAirShip;

import game.Background2;
import game.GameObject;
import game.Physic.BoxCollider;
import game.Player.Player;
import game.Player.PlayerExplosion;
import game.Renderer.Renderer;

import game.Settings;
import tklibs.SpriteUtils;

import javax.print.attribute.standard.RequestingUserName;

public class EnemyAirShipBullet extends GameObject {
    public int damage;

    public EnemyAirShipBullet() {
        renderer = new Renderer("assets/images/EnemyAirship/AirshipAttackDown.png");
        velocity.set(0, 4);
        velocity.setAngle(Math.toRadians(90));
        this.hitBox = new BoxCollider(this, 14, 61);
        this.damage = 1;
    }

    @Override
    public void run() {
        super.run();
        this.checkPlayer();
        this.deactiveIfNeeded();
        this.checkBackground();
    }

    private void checkBackground() {
        Background2 background2 = GameObject.findIntersects(Background2.class, this.hitBox);
        if (background2 != null) {
            this.deactive();
            PlayerExplosion explosion = GameObject.recycle((PlayerExplosion.class));
            explosion.position.set(position.x, position.y);
        }
    }

    private void deactiveIfNeeded() {
        if(this.position.y > Settings.GAME_HEIGHT + 50) {
            this.deactive();
        }
    }

    private void checkPlayer() {
        Player player = GameObject.findIntersects(Player.class, this.hitBox);
        if(player != null) {
            this.deactive();
            player.takeDamage(this.damage);
        }
    }
}
