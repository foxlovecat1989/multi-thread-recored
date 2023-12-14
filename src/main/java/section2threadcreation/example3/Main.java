package section2threadcreation.example3;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static final int MAX_PASSWORD = 999;

    public static void main(String[] args) {
        Vault vault = new Vault(new Random().nextInt(MAX_PASSWORD));
        List.of(new AscendingHackerThread(vault),
                        new DecendingHackerThread(vault),
                        new PoliceThread()
                ).forEach(Thread::start);
    }

    private record Vault(int password) {
        private boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread {

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            IntStream.range(0, MAX_PASSWORD).forEach(guess -> {
                boolean isCorrectPassword = vault.isCorrectPassword(guess);
                if (isCorrectPassword) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            });
        }
    }

    private static class DecendingHackerThread extends HackerThread {

        public DecendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            IntStream.range(0, MAX_PASSWORD).forEach(i -> {
                int guess = MAX_PASSWORD - i;
                boolean isCorrectPassword = vault.isCorrectPassword(guess);
                if (isCorrectPassword) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            });
        }
    }

    private static class PoliceThread extends Thread {
        @Override
        public void run() {
            IntStream.range(0, 10).forEach(i -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println(10 - i);
            });

            System.out.println("Game over for you hackers");
            System.exit(0);
        }
    }
}
