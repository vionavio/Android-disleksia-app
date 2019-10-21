package dyslexia.app.services;

import android.content.Context;

import java.util.List;

import dyslexia.app.database.AppDatabase;
import dyslexia.app.database.entities.UserEntity;

public class UserService {
    public static List<UserEntity> getUsers(Context context) {
        return AppDatabase.getInstance(context).userDao().getAll();
    }
}
