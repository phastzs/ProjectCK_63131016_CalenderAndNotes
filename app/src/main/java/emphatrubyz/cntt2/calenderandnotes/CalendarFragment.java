package emphatrubyz.cntt2.calenderandnotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CalendarFragment extends Fragment {

    private CalendarView calendarView; // View lịch
    private RecyclerView eventRecyclerView; // RecyclerView cho danh sách sự kiện
    private EventAdapter eventAdapter; // Adapter cho RecyclerView
    private List<Event> eventList; // Danh sách sự kiện
    private DatabaseHelper databaseHelper; // DatabaseHelper để thao tác với cơ sở dữ liệu

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Ánh xạ các thành phần từ layout
        calendarView = view.findViewById(R.id.calendarView);
        eventRecyclerView = view.findViewById(R.id.eventRecyclerView);

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(getContext());

        // Lấy danh sách sự kiện từ cơ sở dữ liệu
        eventList = databaseHelper.getAllEvents();

        // Thiết lập RecyclerView và Adapter
        eventAdapter = new EventAdapter(eventList, getFragmentManager(), databaseHelper);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventRecyclerView.setAdapter(eventAdapter);

        // Lắng nghe sự kiện khi ngày được chọn trên lịch
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Hiển thị thông báo khi ngày được chọn
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(getContext(), "Ngày được chọn: " + selectedDate, Toast.LENGTH_SHORT).show();

                // Hiển thị EventDialogFragment để tạo sự kiện mới
                EventDialogFragment eventDialogFragment = EventDialogFragment.newInstance(selectedDate);
                eventDialogFragment.setTargetFragment(CalendarFragment.this, 1);
                eventDialogFragment.show(getFragmentManager(), "EventDialogFragment");
            }
        });

        return view;
    }

    // Phương thức để thêm sự kiện mới vào danh sách và cập nhật RecyclerView
    public void addEvent(String title, String description, String date, boolean isCompleted) {
        Event newEvent = new Event(0, title, description, date, isCompleted);
        databaseHelper.addEvent(newEvent); // Thêm sự kiện mới vào cơ sở dữ liệu
        eventList.clear();
        eventList.addAll(databaseHelper.getAllEvents()); // Lấy lại danh sách sự kiện từ cơ sở dữ liệu
        eventAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
    }
}
