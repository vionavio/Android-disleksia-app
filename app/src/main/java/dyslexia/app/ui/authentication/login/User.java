package dyslexia.app.ui.authentication.login;

public class User {
    public String id_user;
    public String name;
    public String jk;
    public String tanggaLahir;
    public String email;
    public String password;

    public User(String id_user, String name, String jk, String tanggaLahir,  String email, String password) {
        this.id_user = id_user;
        this.name = name;
        this.jk = jk;
        this.tanggaLahir = tanggaLahir;
        this.email = email;
        this.password = password;
    }
}
