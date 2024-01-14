package synchronization.utilities.semaphores;

import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintQueue {
    private final Semaphore semaphore;
    private final boolean[] freePrinters;
    private final Lock lockPrinters;
    private final static int numOfPrinters = 3;

    public PrintQueue() {
        semaphore = new Semaphore(numOfPrinters);
        lockPrinters = new ReentrantLock();
        freePrinters = new boolean[numOfPrinters];
        for (int i = 0; i < numOfPrinters; i++) {
            freePrinters[i] = true;
        }
    }

    public void printJob(Object document) {
        try {
            semaphore.acquire();
            int assignedPrinter = getPrinter();

            long duration = (long) (Math.random() * 10);
            System.out.printf("%s - %s: PrintQueue: Printing a Job in Printer %d during %d seconds\n",
                    new Date(),
                    Thread.currentThread().getName(),
                    assignedPrinter,
                    duration);
            TimeUnit.SECONDS.sleep(duration);
            freePrinters[assignedPrinter] = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    private int getPrinter() {
        int ret = -1;

        try {
            lockPrinters.lock();
            for (int i = 0; i < freePrinters.length; i++) {
                if (freePrinters[i]) {
                    ret = i;
                    freePrinters[i] = false;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lockPrinters.unlock();
        }

        return ret;
    }

}
