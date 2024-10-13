package coffee;

public class Coffee {
    public record Ingredient(CoffeeIngredientType type, int amount) {}
    private final Coffee.Ingredient[] ingredients;

    Coffee(Coffee.Ingredient... ingredients) {
        this.ingredients = ingredients;
    }

    public int getIngredientAmount(CoffeeIngredientType ingredientType) {
        for (Ingredient currentIngredient: this.ingredients) {
            if (currentIngredient.type().equals(ingredientType)) {
                return currentIngredient.amount;
            }
        }

        return 0;
    }
}
