package dyslexia.app.ui.perbaikanKata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import dyslexia.app.R;

public class WordRepairAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> list;

    public WordRepairAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        mContext = context;
        list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.word_repair_item, parent, false);
        }
        String currentWord = list.get(position);

        TextView text = listItem.findViewById(R.id.tv_text);
        text.setText(currentWord);
        if (position == 0) {
            text.setTextColor(mContext.getResources().getColor(R.color.colorPrimary3));
            text.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary2));
        }

        return listItem;
    }
}
