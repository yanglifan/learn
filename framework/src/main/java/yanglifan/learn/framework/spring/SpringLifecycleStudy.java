package yanglifan.learn.framework.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@ComponentScan
@Configuration
public class SpringLifecycleStudy {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLifecycleStudy.class);

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(SpringLifecycleStudy.class);
    }

    @Component
    static class UserService {
        private UserDao userDao;

        public UserService() {
            LOGGER.info("UserService.new");
        }

        @PostConstruct
        public void init() {
            LOGGER.info("UserService.init");
        }

        @Autowired
        public void setUserDao(UserDao userDao) {
            this.userDao = userDao;
            LOGGER.info("Set userDao into " + this.getClass().getSimpleName());
        }
    }

    @Component
    static class UserController {
        private UserService userService;

        public UserController() {
            LOGGER.info("UserController.new");
        }

        public UserService getUserService() {
            return userService;
        }

        @Autowired
        public void setUserService(UserService userService) {
            this.userService = userService;
            LOGGER.info("Set userService into " + this.getClass().getSimpleName());
        }
    }

    @Component
    static class UserDao {
        private UserController userController;

        public UserDao() {
            LOGGER.info("UserDao.new");
        }

        @PostConstruct
        public void init() {
            LOGGER.info(getClass().getSimpleName() + ".init");
            LOGGER.info("userService in userController is {}", userController.getUserService());
        }

        @Autowired
        public void setUserController(UserController userController) {
            this.userController = userController;
            LOGGER.info("Set userController into " + this.getClass().getSimpleName());
        }
    }
}
