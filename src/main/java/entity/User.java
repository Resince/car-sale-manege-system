package entity;

public class User {
    private int userId;
    private String password;
    private String name;
    private String phoneNumber;
    private String type;

    public User(String password, String name, String phoneNumber, String type) {
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public User(int userId, String password, String name, String phoneNumber, String type) {
        this(password,name,phoneNumber,type);
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
