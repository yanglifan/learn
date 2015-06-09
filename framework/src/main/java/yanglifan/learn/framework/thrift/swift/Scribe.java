package yanglifan.learn.framework.thrift.swift;

import java.util.List;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;

@ThriftService
public interface Scribe {
    @ThriftMethod
    ResultCode log(List<LogEntry> messages);
}