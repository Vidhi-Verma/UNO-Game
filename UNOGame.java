import java.util.*;

public class UNOGame {
    private final List<Player> players = new ArrayList<>();
    private final Deck deck = new Deck();
    private Card topCard;
    private Card.Color currentColor;
    private int currentPlayer = 0;
    private boolean direction = true;
    private final Scanner scanner = new Scanner(System.in);
    private boolean extraTurn = false;

    public void start() {
        System.out.print("Enter number of players: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter player " + (i + 1) + " name: ");
            players.add(new Player(scanner.nextLine()));
        }

        for (Player player : players) {
            player.silentDraw(deck, 7);  // No print
        }

        do {
            topCard = deck.drawCard();
        } while (topCard.getColor() == Card.Color.WILD);

        currentColor = topCard.getColor();

        while (true) {
            Player player = players.get(currentPlayer);
            Card played = player.playTurn(topCard, currentColor, scanner);

            if (played == null) {
                Card drawn = deck.drawCard();
                System.out.println(player.getName() + " drew: " + drawn);

                if (drawn.isPlayable(topCard, currentColor)) {
                    scanner.nextLine(); // Clear buffer
                    System.out.print("Do you want to play the drawn card? (yes/no): ");
                    String response = scanner.nextLine();
                    if (response.equalsIgnoreCase("yes")) {
                        played = drawn;
                    } else {
                        player.addCard(drawn);
                        nextPlayer();
                        continue;
                    }
                } else {
                    System.out.println("Card not playable. Turn skipped.");
                    player.addCard(drawn);
                    nextPlayer();
                    continue;
                }
            }

            topCard = played;
            currentColor = (played.getColor() == Card.Color.WILD) ? chooseColor() : played.getColor();

            handleActionCard(played);

            if (player.hasWon()) {
                System.out.println("\n🎉 " + player.getName() + " wins the game!");
                break;
            }

            if (!extraTurn) {
                nextPlayer();
            }
        }
    }

    private void handleActionCard(Card card) {
        extraTurn = false;

        switch (card.getValue()) {
            case REVERSE -> {
                direction = !direction;
                if (players.size() == 2) {
                    nextPlayer();
                    extraTurn = true;
                }
            }
            case SKIP -> {
                nextPlayer();
                System.out.println(players.get(currentPlayer).getName() + " is skipped!");
                nextPlayer();
                extraTurn = true;
            }
            case DRAW_TWO -> {
                nextPlayer();
                Player next = players.get(currentPlayer);
                next.draw(deck, 2);
                System.out.println(next.getName() + " draws 2 cards and is skipped!");
                nextPlayer();
                extraTurn = true;
            }
            case WILD_DRAW_FOUR -> {
                nextPlayer();
                Player next = players.get(currentPlayer);
                next.draw(deck, 4);
                System.out.println(next.getName() + " draws 4 cards and is skipped!");
                nextPlayer();
                extraTurn = true;
            }
        }
    }

    private void nextPlayer() {
        currentPlayer = direction
                ? (currentPlayer + 1) % players.size()
                : (currentPlayer - 1 + players.size()) % players.size();
    }

    private Card.Color chooseColor() {
        System.out.print("Choose color (RED, GREEN, BLUE, YELLOW): ");
        while (true) {
            try {
                return Card.Color.valueOf(scanner.next().toUpperCase());
            } catch (Exception e) {
                System.out.print("Invalid color. Try again: ");
            }
        }
    }
}
