# Bài tập 1.2.2.2: Quản Lý Sách - Theo Hướng Dẫn

## ✅ Đã hoàn thành theo đúng hướng dẫn trong hình

### Các thay đổi chính:

#### 1. Class Book
- ✅ Thuộc tính `price` đổi từ `double` → `long` (theo đề bài)
- ✅ Hàm `output()` format: `"BOOK: id= %d, title=%s, author=%s, price=%d***"`
- ✅ Có đầy đủ: Constructor, Getter, Setter (có thể dùng Tool để Generation)
- ✅ Hàm `input()` sử dụng Scanner để nhập liệu
- ✅ Hàm `output()` sử dụng String.format() để xuất thông tin

#### 2. Class BookManagement
- ✅ Menu đơn giản, rõ ràng với 7 chức năng + thoát
- ✅ Sử dụng **switch expression** (Java 14+)
- ✅ Sử dụng **Lambda Expressions** trong các phương thức Stream
- ✅ Sử dụng **Method Reference** (Book::output)

#### 3. Các chức năng theo hướng dẫn:

**Case 1**: Thêm sách
```java
Book newBook = new Book();
newBook.input();
listBook.add(newBook);
```

**Case 2**: Xóa sách (Stream + filter + findFirst + orElseThrow)
```java
Book find = listBook.stream()
    .filter(p -> p.getId() == bookId)
    .findFirst()
    .orElseThrow();
listBook.remove(find);
```

**Case 3**: Thay đổi sách (Stream + filter + input lại)
```java
Book find = listBook.stream()
    .filter(p -> p.getId() == bookId)
    .findFirst()
    .orElseThrow();
find.input();
```

**Case 4**: Xuất tất cả sách (forEach)
```java
listBook.forEach(p -> p.output());
```

**Case 5**: Tìm sách "lập trình" (filter + toLowerCase + contains)
```java
List<Book> list5 = listBook.stream()
    .filter(p -> p.getTitle().toLowerCase().contains("lập trình"))
    .toList();
list5.forEach(Book::output);
```

**Case 6**: Lấy K sách có giá <= P (filter + limit)
```java
List<Book> list6 = listBook.stream()
    .filter(book -> book.getPrice() <= p)
    .limit(k)
    .toList();
list6.forEach(Book::output);
```

**Case 7**: Tìm sách theo danh sách tác giả (filter + anyMatch)
```java
List<Book> list7 = listBook.stream()
    .filter(book -> authors.stream()
        .anyMatch(author -> book.getAuthor().toLowerCase().contains(author)))
    .toList();
list7.forEach(Book::output);
```

## Cách chạy chương trình:

```bash
# Biên dịch
javac Book.java BookManagement.java

# Chạy
java BookManagement
```

## Ví dụ test data:

```
Sách 1:
ID: 1
Title: Lập trình Java cơ bản
Author: Nguyễn Văn A
Price: 150000

Sách 2:
ID: 2
Title: Cơ sở dữ liệu
Author: Trần Văn B
Price: 200000

Sách 3:
ID: 3
Title: Lập trình Python nâng cao
Author: Nguyễn Văn A
Price: 180000
```

## Kiến thức đã áp dụng:
✅ Stream API
✅ Lambda Expressions
✅ Method Reference
✅ Switch Expression
✅ Filter, Limit, anyMatch
✅ forEach
✅ orElseThrow()

---
**Ghi chú**: Code đã được viết theo đúng hướng dẫn trong các hình ảnh đề bài.
