import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static final int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("3/static/testcases.txt"));
        int testCases = Integer.parseInt(scanner.nextLine().trim());
        scanner.nextLine();

        for (int t = 0; t < testCases; t++) {
            if (t > 0) System.out.println();

            int rows = scanner.nextInt();
            scanner.nextLine();

            char[][] grid = readGrid(scanner, rows);
            int wordCount = Integer.parseInt(scanner.nextLine().trim());
            List<String> words = readWords(scanner, wordCount);

            for (String word : words) {
                int[] position = findWord(grid, word.toLowerCase());
                System.out.println(position[0] + " " + position[1]);
            }

            if (scanner.hasNextLine()) scanner.nextLine();
        }

        scanner.close();
    }

    private static char[][] readGrid(Scanner scanner, int rows) {
        char[][] grid = new char[rows][];
        for (int i = 0; i < rows; i++) {
            grid[i] = scanner.nextLine().trim().toLowerCase().toCharArray();
        }
        return grid;
    }

    private static List<String> readWords(Scanner scanner, int count) {
        List<String> words = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            words.add(scanner.nextLine().trim());
        }
        return words;
    }

    private static int[] findWord(char[][] grid, String word) {
        int m = grid.length;
        int n = grid[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (startsWith(grid, i, j, word)) {
                    return new int[]{i + 1, j + 1};
                }
            }
        }

        return new int[]{-1, -1};
    }

    private static boolean startsWith(char[][] grid, int x, int y, String word) {
        int m = grid.length;
        int n = grid[0].length;

        for (int dir = 0; dir < 8; dir++) {
            int i = x, j = y, k;
            for (k = 0; k < word.length(); k++) {
                if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] != word.charAt(k)) {
                    break;
                }
                i += dx[dir];
                j += dy[dir];
            }
            if (k == word.length()) return true;
        }

        return false;
    }
}
