package synchronization.utilities.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class SeedGenerator implements Runnable {
    private final CompletableFuture<Integer> resultCommunicator;

    public SeedGenerator(CompletableFuture<Integer> completable) {
        this.resultCommunicator = completable;
    }

    @Override
    public void run() {
        System.out.print("SeedGenerator: Generating seed...\n");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int seed = (int) Math.rint(Math.random() * 10);

        System.out.printf("SeedGenerator: Seed generated: %d\n", seed);

        resultCommunicator.complete(seed);
    }
}
