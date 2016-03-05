package models;

public class HomeModel {
    private String username;
    private String loginError;

    public HomeModel() {

    }

    public HomeModel(String username, String loginError) {
        this.username = username;
        this.loginError = loginError;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginError() {
        return loginError;
    }

    public void setLoginError(String loginError) {
        this.loginError = loginError;
    }
}
