import java.util.*;

public class Main {
    final static String LETTERS = "RLRFR";

    final static int LENGTH = 100;

    final static int THREAD_COUNT = 1000;

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        Runnable logic = Main::lineOfLetter;

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads.add(new Thread(logic));
            threads.get(i).start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        Map.Entry<Integer, Integer> max = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();

        System.out.println("Самое частое кол-во повторений " + max.getKey()
                + " - встретилось " + max.getValue() + " раз(а)");

        System.out.println("Все варианты размеров: ");

        sizeToFreq
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println( e.getKey() + " - " + e.getValue() + " раз(а)"));

    }

    public static synchronized void lineOfLetter() {
        String text = generateRoute(LETTERS, LENGTH);
        int maxSize = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == 'R') maxSize++;
        }
        if (!sizeToFreq.containsKey(maxSize)) {
            sizeToFreq.put(maxSize, 1);
        } else {
            int newNum = sizeToFreq.get(maxSize) + 1;
            sizeToFreq.put(maxSize, newNum);
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

}
