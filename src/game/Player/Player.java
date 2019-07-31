package game.Player;

import game.*;
import game.Physic.BoxCollider;
import game.Renderer.Renderer;
import tklibs.Mathx;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Set;

public class Player extends GameObject {
    public int hp;
    public boolean immune;
    public static boolean is_moving;

    public Player() {
//        image = SpriteUtils.loadImage("assets/images/players/straight/0.png");
        renderer = new Renderer("assets/images/players/straight");
        position.set(10, 450);
        this.hitBox = new BoxCollider(this, Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT);
        hp = 3;
        immune = false;
    }

    public void takeDamage(int damage) {
        if(!immune) {
            hp -= damage;
            immune = true;
            if(hp <= 0 ) {
                hp = 0;
                this.deactive();
        }
        }
    }

    int countRender = 0;
    @Override
    public void render(Graphics g) {
        if(immune) {
            countRender++;
            if (countRender % 2 ==0) {
                super.render(g);
            }

        } else {
            super.render(g);
        }
    }

    @Override
    public void run() {
        super.run();
        // move
        this.move();
        // limit
        this.limitPosition();
        // fire
        this.fire();
        this.checkImmune();
    }

    private void checkImmune() {
        if(this.immune) {
            countImmune++;
            if(countImmune > 120) {
                immune = false;
            }
        } else {
            countImmune = 0;
        }
    }

    int countImmune = 0;


    int frameCount = 0;

    private void fire() {
        frameCount++;
        if (KeyEventPress.isFirePress && frameCount > 20) {
            int numberBullets = 5;
            double startAngle = Math.toRadians(-45);
            double endAngle = Math.toRadians(-135);
            double stepAngle = Math.abs(startAngle - endAngle) / (numberBullets - 1);
            double startX = position.x + 20;
            double endX = position.x - 20;
            double stepX = Math.abs(endX - startX) / (numberBullets - 1);

            for (int i = 0; i < numberBullets; i++) {
//                PlayerBullet bullet = new PlayerBullet();
                PlayerBullet bullet = GameObject.recycle(PlayerBullet.class);
                bullet.position.set(startX - stepX * i, position.y);
                bullet.velocity.setAngle(startAngle - stepAngle * i);
            }
            frameCount = 0;
    }
    }

    private void move() {
        double vx = 0;
        double vy = 0;
        double speed = 15;
        int threshold = 250;
        if(KeyEventPress.isUpPress) {
            vy -= speed;
        }
        if(KeyEventPress.isDownPress) {
            vy += speed;
        }
        if(KeyEventPress.isLeftPress) {
            vx -= speed;
        }
        if(KeyEventPress.isRightPress && position.x >= threshold) {
            vx += 0;
            is_moving = false;
        }
        if(KeyEventPress.isRightPress && position.x < threshold) {
            vx += speed;
            is_moving = true;
        }
        if(KeyEventPress.isRightPress && Background.is_moving == false) {
            vx += speed;
            is_moving = true;
        }
        velocity.set(vx, vy);
        velocity.setLength(speed);
    }

    private void limitPosition() {
        position.setX(Mathx.clamp(position.x,0, Settings.BACKGROUND_WIDTH-Settings.PLAYER_WIDTH));
        position.setY(Mathx.clamp(position.y,0,Settings.BACKGROUND_HEIGHT - Settings.PLAYER_HEIGHT));
    }
}