DROP DATABASE lib;

create DATABASE lib;

use lib;

CREATE TABLE
    `category` (
        `category_id` int NOT NULL AUTO_INCREMENT,
        `name` varchar(250) NOT NULL,
        PRIMARY KEY (`category_id`)
    ) DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;

CREATE TABLE
    `author` (
        `author_id` int NOT NULL AUTO_INCREMENT,
        `name` varchar(250) NOT NULL,
        PRIMARY KEY (`author_id`)
    ) DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;

CREATE TABLE `book` (
    `id` int NOT NULL AUTO_INCREMENT,
    `book_id` VARCHAR(250) NOT NULL,
    `name` varchar(250) NOT NULL,
    `category_id` int NOT NULL,
    `author_id` int DEFAULT NULL,
    `amount` int NOT NULL,
    `create_day` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `is_delete` bit(1) DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY `category_id` (`category_id`),
    KEY `author_id` (`author_id`),
    CONSTRAINT `book_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
    CONSTRAINT `book_ibfk_2` FOREIGN KEY (`author_id`) REFERENCES `author` (`author_id`)
) DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;


CREATE TABLE
    `customer` (
        `customer_id` int NOT NULL AUTO_INCREMENT,
        `name` varchar(250) NOT NULL,
        `email` varchar(250) NOT Null,
        `phone_number` varchar(11) Not null,
        `identity_card` varchar(250) NOT NULL,
        `is_delete` bit(1) DEFAULT 0,
        -- neu =1 thi da xoa, =0 thi chua xoa
        PRIMARY KEY (`customer_id`)
    ) DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;

CREATE TABLE
    `borrow` (
        `borrow_id` int NOT NULL AUTO_INCREMENT,
        `customer_id` int NOT NULL,
        `id` int NOT NULL,
        `start_day` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
        `end_day` timestamp NULL DEFAULT NULL,
        `return_date` TIMESTAMP NULL DEFAULT NULL,
        `status` bit(1) DEFAULT 0,
        PRIMARY KEY (`borrow_id`),
        KEY `customer_id` (`customer_id`),
        KEY `book_id` (`id`),
        CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
        CONSTRAINT `borrow_ibfk_2` FOREIGN KEY (`id`) REFERENCES `book` (`id`)
    ) DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;

CREATE TABLE
    `user` (
        `user_id` int NOT NULL AUTO_INCREMENT,
        `username` varchar(250) NOT NULL,
        `password` varchar(250) NOT NULL,
        PRIMARY KEY (`user_id`)
    ) DEFAULT CHARSET = utf8 COLLATE = utf8_general_ci;

INSERT INTO category (name)
VALUES ('Category 1'), ('Category 2'), ('Category 3'), ('Category 4'), ('Category 5');

INSERT INTO author (name)
VALUES ('Author 1'), ('Author 2'), ('Author 3'), ('Author 4'), ('Author 5');

INSERT INTO
    book (
        book_id,
        name,
        category_id,
        author_id,
        amount
    )
VALUES ('BOO1', 'Book 1', 1, 1, 10), ('BOO2', 'Book 2', 2, 2, 8), ('BOO3', 'Book 3', 3, 3, 15), ('BOO4', 'Book 4', 1, 4, 12), ('BOO5', 'Book 5', 2, 5, 7);

INSERT INTO
    customer (
        name,
        email,
        phone_number,
        identity_card
    )
VALUES (
        'Customer 1',
        'customer1@example.com',
        '1234567891',
        'ID1111'
    ), (
        'Customer 2',
        'customer2@example.com',
        '1234567892',
        'ID2222'
    ), (
        'Customer 3',
        'customer3@example.com',
        '1234567893',
        'ID3333'
    ), (
        'Customer 4',
        'customer4@example.com',
        '1234567894',
        'ID4444'
    ), (
        'Customer 5',
        'customer5@example.com',
        '1234567895',
        'ID5555'
    );

INSERT INTO
    borrow (
        customer_id,
        id,
        start_day,
        end_day
    )
VALUES (
        1,
        1,
        '2023-11-01',
        '2023-11-10'
    ), (
        2,
        1,
        '2023-11-02',
        '2023-11-11'
    ), (
        3,
        1,
        '2023-11-03',
        '2023-11-12'
    ), (
        4,
        4,
        '2023-11-04',
        '2023-11-13'
    ), (
        5,
        5,
        '2023-11-05',
        '2023-11-14'
    );

insert into
    borrow(
        customer_id,
        id,
        start_day,
        end_day
    )
VALUES (
        5,
        2,
        '2023-11-04',
        '2023-11-01'
    );

insert into user VALUES(1,'1','1');

select * from author;

INSERT INTO `author` (`name`)
VALUES ("Author 2") ON DUPLICATE KEY
UPDATE `name` = `name`;

Select author_id from author where author_id = 'Author 1';

SELECT * from author WHERE name = 'author 1' ;

select * from book;

select name from book where author_id = 1 and category_id =1;

select * from category;

SELECT COUNT(*) FROM author WHERE name = '1' ;

update book set is_delete = 0 where id = 1;

SELECT
    c.identity_card,
    b.book_id,
    b.name AS book_name,
    br.start_day,
    br.end_day,
    br.return_date,
    CASE
        WHEN br.status = 0 THEN 'Chưa trả'
        WHEN br.status = 1 THEN 'Đã trả'
        ELSE 'Trạng thái không rõ'
    END AS status
FROM customer c
    JOIN borrow br ON c.customer_id = br.customer_id
    JOIN book b ON br.id = b.id
WHERE
    c.identity_card = 'ID5555';

SELECT * from borrow;

select * from customer;

select * from book;

update borrow
set
    status = 1,
    return_date = Now()
where id = (
        select id
        from book
        where
            book_id = 'BOO2'
            and is_delete = 0
    )
    and customer_id = (
        select customer_id
        from customer
        where
            identity_card = 'ID2222'
    );

SELECT customer_id FROM customer WHERE identity_card = 'ID2222';

select id from book where book_id = 'BOO2';

select * from book;

insert into
    borrow(
        customer_id,
        id,
        start_day,
        end_day
    )
VALUES (
        2,
        1,
        '2023-11-01',
        '2023-11-10'
    );

SELECT
    c.identity_card,
    b.book_id,
    b.name AS book_name,
    br.start_day,
    br.end_day,
    br.return_date,
    CASE
        WHEN br.status = 0 THEN 'Chưa trả'
        WHEN br.status = 1 THEN 'Đã trả'
        ELSE 'Quá hạn'
    END AS status
FROM customer c
    JOIN borrow br ON c.customer_id = br.customer_id
    JOIN book b ON br.id = b.id
WHERE
    b.is_delete = 0
    and c.is_delete = 0;

insert into
    borrow(
        customer_id,
        id,
        start_day,
        end_day
    )
values (
        2,
        2,
        '2023-11-01',
        '2023-11-10'
    );

select * from borrow;

select * from book;

update customer set is_delete = 0 WHERE customer_id = 1;

SELECT
    COUNT(*) AS total_overdue_books
FROM borrow
WHERE
    status = 0
    AND return_date IS NULL
    AND (
        end_day < CURRENT_DATE
        OR (
            end_day = CURRENT_DATE
            AND CURRENT_TIME > '23:59:59'
        )
    );

SELECT * from borrow;

SHOW TRIGGERs;



-- them vai sach qua han vao bang borrow

insert into
    borrow(
        customer_id,
        id,
        start_day,
        end_day
    )
values (
        2,
        2,
        '2023-11-01',
        '2023-11-10'
    );



-- Thêm dữ liệu vào bảng `category`

INSERT INTO `category` (`name`)
VALUES ('Khoa Học Viễn Tưởng'), ('Lãng Mạn'), ('Trinh Thám'), ('Fantasy'), ('Gây Cấn'), ('Tiểu Sử'), ('Lịch Sử'), ('Phát Triển Bản Thân'), ('Nấu Ăn'), ('Thơ');

-- Thêm dữ liệu vào bảng `author`

INSERT INTO `author` (`name`)
VALUES ('Nguyễn Văn A'), ('Trần Thị B'), ('Lê Văn C'), ('Đàm Duy D'), ('Võ Thị E'), ('Nguyễn Văn F'), ('Trần Thị G'), ('Lê Văn H'), ('Nguyễn Thị I'), ('Trần Văn J');

-- Thêm dữ liệu vào bảng `book`

INSERT INTO
    `book` (
        `book_id`,
        `name`,
        `category_id`,
        `author_id`,
        `amount`
    )
VALUES (
        'B001',
        'Máy Du Hành Thời Gian',
        1,
        1,
        50
    ), ('B002', 'Đắc Nhân Tâm', 8, 2, 40), (
        'B003',
        'Mật Mã Da Vinci',
        3,
        3,
        30
    ), (
        'B004',
        'Harry Potter và Hòn Đá Phù Thủy',
        4,
        4,
        60
    ), (
        'B005',
        'Cô Gái Có Hình Rồng',
        5,
        5,
        45
    ), (
        'B006',
        'Nhật Ký Anne Frank',
        6,
        6,
        25
    ), (
        'B007',
        'Nghệ Thuật Chiến Lược',
        7,
        7,
        35
    ), (
        'B008',
        'Làm Thế Nào Để Bạn Bè Thích Bạn',
        8,
        8,
        55
    ), (
        'B009',
        'Niềm Vui Của Việc Nấu Ăn',
        9,
        9,
        20
    ), (
        'B010',
        'Quoth Con Quạ',
        10,
        10,
        15
    );

-- Thêm dữ liệu vào bảng `customer`

INSERT INTO
    `customer` (
        `name`,
        `email`,
        `phone_number`,
        `identity_card`
    )
VALUES (
        'Nguyễn Văn X',
        'nguyenx@email.com',
        '1234567890',
        'ID123456'
    ), (
        'Trần Thị Y',
        'tranY@email.com',
        '9876543210',
        'ID654321'
    ), (
        'Lê Văn Z',
        'leZ@email.com',
        '4567890123',
        'ID789012'
    ), (
        'Đàm Duy K',
        'damK@email.com',
        '7890123456',
        'ID234567'
    ), (
        'Võ Thị L',
        'voL@email.com',
        '3456789012',
        'ID345678'
    ), (
        'Nguyễn Văn M',
        'nguyenM@email.com',
        '2345678901',
        'ID456789'
    ), (
        'Trần Thị N',
        'tranN@email.com',
        '5678901234',
        'ID567890'
    ), (
        'Lê Văn P',
        'leP@email.com',
        '8901234567',
        'ID678901'
    ), (
        'Nguyễn Thị Q',
        'nguyenQ@email.com',
        '0123456789',
        'ID789012'
    ), (
        'Trần Văn S',
        'tranS@email.com',
        '6789012345',
        'ID890123'
    );

-- Thêm dữ liệu vào bảng `borrow`

INSERT INTO
    `borrow` (
        `customer_id`,
        `id`,
        `start_day`,
        `end_day`,
        `return_date`,
        `status`
    )
VALUES (
        1,
        1,
        '2023-01-01',
        '2023-02-01',
        '2023-02-10',
        1
    ), (
        2,
        2,
        '2023-02-01',
        '2023-03-01',
        '2023-03-10',
        1
    ), (
        3,
        3,
        '2023-03-01',
        '2023-04-01',
        NULL,
        0
    ), (
        4,
        4,
        '2023-04-01',
        '2023-05-01',
        NULL,
        0
    ), (
        5,
        5,
        '2023-05-01',
        '2023-06-01',
        '2023-06-10',
        1
    ), (
        6,
        6,
        '2023-06-01',
        '2023-07-01',
        NULL,
        0
    ), (
        7,
        7,
        '2023-07-01',
        '2023-08-01',
        '2023-08-10',
        1
    ), (
        8,
        8,
        '2023-08-01',
        '2023-09-01',
        NULL,
        0
    ), (
        9,
        9,
        '2023-09-01',
        '2023-10-01',
        '2023-10-10',
        1
    ), (
        10,
        10,
        '2023-10-01',
        '2023-11-01',
        NULL,
        0
    );

use lib;

select * from borrow;

UPDATE borrow
SET status = 1, return_date = NOW()
WHERE id IN (SELECT id FROM book WHERE book_id = 3)
AND customer_id IN (SELECT customer_id FROM customer WHERE identity_card = 'ID789012');


select customer_id
from customer
where
    identity_card = 'ID789012';

select * from customer;