package machine;

public class Coffee {
    private final int[] ingredients = new int[CoffeeIngredient.values().length];
    private final int price;

    public Coffee(int[] ingredients, int price) {
        System.arraycopy(ingredients, 0, this.ingredients, 0, ingredients.length);
        this.ingredients[3] = 1; //One cup
        this.price = price;
    }

    public int[] getIngredients() {
        return this.ingredients;
    }

    public int getPrice() {
        return this.price;
    }
}
