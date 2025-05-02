package app.main.dto;

public class UserLoginDto {

    private String userName;
    private String password;

    // Getterek és setterek

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
