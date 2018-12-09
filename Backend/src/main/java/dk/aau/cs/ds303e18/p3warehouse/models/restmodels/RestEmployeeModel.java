package dk.aau.cs.ds303e18.p3warehouse.models.restmodels;

public class RestEmployeeModel extends RestUserModel{
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phonenumber;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        //Check to make sure input is not empty.
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(isContentPresent(email)) this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        if(isContentPresent(phonenumber)) this.phonenumber = phonenumber;
    }

    private boolean isContentPresent(String string){
        return string.length() > 0;
    }
}
