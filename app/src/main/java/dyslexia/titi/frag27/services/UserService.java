package dyslexia.titi.frag27.services;

import android.content.Context;

import java.util.List;

import dyslexia.titi.frag27.database.AppDatabase;
import dyslexia.titi.frag27.database.entities.UserEntity;

public class UserService {
    public static List<UserEntity> getUsers(Context context) {
        return AppDatabase.getInstance(context).userDao().getAll();
    }
}
