package machine.coffee;

import machine.Coffee;

public class Cappuccino extends Coffee {
    private static final int CAPPUCCINO_WATER = 200;
    private static final int CAPPUCCINO_MILK = 100;
    private static final int CAPPUCCINO_COFFEE_BEANS = 12;
    private static final int CAPPUCCINO_PRICE = 6;

    public Cappuccino() {
        super(new int[] { CAPPUCCINO_WATER, CAPPUCCINO_MILK, CAPPUCCINO_COFFEE_BEANS }, CAPPUCCINO_PRICE);
    }
}
