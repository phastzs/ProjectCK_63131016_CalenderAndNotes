package emphatrubyz.cntt2.calenderandnotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList; // Danh sách các sự kiện
    private FragmentManager fragmentManager; // FragmentManager để quản lý fragment
    private DatabaseHelper databaseHelper; // DatabaseHelper để thao tác với cơ sở dữ liệu

    public EventAdapter(List<Event> eventList, FragmentManager fragmentManager, DatabaseHelper databaseHelper) {
        this.eventList = eventList;
        this.fragmentManager = fragmentManager;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo ViewHolder cho mỗi mục trong RecyclerView
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.eventTitleTextView.setText(event.getTitle());
        holder.eventDescriptionTextView.setText(event.getDescription());
        holder.eventDateTextView.setText(event.getDate());
        holder.eventCompletedCheckBox.setChecked(event.isCompleted());

        holder.eventCompletedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            event.setCompleted(isChecked);
            databaseHelper.updateEvent(event); // Cập nhật trạng thái hoàn thành trong cơ sở dữ liệu
            Toast.makeText(buttonView.getContext(), "Sự kiện " + (isChecked ? "đã hoàn thành" : "chưa hoàn thành"), Toast.LENGTH_SHORT).show();
            if (isChecked) {
                // Thêm sự kiện hoàn thành vào UserFragment
                UserFragment historyFragment = (UserFragment) fragmentManager.findFragmentById(R.id.nav_user);
                if (historyFragment != null) {
                    historyFragment.addCompletedEvent(event);
                }
                // Xóa sự kiện khỏi danh sách hiện tại và cập nhật RecyclerView
                eventList.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView eventTitleTextView; // TextView để hiển thị tiêu đề sự kiện
        TextView eventDescriptionTextView; // TextView để hiển thị mô tả sự kiện
        TextView eventDateTextView; // TextView để hiển thị ngày sự kiện
        CheckBox eventCompletedCheckBox; // CheckBox để xác định trạng thái hoàn thành của sự kiện

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitleTextView = itemView.findViewById(R.id.eventTitleTextView);
            eventDescriptionTextView = itemView.findViewById(R.id.eventDescriptionTextView);
            eventDateTextView = itemView.findViewById(R.id.eventDateTextView);
            eventCompletedCheckBox = itemView.findViewById(R.id.eventCompletedCheckBox);
        }
    }
}
