package emphatrubyz.cntt2.calenderandnotes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "events.db"; // Tên của cơ sở dữ liệu
    private static final int DATABASE_VERSION = 1; // Phiên bản của cơ sở dữ liệu

    private static final String TABLE_EVENTS = "events"; // Tên của bảng sự kiện
    private static final String COLUMN_ID = "id"; // Tên cột ID
    private static final String COLUMN_TITLE = "title"; // Tên cột tiêu đề
    private static final String COLUMN_DESCRIPTION = "description"; // Tên cột mô tả
    private static final String COLUMN_DATE = "date"; // Tên cột ngày
    private static final String COLUMN_IS_COMPLETED = "is_completed"; // Tên cột trạng thái hoàn thành

    // Constructor của DatabaseHelper
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng events trong cơ sở dữ liệu
        String createTable = "CREATE TABLE " + TABLE_EVENTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_IS_COMPLETED + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại và tạo lại bảng mới
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    // Thêm sự kiện vào cơ sở dữ liệu
    public void addEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, event.getTitle());
        values.put(COLUMN_DESCRIPTION, event.getDescription());
        values.put(COLUMN_DATE, event.getDate());
        values.put(COLUMN_IS_COMPLETED, event.isCompleted() ? 1 : 0);
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }

    // Lấy danh sách tất cả các sự kiện từ cơ sở dữ liệu
    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                @SuppressLint("Range") boolean isCompleted = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_COMPLETED)) == 1;
                eventList.add(new Event(id, title, description, date, isCompleted));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return eventList;
    }

    // Lấy danh sách các sự kiện đã hoàn thành từ cơ sở dữ liệu
    public List<Event> getCompletedEvents() {
        List<Event> eventList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_IS_COMPLETED + " = 1", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                @SuppressLint("Range") boolean isCompleted = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_COMPLETED)) == 1;
                eventList.add(new Event(id, title, description, date, isCompleted));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return eventList;
    }

    // Cập nhật sự kiện trong cơ sở dữ liệu
    public void updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, event.getTitle());
        values.put(COLUMN_DESCRIPTION, event.getDescription());
        values.put(COLUMN_DATE, event.getDate());
        values.put(COLUMN_IS_COMPLETED, event.isCompleted() ? 1 : 0);
        db.update(TABLE_EVENTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(event.getId())});
        db.close();
    }
}
