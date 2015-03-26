package yanglifan.learn.concurrency.dsl;

import java.util.concurrent.ExecutorService;
import java.util.function.Function;

public class NextStep extends AbstractStep {
    private Function[] functions;

    public NextStep(ExecutorService executorService, Function[] functions) {
        super(executorService);
        this.functions = functions;
    }

    public Function[] getFunctions() {
        return functions;
    }
}
