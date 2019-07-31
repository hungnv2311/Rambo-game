package game.Enemy;

import game.GameObject;
import game.Physic.BoxCollider;
import game.Player.Player;
import game.Renderer.Renderer;

import game.Settings;
import tklibs.SpriteUtils;

import javax.print.attribute.standard.RequestingUserName;

public class EnemyBullet extends GameObject {
    public int damage;

    public EnemyBullet() {
//        image = SpriteUtils.loadImage("assets/images/enemies/bullets/blue.png");
        renderer = new Renderer("assets/images/enemies/bullets");
        velocity.set(0, 4);
        this.hitBox = new BoxCollider(this, 16, 16);
        this.damage = 1;
    }

    @Override
    public void run() {
        super.run();
        this.checkPlayer();
        this.deactiveIfNeeded();
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
//            player.deactive();
            player.takeDamage(this.damage);
        }

    }
}
