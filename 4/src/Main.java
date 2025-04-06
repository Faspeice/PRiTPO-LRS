import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("4/static/testcases.txt"));
        List<String> inputLines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) break;
            inputLines.add(line);
        }
        scanner.close();

        List<String> outputLines = new ArrayList<>();
        for (String line : inputLines) {
            int[] stack = parseStack(line);
            List<Integer> flips = pancakeSort(stack);
            outputLines.add(line + " " + formatFlips(flips));
        }

        for (String output : outputLines) {
            System.out.println(output);
        }
    }

    private static int[] parseStack(String line) {
        String[] tokens = line.split("\\s+");
        int[] stack = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            stack[i] = Integer.parseInt(tokens[i]);
        }
        return stack;
    }

    private static List<Integer> pancakeSort(int[] origStack) {
        int n = origStack.length;
        int[] stack = Arrays.copyOf(origStack, n);
        List<Integer> flips = new ArrayList<>();

        for (int currSize = n; currSize > 1; currSize--) {
            int maxIndex = findMaxIndex(stack, currSize);
            if (maxIndex != currSize - 1) {
                if (maxIndex != 0) {
                    flips.add(n - maxIndex);
                    flip(stack, maxIndex);
                }
                flips.add(n - (currSize - 1));
                flip(stack, currSize - 1);
            }
        }
        flips.add(0);
        return flips;
    }

    private static int findMaxIndex(int[] stack, int limit) {
        int maxIndex = 0;
        for (int i = 1; i < limit; i++) {
            if (stack[i] > stack[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private static void flip(int[] stack, int r) {
        int i = 0, j = r;
        while (i < j) {
            int temp = stack[i];
            stack[i] = stack[j];
            stack[j] = temp;
            i++;
            j--;
        }
    }

    private static String formatFlips(List<Integer> flips) {
        return String.join(" ", flips.stream().map(String::valueOf).toArray(String[]::new));
    }
}
