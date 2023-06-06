import java.util.*;

public class EventSimulation {
    private static Random rand = new Random();

    public static void main(String[] args) {
        // Example: Rolling a six-faced biased dice
        Map<Integer, Double> diceProbabilities = new HashMap<>();
        diceProbabilities.put(1, 10.0);
        diceProbabilities.put(2, 30.0);
        diceProbabilities.put(3, 15.0);
        diceProbabilities.put(4, 15.0);
        diceProbabilities.put(5, 30.0);
        diceProbabilities.put(6, 0.0);

        int numOccurrences = 1000;
        Map<Integer, Integer> diceOccurrences = simulateEvent(diceProbabilities, numOccurrences);
        System.out.println("Dice occurrences: " + diceOccurrences);

        // Example: Flipping a biased coin
        Map<String, Double> coinProbabilities = new HashMap<>();
        coinProbabilities.put("Head", 35.0);
        coinProbabilities.put("Tail", 65.0);

        Map<String, Integer> coinOccurrences = simulateEvent(coinProbabilities, numOccurrences);
        System.out.println("Coin occurrences: " + coinOccurrences);
    }

    public static <T> Map<T, Integer> simulateEvent(Map<T, Double> probabilities, int numOccurrences) {
        Map<T, Integer> occurrences = new HashMap<>();
        double totalProbability = probabilities.values().stream().mapToDouble(Double::doubleValue).sum();

        for (int i = 0; i < numOccurrences; i++) {
            double randomValue = rand.nextDouble() * totalProbability; // Generate a random value between 0 and total probability
            double cumulativeProbability = 0;
            // Iterate through the probabilities to find the outcome for the current occurrence
            for (Map.Entry<T, Double> entry : probabilities.entrySet()) {
                cumulativeProbability += entry.getValue();
                if (randomValue <= cumulativeProbability) {
                    T outcome = entry.getKey();
                    occurrences.put(outcome, occurrences.getOrDefault(outcome, 0) + 1); // Add one to the occurrence count for the outcome
                    break;
                }
            }
        }

        return occurrences; // Return the map containing the number of occurrences for each outcome
    }
}
