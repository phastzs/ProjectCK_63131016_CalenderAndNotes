package emphatrubyz.cntt2.calenderandnotes;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList; // Danh sách các ghi chú
    private OnNoteListener onNoteListener; // Listener để xử lý các sự kiện trên ghi chú

    public NoteAdapter(List<Note> noteList, OnNoteListener onNoteListener) {
        this.noteList = noteList;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho từng mục trong RecyclerView
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        // Gắn dữ liệu vào các View trong ViewHolder
        Note note = noteList.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.contentTextView.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng ghi chú trong danh sách
        return noteList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView titleTextView; // TextView để hiển thị tiêu đề ghi chú
        TextView contentTextView; // TextView để hiển thị nội dung ghi chú
        OnNoteListener onNoteListener; // Listener để xử lý các sự kiện trên ghi chú

        public NoteViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tv_note_title);
            contentTextView = itemView.findViewById(R.id.tv_note_content);
            this.onNoteListener = onNoteListener;

            // Thiết lập các sự kiện click và long click cho itemView
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Xử lý sự kiện click vào ghi chú
            onNoteListener.onNoteClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            // Xử lý sự kiện long click vào ghi chú
            showPopup(v);
            return true;
        }

        private void showPopup(View v) {
            // Hiển thị PopupMenu khi long click vào ghi chú
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.menu_note);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // Xử lý các tùy chọn trong PopupMenu
                    switch (item.getItemId()) {
                        case R.id.action_edit:
                            // Chỉnh sửa ghi chú
                            onNoteListener.onEditClick(getAdapterPosition());
                            return true;
                        case R.id.action_delete:
                            // Xóa ghi chú
                            onNoteListener.onDeleteClick(getAdapterPosition());
                            return true;
                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        }
    }

    // Interface để xử lý các sự kiện trên ghi chú
    public interface OnNoteListener {
        void onNoteClick(int position);
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
}
