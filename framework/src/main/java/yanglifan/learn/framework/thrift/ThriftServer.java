package yanglifan.learn.framework.thrift;

import java.util.concurrent.Executors;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class ThriftServer {
    static CalculatorHandler handler;

    public static void startServer() {
        Executors.newSingleThreadExecutor().execute(() -> {
            handler = new CalculatorHandler();
            Calculator.Processor<CalculatorHandler> processor = new Calculator.Processor<>(handler);

            TServerTransport serverTransport = null;
            try {
                serverTransport = new TServerSocket(9090);
            } catch (TTransportException e) {
                e.printStackTrace();
            }
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));

            // Use this for a multithreaded server
            // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the simple server...");
            server.serve();
        });
    }

    public static void main(String[] args) {
        startServer();
        ThriftClient.remoteCall();
    }
}