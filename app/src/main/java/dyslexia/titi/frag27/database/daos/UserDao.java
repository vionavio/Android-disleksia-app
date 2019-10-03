package dyslexia.titi.frag27.database.daos;
// Created by Arif Ikhsanudin on 10/3/2019.

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dyslexia.titi.frag27.database.entities.UserEntity;
import dyslexia.titi.frag27.login.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<UserEntity> getAll();

    @Query("SELECT * FROM users WHERE email = :email")
    UserEntity getSingle(String email);

    @Insert
    void insert(UserEntity userEntity);

    @Update
    void update(UserEntity userEntity);

    @Delete
    void delete(UserEntity userEntity);
}
