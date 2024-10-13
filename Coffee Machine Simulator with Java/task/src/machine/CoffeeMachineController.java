package machine;

import coffee.CoffeeIngredientType;

import java.util.Scanner;

public class CoffeeMachineController {
    private static class Message {
        public final static String MENU = "Write action (buy, fill, take, clean, remaining, exit): ";

        public final static String CHOOSE_COFFEE_QUESTION =
            "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ";

        public final static String SUCCESS_SELL = "I have enough resources, making you a coffee!\n";

        public final static String NOT_ENOUGH_WATER = "Sorry, not enough water!\n";
        public final static String NOT_ENOUGH_MILK = "Sorry, not enough milk!\n";
        public final static String NOT_ENOUGH_COFFEE_BEANS = "Sorry, not enough coffee beans!\n";
        public final static String NOT_ENOUGH_CUPS = "Sorry, not enough disposable cups!\n";

        public final static String CHOOSE_NEW_WATER_AMOUNT = "Write how many ml of water you want to add: ";
        public final static String CHOOSE_NEW_MILK_AMOUNT = "Write how many ml of milk you want to add: ";
        public final static String CHOOSE_NEW_COFFEE_BEANS_AMOUNT = "Write how many grams of coffee beans you want to add: ";
        public final static String CHOOSE_NEW_CUPS_AMOUNT = "Write how many disposable cups you want to add: ";

        public final static String TAKE_MESSAGE = "I gave you $%d\n";

        public final static String NEED_CLEANING = "I need cleaning!\n";
        public final static String SUCCESS_CLEANING = "I have been cleaned!\n";

        public final static String CURRENT_STATE = """
            The coffee machine has:
            %d ml of water
            %d ml of milk
            %d g of coffee beans
            %d disposable cups
            $%d of money
            """;
    }

    private enum Action {
        BUY,
        FILL,
        TAKE,
        CLEAN,
        REMAINING,
        EXIT
    }

    private final static Scanner inputMaster = new Scanner(System.in);
    private final CoffeeMachine coffeeMachine;

    public CoffeeMachineController(CoffeeMachine coffeeMachine) {
        this.coffeeMachine = coffeeMachine;
    }

    private static CoffeeMachine.Product getCoffeeMachineProductByQuestion() {
        for (;;) {
            String coffeeNumberString;

            System.out.print(Message.CHOOSE_COFFEE_QUESTION);
            coffeeNumberString = inputMaster.nextLine();

            switch (coffeeNumberString) {
                case "1":
                    return CoffeeMachine.Product.ESPRESSO;
                case "2":
                    return CoffeeMachine.Product.LATTE;
                case "3":
                    return CoffeeMachine.Product.CAPPUCCINO;
                case "back":
                    return null;
                default:
                    System.out.println("ERROR");
                    break;
            }
        }
    }

    private static int getNextIntegerByQuestion(String question) {
        for (;;) {
            String integerString;
            int integer;

            System.out.print(question);
            integerString = inputMaster.nextLine();

            try {
                integer = Integer.parseInt(integerString);
            } catch (NumberFormatException numberFormatException) {
                System.out.println("ERROR");
                continue;
            }

            if (integer < 0) {
                System.out.println("ERROR");
                continue;
            }

            return integer;
        }
    }

    private void buy(CoffeeMachine.Product product) {
        if (product == null) {
            return;
        }

        for (CoffeeMachine.Supply supply: this.coffeeMachine.getSupplyStorage()) {
            int productIngredientAmount = product.getCoffee().getIngredientAmount(supply.getType());
            if (supply.getAmount() < productIngredientAmount) {
                System.out.print(
                    switch (supply.getType()) {
                        case WATER -> Message.NOT_ENOUGH_WATER;
                        case MILK -> Message.NOT_ENOUGH_MILK;
                        case COFFEE_BEANS -> Message.NOT_ENOUGH_COFFEE_BEANS;
                    }
                );
                return;
            }
        }

        if (this.coffeeMachine.getCupsRemain() == 0) {
            System.out.print(Message.NOT_ENOUGH_CUPS);
            return;
        }

        System.out.print(Message.SUCCESS_SELL);
        this.coffeeMachine.sellProduct(product);
    }

    private void fill() {
        CoffeeMachine.Supply[] newSupplies = new CoffeeMachine.Supply[3];
        int newCups;

        for (CoffeeMachine.Supply supply: this.coffeeMachine.getSupplyStorage()) {
            int amount;

            switch (supply.getType()) {
                case WATER -> {
                    amount = getNextIntegerByQuestion(Message.CHOOSE_NEW_WATER_AMOUNT);
                    newSupplies[0] = new CoffeeMachine.Supply(CoffeeIngredientType.WATER, amount);
                }
                case MILK -> {
                    amount = getNextIntegerByQuestion(Message.CHOOSE_NEW_MILK_AMOUNT);
                    newSupplies[1] = new CoffeeMachine.Supply(CoffeeIngredientType.MILK, amount);
                }
                case COFFEE_BEANS -> {
                    amount = getNextIntegerByQuestion(Message.CHOOSE_NEW_COFFEE_BEANS_AMOUNT);
                    newSupplies[2] = new CoffeeMachine.Supply(CoffeeIngredientType.COFFEE_BEANS, amount);
                }
            }
        }

        newCups = getNextIntegerByQuestion(Message.CHOOSE_NEW_CUPS_AMOUNT);

        this.coffeeMachine.addSupplies(newSupplies, newCups);
    }

    private void remaining() {
        CoffeeMachine.Supply[] machineIngredientStorage = this.coffeeMachine.getSupplyStorage();
        CoffeeMachine.Supply water = machineIngredientStorage[0];
        CoffeeMachine.Supply milk = machineIngredientStorage[1];
        CoffeeMachine.Supply coffeeBeans = machineIngredientStorage[2];

        System.out.printf(
            Message.CURRENT_STATE,
            water.getAmount(),
            milk.getAmount(),
            coffeeBeans.getAmount(),
            this.coffeeMachine.getCupsRemain(),
            this.coffeeMachine.getMoneyCollected()
        );
    }


    public void menu() {
        for (;;) {
            String actionString;
            Action action;

            System.out.print(Message.MENU);
            actionString = inputMaster.nextLine();

            try {
                action = Action.valueOf(actionString.toUpperCase());
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println("ERROR");
                continue;
            }

            switch (action) {
                case BUY -> {
                    if (this.coffeeMachine.getCoffeeCupsDone() < 10) {
                        CoffeeMachine.Product chosenProduct = getCoffeeMachineProductByQuestion();
                        buy(chosenProduct);
                    } else {
                        System.out.print(Message.NEED_CLEANING);
                    }
                }
                case FILL -> fill();
                case TAKE -> {
                    int moneyCollected = this.coffeeMachine.getMoneyCollected();

                    this.coffeeMachine.extractMoney();
                    System.out.printf(Message.TAKE_MESSAGE, moneyCollected);
                }
                case CLEAN -> {
                    this.coffeeMachine.clean();
                    System.out.print(Message.SUCCESS_CLEANING);
                }
                case REMAINING -> remaining();
                case EXIT -> {
                    return;
                }
            }
        }
    }
}
