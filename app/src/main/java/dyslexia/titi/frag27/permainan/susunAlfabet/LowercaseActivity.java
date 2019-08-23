package dyslexia.titi.frag27.permainan.susunAlfabet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import java.util.Random;

import dyslexia.titi.frag27.R;

public class LowercaseActivity extends AppCompatActivity {

    private int presCounter = 0;
    private int maxPresCounter = 26;
    private String[] keys = {"a", "b", "c", "d", "e", "f", "g", "h", "i"
            , "j", "k", "l", "m", "n", "o", "p", "q", "r"
            , "s", "t", "u", "v", "w", "x", "y", "z"};
    private String textAnswer = "abcdefghijklmnopqrstuvwxyz";
    TextView textQuestion, textTitle;
    Animation smallbigforth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lowercase);

        Button uppercase = findViewById(R.id.uppercase);
        uppercase.setOnClickListener(view -> {
            Intent uppercaseIntent = new Intent(LowercaseActivity.this, SusunAlfabet.class);
            startActivity(uppercaseIntent);
        });

        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);

        keys = shuffleArray(keys);

        for (String key : keys) {
            addView((findViewById(R.id.layoutParent)), key, (findViewById(R.id.editText)));
        }
        maxPresCounter = 26;
    }


    private String[] shuffleArray(String[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }


    private void addView(FlexboxLayout viewParent, final String text, final EditText editText) {
        FlexboxLayout.LayoutParams flexboxLayoutParams = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
        );

        flexboxLayoutParams.rightMargin = 15;
        flexboxLayoutParams.leftMargin = 15;
        flexboxLayoutParams.topMargin = 10;
        flexboxLayoutParams.bottomMargin = 10;

        final TextView textView = new TextView(this);

        textView.setLayoutParams(flexboxLayoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        textView.setTextColor(this.getResources().getColor(R.color.colorPurple));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(32);
        textView.setPadding(40, 20, 40, 20);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOneRegular.ttf");

        textQuestion = (TextView) findViewById(R.id.textQuestion);

        textQuestion.setTypeface(typeface);
        editText.setTypeface(typeface);
        textView.setTypeface(typeface);

        textView.setOnClickListener(v -> {
            if (presCounter < maxPresCounter) {
                if (presCounter == 0)
                    editText.setText("");

                editText.setText(editText.getText().toString() + text);
                textView.startAnimation(smallbigforth);
                textView.animate().alpha(0).setDuration(300);
                presCounter++;

                if (presCounter == maxPresCounter)
                    doValidate();
            }
        });

        viewParent.addView(textView);
    }


    private void doValidate() {
        presCounter = 0;

        EditText editText = findViewById(R.id.editText);
        FlexboxLayout flexboxLayout = findViewById(R.id.layoutParent);

        if (editText.getText().toString().equals(textAnswer)) {
            Toast.makeText(LowercaseActivity.this, "Correct", Toast.LENGTH_SHORT).show();

//            Intent a = new Intent(MainActivity.this, BossAct.class);
//            startActivity(a);

            //   editText.setText("");
        } else {
            Toast.makeText(LowercaseActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
            //   editText.setText("");
        }

        keys = shuffleArray(keys);
        flexboxLayout.removeAllViews();
        for (String key : keys) {
            addView(flexboxLayout, key, editText);
        }

    }


}
