package models;

public class RegisterModel {
    private String username;
    private String password;
    private String passwordAgain;
    private String registerError;

    public RegisterModel() {

    }

    public RegisterModel(String username, String registerError) {
        this.username = username;
        this.registerError = registerError;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public String getRegisterError() {
        return registerError;
    }

    public void setRegisterError(String registerError) {
        this.registerError = registerError;
    }
}
