package section2threadcoordinationinterrupted.example2;

import java.math.BigInteger;

public class ParticularInterruptExample {
    public static void main(String[] args) {
        Thread thread = new Thread(
                new LongComputationTask(
                        new BigInteger("200000"),
                        new BigInteger("100000000")));
        thread.start();
        thread.interrupt();
    }

    private static class LongComputationTask implements Runnable {
        private final BigInteger base;
        private final BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                // !!! to check the current thread is interrupted
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Prematurely interrupted computation");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }

            return result;
        }
    }
}
