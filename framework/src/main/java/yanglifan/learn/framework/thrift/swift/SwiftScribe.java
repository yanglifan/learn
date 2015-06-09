package yanglifan.learn.framework.thrift.swift;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.facebook.nifty.client.FramedClientConnector;
import com.facebook.nifty.processor.NiftyProcessor;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.service.ThriftClientManager;
import com.facebook.swift.service.ThriftServer;
import com.facebook.swift.service.ThriftServerConfig;
import com.facebook.swift.service.ThriftServiceProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.net.HostAndPort.fromParts;

public class SwiftScribe implements Scribe {
    private static final Logger LOGGER = LoggerFactory.getLogger(SwiftScribe.class);

    private final List<LogEntry> messages = new ArrayList<>();

    public List<LogEntry> getMessages() {
        return messages;
    }

    @Override
    public ResultCode log(List<LogEntry> messages) {
        LOGGER.info("Receive {} messages", messages.size());
        this.messages.addAll(messages);
        return ResultCode.OK;
    }

    public static void main(String[] args) throws Exception {
        SwiftScribe scribeService = new SwiftScribe();
        NiftyProcessor processor = new ThriftServiceProcessor(new ThriftCodecManager(), Collections.emptyList(), scribeService);
        ThriftServer server = new ThriftServer(processor, new ThriftServerConfig().setPort(8899));
        server.start();
        LOGGER.info("Start Swift Nifty server...");

        ThriftClientManager clientManager = new ThriftClientManager();
        FramedClientConnector connector = new FramedClientConnector(fromParts("localhost", 8899));
        Scribe scribe = clientManager.createClient(connector, Scribe.class).get();
        scribe.log(Collections.singletonList(new LogEntry("c1", "m1")));

        server.close();
    }
}