package synchronization.utilities.countdownlatch;

// CountDownLatch - Allow one or more threads to wait until a set of operations are made.

public class Main {
    private static final int numOfParticipant = 10;

    public static void main(String[] args) {
        Videoconference conference = new Videoconference(numOfParticipant);
        Thread threadConference = new Thread(conference);
        threadConference.start();

        for (int i = 0; i < 10; i++) {
            Participant participant = new Participant(conference, "Participant " + i);
            new Thread(participant).start();
        }

    }

}
