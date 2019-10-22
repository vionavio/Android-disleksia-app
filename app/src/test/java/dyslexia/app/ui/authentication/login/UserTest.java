package dyslexia.app.ui.authentication.login;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
// Created by Arif Ikhsanudin on 9/2/2019.

public class UserTest {

    private User user;
    private String id = "1";
    private String name = "nama";
    private String jk = "l";
    private String tanggalLahir = "12122012";
    private String userName = "username";
    private String email = "a@a.com";
    private String password = "123456";

    @Before
    public void setUp() throws Exception {
        user = new User(id, name, jk, tanggalLahir, userName, email, password);
    }

    @Test
    public void isNotNull() {
        assertNotNull(user);
    }
}
