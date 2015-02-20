package yanglifan.learn.concurrency.dsl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentDslBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentDslBuilder.class);

    private static ExecutorService defaultExecutorService = Executors.newCachedThreadPool();

    private Mode mode = Mode.ASYNC;
    private Map<Class, Object> resultMap = new ConcurrentHashMap<>();

    private FirstStep firstStep;
    private List<NextStep> nextSteps = new ArrayList<>();

    private Consumer<Throwable> exceptionHandler = new ExceptionLogger();

    private ConcurrentDslBuilder() {
    }

    private ConcurrentDslBuilder(Mode mode) {
        this.mode = mode;
    }

    public static <T> ConcurrentDslBuilder startCall(Callable<T> callable) throws Exception {
        return startCall(defaultExecutorService, Mode.ASYNC, callable);
    }

    public static <T> ConcurrentDslBuilder startCall(Mode mode, Callable<T> callable) throws Exception {
        return startCall(defaultExecutorService, mode, callable);
    }

    public static <T> ConcurrentDslBuilder startCall(ExecutorService executorService, Callable<T> callable) throws Exception {
        return startCall(executorService, Mode.ASYNC, callable);
    }

    public static <T> ConcurrentDslBuilder startCall(ExecutorService executorService, Mode mode, Callable<T> callable) {
        if (mode == Mode.ASYNC) {
            return addFirstStep(executorService, callable);
        } else {
            ConcurrentDslBuilder builder = new ConcurrentDslBuilder(mode);
            return startSyncCall(builder, executorService, callable);
        }
    }

    private static <T> ConcurrentDslBuilder addFirstStep(ExecutorService executorService, Callable<T> callable) {
        LOGGER.debug("Start to build a ConcurrentDslBuilder");
        ConcurrentDslBuilder builder = new ConcurrentDslBuilder();
        builder.firstStep = new FirstStep(executorService, callable);
        return builder;
    }

    private static <T> ConcurrentDslBuilder startSyncCall(ConcurrentDslBuilder builder, ExecutorService executorService,
                                                          Callable<T> callable) {
        CompletionService<T> completionService = new ExecutorCompletionService<>(executorService);
        completionService.submit(callable);

        T result;
        try {
            result = completionService.take().get();
        } catch (Exception e) {
            builder.exceptionHandler.accept(e.getCause());
            throw new RuntimeException(e);
        }

        builder.resultMap.put(callable.getClass(), result);
        return builder;
    }

    public ConcurrentDslBuilder thenCall(Function<?, ?>... functions) {
        if (mode == Mode.ASYNC) {
            return addNextStep(defaultExecutorService, functions);
        } else {
            return thenCall(defaultExecutorService, functions);
        }
    }

    private ConcurrentDslBuilder addNextStep(ExecutorService executorService, Function<?, ?>... functions) {
        LOGGER.debug("Add a NextStep for {}", buildStepName(functions));
        nextSteps.add(new NextStep(executorService, functions));
        return this;
    }


    @SuppressWarnings("unchecked")
    public ConcurrentDslBuilder thenCall(ExecutorService executorService, Function<?, ?>... functions) {
        CompletionService completionService = new ExecutorCompletionService<>(executorService);

        if (isSinglePreviousCall()) {
            Object singleResult = resultMap.values().iterator().next();
            for (Function function : functions) {
                completionService.submit(() -> new Object[]{function.getClass(), function.apply(singleResult)});
            }
        } else {
            Map<Class, Object> copyResultMap = copy(resultMap);
            for (Function function : functions) {
                completionService.submit(() -> new Object[]{function.getClass(), function.apply(copyResultMap)});
            }
        }

        getResults(completionService, functions);

        return this;
    }

    public void run() {
        if (mode != Mode.ASYNC)
            return;

        Executors.newSingleThreadExecutor().execute(() -> {
            LOGGER.debug("Start to run");
            executeFirstStep();
            executeNextSteps();
        });
    }

    private void executeNextSteps() {
        nextSteps.forEach(step -> {
            LOGGER.debug("Execute a NextStep for {}", buildStepName(step.getFunctions()));
            try {
                thenCall(step.getExecutorService(), step.getFunctions());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @SuppressWarnings("unchecked")
    private ConcurrentDslBuilder executeFirstStep() {
        LOGGER.debug("Execute a FirstStep {}", firstStep.getCallable().getClass().getSimpleName());
        return startSyncCall(this, firstStep.getExecutorService(), firstStep.getCallable());
    }

    private Map<Class, Object> copy(Map<Class, Object> resultMap) {
        return new HashMap<>(resultMap);
    }

    private void getResults(CompletionService completionService, Function<?, ?>... functions) {
        resultMap.clear();
        int i = 0;
        while (i++ < functions.length) {
            Object[] result;

            try {
                result = (Object[]) completionService.take().get();
            } catch (Exception e) {
                exceptionHandler.accept(e);
                throw new RuntimeException(e);
            }

            if (result[1] != null) {
                resultMap.put((Class) result[0], result[1]);
            }
        }
    }

    private boolean isSinglePreviousCall() {
        return resultMap.entrySet().size() == 1;
    }

    private String buildStepName(Function<?, ?>[] functions) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Function function : functions) {
            stringBuilder.append("[");
            stringBuilder.append(function.getClass().getSimpleName());
            stringBuilder.append("] ");
        }

        return stringBuilder.toString();
    }

    public void setExceptionHandler(Consumer<Throwable> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }
}
