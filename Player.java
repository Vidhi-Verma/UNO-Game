import java.util.*;

public class Player {
    private final String name;
    private final List<Card> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void silentDraw(Deck deck, int count) {
        for (int i = 0; i < count; i++) {
            if (!deck.isEmpty()) {
                hand.add(deck.drawCard());
            }
        }
    }

    public void draw(Deck deck, int count) {
        for (int i = 0; i < count; i++) {
            if (!deck.isEmpty()) {
                Card card = deck.drawCard();
                hand.add(card);
                System.out.println(name + " draws: " + card);
            }
        }
    }

    public boolean hasWon() {
        return hand.isEmpty();
    }

    public Card playTurn(Card topCard, Card.Color currentColor, Scanner scanner) {
        System.out.println("\n" + name + "'s turn:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(i + ": " + hand.get(i));
        }

        System.out.println("Top card: " + topCard + " | Current color: " + currentColor);
        System.out.print("Choose card index to play or -1 to draw: ");
        int choice = scanner.nextInt();

        if (choice == -1) return null;
        if (choice < 0 || choice >= hand.size()) return null;

        Card selected = hand.get(choice);
        if (selected.isPlayable(topCard, currentColor)) {
            hand.remove(choice);
            return selected;
        } else {
            System.out.println("Invalid move. Card not playable.");
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public void addCard(Card card) {
        hand.add(card);
    }
}
