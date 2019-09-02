package dyslexia.titi.frag27.perbaikanKata;
// Created by Arif Ikhsanudin on 9/3/2019.

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

import dyslexia.titi.frag27.kamus.model.KamusSimilar;

public class KamusAdapter extends ArrayAdapter<KamusSimilar> {
    public KamusAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public KamusAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public KamusAdapter(@NonNull Context context, int resource, @NonNull KamusSimilar[] objects) {
        super(context, resource, objects);
    }

    public KamusAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull KamusSimilar[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public KamusAdapter(@NonNull Context context, int resource, @NonNull List<KamusSimilar> objects) {
        super(context, resource, objects);
    }

    public KamusAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<KamusSimilar> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
