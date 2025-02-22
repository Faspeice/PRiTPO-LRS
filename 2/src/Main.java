import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int testCasesQuantity = Integer.parseInt(scanner.nextLine().trim());
        scanner.nextLine();

        String[] deckTemplate = generateDeck();
        List<String> results = new ArrayList<>();

        for (int t = 0; t < testCasesQuantity; t++) {
            if (t > 0) results.add("");

            int[][] tricks = readTricks(scanner);
            List<Integer> shuffleOrder = readShuffleOrder(scanner);

            String[] shuffledDeck = applyTricks(deckTemplate, tricks, shuffleOrder);
            results.addAll(Arrays.asList(shuffledDeck));
        }

        scanner.close();
        printResults(results);
    }

    private static int[][] readTricks(Scanner scanner) {
        int tricksQuantity = Integer.parseInt(scanner.nextLine().trim());
        int[][] tricks = new int[tricksQuantity][52];

        for (int i = 0; i < tricksQuantity; i++) {
            for (int j = 0; j < 52; j++) {
                tricks[i][j] = scanner.nextInt() - 1;
            }
        }
        scanner.nextLine();

        return tricks;
    }

    private static List<Integer> readShuffleOrder(Scanner scanner) {
        List<Integer> shuffleOrder = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) break;
            shuffleOrder.add(Integer.parseInt(line) - 1);
        }
        return shuffleOrder;
    }

    private static String[] applyTricks(String[] deckTemplate, int[][] tricks, List<Integer> shuffleOrder) {
        String[] deck = Arrays.copyOf(deckTemplate, 52);
        for (int trick : shuffleOrder) {
            deck = applyShuffle(deck, tricks[trick]);
        }
        return deck;
    }

    private static void printResults(List<String> results) {
        for (String line : results) {
            System.out.println(line);
        }
    }

    private static String[] generateDeck() {
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] deck = new String[52];

        int index = 0;
        for (String suit : suits) {
            for (String value : values) {
                deck[index++] = value + " of " + suit;
            }
        }
        return deck;
    }

    private static String[] applyShuffle(String[] deck, int[] trick) {
        String[] newDeck = new String[52];
        for (int i = 0; i < 52; i++) {
            newDeck[trick[i]] = deck[i];
        }
        return newDeck;
    }
}
