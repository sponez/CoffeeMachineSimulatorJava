package machine;

import coffee.Coffee;
import coffee.CoffeeIngredientType;
import coffee.CoffeeType;

public class CoffeeMachine {
    public static class Supply {
        private final CoffeeIngredientType type;
        private int amount;

        Supply(CoffeeIngredientType type, int amount) {
            this.type = type;
            this.amount = amount;
        }

        public CoffeeIngredientType getType() {
            return this.type;
        }

        public int getAmount() {
            return this.amount;
        }

        public void add(int amount) {
            this.amount += amount;
        }

        public void reduce(int amount) {
            this.amount -= amount;
        }
    }

    public enum Product {
        ESPRESSO(CoffeeType.ESPRESSO.instance(), 4),
        LATTE(CoffeeType.LATTE.instance(), 7),
        CAPPUCCINO(CoffeeType.CAPPUCCINO.instance(), 6);

        private final Coffee coffee;
        private final int price;

        Product(Coffee coffee, int price) {
            this.coffee = coffee;
            this.price = price;
        }

        public Coffee getCoffee() {
            return this.coffee;
        }

        public int getPrice() {
            return this.price;
        }
    }

    private final CoffeeMachineController controller;
    private final Supply[] supplyStorage;
    private int cupsRemain;
    private int moneyCollected;
    private int coffeeCupsDone;

    public CoffeeMachine() {
        controller = new CoffeeMachineController(this);
        supplyStorage = new Supply[] {
            new Supply(CoffeeIngredientType.WATER, 400),
            new Supply(CoffeeIngredientType.MILK, 540),
            new Supply(CoffeeIngredientType.COFFEE_BEANS, 120)
        };
        cupsRemain = 9;
        moneyCollected = 550;
        coffeeCupsDone = 0;
    }

    public Supply[] getSupplyStorage() {
        return this.supplyStorage;
    }

    public int getCupsRemain() {
        return this.cupsRemain;
    }

    public int getMoneyCollected() {
        return this.moneyCollected;
    }

    public int getCoffeeCupsDone() {
        return this.coffeeCupsDone;
    }

    public void start() {
        controller.menu();
    }

    public void sellProduct(Product product) {
        for (Supply supply: supplyStorage) {
            int ingredientAmount = product.getCoffee().getIngredientAmount(supply.getType());
            supply.reduce(ingredientAmount);
        }

        --cupsRemain;
        ++coffeeCupsDone;

        moneyCollected += product.getPrice();
    }

    public void addSupplies(Supply[] supplies, int newCups) {
        for (Supply supply: supplies) {
            switch (supply.getType()) {
                case WATER -> this.supplyStorage[0].add(supply.getAmount());
                case MILK -> this.supplyStorage[1].add(supply.getAmount());
                case COFFEE_BEANS -> this.supplyStorage[2].add(supply.getAmount());
            }
        }

        this.cupsRemain += newCups;
    }

    public void extractMoney() {
        this.moneyCollected = 0;
    }

    public void clean() {
        this.coffeeCupsDone = 0;
    }
}
