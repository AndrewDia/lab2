import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FutureResult<T> {
    private boolean hasResult;
    private T result;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void setResult(T result) {
        lock.lock();
        hasResult = true;
        this.result = result;
        condition.signal();
        lock.unlock();
    }

    public T getResult() throws InterruptedException {
        lock.lock();
        if (!hasResult)
            condition.await();
        lock.unlock();
        return result;
    }
}
