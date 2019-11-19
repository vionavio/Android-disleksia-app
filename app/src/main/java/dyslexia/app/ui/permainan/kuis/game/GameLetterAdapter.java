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
    private ArrayList<Character> letters;
    private OnItemClickListener onItemClickListener;

    public GameLetterAdapter(ArrayList<Character> letters, OnItemClickListener onItemClickListener) {
        this.letters = letters;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_letter_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Character letter = letters.get(position);
        holder.textViewLetter.setText(letter.toString());

        holder.itemView.setOnClickListener(v -> {
            onItemClickListener.onClick(letter);
            letters.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, letters.size());
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

    public interface OnItemClickListener {
        public void onClick(Character clickedLetter);
    }

}
