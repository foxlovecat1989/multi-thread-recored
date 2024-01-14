package synchronization.utilities.phaser.exam2;

import java.util.Date;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Student implements Runnable {
	private final Phaser phaser;

	public Student(Phaser phaser) {
		this.phaser=phaser;
	}

	public void run() {
		System.out.printf("%s: Has arrived to do the exam. %s\n",Thread.currentThread().getName(),new Date());
		phaser.arriveAndAwaitAdvance();
		System.out.printf("%s: Is going to do the first exercise. %s\n",Thread.currentThread().getName(),new Date());
		doExercise();
		System.out.printf("%s: Has done the first exercise. %s\n",Thread.currentThread().getName(),new Date());
		phaser.arriveAndAwaitAdvance();
		System.out.printf("%s: Is going to do the second exercise. %s\n",Thread.currentThread().getName(),new Date());
		doExercise();
		System.out.printf("%s: Has finished the exam. %s\n",Thread.currentThread().getName(),new Date());
		phaser.arriveAndAwaitAdvance();
	}

	private void doExercise() {
		try {
			long duration=(long)(Math.random()*10);
			TimeUnit.SECONDS.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
