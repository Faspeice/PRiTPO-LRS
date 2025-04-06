import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("1/static/testcases.txt"));
        int testCasesQuantity = Integer.parseInt(scanner.nextLine().trim());
        scanner.nextLine();

        List<String> results = new ArrayList<>();
        for (int t = 0; t < testCasesQuantity; t++) {
            int numCandidates = Integer.parseInt(scanner.nextLine().trim());
            String[] candidates = readCandidates(scanner, numCandidates);
            List<List<Integer>> ballots = readBallots(scanner);

            results.add(runElection(candidates, ballots));
            if (t < testCasesQuantity - 1) results.add("");
        }
        scanner.close();

        System.out.println(String.join("\n", results));
    }

    private static String[] readCandidates(Scanner scanner, int numCandidates) {
        String[] candidates = new String[numCandidates];
        for (int i = 0; i < numCandidates; i++) {
            candidates[i] = scanner.nextLine().trim();
        }
        return candidates;
    }

    private static List<List<Integer>> readBallots(Scanner scanner) {
        List<List<Integer>> ballots = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) break;
            ballots.add(parseBallot(line));
        }
        return ballots;
    }

    private static List<Integer> parseBallot(String line) {
        List<Integer> ballot = new ArrayList<>();
        for (String part : line.split(" ")) {
            ballot.add(Integer.parseInt(part) - 1);
        }
        return ballot;
    }

    private static String runElection(String[] candidates, List<List<Integer>> ballots) {
        Set<Integer> activeCandidates = initializeCandidates(candidates.length);

        while (true) {
            Map<Integer, Integer> votes = countVotes(activeCandidates, ballots);
            int totalVotes = ballots.size();
            int maxVotes = Collections.max(votes.values());
            int minVotes = Collections.min(votes.values());

            if (isWinner(maxVotes, totalVotes)) {
                return findWinner(candidates, activeCandidates, votes, maxVotes);
            }

            Set<Integer> toEliminate = findEliminatedCandidates(activeCandidates, votes, minVotes);
            if (isTie(toEliminate, activeCandidates)) {
                return listTiedCandidates(candidates, activeCandidates);
            }

            activeCandidates.removeAll(toEliminate);
        }
    }

    private static Set<Integer> initializeCandidates(int numCandidates) {
        Set<Integer> candidates = new HashSet<>();
        for (int i = 0; i < numCandidates; i++) {
            candidates.add(i);
        }
        return candidates;
    }

    private static Map<Integer, Integer> countVotes(Set<Integer> activeCandidates, List<List<Integer>> ballots) {
        Map<Integer, Integer> votes = new HashMap<>();
        for (int candidate : activeCandidates) votes.put(candidate, 0);

        for (List<Integer> ballot : ballots) {
            for (int choice : ballot) {
                if (activeCandidates.contains(choice)) {
                    votes.put(choice, votes.get(choice) + 1);
                    break;
                }
            }
        }
        return votes;
    }

    private static boolean isWinner(int maxVotes, int totalVotes) {
        return maxVotes * 2 > totalVotes;
    }

    private static String findWinner(String[] candidates, Set<Integer> activeCandidates, Map<Integer, Integer> votes, int maxVotes) {
        for (int i : activeCandidates) {
            if (votes.get(i) == maxVotes) {
                return candidates[i];
            }
        }
        return "";
    }

    private static Set<Integer> findEliminatedCandidates(Set<Integer> activeCandidates, Map<Integer, Integer> votes, int minVotes) {
        Set<Integer> toEliminate = new HashSet<>();
        for (int i : activeCandidates) {
            if (votes.get(i) == minVotes) {
                toEliminate.add(i);
            }
        }
        return toEliminate;
    }

    private static boolean isTie(Set<Integer> toEliminate, Set<Integer> activeCandidates) {
        return toEliminate.size() == activeCandidates.size();
    }

    private static String listTiedCandidates(String[] candidates, Set<Integer> activeCandidates) {
        List<String> tiedCandidates = new ArrayList<>();
        for (int i : activeCandidates) {
            tiedCandidates.add(candidates[i]);
        }
        return String.join("\n", tiedCandidates);
    }
}