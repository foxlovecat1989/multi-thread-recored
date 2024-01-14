package synchronization.utilities.semaphores;

// A semaphore is a counter that controls access to one or more shared resources.

public class Main {
    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();
        Thread[] threads = new Thread[12];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Job(printQueue), "Thread " + i);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}
