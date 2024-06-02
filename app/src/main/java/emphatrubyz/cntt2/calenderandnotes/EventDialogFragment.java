package emphatrubyz.cntt2.calenderandnotes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EventDialogFragment extends DialogFragment {

    private static final String ARG_DATE = "date";
    private String date; // Ngày của sự kiện

    // Tạo một instance mới của EventDialogFragment với ngày được chuyển vào
    public static EventDialogFragment newInstance(String date) {
        EventDialogFragment fragment = new EventDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            date = getArguments().getString(ARG_DATE); // Lấy ngày từ các đối số
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_event, null); // Inflate layout cho dialog

        EditText titleEditText = view.findViewById(R.id.eventTitleEditText);
        EditText descriptionEditText = view.findViewById(R.id.eventDescriptionEditText);
        CheckBox completedCheckBox = view.findViewById(R.id.eventCompletedCheckBox);

        builder.setView(view)
                .setTitle("Thêm Sự Kiện")
                .setPositiveButton("Thêm", null) // Thiết lập nút "Thêm"
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss()); // Thiết lập nút "Hủy" và xử lý sự kiện click

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                String title = titleEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                boolean isCompleted = completedCheckBox.isChecked();

                if (title.isEmpty() || description.isEmpty()) {
                    // Hiển thị lỗi nếu người dùng chưa nhập đủ thông tin
                    if (title.isEmpty()) {
                        titleEditText.setError("Tiêu đề không được để trống");
                    }
                    if (description.isEmpty()) {
                        descriptionEditText.setError("Mô tả không được để trống");
                    }
                } else {
                    // Thêm sự kiện vào CalendarFragment
                    CalendarFragment calendarFragment = (CalendarFragment) getTargetFragment();
                    if (calendarFragment != null) {
                        calendarFragment.addEvent(title, description, date, isCompleted);
                    }
                    dialog.dismiss(); // Đóng dialog sau khi thêm sự kiện thành công
                }
            });
        });

        return dialog;
    }
}
