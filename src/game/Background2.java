package game;

import game.Physic.BoxCollider;
import game.Renderer.Renderer;

public class Background2 extends Background {
    public Background2() {
//        image = SpriteUtils.loadImage("assets/images/background/0.png");
        renderer = new Renderer("assets/images/background/Map1.png");
        position.set(0, 475);
        velocity.set(-5,0);
        anchor.set(0, 0);
        is_moving = true;
        this.hitBox = new BoxCollider(this, Settings.BACKGROUND_WIDTH, 300);
    }

    @Override
    public void run() {
        super.run();
    }
}
