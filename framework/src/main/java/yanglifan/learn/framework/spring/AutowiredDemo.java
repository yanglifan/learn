package yanglifan.learn.framework.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

interface Service {
    void process();
}

@ComponentScan
@Configuration
public class AutowiredDemo {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AutowiredDemo.class);
        Action action = applicationContext.getBean(Action.class);
        action.handle();
    }
}

@Component
class LocalService implements Service {
    @Override
    public void process() {
        System.out.println("local process");
    }
}

@Component
class RemoteService implements Service {
    @Override
    public void process() {
        System.out.println("remote process");
    }
}

@Component
class Action {

    @Autowired
    @Resource(name = "localService")
    private Service localService1;

    public void handle() {
        localService1.process();
    }
}