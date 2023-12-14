package section3threadcoordinationinterrupted.quiz1;

public class CorrectInterruptExample {
    public static void main(String [] args) {
        Thread thread = new Thread(new BlockingTask());
        thread.start();
        thread.interrupt();
    }

    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            //do things
           while (true) {
               try {
                   Thread.sleep(5);
               } catch (InterruptedException e) {
                   System.out.println("Existing blocking thread");
                   // !!! import - without return, the thread will not terminate
                   return;
               }
           }
        }
    }
}