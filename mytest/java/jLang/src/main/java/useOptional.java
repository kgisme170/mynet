import java.util.Optional;

public class useOptional {
    public static class User{
        private String name = "name";
        private int age = 20;
        String getUser(){return name;}
    }
    public static void f(Optional<User> u){
        u.map(User::getUser).map(name->name.toUpperCase()).ifPresent(System.out::println);
    }
    public static void main(String [] args){
        User u = new User();
        useOptional.f(Optional.of(u));
        useOptional.f(Optional.ofNullable(null));
    }
}
