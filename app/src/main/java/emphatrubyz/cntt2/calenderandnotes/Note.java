package emphatrubyz.cntt2.calenderandnotes;

public class Note {
    private String title; // Tiêu đề của ghi chú
    private String content; // Nội dung của ghi chú

    // Constructor mặc định
    public Note() {
    }

    // Constructor với tham số để khởi tạo đối tượng Note
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Phương thức getter để lấy tiêu đề của ghi chú
    public String getTitle() {
        return title;
    }

    // Phương thức setter để thiết lập tiêu đề của ghi chú
    public void setTitle(String title) {
        this.title = title;
    }

    // Phương thức getter để lấy nội dung của ghi chú
    public String getContent() {
        return content;
    }

    // Phương thức setter để thiết lập nội dung của ghi chú
    public void setContent(String content) {
        this.content = content;
    }
}
