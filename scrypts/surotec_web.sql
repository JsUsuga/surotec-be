DROP database surotec_web;


CREATE database surotec_web;
use surotec_web;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    document_type ENUM('CC', 'TI', 'PAS', 'CE', 'RC', 'NIT', 'PEP')  NOT NULL,   -- Ejemplo: CC, TI, PAS, CEDULA DE EXTRANGERIA (CE), registro civil (RC), NIT, permiso de permanencia(PEP)
	document_number VARCHAR(50) UNIQUE NOT NULL, -- Ejemplo: 123456789
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    username VARCHAR(30) NOT NULL UNIQUE,
	age date,
    date_create datetime,
    date_update datetime,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT UNIQUE,
    date_create datetime,
    date_update datetime,
    status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED', 'GRADUADED') NOT NULL DEFAULT 'ACTIVE',
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
);
CREATE TABLE cohort (
    id INT AUTO_INCREMENT PRIMARY KEY,
    year INT NOT NULL,
    semester ENUM('FIRST', 'SECOND') NOT NULL DEFAULT 'FIRST',  -- según tu SemesterEnum
    description VARCHAR(200),
    date_create DATETIME DEFAULT CURRENT_TIMESTAMP,
    date_update DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT UNIQUE,
    position VARCHAR(100),
    area VARCHAR(100),
    hire_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_user) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE roles (
    id int primary key AUTO_INCREMENT,
    name VARCHAR(50) unique,
    description VARCHAR(200)
);

CREATE TABLE employee_role (
    employee_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (employee_id, role_id),
    FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE news (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    publish_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    images_url VARCHAR(900),
    status ENUM('DRAFT', 'PUBLISHED', 'ARCHIVED') NOT NULL DEFAULT 'DRAFT',
    FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE
);

CREATE TABLE academy_project (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    image_url VARCHAR(255),        -- Imagen principal (una sola)
    caption VARCHAR(200),          -- Texto descriptivo de la imagen
    publish_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('DRAFT','PUBLISHED','ARCHIVED') DEFAULT 'DRAFT',
    FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE
    );

CREATE TABLE donation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(200),
    amount INT NOT NULL,
    id_user INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES user(id) ON DELETE CASCADE
);

-- 6 USERS EMPLEADOS con fechas de nacimiento actualizadas
INSERT INTO user (document_type, document_number, first_name, last_name, age, username, date_create, date_update, password, email, status)
VALUES
('CC','1001','Gabriela','Montilla','1997-05-10','gmontilla','2025-01-01 09:00:00','2025-01-01 09:00:00','pass123','gabriela@example.com','ACTIVE'),
('CC','1002','Esteban','Castaño','2001-08-20','ecastano','2025-01-01 09:10:00','2025-01-01 09:10:00','pass123','esteban@example.com','ACTIVE'),
('CC','1003','Samuel','Ospina','2002-03-15','sospina','2025-01-01 09:20:00','2025-01-01 09:20:00','pass123','samuel.o@example.com','ACTIVE'),
('CC','1004','Sebastian','Usuga','1992-07-12','susuga','2025-01-01 09:30:00','2025-01-01 09:30:00','pass123','sebastian@example.com','ACTIVE'),
('CC','1005','Samuel','Alvarez','1985-11-05','salvarez','2025-01-01 09:40:00','2025-01-01 09:40:00','pass123','samuel.a@example.com','ACTIVE'),
('CC','1006','Lorena','Mejia','1997-02-28','lmejia','2025-01-01 09:50:00','2025-01-01 09:50:00','pass123','lorena@example.com','ACTIVE');

-- 6 EMPLEADOS
INSERT INTO employee (id_user, position, area)
VALUES
(1,'Administrator','Management'),
(2,'News Publisher','Communication'),
(3,'Academic Projects Publisher','Academics'),
(4,'Administrator','Management'),
(5,'News & Academic Publisher','Academics/Communication'),
(6,'Academic Projects Publisher','Academics');

-- 3 ROLES
INSERT INTO roles (name, description)
VALUES
('ADMIN','Administrator role'),
('NEWS_PUBLISHER','Can publish news'),
('ACADEMIC_PROJECT_PUBLISHER','Can publish academic projects');

-- ASIGNACIÓN DE ROLES A EMPLEADOS
INSERT INTO employee_role (employee_id, role_id)
VALUES
(1,1),(1,2),  -- Gabriela
(2,2),        -- Esteban
(3,3),        -- Samuel O.
(4,1),        -- Sebastian
(5,2),(5,3),  -- Samuel A.
(6,3);        -- Lorena

