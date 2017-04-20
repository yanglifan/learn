package yanglifan.learn.core;

interface Handler {
    void call(Service service);

    void handle();
}

public class Service {
    void handle(HandlerA handlerA) {
        
    }

    void handle(HandlerB handlerB) {

    }

    public void execute(Handler handler) {
        handler.call(this);
    }
}

class HandlerA implements Handler {
    @Override
    public void handle() {
        System.out.println("Handle by A");
    }

    @Override
    public void call(Service service) {
        service.handle(this);
    }
}

class HandlerB implements Handler {
    @Override
    public void handle() {
        System.out.println("Handle by B");
    }

    @Override
    public void call(Service service) {
        service.handle(this);
    }
}