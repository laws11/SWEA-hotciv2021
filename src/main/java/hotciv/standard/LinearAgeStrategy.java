package hotciv.standard;

import hotciv.framework.AgeStrategy;

public class LinearAgeStrategy implements AgeStrategy {

    @Override
    public int increaseAge(int currentAge) {
        return currentAge +=100;
    }
}
