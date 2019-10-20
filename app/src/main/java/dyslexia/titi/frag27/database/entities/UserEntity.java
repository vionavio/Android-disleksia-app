package dyslexia.titi.frag27.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    public int id_user;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "gender")
    public String gender;

    @ColumnInfo(name = "dob")
    public String dob;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;



    public UserEntity(String name , String gender, String dob, String email, String password) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.password = password;

    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id_user=" + id_user +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +

                "}\n";
    }
}
