-- database : employee_system

-- DML for users table

-- truncate users;

INSERT INTO `users` (id, email, first_name, last_name, password, role, departement_id, manager_id)
VALUES
(1001, "tom@google.com", "tom", "chen", "123", "USER", 1, 4),
(1002, "lisa@google.com", "lisa", "park", "123", "USER", 2, 4),
(1003, "shohei@google.com", "shohei", "ohtani", "123", "USER", 3, 4),
(1004, "k@google.com", "k", "k", "123", "MANAGER", 3, 0),
(1005, 'user1001@example.com', 'John', 'Doe', '123', 'USER', 1, 4),
(1006, 'user1002@example.com', 'Jane', 'Smith', '123', 'USER', 2, 4),
(1007, 'user1003@example.com', 'Alex', 'Johnson', '123', 'USER', 3, 4),
(1008, 'manager1004@example.com', 'Manager', 'Doe', '123', 'MANAGER', 3, 0);


-- truncate department;

INSERT INTO `department` (id, name)
VALUES
(1, "TECH"),
(2, "PROD"),
(3, "MKT"),
(4, "SALES"),
(5, "DESIGN");


-- truncate vacation;

INSERT INTO `vacation` (id, start_date, end_date, status, type, user_id)
VALUES
(1, "2023-12-01", "2023-12-02", "PENDING", "normal", 1001),
(2, "2024-12-10", "2027-12-11", "PENDING", "normal", 1002),
(3, "2023-12-10", "2023-12-11", "REJECTED", "normal", 1002),
(4, "2023-12-30", "2023-12-31", "APPROVED", "normal", 1003),
(5, "2024-12-01", "2024-12-02", "CANCELLED", "normal", 1004);

-- truncate tickets;

INSERT INTO `tickets` (id, description, status, subject, tag, user_id, assigned_to)
VALUES
(1001, "this is a ticket ...", "PENDING", "subject 1", "tag1", 1001, 1001),
(1002, "this is a small ticket ...", "PENDING", "subject 2", "tag1", 1001, 1003),
(1003, "this is a big ticket ...", "PENDING", "subject 3", "tag1", 1002, 1004);