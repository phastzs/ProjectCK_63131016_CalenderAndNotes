package emphatrubyz.cntt2.calenderandnotes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment implements NoteAdapter.OnNoteListener {

    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private List<Note> noteList;
    private FloatingActionButton fabAddNote;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_notes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy data for testing
        noteList = new ArrayList<>();
        noteList.add(new Note("Title 1", "Content 1"));
        noteList.add(new Note("Title 2", "Content 2"));

        noteAdapter = new NoteAdapter(noteList, this);
        recyclerView.setAdapter(noteAdapter);

        // FloatingActionButton to add new note
        fabAddNote = view.findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEditNoteDialog(null);
            }
        });

        return view;
    }

    private void showAddEditNoteDialog(final Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(note == null ? "Add Note" : "Edit Note");

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_edit_note, null);
        final EditText etNoteTitle = view.findViewById(R.id.et_note_title);
        final EditText etNoteContent = view.findViewById(R.id.et_note_content);

        if (note != null) {
            etNoteTitle.setText(note.getTitle());
            etNoteContent.setText(note.getContent());
        }

        builder.setView(view);
        builder.setPositiveButton(note == null ? "Add" : "Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = etNoteTitle.getText().toString();
                String content = etNoteContent.getText().toString();

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(getContext(), "Title and content cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (note == null) {
                    // Add new note
                    noteList.add(new Note(title, content));
                } else {
                    // Edit existing note
                    note.setTitle(title);
                    note.setContent(content);
                }

                noteAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    public void onNoteClick(int position) {
        // Handle note click
    }

    @Override
    public void onEditClick(int position) {
        Note note = noteList.get(position);
        showAddEditNoteDialog(note);
    }

    @Override
    public void onDeleteClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete Note");
        builder.setMessage("Are you sure you want to delete this note?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                noteList.remove(position);
                noteAdapter.notifyItemRemoved(position);
                noteAdapter.notifyItemRangeChanged(position, noteList.size());
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}
