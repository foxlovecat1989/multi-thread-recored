package synchronization.utilities.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

// CyclicBarrier - Allows the synchronization of multiple threads at a determined point.

public class Main {
    private static final int ROWS = 10000;
    private static final int NUMBERS = 1000;
    private static final int SEARCH = 5;
    private static final int PARTICIPANTS = 5;
    private static final int LINES_PARTICIPANT = 2000;

    public static void main(String[] args) {
        MatrixMock mock = new MatrixMock(ROWS, NUMBERS, SEARCH);
        Results results = new Results(ROWS);
        Grouper grouper = new Grouper(results);
        CyclicBarrier barrier = new CyclicBarrier(PARTICIPANTS, grouper);
        Searcher[] searchers = new Searcher[PARTICIPANTS];

        for (int i = 0; i < PARTICIPANTS; i++) {
            searchers[i] = new Searcher(i * LINES_PARTICIPANT, (i * LINES_PARTICIPANT) + LINES_PARTICIPANT, mock, results, 5, barrier);
            Thread thread = new Thread(searchers[i]);
            thread.start();
        }

        System.out.print("Main: The main thread has finished.\n");
    }

}
