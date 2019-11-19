package dyslexia.app.ui.permainan.kuis.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dyslexia.app.R;

public class GameLetterAdapter extends RecyclerView.Adapter<GameLetterAdapter.ListViewHolder> {
    private ArrayList<String> letters;

    public GameLetterAdapter(ArrayList<String> letters) {
        this.letters = letters;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_letter_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        String letter = letters.get(position);
        holder.textViewLetter.setText(letter);

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(holder.itemView.getContext(), letter, Toast.LENGTH_SHORT).show();
            letters.remove(letter);
        });
    }

    @Override
    public int getItemCount() {
        return letters.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView textViewLetter;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLetter = itemView.findViewById(R.id.tv_letter);
        }
    }
}
