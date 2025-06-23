import java.util.*;

public class Deck {
    private final Stack<Card> cards = new Stack<>();

    public Deck() {
        List<Card> newCards = new ArrayList<>();

        for (Card.Color color : Card.Color.values()) {
            if (color == Card.Color.WILD) continue;

            for (Card.Value value : Card.Value.values()) {
                if (value == Card.Value.WILD || value == Card.Value.WILD_DRAW_FOUR) continue;

                newCards.add(new Card(color, value));
                if (value != Card.Value.ZERO) {
                    newCards.add(new Card(color, value)); // two of each, except 0
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            newCards.add(new Card(Card.Color.WILD, Card.Value.WILD));
            newCards.add(new Card(Card.Color.WILD, Card.Value.WILD_DRAW_FOUR));
        }

        Collections.shuffle(newCards);
        cards.addAll(newCards);
    }

    public Card drawCard() {
        return cards.pop();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
