package section2threadcreation.exec1;

import java.util.List;

public class MultiExecutor {
    private final List<Runnable> tasks;

    public MultiExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    public static void main(String[] args) {
        List<Runnable> tasks = List.of(
                () -> System.out.println("Task A"),
                () -> System.out.println("Task B"),
                () -> System.out.println("Task C")
        );
        new MultiExecutor(tasks).executeAll();
    }

    private void executeAll() {
        tasks.stream()
                .map(Thread::new)
                .forEach(Thread::start);
    }
}