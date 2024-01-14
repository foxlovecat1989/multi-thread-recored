package synchronization.utilities.phaser.exam2;


public class Main {
    private static final int NUM_OF_STUDENT = 5;
    public static void main(String[] args) {
        MyPhaser phaser = new MyPhaser();

        Student[] students = new Student[NUM_OF_STUDENT];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(phaser);
            phaser.register();
        }

        Thread[] threads = new Thread[students.length];
        for (int i = 0; i < students.length; i++) {
            threads[i] = new Thread(students[i], "Student " + i);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Main: The phaser has finished: %s.\n", phaser.isTerminated());
    }

}
