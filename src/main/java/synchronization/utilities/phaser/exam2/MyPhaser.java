package synchronization.utilities.phaser.exam2;

import java.util.concurrent.Phaser;

public class MyPhaser extends Phaser {
	@Override
	protected boolean onAdvance(int phase, int registeredParties) {
        return switch (phase) {
            case 0 -> studentsArrived();
            case 1 -> finishFirstExercise();
            case 3 -> finishExam();
            default -> true;
        };
	}

	private boolean studentsArrived() {
		System.out.print("Phaser: The exam are going to start. The students are ready.\n");
		System.out.printf("Phaser: We have %d students.\n",getRegisteredParties());

		return false;
	}

	private boolean finishFirstExercise() {
		System.out.print("Phaser: All the students has finished the first exercise.\n");
		System.out.print("Phaser: It's turn for the second one.\n");

		return false;
	}

	private boolean finishExam() {
		System.out.print("Phaser: All the students has finished the exam.\n");
		System.out.print("Phaser: Thank you for your time.\n");

		return true;
	}
}
