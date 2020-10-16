package adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.Notes;
import com.example.notesapp.R;

public class NotesViewHolder extends RecyclerView.ViewHolder {

    private TextView titletv, descriptiontv;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        titletv = itemView.findViewById(R.id.title);
        descriptiontv = itemView.findViewById(R.id.description);
    }

    public void bind(Notes notes) {
        titletv.setText(notes.getTitle());
        descriptiontv.setText(notes.getDescription());
    }
}
