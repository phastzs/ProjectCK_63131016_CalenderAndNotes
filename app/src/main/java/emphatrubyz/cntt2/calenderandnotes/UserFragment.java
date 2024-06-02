package emphatrubyz.cntt2.calenderandnotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserFragment extends Fragment {

    private RecyclerView historyRecyclerView;
    private EventAdapter eventAdapter;
    private List<Event> completedEventList;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        historyRecyclerView = view.findViewById(R.id.historyRecyclerView);

        // Khởi tạo cơ sở dữ liệu
        databaseHelper = new DatabaseHelper(getContext());

        // Lấy danh sách sự kiện đã hoàn thành từ cơ sở dữ liệu
        completedEventList = databaseHelper.getCompletedEvents();

        // Thiết lập RecyclerView
        eventAdapter = new EventAdapter(completedEventList, getFragmentManager(), databaseHelper);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        historyRecyclerView.setAdapter(eventAdapter);

        return view;
    }

    public void addCompletedEvent(Event event) {
        completedEventList.add(event);
        eventAdapter.notifyDataSetChanged();
    }
}