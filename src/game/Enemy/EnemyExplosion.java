package game.Enemy;

import game.GameObject;
import game.Renderer.Renderer;

public class EnemyExplosion extends GameObject {
    public EnemyExplosion() {
        renderer = new Renderer("assets/images/EnemyScientist",
                        8, true);
    }
}
