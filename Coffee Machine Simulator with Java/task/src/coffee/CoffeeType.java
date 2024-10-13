package coffee;

public enum CoffeeType {
    ESPRESSO(
        new Coffee.Ingredient(CoffeeIngredientType.WATER, 250),
        new Coffee.Ingredient(CoffeeIngredientType.MILK, 0),
        new Coffee.Ingredient(CoffeeIngredientType.COFFEE_BEANS, 16)
    ),
    LATTE(
        new Coffee.Ingredient(CoffeeIngredientType.WATER, 350),
        new Coffee.Ingredient(CoffeeIngredientType.MILK, 75),
        new Coffee.Ingredient(CoffeeIngredientType.COFFEE_BEANS, 20)
    ),
    CAPPUCCINO(
        new Coffee.Ingredient(CoffeeIngredientType.WATER, 200),
        new Coffee.Ingredient(CoffeeIngredientType.MILK, 100),
        new Coffee.Ingredient(CoffeeIngredientType.COFFEE_BEANS, 12)
    );

    private final Coffee.Ingredient[] ingredients;

    CoffeeType(Coffee.Ingredient... ingredients) {
        this.ingredients = ingredients;
    }

    public Coffee instance() {
        return new Coffee(ingredients);
    }
}
