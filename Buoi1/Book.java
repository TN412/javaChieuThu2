import java.util.Scanner;

public class Book {
    private int id;
    private String title;
    private String author;
    private long price;

    // Constructor mặc định
    public Book() {
    }

    // Constructor đầy đủ tham số
    public Book(int id, String title, String author, long price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public long getPrice() {
        return price;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    // Hàm nhập thông tin sách
    public void input(Scanner x) {
        System.out.print("Nhập mã sách: ");
        this.id = Integer.parseInt(x.nextLine());
        System.out.print("Nhập tên sách: ");
        this.title = x.nextLine();
        System.out.print("Nhập Tác giả: ");
        this.author = x.nextLine();
        System.out.print("Nhập Đơn giá: ");
        this.price = Long.parseLong(x.nextLine());
    }

    // Hàm xuất thông tin sách
    public void output() {
        String msg = String.format("BOOK: id= %d, title=%s, author=%s, price=%d***", id, title, author, price);
        System.out.println(msg);
    }

    @Override
    public String toString() {
        return String.format("Book{id=%d, title='%s', author='%s', price=%d}", id, title, author, price);
    }
}
