package machine.coffee;

import machine.Coffee;

public class Latte extends Coffee {
    private static final int LATTE_WATER = 350;
    private static final int LATTE_MILK = 75;
    private static final int LATTE_COFFEE_BEANS = 20;
    private static final int LATTE_PRICE = 7;

    public Latte() {
        super(new int[] { LATTE_WATER, LATTE_MILK, LATTE_COFFEE_BEANS }, LATTE_PRICE);
    }
}
