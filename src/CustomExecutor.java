import java.util.concurrent.SynchronousQueue;
import java.util.function.Function;

public class CustomExecutor {
    private SynchronousQueue<WorkItem> queue;
    private WorkerThread[] workerThreads;

    public CustomExecutor(int maxWorkers) {
        queue = new SynchronousQueue<>();
        workerThreads = new WorkerThread[maxWorkers];
        for (int i = 0; i < workerThreads.length; i++) {
            workerThreads[i] = new WorkerThread(queue);
            workerThreads[i].start();
        }
    }

    public FutureResult[] map(Function function, int[] args) throws InterruptedException {
        WorkItem<Integer, Integer>[] workItems = new WorkItem[args.length];
        FutureResult[] results = new FutureResult[args.length];
        for (int i = 0; i < args.length; i++) {
            workItems[i] = new WorkItem<>(function, args[i]);
            queue.put(workItems[i]);
            results[i] = workItems[i].getFutureResult();
        }
        return results;
    }

    public FutureResult execute(Function function, int arg) {
        WorkItem<Integer, Integer> workItem = new WorkItem<>(function, arg);
        queue.add(workItem);
        return workItem.getFutureResult();
    }

    public void shutdown() throws InterruptedException {
        for (WorkerThread notWorking : workerThreads)
            queue.put(new WorkItem(null, null));

        for (WorkerThread workerThread : workerThreads)
            workerThread.join();
    }
}