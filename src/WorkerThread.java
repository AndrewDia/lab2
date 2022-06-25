import java.util.concurrent.SynchronousQueue;

import static java.util.Objects.isNull;

public class WorkerThread extends Thread {
    private SynchronousQueue<WorkItem> queue;

    public WorkerThread(SynchronousQueue<WorkItem> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            WorkItem item = null;
            try {
                item = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isNull(item.getArgument()) || isNull(item.getFunction()))
                return;

            item.getFutureResult().setResult(item.getFunction().apply(item.getArgument()));
        }
    }
}
