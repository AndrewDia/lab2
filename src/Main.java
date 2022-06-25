import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Function longRunningTask = (x) -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return (int) x * 2;
        };

        CustomExecutor executor = new CustomExecutor(2);
        FutureResult[] futures = executor.map(longRunningTask, new int[]{1, 2, 3, 4});
        for (FutureResult future : futures)
            System.out.println(future.getResult() + " - " +
                    LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        executor.shutdown();
    }
}
