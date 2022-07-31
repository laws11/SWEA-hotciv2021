package hotciv.standard;

import hotciv.framework.Die;

import java.util.Random;

public class randomDie implements Die {
    private Random randomRoll = new Random();

    @Override
    public int rollDie() {
        return randomRoll.nextInt(6) + 1;
    }
}
