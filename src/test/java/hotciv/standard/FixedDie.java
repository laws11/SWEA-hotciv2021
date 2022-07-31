package hotciv.standard;

import hotciv.framework.Die;

public class FixedDie implements Die {
    private int fixedValue;

    public FixedDie(int fixedValue) {
        this.fixedValue = fixedValue;
    }

    public void setFixedValue(int newFixedValue) {
        this.fixedValue = newFixedValue;
    }

    @Override
    public int rollDie() {
        return fixedValue;
    }
}
