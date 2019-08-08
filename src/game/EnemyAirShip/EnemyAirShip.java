package game.EnemyAirShip;

import game.Enemy.EnemyExplosion;
import game.GameObject;
import game.Physic.BoxCollider;
import game.Player.PlayerExplosion;
import game.Renderer.Renderer;
import game.Settings;
import tklibs.SpriteUtils;

public class EnemyAirShip extends GameObject {
    public boolean immune;
    public int hp;

    public EnemyAirShip() {
        renderer = new Renderer("assets/images/EnemyAirship/EnemyAirship.png");
        position.set(Settings.GAME_WIDTH + 50, 100);
        velocity.set(3, 0);
        velocity.setAngle(Math.toRadians(-180));
        hitBox = new BoxCollider(this, 208, 105);
        immune = false;
        hp = 10;
    }

    public void takeDamage(int damage) {
            hp -= damage;
            if(hp <= 0 ) {
                hp = 0;
                this.deactive();
                EnemyAirShipExplosion explosion = GameObject.recycle((EnemyAirShipExplosion.class));
                explosion.position.set(position.x, position.y);
            }
    }

    @Override
    public void run() {
        super.run();
        this.checkChaneDirection();
        this.fire();
        this.deactiveIfNeeded();
    }

    private void deactiveIfNeeded() {
        if(this.position.y > Settings.GAME_HEIGHT + 50) {
            this.deactive();
        }
    }

    int count=0;
    private void fire() {
        count++;
        if(count >60) {
            EnemyAirShipBullet bullet = GameObject.recycle(EnemyAirShipBullet.class);
            bullet.position.set(position.x, position.y);

            count = 0;
        }
    }

    private void checkChaneDirection() {
        if (this.outOfBoundRight() && this.onGoingRight()) {
            velocity.x = -velocity.x;
        }

        if (this.outOfBoundLeft() && this.onGoingLeft()) {
            velocity.x = -velocity.x;
        }
    }

    private boolean onGoingLeft() {
        return velocity.x <0;
    }

    private boolean outOfBoundLeft() {
        return position.x <= 0;
    }

    private boolean onGoingRight() {
        return velocity.x >0;
    }

    private boolean outOfBoundRight() {
        return position.x >= Settings.GAME_WIDTH - Settings.ENEMY_WIDTH;
    }

    @Override
    public void reset() {
        super.reset();
        position.set(Settings.GAME_WIDTH + 50, 100);
    }

    @Override
    public void deactive() {
        super.deactive();
        EnemyAirShipExplosion explosion = GameObject.recycle((EnemyAirShipExplosion.class));
        explosion.position.set(position.x, position.y);
    }
}