-- 3 ESTUDIANTES (USERS)
INSERT INTO user (document_type, document_number, first_name, last_name, age, username, date_create, date_update, password, email)
VALUES
('CC','2001','Juan','Perez','2002-01-01','jperez','2025-01-01 10:00:00','2025-01-01 10:00:00','pass123','juanp@example.com'),
('CC','2002','Maria','Lopez','2003-02-02','mlopez','2025-01-01 10:05:00','2025-01-01 10:05:00','pass123','marial@example.com'),
('CC','2003','Carlos','Gomez','2001-03-03','cgomez','2025-01-01 10:10:00','2025-01-01 10:10:00','pass123','carlosg@example.com');

-- 3 STUDENT
INSERT INTO student (id_user, date_create, date_update)
VALUES
(7,'2025-01-01 10:00:00','2025-01-01 10:00:00'),
(8,'2025-01-01 10:05:00','2025-01-01 10:05:00'),
(9,'2025-01-01 10:10:00','2025-01-01 10:10:00');

-- 3 COHORTS
INSERT INTO cohort (year, semester, description)
VALUES
(2025,'FIRST','Cohort 2025 First Semester'),
(2025,'SECOND','Cohort 2025 Second Semester'),
(2024,'SECOND','Cohort 2024 Second Semester');

-- 3 NEWS con images_url y caption
INSERT INTO news (employee_id, title, content, images_url, publish_date, status)
VALUES
(1,'Gabriela News 1','Content Gabriela news 1',
'https://github.com/GabrielaMontilla/surotec-cdn/blob/main/news/Emerging-Healthcare-Technology-2-scaled.jpg',
'2025-10-10 21:35:14','PUBLISHED'),

(2,'Esteban News 1','Content Esteban news 1',
'https://github.com/GabrielaMontilla/surotec-cdn/blob/main/news/T3-Tech-News-Banner-Dark-2.jpg',
'2025-10-10 21:35:14','PUBLISHED'),

(5,'Samuel A News 1','Content Samuel A news 1',
'https://github.com/GabrielaMontilla/surotec-cdn/blob/main/news/technologies-page-hero.jpg.jpeg',
'2025-10-10 21:35:14','DRAFT');

-- 3 ACADEMY PROJECTS con image_url y caption
INSERT INTO academy_project (employee_id, title, description, image_url, caption, status)
VALUES
(3,'Samuel O Project 1','Description Samuel O project 1',
'https://github.com/GabrielaMontilla/surotec-cdn/blob/main/news/Emerging-Healthcare-Technology-2-scaled.jpg',
'Emerging Healthcare Technology','PUBLISHED'),

(5,'Samuel A Project 1','Description Samuel A project 1',
'https://github.com/GabrielaMontilla/surotec-cdn/blob/main/news/T3-Tech-News-Banner-Dark-2.jpg',
'T3 Tech News Banner Dark','DRAFT'),

(6,'Lorena Project 1','Description Lorena project 1',
'https://github.com/GabrielaMontilla/surotec-cdn/blob/main/news/technologies-page-hero.jpg.jpeg',
'Technologies Page Hero','PUBLISHED');

-- DONATIONS (asignadas a user)
INSERT INTO donation (description, amount, id_user)
VALUES
('Donation 1',100,7),
('Donation 2',200,8),
('Donation 3',150,9);

-- Ver todos los estudiantes con la información del usuario
SELECT s.id AS student_id, u.first_name, u.last_name, u.username, u.age, s.status AS student_status, s.date_create, s.date_update
FROM student s
JOIN user u ON s.id_user = u.id;

-- Ver todos los empleados con información del usuario
SELECT e.id AS employee_id, u.first_name, u.last_name, u.username, e.position, e.area, e.hire_date
FROM employee e
JOIN user u ON e.id_user = u.id;

-- Ver todos los roles asignados a empleados
SELECT e.id AS employee_id, u.first_name, u.last_name, r.name AS role_name
FROM employee_role er
JOIN employee e ON er.employee_id = e.id
JOIN roles r ON er.role_id = r.id
JOIN user u ON e.id_user = u.id
ORDER BY e.id;

-- Ver todas las noticias con empleado
SELECT n.id AS news_id, n.title, n.content, n.images_url, n.publish_date, n.status, u.first_name, u.last_name
FROM news n
JOIN employee e ON n.employee_id = e.id
JOIN user u ON e.id_user = u.id;

-- Ver todos los proyectos académicos con empleado
SELECT ap.id AS project_id, ap.title, ap.description, ap.image_url, ap.caption, ap.publish_date, ap.status, u.first_name, u.last_name
FROM academy_project ap
JOIN employee e ON ap.employee_id = e.id
JOIN user u ON e.id_user = u.id;

-- Ver todas las donaciones con estudiante (usuario)
SELECT d.id AS donation_id, d.description, d.amount, u.first_name, u.last_name, u.username
FROM donation d
JOIN user u ON d.id_user = u.id;
