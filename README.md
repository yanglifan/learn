# Core
This module is used to demo the java core knowledge. Include Java features in JDK 5 and 6 (except Java concurrency)

# Concurrency
This module is used to demo the java concurrency knowledge.

## `ExecutorBuilder`
JDK `ThreadPoolExecutor` is a powerful concurrency tool, but it is complex to construct. JDK provides `Executors` util class to easy this process, but it cannot full satisfy programmers' needs. So `ExecutorBuilder` is created to help this.

### Sample

```java
ExecutorBuilder.newBuilder().corePoolSize(10).build("MyThread");
```

`ExecutorBuilder` can be used easily in the above way. We specify the core size of the thread pool and give a meaningful name for threads created by this thread pool.