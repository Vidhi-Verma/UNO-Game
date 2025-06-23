public class Card {
    public enum Color { RED, GREEN, BLUE, YELLOW, WILD }
    public enum Value {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,
        SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR
    }

    private final Color color;
    private final Value value;

    public Card(Color color, Value value) {
        this.color = color;
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public Value getValue() {
        return value;
    }

    public boolean isPlayable(Card topCard, Color currentColor) {
        return this.color == currentColor || this.value == topCard.value || this.color == Color.WILD;
    }

    @Override
    public String toString() {
        String valStr = switch (value) {
            case ZERO -> "0"; case ONE -> "1"; case TWO -> "2"; case THREE -> "3";
            case FOUR -> "4"; case FIVE -> "5"; case SIX -> "6"; case SEVEN -> "7";
            case EIGHT -> "8"; case NINE -> "9";
            case SKIP -> "SKIP"; case REVERSE -> "REVERSE"; case DRAW_TWO -> "DRAW 2";
            case WILD -> "WILD"; case WILD_DRAW_FOUR -> "WILD DRAW 4";
        };
        return color + " " + valStr;
    }
}
