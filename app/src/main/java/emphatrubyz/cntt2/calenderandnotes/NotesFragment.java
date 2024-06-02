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

        // Dữ liệu mẫu để thử nghiệm
        noteList = new ArrayList<>();
        noteList.add(new Note("Tiêu đề 1", "Nội dung 1"));
        noteList.add(new Note("Tiêu đề 2", "Nội dung 2"));

        noteAdapter = new NoteAdapter(noteList, this);
        recyclerView.setAdapter(noteAdapter);

        // FloatingActionButton để thêm ghi chú mới
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
        builder.setTitle(note == null ? "Thêm ghi chú" : "Chỉnh sửa ghi chú");

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_edit_note, null);
        final EditText etNoteTitle = view.findViewById(R.id.et_note_title);
        final EditText etNoteContent = view.findViewById(R.id.et_note_content);

        if (note != null) {
            etNoteTitle.setText(note.getTitle());
            etNoteContent.setText(note.getContent());
        }

        builder.setView(view);
        builder.setPositiveButton(note == null ? "Thêm" : "Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = etNoteTitle.getText().toString();
                String content = etNoteContent.getText().toString();

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(getContext(), "Tiêu đề và nội dung không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (note == null) {
                    // Thêm ghi chú mới
                    noteList.add(new Note(title, content));
                } else {
                    // Chỉnh sửa ghi chú hiện có
                    note.setTitle(title);
                    note.setContent(content);
                }

                noteAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    @Override
    public void onNoteClick(int position) {
        // Xử lý khi nhấp vào ghi chú
    }

    @Override
    public void onEditClick(int position) {
        Note note = noteList.get(position);
        showAddEditNoteDialog(note);
    }

    @Override
    public void onDeleteClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa ghi chú");
        builder.setMessage("Bạn có chắc muốn xóa ghi chú này không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                noteList.remove(position);
                noteAdapter.notifyItemRemoved(position);
                noteAdapter.notifyItemRangeChanged(position, noteList.size());
            }
        });
        builder.setNegativeButton("Không", null);
        builder.show();
    }
}
