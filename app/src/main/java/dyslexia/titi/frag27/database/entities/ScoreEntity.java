package dyslexia.titi.frag27.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scores")
public class ScoreEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "quiz_type")
    public String quizType;

    @ColumnInfo(name = "score")
    public double score;

    @ColumnInfo(name = "created_at")
    public String createdAt;

    public ScoreEntity(int userId, String quizType, double score, String createdAt) {
        this.userId = userId;
        this.quizType = quizType;
        this.score = score;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ScoreEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", quizType='" + quizType + '\'' +
                ", score=" + score +
                ", createdAt='" + createdAt + '\'' +
                "}\n";
    }
}
