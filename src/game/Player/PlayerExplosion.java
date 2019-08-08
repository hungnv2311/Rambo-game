package game.Player;

import game.GameObject;
import game.Renderer.Renderer;

public class PlayerExplosion extends GameObject {
    public PlayerExplosion() {
        renderer = new Renderer("assets/images/Player/PlayerExplosions",
                2, true);
    }
}
