package game;

import game.Physic.BoxCollider;
import game.Physic.NewBoxCollider;
import game.Player.Player;
import game.Renderer.Renderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameObject {
    // quan ly doi tuong
    public static ArrayList<GameObject> gameObjects = new ArrayList<>();

    public  static void runAll() {
        System.out.println(gameObjects.size());
        for (int i =0; i< gameObjects.size(); i++) {
            GameObject object = gameObjects.get(i);
            if(object.active) {
                object.run();
            }
        }
    }

    public static void renderAll(Graphics g) {
        for (int i =0; i< gameObjects.size(); i++) {
            GameObject object = gameObjects.get(i);
            if (object.active) {
                object.render(g);
            }
        }
    }

    public static void clearAll() {
        gameObjects.clear();
    }

    public static <E> E findIntersects(Class<E> cls, BoxCollider hitBox) {
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject object = gameObjects.get(i);
            if(object.active
                && cls.isAssignableFrom(object.getClass())
                && object.hitBox != null
                && object.hitBox.intersects(hitBox)) {
                return (E) object;
            }
        }
        return null;
    }

    public static <E> E newfindIntersects(Class<E> cls, NewBoxCollider nextBoxCollider) {
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject object = gameObjects.get(i);
            if(object.active
                    && cls.isAssignableFrom(object.getClass())
                    && object.hitBox != null
                    && nextBoxCollider.intersects(object.hitBox)) {
                return (E) object;
            }
        }
        return null;
    }

    // recycle: su dung lai game object
    // E ~ Player, Enemy, ...
    public static <E extends GameObject> E recycle(Class <E> cls) {
        // 1, tim 1 gameObject dang k active, neu co => reset => it return
        // 2, neu k co => tao moi
        E gameObject = findInactive(cls);
        if(gameObject != null) {
            gameObject.reset();
            return (E) gameObject;
        }
        try {
            gameObject = cls.getConstructor().newInstance();
            return (E) gameObject;
        } catch (Exception ex) {
            return null;
        }
    }

    private static <E extends GameObject> E findInactive(Class<E> cls) {
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject object = gameObjects.get(i);
            if(!object.active
                && cls.isAssignableFrom(object.getClass())){
                return (E) object;
            }
        }
        return null;
    }

    public Renderer renderer;
    public Vector2D position;
    public Vector2D velocity;
    public boolean active;
    public BoxCollider hitBox;
    public Vector2D anchor;

    public GameObject() {
        gameObjects.add(this);
        System.out.println(gameObjects.size());
        position = new Vector2D();
        velocity = new Vector2D();
        active = true;
        anchor = new Vector2D(0.5, 0.5);
    }

    public void render(Graphics g) {
        if (renderer != null) {
            renderer.render(g, this);
        }
    }

    public void run() {
        position.add(velocity.x, velocity.y);
    }
    public void deactive() {
        active = false;
    }
    public void reset() {
        active = true;

    }

//    public static void main(String[] args) {
//        Player player = find(Player.class);
//
//        System.out.println(player);
//    }
//
//    public static <E> E find(Class<E> cls) {
//        try {
//            return cls.getConstructor().newInstance();
//        } catch (Exception ex) {
//            return null;
//        }
//    }
//
}
