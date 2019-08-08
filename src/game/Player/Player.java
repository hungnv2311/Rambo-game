package game.Player;

import game.*;
import game.Physic.BoxCollider;
import game.Physic.NewBoxCollider;
import game.Renderer.Renderer;
import tklibs.Mathx;

import java.awt.*;

public class Player extends GameObject {
    public int hp;
    public int player_width;
    public int player_height;
    public boolean immune;
    public static boolean is_moving;
    public String image_path;
    public final float GRAVITY = 0.5f;
    public int JUMPSPEED = 10;

    public Player() {
        this.image_path = "assets/images/Player/PlayerIdle";
        renderer = new Renderer(image_path);
        position.set(10, 0);
        velocity.set(0,0);
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
        // Gravity of player
        velocity.y += GRAVITY;
        // Platform
        moveVertical();
        // Jump
        this.jump();

        super.run();
        // move
        this.move();
        // limit
        this.limitPosition();
        // fire
        this.fire();

        this.checkImmune();
    }

    private void jump() {
        if (KeyEventPress.isJumpPress) {
            if (GameObject.findIntersects(Background2.class, this.hitBox.shift(this, 0, 0.1)) != null) {
                velocity.y = -JUMPSPEED;
            }
        }
    }

    private void moveVertical() {
        Vector2D newposition = this.position.clone();
        NewBoxCollider ThisBoxCollider = new NewBoxCollider(this.position.clone(), this.anchor, player_width, player_height);
        newposition.add(0, velocity.y);
        NewBoxCollider nextBoxCollider = new NewBoxCollider(newposition, this.anchor, player_width, player_height);

        Background2 background2 = GameObject.newfindIntersects(Background2.class, nextBoxCollider);
        if(background2 != null) {
            boolean moveContinue = true;
            double shiftDistance = 0.5;

            // Giusp player rơi mượt hơn
            while (moveContinue){
                if (GameObject.newfindIntersects(Background2.class, ThisBoxCollider.shift(0, shiftDistance)) != null) {
                    moveContinue = false;
                } else {
                    shiftDistance += 0.5;
                    this.position.add(0, 0.5);
                }
            }
            // Update velocity if needed
            velocity.y = 0;
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
                for (int i = 0; i < numberBullets; i++) {
                    PlayerBulletUp bulletUp = GameObject.recycle(PlayerBulletUp.class);
                    bulletUp.position.set(startX - stepX * i, position.y);
                    bulletUp.velocity.setAngle(angle);
                }
            } else {
                angle = Math.toRadians(0);
                for (int i = 0; i < numberBullets; i++) {
                    PlayerBullet bullet = GameObject.recycle(PlayerBullet.class);
                    bullet.position.set(startX - stepX * i, position.y);
                    bullet.velocity.setAngle(angle);
                }
            }
            frameCount = 0;
        }
    }

    private void move() {
        double vx = 0;
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

        velocity.x = vx;
//        velocity.setLength(speed);
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