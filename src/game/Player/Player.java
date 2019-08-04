package game.Player;

import game.*;
import game.Physic.BoxCollider;
import game.Renderer.Renderer;
import tklibs.Mathx;
import tklibs.SpriteUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.Key;
import java.util.ArrayList;
import java.util.Set;

public class Player extends GameObject {
    public int hp;
    public int player_width;
    public int player_height;
    public boolean immune;
    public static boolean is_moving;
    public String image_path;
    public float gravity = 0.5f;
    public boolean is_rotate = false;

    double vx = 0;
    double vy = 0;
    double gy = 0;
    double speed = 0;
    public Player() {
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
//        this.jump();

//        if(KeyEventPress.isJumpPress) {
//            gravity = 2;
////            speed = 2;
//        }
//        this.jump();

        this.checkImmune();
    }

    long PreviousTime = System.currentTimeMillis();
    private void jump() {

        vx = 0;
        vy = 0;
//        vx += speed;
        vy+= gravity;
        long CurrentTime = System.currentTimeMillis();
        double dt = (CurrentTime - PreviousTime)/1000.;
        System.out.println(dt);
        System.out.println(CurrentTime - PreviousTime);
        vy = vy + gy * dt;
        velocity.set(vx, vy);
//        velocity.setLength(speed);
        if(dt >= 2) {
            PreviousTime = System.currentTimeMillis();
        }
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
        if (KeyEventPress.isFirePress && frameCount > 10) {
            double angle = 0;
            int numberBullets = 1;
            double stepX = 0;
            double startX = position.x;

            // Rotate player bullet if fire up
            if(KeyEventPress.isUpPress) {
                angle = Math.toRadians(-90);
                is_rotate = true;
            } else {
                angle = Math.toRadians(0);
                is_rotate = false;
            }
            for (int i = 0; i < numberBullets; i++) {
                PlayerBullet bullet = GameObject.recycle(PlayerBullet.class);
                bullet.position.set(startX - stepX * i, position.y);
                bullet.velocity.setAngle(angle);
                bullet.rotate(is_rotate);
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
            this.player_width = Settings.PLAYER_WIDTH;
            this.player_height = Settings.PLAYER_HEIGHT;
        }
        if(KeyEventPress.isDownPress) {
            this.player_width = 32;
            this.player_height = 24;
        }
        if(KeyEventPress.isLeftPress) {
            vx -= speed;
            this.player_width = Settings.PLAYER_WIDTH;
            this.player_height = Settings.PLAYER_HEIGHT;
        }
        if(KeyEventPress.isRightPress) {
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
            this.player_width = Settings.PLAYER_WIDTH;
            this.player_height = Settings.PLAYER_HEIGHT;
        }

        velocity.set(vx, vy);
        velocity.setLength(speed);
        this.hitBox.set(player_width, player_height);
    }

    private void new_renderer () {
        if(KeyEventPress.isUpPress==false
                && KeyEventPress.isDownPress==false
                && KeyEventPress.isLeftPress==false
                && KeyEventPress.isRightPress==false) {
            this.image_path = "assets/images/Player/PlayerIdle";
        }
        if(KeyEventPress.isUpPress) {
            if(KeyEventPress.isFirePress) {
                this.image_path = "assets/images/Player/PlayerStanding";
            } else {
                this.image_path = "assets/images/Player/PlayerUp";
            }
        }
        if(KeyEventPress.isDownPress) {
            if(KeyEventPress.isFirePress) {
                this.image_path = "assets/images/Player/PlayerCrouchShoot";
            } else {
                this.image_path = "assets/images/Player/Player/PlayerCrouch.png";
            }
        }
        if(KeyEventPress.isLeftPress) {
            if (KeyEventPress.isFirePress) {
                this.image_path = "assets/images/Player/PlayerShooting";
            } else {
                this.image_path = "assets/images/Player/PlayerWalking";
            }
        }
        if(KeyEventPress.isRightPress) {
            if(KeyEventPress.isFirePress) {
                this.image_path = "assets/images/Player/PlayerShooting";
            } else {
                this.image_path = "assets/images/Player/PlayerWalking";
            }
        }
        if(KeyEventPress.isFirePress) {
            this.image_path = "assets/images/Player/PlayerShooting";
        }
        renderer = new Renderer(this.image_path);
    }

    private void limitPosition() {
        position.setX(Mathx.clamp(position.x,0, Settings.GAME_WIDTH - Settings.PLAYER_WIDTH));
        position.setY(Mathx.clamp(position.y,0,Settings.GAME_HEIGHT - Settings.PLAYER_HEIGHT));
    }
}