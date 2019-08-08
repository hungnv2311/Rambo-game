package game.EnemyAirShip;

import game.GameObject;
import game.Renderer.Renderer;

public class EnemyAirShipExplosion extends GameObject {
    public EnemyAirShipExplosion() {
        renderer = new Renderer("assets/images/EnemyAirship",
                8, true);
    }
}
