package dyslexia.app.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scores")
public class ScoreEntity {
    @PrimaryKey(autoGenerate = true)
    public int id_score;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "quiz_type")
    public String quizType;

    @ColumnInfo(name = "score")
    public Integer score;

    @ColumnInfo(name = "created_at")
    public String createdAt;

    public ScoreEntity(int userId, String quizType, int score, String createdAt) {
        this.userId = userId;
        this.quizType = quizType;
        this.score = score;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ScoreEntity{" +
                "id_score=" + id_score +
                ", userId=" + userId +
                ", quizType='" + quizType + '\'' +
                ", score=" + score +
                ", createdAt='" + createdAt + '\'' +
                "}\n";
    }

    public int getId() {
        return id_score
                ;
    }

    public void setId(int id_score) {
        this.id_score = id_score;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public double getScore() {
        int value = score;
        return value;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
