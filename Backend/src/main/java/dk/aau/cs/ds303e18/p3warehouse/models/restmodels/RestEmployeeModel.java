package dk.aau.cs.ds303e18.p3warehouse.models.restmodels;

public class RestEmployeeModel extends RestUserModel{

    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
