# Mô tả dự án

## 1. Bài toán

- Về phía người dùng:
    - Xây dựng ứng dụng quản lý ghi chú cá nhân, cho phép người dùng tạo, sửa, xóa ghi chú.
    - Quản lý ghi chú người dùng tập trung, hướng tại Portable App, nghĩa là người dùng có thể sử dụng ứng dụng mà không
      cần cài đặt.
- Về phía phát triển:
    - Học tập, tổng kết kiến thức đã học.
    - Tìm hiểu nghiên cứu thêm về JavaFX, Spring Data, Spring Boot.

## 2. Giải pháp

### 2.1. Công nghệ sử dụng

- Nhóm quyết định sử dụng JavaFX để xây dựng giao diện ứng dụng vì:
    - JavaFX là một nền tảng phát triển ứng dụng đa nền tảng, hỗ trợ xây dựng ứng dụng giao diện người dùng (GUI) hiện
      đại, đẹp mắt.
    - JavaFX hỗ trợ CSS, FXML, Scene Builder, hỗ trợ tạo giao diện một cách dễ dàng.
    - Tuy nhiên, có một số nhược điểm như:
        - Phức tạp hơn so với một số framework.
        - Các framwork hỗ trợ ứng dụng deskop cho JavaFX không nhiều.

- Nhóm quyết định sử dụng Spring Data JPA vì:
    - Spring Data JPA là một phần của Spring Data, giúp giảm thiểu việc lập trình, đặc biệt là việc lập trình với cơ sở
      dữ liệu.
    - Trong Spring Data JPA, nhóm sử dụng dụng Hibernate ORM để tương tác với cơ sở dữ liệu.

- Nhóm quyết định sử dụng Spring Boot vì:
    - Sử dụng kỹ thuật Dependency Injection của Spring Framework.

### 2.2. Cấu trúc dự án


