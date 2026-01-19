import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookManagement {
    public static void main(String[] args) {
        List<Book> listBook = new ArrayList<>();
        Scanner x = new Scanner(System.in);
        String msg = """
                Chương trình quản lý sách
                1. Thêm 1 cuốn sách
                2. Xóa 1 cuốn sách
                3. Thay đổi sách
                4. Xuất thông tin tất cả các cuốn sách
                5. Tìm sách lập trình
                6. Lấy sách K1 để tìm giá
                7. Tìm kiếm theo tác giả
                0. Thoát
                Chọn chức năng:""";

        int chon = 0;
        do {
            System.out.print(msg);
            chon = Integer.parseInt(x.nextLine());
            switch (chon) {
                case 1 -> {
                    Book newBook = new Book();
                    newBook.input(x);
                    listBook.add(newBook);
                }
                
                case 2 -> {
                    System.out.print("Nhập vào mã sách cần xóa:");
                    int bookId = Integer.parseInt(x.nextLine());
                    // Kiểm tra có sách
                    Book find = listBook.stream().filter(p -> p.getId() == bookId).findFirst().orElseThrow();
                    listBook.remove(find);
                    System.out.print("Đã xóa sách thành công");
                }
                
                case 3 -> {
                    System.out.print("Nhập vào mã sách cần điều chỉnh:");
                    int bookId = Integer.parseInt(x.nextLine());
                    Book find = listBook.stream().filter(p -> p.getId() == bookId).findFirst().orElseThrow();
                    find.input(x);
                }
                
                case 4 -> {
                    System.out.println("\n Xuất tổng tin danh sách:");
                    listBook.forEach(p -> p.output());
                }
                
                case 5 -> {
                    List<Book> list5 = listBook.stream()
                            .filter(p -> p.getTitle().toLowerCase().contains("lập trình"))
                            .toList();
                    list5.forEach(Book::output);
                }
                
                case 6 -> {
                    System.out.print("Nhập số lượng K:");
                    int k = Integer.parseInt(x.nextLine());
                    System.out.print("Nhập giá P:");
                    long p = Long.parseLong(x.nextLine());
                    
                    List<Book> list6 = listBook.stream()
                            .filter(book -> book.getPrice() <= p)
                            .limit(k)
                            .toList();
                    list6.forEach(Book::output);
                }
                
                case 7 -> {
                    System.out.print("Nhập số lượng tác giả:");
                    int n = Integer.parseInt(x.nextLine());               
                    List<String> authors = new ArrayList<>();
                    for (int i = 0; i < n; i++) {
                        System.out.print("Nhập tên tác giả " + (i + 1) + ":");
                        authors.add(x.nextLine().toLowerCase());
                    }
                    
                    List<Book> list7 = listBook.stream()
                            .filter(book -> authors.stream()
                                    .anyMatch(author -> book.getAuthor().toLowerCase().contains(author)))
                            .toList();
                    list7.forEach(Book::output);
                }
            }
        } while (chon != 0);
        
        x.close();
    }
}

