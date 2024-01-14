package synchronization.utilities.phaser;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class FileSearch implements Runnable {
    private final String initPath;
    private final String fileExtension;
    private List<String> results;
    private final Phaser phaser;

    public FileSearch(String initPath, String fileExtension, Phaser phaser) {
        this.initPath = initPath;
        this.fileExtension = fileExtension;
        this.phaser = phaser;
        results = new ArrayList<>();
    }

    @Override
    public void run() {
        phaser.arriveAndAwaitAdvance();
        System.out.printf("%s: Starting.\n", Thread.currentThread().getName());

        // 1st Phase: Look for the files
        File file = new File(initPath);
        if (file.isDirectory()) {
            directoryProcess(file);
        }
        // If no results, deregister in the phaser and ends
        if (checkResults()) {
            return;
        }

        // 2nd Phase: Filter the results
        filterResults();
        // If no results after the filter, deregister in the phaser and ends
        if (checkResults()) {
            return;
        }

        // 3rd Phase: Show info
        showInfo();
        phaser.arriveAndDeregister();
        System.out.printf("%s: Work completed.\n", Thread.currentThread().getName());
    }

    private void showInfo() {
        for (String result : results) {
            File file = new File(result);
            System.out.printf("%s: %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
        }
        // Waits for the end of all the FileSearch threads that are registered in the phaser
        phaser.arriveAndAwaitAdvance();
    }

    private boolean checkResults() {
        if (results.isEmpty()) {
            System.out.printf("%s: Phase %d: 0 results.\n", Thread.currentThread().getName(), phaser.getPhase());
            System.out.printf("%s: Phase %d: End.\n", Thread.currentThread().getName(), phaser.getPhase());
            // No results. Phase is completed but no more work to do. Deregister for the phaser
            phaser.arriveAndDeregister();

            return true;
        } else {
            // There are results. Phase is completed. Wait to continue with the next phase
            System.out.printf("%s: Phase %d: %d results.\n", Thread.currentThread().getName(), phaser.getPhase(),
                    results.size());
            phaser.arriveAndAwaitAdvance();

            return false;
        }
    }

    private void filterResults() {
        List<String> newResults = new ArrayList<>();
        long actualDate = new Date().getTime();
        for (String result : results) {
            File file = new File(result);
            long fileDate = file.lastModified();

            if (actualDate - fileDate < TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)) {
                newResults.add(result);
            }
        }
        results = newResults;
    }

    private void directoryProcess(File file) {
        File[] list = file.listFiles();
        if (list != null) {
            for (File value : list) {
                if (value.isDirectory()) {
                    directoryProcess(value);
                } else {
                    fileProcess(value);
                }
            }
        }
    }

    private void fileProcess(File file) {
        if (file.getName().endsWith(fileExtension)) {
            results.add(file.getAbsolutePath());
        }
    }
}
