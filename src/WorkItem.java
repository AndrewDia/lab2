import java.util.function.Function;

public class WorkItem<TArg, TResult> {
    private Function function;
    private TArg argument;
    private FutureResult<TResult> futureResult;

    public WorkItem(Function function, TArg argument) {
        this.function = function;
        this.argument = argument;
        this.futureResult = new FutureResult<>();
    }

    public Function getFunction() {
        return function;
    }

    public TArg getArgument() {
        return argument;
    }

    public FutureResult<TResult> getFutureResult() {
        return futureResult;
    }
}
