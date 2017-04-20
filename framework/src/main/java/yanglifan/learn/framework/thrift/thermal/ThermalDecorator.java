package yanglifan.learn.framework.thrift.thermal;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import yanglifan.learn.framework.thrift.Calculator;
import yanglifan.learn.framework.thrift.ThriftClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Executors;

public class ThermalDecorator<T, U> implements InvocationHandler {
    private Class<U> thriftInterface;
    private T userService;

    public static void main(String[] args) throws Exception {
        ThermalDecorator<ThermalDemoService, Calculator.Iface> td = new ThermalDecorator<>();
        td.setThriftInterface(Calculator.Iface.class);
        td.setUserService(new ThermalDemoServiceImpl());

        Calculator.Iface handler = td.makeService();

        Executors.newSingleThreadExecutor().execute(() -> {
            Calculator.Processor<Calculator.Iface> processor = new Calculator.Processor<>(handler);

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

        ThriftClient.remoteCall();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method userServiceMethod;

        if (args == null) {
            userServiceMethod = userService.getClass().getMethod(method.getName());
        } else {
            Class<?>[] classes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classes[i] = args[i].getClass();
            }
            userServiceMethod = userService.getClass().getMethod(method.getName(), classes);
        }

        return userServiceMethod.invoke(userService, args);
    }

    public void setThriftInterface(Class<U> thriftInterface) {
        this.thriftInterface = thriftInterface;
    }

    public void setUserService(T userService) {
        this.userService = userService;
    }

    @SuppressWarnings("unchecked")
    private U makeService() throws Exception {
        return (U) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{thriftInterface}, this);
    }

    interface ThermalDemoService {
        void ping();

        int add(Integer num1, Integer num2) throws Exception;
    }

    static class ThermalDemoServiceImpl implements ThermalDemoService {
        @Override
        public void ping() {
            System.out.println("================== Thermal ping");
        }

        @Override
        public int add(Integer num1, Integer num2) throws Exception {
            return num1 + num2;
        }
    }
}
