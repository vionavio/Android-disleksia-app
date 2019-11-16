package dyslexia.app.ui.permainan.susunAlfabet;

import androidx.appcompat.app.AppCompatActivity;
import dyslexia.app.R;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class LowercaseAnswerActivity extends AppCompatActivity {


    private int[] idChar = {R.id.btnA, R.id.btnB, R.id.btnC, R.id.btnD, R.id.btnE, R.id.btnF, R.id.btnG,
            R.id.btnH, R.id.btnI, R.id.btnJ, R.id.btnK, R.id.btnL, R.id.btnM, R.id.btnN,
            R.id.btnO, R.id.btnP, R.id.btnQ, R.id.btnR, R.id.btnS, R.id.btnT, R.id.btnU,
            R.id.btnV, R.id.btnW, R.id.btnX, R.id.btnY, R.id.btnZ};

    private ImageButton[] btnChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lowercase_answer);

        init();

    }

    private void init() {

        /// ImageButton
        btnChar = new ImageButton[26];
        for(int i=0; i<26; i++){
            btnChar[i] = findViewById(idChar[i]);

            // Set Sound Effect
            // final MediaPlayer mp = MediaPlayer.create(this, idSound[i]);



            // Set Animation for button
            final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

            // Set Click
            btnChar[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Start animation
                    view.startAnimation(animAlpha);

                    // Start sound effect
                    // mp.start();

                }
            });
        }
    }
}
