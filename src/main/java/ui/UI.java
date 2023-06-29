package ui;

import server.Login;

public class UI {
    public static void main(String[] args) {
        Login login = new Login();
        System.out.println(login.login("duanyue", "123456", "yang", "15279031315"));
    }
}
