package game.Player.Item;

import game.GameObject;
import game.Physic.BoxCollider;
import game.Player.Player;
import game.Settings;

public abstract class Item extends GameObject {

    public Item() {
        hitBox = new BoxCollider(this, 12, 12);
    }

    @Override
    public void run() {
        super.run();
        this.checkPlayer();
        this.deactiveIfNeeded();
    }

    private void deactiveIfNeeded() {
        if (position.y > Settings.GAME_HEIGHT + 50) {
            this.deactive();
        }
    }

    private void checkPlayer() {
        Player player = GameObject.findIntersects(Player.class, hitBox);
        if (player != null) {
            this.powerUp(player);
            this.deactive();
        }
    }

    public abstract void powerUp(Player player);

}


