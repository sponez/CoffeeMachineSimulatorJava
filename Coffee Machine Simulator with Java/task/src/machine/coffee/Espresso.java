package machine.coffee;

import machine.Coffee;

public class Espresso extends Coffee {
    private static final int ESPRESSO_WATER = 250;
    private static final int ESPRESSO_MILK = 0;
    private static final int ESPRESSO_COFFEE_BEANS = 16;
    private static final int ESPRESSO_PRICE = 4;

    public Espresso() {
        super(new int[] { ESPRESSO_WATER, ESPRESSO_MILK, ESPRESSO_COFFEE_BEANS }, ESPRESSO_PRICE);
    }
}
