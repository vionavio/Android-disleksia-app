package dyslexia.app.repositories.database.prepopulates;

import dyslexia.app.repositories.database.entities.UserEntity;

public class UserEntityPopulate {
    private static final UserEntity USER = new UserEntity(
            "aaaaaa",
            "laki",
            "12-12-2012",
            "a@a.a",
            "aaaaaa"
    );

    public static UserEntity getUser() {
        return USER;
    }
}
