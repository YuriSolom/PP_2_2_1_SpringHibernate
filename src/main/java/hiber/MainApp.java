package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        User user1 = new User("Charlie", "Chaplin", "chch@mail.ru");
        User user2 = new User("Anthony", "Hopkins", "anthopk@mail.ru");
        User user3 = new User("Tom", "Hanks", "tomhank@mail.ru");
        User user4 = new User("Scarlett", "Johansson", "scarjoh@mail.ru");
        user1.setUserCar(new Car("Porsche", 911));
        user2.setUserCar(new Car("Audi", 100));
        user3.setUserCar(new Car("BMW", 320));
        user4.setUserCar(new Car("Peugeot", 3008));

        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        userService.add(user4);

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            if (user.getUserCar() != null)
                System.out.println("Car = model: " + user.getUserCar().getModel() +
                        ", series: " + user.getUserCar().getSeries());
            System.out.println();
        }

        List<User> usersWithCar = userService.getUser("Porsche", 911);
        System.out.println("Пользователи с автомобилем Porsche серии 911:");
        for (User user : usersWithCar) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
        }

        List<User> usersWithNonExistCar = userService.getUser("Ford", 777);
        System.out.println("Пользователи с автомобилем Ford серии 777:");
        for (User user : usersWithNonExistCar) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
        }

        context.close();
    }
}
