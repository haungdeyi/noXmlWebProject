package domain;

public class User {
    private Long id;
    private String usrename;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsrename() {
        return usrename;
    }

    public void setUsrename(String usrename) {
        this.usrename = usrename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
