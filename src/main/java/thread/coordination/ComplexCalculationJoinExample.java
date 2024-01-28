package thread.coordination;

import java.math.BigInteger;

class PowCalculatingThread extends Thread{
    private final BigInteger base;
    private final BigInteger power;
    private BigInteger result;

    public PowCalculatingThread(BigInteger base, BigInteger power) {
        this.base = base;
        this.power = power;
        this.result = BigInteger.ONE;
    }

    @Override
    public void run() {
        calculate();
    }

    private void calculate() {
        for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
            result = result.multiply(base);
        }
    }

    public BigInteger getResult() {
        return result;
    }
}

public class ComplexCalculationJoinExample {
    public static void main(String[] args) {
        PowCalculatingThread thread1 = new PowCalculatingThread(BigInteger.ONE.multiply(BigInteger.TEN), BigInteger.TEN.multiply(BigInteger.TEN));
        PowCalculatingThread thread2 = new PowCalculatingThread(BigInteger.TWO.multiply(BigInteger.TEN), BigInteger.TEN.multiply(BigInteger.TEN));
        thread1.start();
        thread2.start();
        try {
            thread1.join(3000);
            thread2.join(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        BigInteger sum = thread1.getResult().add(thread2.getResult());
        System.out.println(sum);
    }
}
