package dk.aau.cs.ds303e18.p3warehouse.models.restmodels;

public class RestEmployeeModel extends RestUserModel{
    private String username;
    private String password;
    private String nickname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        isContentPresent(username);
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        if(isContentPresent(nickname)) this.nickname = nickname;
    }

    private boolean isContentPresent(String string){
        return string.length() > 0;
    }
}
