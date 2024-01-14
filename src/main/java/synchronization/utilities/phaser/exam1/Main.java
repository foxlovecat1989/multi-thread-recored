package synchronization.utilities.phaser.exam1;

import java.util.concurrent.Phaser;

// Phaser:
// Control the execution of concurrent takes divided in phases.
// All the threads must finish one phase before they can continue with the next one.

public class Main {
    private static final int NUM_OF_PARTIES = 3;
    public static void main(String[] args) {
        Phaser phaser = new Phaser(NUM_OF_PARTIES);
        FileSearch desktop = new FileSearch("/Users/foxlovecatii/Desktop/", "log", phaser);
        FileSearch apps = new FileSearch("/System/Applications", "log", phaser);
        FileSearch documents = new FileSearch("/Users/foxlovecatii/Documents", "log", phaser);

        Thread deskTopThread = new Thread(desktop, "System");
        Thread appsThread = new Thread(apps, "Apps");
        Thread documentsThread = new Thread(documents, "Documents");
        deskTopThread.start();
        appsThread.start();
        documentsThread.start();

        try {
            deskTopThread.join();
            appsThread.join();
            documentsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Terminated: %s\n", phaser.isTerminated());
    }
}
