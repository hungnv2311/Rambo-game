package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by hungnv2311 on 27/7/2019.
 */
public class Program {
    public static void main(String[] args) {
//        ArrayList<game.Vector2D> listVectors = new ArrayList<>();
//        // .add() ; .size() ; .remove() ; .get()
//        listVectors.add(new game.Vector2D(1, 5));
//        listVectors.add(new game.Vector2D(4, 5));
//        listVectors.add(new game.Vector2D(10, 7));
//        listVectors.add(new game.Vector2D(7, 9));
//
//        double maxSum = 0;
//        int maxIndex = -1;
//        for(int i =0; i < listVectors.size(); i++) {
//            game.Vector2D vector = listVectors.get(i);
//            double sum = (vector.x + vector.y);
//            System.out.println(vector.x + " " + vector.y);
//        }

//        System.out.println(System.currentTimeMillis());    // moc thoi gian tu 0h00 1970 -> present tinh bang ms

        JFrame window = new JFrame();
        window.setTitle("Game Touhou");
//        window.setSize(800, 638);
        window.setLocation(400, 100);
//        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);     // Stop process when exit window

        GamePanel panel = new GamePanel();
        panel.setPreferredSize(new Dimension(Settings.GAME_WIDTH,Settings.GAME_HEIGHT));
        window.add(panel);
        window.pack();       // .pack: Window tu mo rong de vua khit voi panel size 800, 600

        // BĂT SỰ KIỆN PHÍM
        KeyAdapter keyHandler = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        KeyEventPress.isUpPress = true;
                        break;
                    case KeyEvent.VK_A:
                        KeyEventPress.isLeftPress = true;
                        break;
                    case KeyEvent.VK_S:
                        KeyEventPress.isDownPress = true;
                        break;
                    case KeyEvent.VK_D:
                        KeyEventPress.isRightPress = true;
                        break;
                    case KeyEvent.VK_SPACE:
                        KeyEventPress.isFirePress = true;
                        break;
                }
            }

            @Override                                   // KeyCode: Do đó cần dùng KeyCode để bắt tất cả các phím
            public void keyReleased(KeyEvent e) {       // KeyChar(): không bắt được các phím crtl, shift, ...
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        KeyEventPress.isUpPress = false;
                        break;
                    case KeyEvent.VK_A:
                        KeyEventPress.isLeftPress = false;
                        break;
                    case KeyEvent.VK_S:
                        KeyEventPress.isDownPress = false;
                        break;
                    case KeyEvent.VK_D:
                        KeyEventPress.isRightPress = false;
                        break;
                    case KeyEvent.VK_SPACE:
                        KeyEventPress.isFirePress = false;
                        break;
                }
            }
        }
            ;
            // add keyHandler vao window
        window.addKeyListener(keyHandler);

            // KEY EVENT PRESS

        window.setVisible(true);

            // start game
        panel.gameLoop();
    /* alt + enter: sửa code
    Shift + f6: đổi tên
    ctrl + alt + l: format code
     */
        // JFrame; class cho cua so
        // JPanel; khung trong cua so
        // BufferdImage; anh
    }
}
