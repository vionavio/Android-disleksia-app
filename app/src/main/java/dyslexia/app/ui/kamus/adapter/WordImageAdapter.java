package dyslexia.app.ui.kamus.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import dyslexia.app.R;
import dyslexia.app.repositories.database.entities.WordEntity;
//import dyslexia.app.ui.kamus.model.Dictionary;

//this adapter have function to connect picture in drawable and show it in listview

public class WordImageAdapter extends BaseAdapter {

    List<WordEntity> list;
    ArrayList<WordEntity> kamusList;
    Context mContext;
    LayoutInflater inflater;
    TextToSpeech textToSpeech;


    public WordImageAdapter(Context context, ArrayList<WordEntity> list) {
        //super(context, R.layout.activity_simbol_huruf, kamusList);
        this.mContext = context;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
        this.kamusList = new ArrayList<>();
        this.kamusList.addAll(list);

    }

    public class ViewHolder{
        ImageView imageView;
        TextView textView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder ;
        if (view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_image, null);
            holder.imageView = view.findViewById(R.id.iv_image);
            holder.textView = view.findViewById(R.id.tv_text);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder)view.getTag();
        }
        final WordEntity currentKamus = list.get(position);

        Resources resources = mContext.getResources();
        final int resourceId = resources.getIdentifier(currentKamus.getWord(),"drawable",mContext.getPackageName());
        holder.imageView.setImageResource(resourceId);

        holder.textView.setText(currentKamus.word);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //you can add your action handling
                Toast.makeText(mContext, " "+currentKamus.word, Toast.LENGTH_SHORT).show();
                textToSpeech.speak(String.valueOf(currentKamus.word), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        textToSpeech = new TextToSpeech(mContext, status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(new Locale("id", "ID"));
            }
        });
        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0){
            list.addAll(kamusList);
        }
        else {
            for (WordEntity strword : kamusList){
                if (strword.word.toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    list.add(strword);
                }
            }
        }
        notifyDataSetChanged();
    }

    protected void onStop()
    {
        if(textToSpeech != null){
            textToSpeech.shutdown();
        }
    }

}
