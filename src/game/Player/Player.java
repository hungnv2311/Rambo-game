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
    public int player_width;
    public int player_height;
    public boolean immune;
    public static boolean is_moving;
    public String image_path;

    public Player() {
//        image = SpriteUtils.loadImage("assets/images/players/straight/0.png");
        this.image_path = "assets/images/Player/PlayerIdle";
        renderer = new Renderer(image_path);
        position.set(10, 450);
        player_width = Settings.PLAYER_WIDTH;
        player_height = Settings.PLAYER_HEIGHT;
        this.hitBox = new BoxCollider(this, player_width, player_height);
        hp = 300;
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

    int countRender = 0;
    @Override
    public void render(Graphics g) {
        if(immune) {
            countRender++;
            if (countRender % 2 == 0) {
                this.new_renderer();
                super.render(g);
            }

        } else {
            this.new_renderer();
            super.render(g);
        }
    }

    int countImmune = 0;
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

    int frameCount = 0;
    private void fire() {
        frameCount++;
//        image_path = "assets/images/Player/PlayerShooting";
//        renderer = new Renderer(image_path);
        if (KeyEventPress.isFirePress && frameCount > 10) {
            int numberBullets = 1;
            double stepX = 0;
            double startX = position.x;

            for (int i = 0; i < numberBullets; i++) {
                PlayerBullet bullet = GameObject.recycle(PlayerBullet.class);
                bullet.position.set(startX - stepX * i, position.y);
                bullet.velocity.setAngle(0);
            }
            frameCount = 0;
    }
    }

    private void move() {
        double vx = 0;
        double vy = 0;
        double speed = 5;
        int threshold = Settings.THRESHOLD;
        if(KeyEventPress.isUpPress) {
//            vy -= speed;
            this.image_path = "assets/images/Player/PlayerUp";
            this.player_width = Settings.PLAYER_WIDTH;
            this.player_height = Settings.PLAYER_HEIGHT;
        }
        if(KeyEventPress.isDownPress) {
//            vy += speed;
            this.image_path = "assets/images/Player/Player/PlayerCrouch.png";
            this.player_width = 32;
            this.player_height = 24;
        }
        if(KeyEventPress.isLeftPress) {
            vx -= speed;
            this.image_path = "assets/images/Player/PlayerWalking";
            this.player_width = Settings.PLAYER_WIDTH;
            this.player_height = Settings.PLAYER_HEIGHT;
        }
        if(KeyEventPress.isRightPress) {
            this.image_path = "assets/images/Player/PlayerWalking";
            this.player_width = Settings.PLAYER_WIDTH;
            this.player_height = Settings.PLAYER_HEIGHT;
        }
        // Player move => Background move
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

        if(KeyEventPress.isUpPress==false
                && KeyEventPress.isDownPress==false
                && KeyEventPress.isLeftPress==false
                && KeyEventPress.isRightPress==false) {
            this.image_path = "assets/images/Player/PlayerIdle";
            this.player_width = Settings.PLAYER_WIDTH;
            this.player_height = Settings.PLAYER_HEIGHT;
        }

        velocity.set(vx, vy);
        velocity.setLength(speed);
//        this.renderer = new Renderer(this.image_path);
        this.hitBox.set(player_width, player_height);
    }

    private void new_renderer () {
        renderer = new Renderer(this.image_path);
    }

    private void limitPosition() {
        position.setX(Mathx.clamp(position.x,0, Settings.GAME_WIDTH-Settings.PLAYER_WIDTH));
        position.setY(Mathx.clamp(position.y,0,Settings.GAME_HEIGHT - Settings.PLAYER_HEIGHT));
    }
}