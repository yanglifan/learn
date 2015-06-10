package yanglifan.learn.framework.thrift.swift;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

import com.facebook.nifty.client.FramedClientConnector;
import com.facebook.nifty.processor.NiftyProcessor;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.service.ThriftClientManager;
import com.facebook.swift.service.ThriftServer;
import com.facebook.swift.service.ThriftServerConfig;
import com.facebook.swift.service.ThriftServiceProcessor;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.net.HostAndPort.fromParts;

public class SwiftService implements Service {
    private static final Logger LOGGER = LoggerFactory.getLogger(SwiftService.class);

    private final List<LogEntry> messages = new ArrayList<>();

    private ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

    public List<LogEntry> getMessages() {
        return messages;
    }

    @Override
    public ResultCode log(List<LogEntry> messages) {
        LOGGER.info("Receive {} messages", messages.size());
        this.messages.addAll(messages);
        return ResultCode.OK;
    }

    @Override
    public ListenableFuture<String> asyncMethod(String message) {
        LOGGER.info("Async method has received a message [{}]", message);
        return listeningExecutorService.submit(() -> {
            Thread.sleep(5000);
            LOGGER.info("Finish the process for [{}]", message);
            return "The result of [" + message + "]";
        });
    }

    public static void main(String[] args) throws Exception {
        SwiftService scribeService = new SwiftService();
        NiftyProcessor processor = new ThriftServiceProcessor(new ThriftCodecManager(), Collections.emptyList(), scribeService);
        ThriftServer server = new ThriftServer(processor, new ThriftServerConfig().setPort(8899));
        server.start();
        LOGGER.info("Start Swift Nifty server...");

        ThriftClientManager clientManager = new ThriftClientManager();
        FramedClientConnector connector = new FramedClientConnector(fromParts("localhost", 8899));
        Service swiftClient = clientManager.createClient(connector, Service.class).get();
        swiftClient.log(Collections.singletonList(new LogEntry("c1", "m1")));

        ListenableFuture<String> listenableFuture = swiftClient.asyncMethod("Message A");
        LOGGER.info("Receive the future");
        String result = listenableFuture.get();
        LOGGER.info("Receive the result [{}]", result);

        server.close();
    }
}