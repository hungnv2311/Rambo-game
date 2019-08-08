package game;

import game.Physic.BoxCollider;
import game.Player.Player;
import game.Renderer.Renderer;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Background extends GameObject{
    public static boolean is_moving;

    public Background() {
//        image = SpriteUtils.loadImage("assets/images/background/0.png");
        renderer = new Renderer("assets/images/background/Map1.png");
        position.set(0, Settings.GAME_HEIGHT -Settings.BACKGROUND_HEIGHT);
        velocity.set(-5,0);
        anchor.set(0, 0);
        is_moving = true;
    }

    @Override
    public void run() {
        double speed = 0;
//        position.add(0, 10);
        if (KeyEventPress.isRightPress == true && Player.is_moving == false) {
            speed = -40;
        }   // position.add(velocity.x, velocity.y)
        if (KeyEventPress.isRightPress == true && Player.is_moving == true) {
            speed = 0;
        }
        velocity.set(speed,0);
        super.run();

        if(position.x < Settings.GAME_WIDTH - Settings.BACKGROUND_WIDTH) {
            position.x = Settings.GAME_WIDTH - Settings.BACKGROUND_WIDTH;
            is_moving = false;}
    }
}

