package yanglifan.learn.framework.thrift.swift;

import java.util.List;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;
import com.google.common.util.concurrent.ListenableFuture;

@ThriftService
public interface Service {
    @ThriftMethod
    ResultCode log(List<LogEntry> messages);

    @ThriftMethod
    ListenableFuture<String> asyncMethod(String message);
}