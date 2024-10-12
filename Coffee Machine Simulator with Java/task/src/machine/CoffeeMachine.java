package machine;

import machine.coffee.Cappuccino;
import machine.coffee.Espresso;
import machine.coffee.Latte;

import java.util.Scanner;

public class CoffeeMachine {
    private static class Message {
        public final static String MENU = "Write action (buy, fill, take, clean, remaining, exit): ";

        public final static String CHOOSE_COFFEE_QUESTION =
            "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ";

        public final static String[] NOT_ENOUGH_MESSAGES = {
            "Sorry, not enough water!\n",
            "Sorry, not enough milk!\n",
            "Sorry, not enough coffee beans!\n",
            "Sorry, not enough disposable cups!\n"
        };

        public final static String SUCCESS_COFFEE = "I have enough resources, making you a coffee!\n";

        public final static String[] FILL_QUESTIONS = {
            "Write how many ml of water you want to add: ",
            "Write how many ml of milk you want to add: ",
            "Write how many grams of coffee beans you want to add: ",
            "Write how many disposable cups you want to add: "
        };

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

    private enum CoffeeType {
        ESPRESSO,
        LATTE,
        CAPPUCCINO
    }

    private final static Scanner SCANNER = new Scanner(System.in);
    private static final int[] SUPPLIES = new int[CoffeeIngredient.values().length];

    private static int currentMoney;
    private static int cupsCoffeeMade;

    private static int getNextIntegerByQuestion(String question) {
        for (;;) {
            String integerString;
            int integer;

            System.out.print(question);
            integerString = SCANNER.nextLine();

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

    private static CoffeeType getCoffeeTypeByQuestion() {
        for (;;) {
            String coffeeNumberString;

            System.out.print(Message.CHOOSE_COFFEE_QUESTION);
            coffeeNumberString = SCANNER.nextLine();

            switch (coffeeNumberString) {
                case "1":
                    return CoffeeType.ESPRESSO;
                case "2":
                    return CoffeeType.LATTE;
                case "3":
                    return CoffeeType.CAPPUCCINO;
                case "back":
                    return null;
                default:
                    System.out.println("ERROR");
                    break;
            };
        }
    }

    private static boolean checkPossibility(Coffee coffee) {
        for (CoffeeIngredient supply: CoffeeIngredient.values()) {
            int supplyNumber = supply.ordinal();

            if (SUPPLIES[supplyNumber] < coffee.getIngredients()[supplyNumber]) {
                System.out.print(Message.NOT_ENOUGH_MESSAGES[supplyNumber]);
                return false;
            }
        }

        System.out.print(Message.SUCCESS_COFFEE);
        return true;
    }

    private static void buy() {
        if (cupsCoffeeMade >= 10) {
            System.out.println(Message.NEED_CLEANING);
            return;
        }

        CoffeeType coffeeType = getCoffeeTypeByQuestion();

        if (coffeeType == null) {
            return;
        }

        Coffee coffee = switch (coffeeType) {
            case ESPRESSO -> new Espresso();
            case LATTE -> new Latte();
            case CAPPUCCINO -> new Cappuccino();
        };

        if (checkPossibility(coffee)) {
            for (CoffeeIngredient supply: CoffeeIngredient.values()) {
                int supplyNumber = supply.ordinal();
                SUPPLIES[supplyNumber] -= coffee.getIngredients()[supplyNumber];
            }

            currentMoney += coffee.getPrice();
            ++cupsCoffeeMade;
        }
    }

    private static void fill() {
        for (CoffeeIngredient supply: CoffeeIngredient.values()) {
            int supplyNumber = supply.ordinal();
            SUPPLIES[supplyNumber] += getNextIntegerByQuestion(Message.FILL_QUESTIONS[supplyNumber]);
        }
    }

    private static void take() {
        System.out.printf(Message.TAKE_MESSAGE, currentMoney);
        currentMoney = 0;
    }

    private static void clean() {
        System.out.print(Message.SUCCESS_CLEANING);
        cupsCoffeeMade = 0;
    }

    private static void remaining() {
        System.out.printf(
            Message.CURRENT_STATE,
            SUPPLIES[CoffeeIngredient.WATER.ordinal()],
            SUPPLIES[CoffeeIngredient.MILK.ordinal()],
            SUPPLIES[CoffeeIngredient.COFFEE_BEANS.ordinal()],
            SUPPLIES[CoffeeIngredient.CUPS.ordinal()],
            currentMoney
        );
    }

    private static void menu() {
        for (;;) {
            Action action;

            System.out.print(Message.MENU);
            try {
                action = Action.valueOf(SCANNER.nextLine().toUpperCase());
            } catch (IllegalArgumentException illegalArgumentException) {
                System.out.println("ERROR");
                continue;
            }

            switch (action) {
                case BUY -> buy();
                case FILL -> fill();
                case TAKE -> take();
                case CLEAN -> clean();
                case REMAINING -> remaining();
                case EXIT -> {
                    return;
                }
            }
        }
    }

    private static void initCoffeeMachine() {
        SUPPLIES[0] = 400;
        SUPPLIES[1] = 540;
        SUPPLIES[2] = 120;
        SUPPLIES[3] = 9;
        currentMoney = 550;
        cupsCoffeeMade = 0;
    }

    public static void main(String[] args) {
        initCoffeeMachine();
        menu();

        SCANNER.close();
    }
}