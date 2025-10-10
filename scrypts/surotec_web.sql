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

CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT UNIQUE,
    position VARCHAR(100),
    area VARCHAR(100),
    hire_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_user) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE donation (
    id INT PRIMARY KEY,
    id_user INT UNIQUE,
    description varchar(200),
    amount INT,
    FOREIGN KEY (id_user) REFERENCES user(id) ON DELETE CASCADE
);

-- TABLAS DE ROLES
CREATE TABLE roles (
    id int primary key AUTO_INCREMENT,
    name VARCHAR(50) unique,
    description VARCHAR(200)
);


CREATE TABLE employee_role (
	employee_id INT,
    role_id int,
    primary key (employee_id, role_id),
    FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

INSERT INTO user (
    id, document_type , document_number, first_name, last_name, age, username, date_create, date_update, password, email, status
) VALUES (
    1, 'CC', '482349837' ,  'John', 'Doe','1999-06-01', 'johndoe01', '2024-06-01 10:00:00', '2024-06-01 10:00:00', 'dummyPass123', 'john.doe@example.com', 'ACTIVE'
);

INSERT INTO student (
    id, id_user, date_create, date_update, status
) VALUES (
    1, 1, '2024-06-01 10:00:00', '2024-06-01 10:00:00', 'ACTIVE'
);

select * from student s
join
user u where s.id_user = u.id;
