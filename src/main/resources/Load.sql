
INSERT INTO appuser(first_name,last_name,email,password,is_enabled) VALUES('test1','t1','t1@gmail.com','$2a$11$EdZ6tza/mLvufk9SUeJYKOSSICq7E1IjTBWVxeW2818PlHwju7g6.',true);
INSERT INTO appuser(first_name,last_name,email,password,is_enabled) VALUES('test2','t2','t3@gmail.com','$2a$11$drYTDn4eCQD0gJzg8wkgquF6.WQ9pu9RQioqTA03b6XeLW/r/jgf2',true);
INSERT INTO appuser(first_name,last_name,email,password,is_enabled) VALUES('test3','t3','t3@gmail.com','$2a$11$kH4h3lrhU5XmAfly19BXhuL1bFAHkH3KmR2oHoQUX7mQ15jGAS1Iy',true);

INSERT INTO Project (name, description, created_by, created_date, updated_by, update_date)
VALUES
('Project A', 'Description for Project A', 1, '2023-10-15', 1, '2023-10-15'),
('Project B', 'Description for Project B', 2, '2023-10-16', 2, '2023-10-16');

INSERT INTO user_project (user_id, project_id)
VALUES
(1, 1),
(2, 2);

INSERT INTO Task (title, description, due_date, status, priority, created_by, created_date, updated_by, update_date, assigned_to, project_id)
VALUES
('Task 1', 'Description for Task 1', '2023-12-31', 'OPEN', 'LOW', 1, '2023-10-10', NULL, NULL, 1, 1),
('Task 2', 'Description for Task 2', '2024-01-15', 'OPEN', 'HIGH', 2, '2023-10-11', NULL, NULL, 2, 2);