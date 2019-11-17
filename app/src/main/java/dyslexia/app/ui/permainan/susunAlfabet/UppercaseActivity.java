package dyslexia.app.ui.permainan.susunAlfabet;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.flexbox.FlexboxLayout;

import java.util.Random;

import dyslexia.app.R;
import dyslexia.app.ui.permainan.PermainanActivity;

public class UppercaseActivity extends AppCompatActivity {

    private int presCounter = 0;
    private int maxPresCounter = 26;
    private String[] keys = {"A ", "B ", "C ", "D ", "E ", "F ", "G ", "H ", "I "
            , "J ", "K ", "L ", "M ", "N ", "O ", "P ", "Q ", "R "
            , "S ", "T ", "U ", "V ", "W ", "X ", "Y ", "Z "};
    private String textAnswer = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z ";
    TextView textQuestion;
    EditText editText;
    Button lowercase;
    FlexboxLayout flexboxLayout;
    Animation smallbigforth;
    ImageView iv_idea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uppercase);

        editText = findViewById(R.id.editText);
        flexboxLayout = findViewById(R.id.layoutParent);
        iv_idea = findViewById(R.id.idea);
        lowercase = findViewById(R.id.lowercase);

        lowercase.setOnClickListener(view -> {
            startActivity(new Intent(UppercaseActivity.this, LowercaseActivity.class));
        });

        Button reset = findViewById(R.id.ulangi);
        reset.setOnClickListener(view -> {
            editText.setText("");
            doValidateReset();
        });

        iv_idea.setOnClickListener(view ->
                startActivity(new Intent(UppercaseActivity.this, UppercaseAnswerActivity.class)));

        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);

        keys = shuffleArray(keys);

        for (String key : keys) {
            addView((flexboxLayout), key, (editText));
        }

        maxPresCounter = 26;
    }


    private String[] shuffleArray(String[] letter) {
        Random random = new Random();
        for (int i = letter.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            String a = letter[index];
            letter[index] = letter[i];
            letter[i] = a;
        }
        return letter;
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
        textView.setPadding(30, 15, 20, 15);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Arial.ttf");

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
            Toast.makeText(UppercaseActivity.this, "Yeayy, Berhasil", Toast.LENGTH_LONG).show();

//            Intent a = new Intent(MainActivity.this,BossAct.class);
//            startActivity(a);

            //     editText.setText("");
        } else {
            editText.setText("");
            keys = shuffleArray(keys);
            flexboxLayout.removeAllViews();
            for (String key : keys) {
                addView(flexboxLayout, key, editText);
            }
            Toast.makeText(UppercaseActivity.this, "Salah, Ulangi Kembali", Toast.LENGTH_LONG).show();
        }
    }

    private void doValidateReset() {
        presCounter = 0;


        EditText editText = findViewById(R.id.editText);
        FlexboxLayout flexboxLayout = findViewById(R.id.layoutParent);

        if (!(editText.getText().toString().equals(textAnswer))) {
            Toast.makeText(UppercaseActivity.this, "Ulangi", Toast.LENGTH_SHORT).show();
        }

        keys = shuffleArray(keys);
        flexboxLayout.removeAllViews();
        for (String key : keys) {
            addView(flexboxLayout, key, editText);
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), PermainanActivity.class);
        startActivity(intent);
    }
}
