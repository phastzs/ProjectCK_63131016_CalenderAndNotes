package emphatrubyz.cntt2.calenderandnotes;

public class Event {
    private int id; // ID của sự kiện
    private String title; // Tiêu đề của sự kiện
    private String description; // Mô tả của sự kiện
    private String date; // Ngày của sự kiện
    private boolean isCompleted; // Trạng thái hoàn thành của sự kiện

    // Constructor để khởi tạo một đối tượng Event
    public Event(int id, String title, String description, String date, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.isCompleted = isCompleted;
    }

    // Phương thức getter để lấy ID của sự kiện
    public int getId() {
        return id;
    }

    // Phương thức getter để lấy tiêu đề của sự kiện
    public String getTitle() {
        return title;
    }

    // Phương thức getter để lấy mô tả của sự kiện
    public String getDescription() {
        return description;
    }

    // Phương thức getter để lấy ngày của sự kiện
    public String getDate() {
        return date;
    }

    // Phương thức getter để kiểm tra trạng thái hoàn thành của sự kiện
    public boolean isCompleted() {
        return isCompleted;
    }

    // Phương thức setter để thiết lập trạng thái hoàn thành của sự kiện
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
