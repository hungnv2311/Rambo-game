package game.EnemyAirShip;

import game.Enemy.Enemy;
import game.GameObject;

public class EnemyAirShipSummoner extends GameObject {
    public EnemyAirShipSummoner() {}


    int framCount = 0;
    @Override
    public void run() {
        super.run();
        this.summonEnemy();
    }

    int numberEnemy = 0;
    private void summonEnemy() {
        framCount++;
        if (framCount > 120) {
            if (numberEnemy <= 5){
                numberEnemy += 1;
                GameObject.recycle(EnemyAirShip.class);
                framCount = 0;
            }
        }
    }
}
