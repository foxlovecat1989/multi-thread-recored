package thread.communitcation;

import java.util.LinkedList;
import java.util.Queue;

class Producer implements Runnable {
    private final int capacity;
    private final Queue<Integer> queue;

    public Producer(Queue<Integer> queue, int capacity) {
        this.capacity = capacity;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                add(queue.size());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void add(int item) throws InterruptedException {
        synchronized (queue) {
            if (queue.size() == capacity) {
                System.out.println("Producer is waiting...");
                queue.wait();
            }

            queue.add(item);
                Thread.sleep(1000);
            System.out.println("Queue size: " + queue.size());

            if (queue.size() == 1) {
                queue.notify();
            }
        }
    }
}

class Consumer implements Runnable {
    private final Queue<Integer> queue;
    private final int capacity;

    public Consumer(Queue<Integer> queue, int capacity) {
        this.queue = queue;
        this.capacity = capacity;
    }

    @Override
    public void run() {
        while (true) {
            try {
                remove();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void remove() throws InterruptedException {
        synchronized (queue) {
            if (queue.isEmpty()) {
                System.out.println("Consumer is waiting...");
                queue.wait();
            }
            queue.remove();
            Thread.sleep(1000);
            System.out.println("Queue size: " + queue.size());

            if (queue.size() == capacity - 1) {
                queue.notify();
            }
        }
    }
}

public class ThreadCommunicationWaitNotifyExample {
    public static final int CAPACITY = 10;

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        Thread producer = new Thread(new Producer(queue, CAPACITY));
        Thread consumer = new Thread(new Consumer(queue, CAPACITY));
        producer.start();
        consumer.start();
    }
}
