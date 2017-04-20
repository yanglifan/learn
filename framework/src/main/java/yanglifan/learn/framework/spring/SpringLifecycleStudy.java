package yanglifan.learn.framework.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@ComponentScan
@Configuration
public class SpringLifecycleStudy {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(SpringLifecycleStudy.class);
    }

    @Component
    static class UserService {
        private UserDao userDao;

        public UserService() {
            System.out.println("UserService.new");
        }

        @PostConstruct
        public void init() {
            System.out.println("UserService.init");
        }

        @Autowired
        public void setUserDao(UserDao userDao) {
            this.userDao = userDao;
            System.out.println("Set userDao");
        }
    }

    @Component
    static class UserController {
        private UserService userService;

        public UserController() {
            System.out.println("UserController.new");
        }

        @Autowired
        public void setUserService(UserService userService) {
            this.userService = userService;
            System.out.println("Set userService");
        }
    }

    @Component
    static class UserDao {
        private UserController userController;

        public UserDao() {
            System.out.println("UserDao.new");
        }

        @Autowired
        public void setUserController(UserController userController) {
            this.userController = userController;
            System.out.println("Set userController");
        }
    }
}
