package game.Enemy;

import game.GameObject;

public class EnemySummoner extends GameObject {
    public EnemySummoner() {}


    int framCount = 0;
    @Override
    public void run() {
        super.run();
        this.summonEnemy();
    }

    private void summonEnemy() {
        framCount++;
        if (framCount > 120) {
//            new Enemy();
            GameObject.recycle(Enemy.class);
            framCount = 0;
        }
    }
}
