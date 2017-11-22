package Model;

/**
 * Created by skrud on 2017-10-01.
 */
public class LoginModel {
    private String id;
    private String pw;
    private String email;
    private String name;
    private String reg_date;

    public LoginModel() {
        id = "";
        pw = "";
        email = "";
        name = "";
        reg_date = "";
    }

    public LoginModel(String id) {
        this.id = id;
    }

    public LoginModel(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }

    public LoginModel(String id, String pw, String email, String name) {
        this.id = id;
        this.pw = pw;
        this.email = email;
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }
}