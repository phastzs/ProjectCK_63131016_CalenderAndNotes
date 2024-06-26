package emphatrubyz.cntt2.calenderandnotes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case  R.id.nav_calendar:
                        selectedFragment = new CalendarFragment(); // Chọn fragment lịch
                        break;
                    case R.id.nav_note:
                        selectedFragment = new NotesFragment(); // Chọn fragment ghi chú
                        break;
                    case R.id.nav_user:
                        selectedFragment = new UserFragment(); // Chọn fragment người dùng
                        break;
                }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                }
                return true;
            }
        });

        // Tải fragment lịch mặc định khi chưa có trạng thái được lưu
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_calendar);
        }
    }
}
