package entity;

public class User {
    private Integer userId;
    private String password;
    private String name;
    private String phoneNumber;
    private String type;

    public User() {
    }

    public User(String password, String name, String phoneNumber, String type) {
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public User(Integer userId, String password, String name, String phoneNumber, String type) {
        this(password, name, phoneNumber, type);
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public User setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getType() {
        return type;
    }

    public User setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
