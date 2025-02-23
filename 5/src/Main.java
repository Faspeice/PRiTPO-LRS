import java.util.*;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        List<String> inputLines = readInput();
        List<String> outputLines = processInput(inputLines);
        printOutput(outputLines);
    }

    private static List<String> readInput() {
        Scanner scanner = new Scanner(System.in);
        List<String> inputLines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) break;
            inputLines.add(line);
        }
        scanner.close();
        return inputLines;
    }

    private static List<String> processInput(List<String> inputLines) {
        List<String> outputLines = new ArrayList<>();
        for (String line : inputLines) {
            outputLines.add(findSmallestPowerOfTwo(line));
        }
        return outputLines;
    }

    private static void printOutput(List<String> outputLines) {
        for (String output : outputLines) {
            System.out.println(output);
        }
    }

    private static String findSmallestPowerOfTwo(String n) {
        int nLength = n.length();
        for (int e = 1; e < 10000; e++) {
            String powerStr = getPowerOfTwo(e);

            if (powerStr.length() > 2 * nLength && powerStr.startsWith(n)) {
                return String.valueOf(e);
            }
        }
        return "no power of 2";
    }

    private static String getPowerOfTwo(int exponent) {
        return BigInteger.TWO.pow(exponent).toString();
    }
}
