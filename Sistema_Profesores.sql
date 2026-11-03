CREATE DATABASE Sistema_Profesores CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE Sistema_Profesores;

DROP TABLE IF EXISTS tarea;
DROP TABLE IF EXISTS firma;
DROP TABLE IF EXISTS profesor;
DROP TABLE IF EXISTS tipo_profesor;

CREATE TABLE tipo_profesor (
    id_tipo INT AUTO_INCREMENT PRIMARY KEY,
    nombre_tipo VARCHAR(30) NOT NULL UNIQUE,
    limite_firmas INT NULL
);

INSERT INTO tipo_profesor (nombre_tipo, limite_firmas) VALUES
    ('Tiempo completo (1 firma)', 1),
    ('Tiempo completo (2 firmas)', 2),
    ('Asignatura', NULL);

CREATE TABLE profesor (
    id_profesor INT AUTO_INCREMENT PRIMARY KEY,
    id_tipo INT NOT NULL,
    correo_institucional VARCHAR(60) NOT NULL UNIQUE,
    contraseña CHAR(60) NOT NULL,
    FOREIGN KEY (id_tipo) REFERENCES tipo_profesor (id_tipo)
);

CREATE TABLE firma (
    id_firma INT AUTO_INCREMENT PRIMARY KEY,
    id_profesor INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    ubicacion VARCHAR(40) NOT NULL,
    fotografia MEDIUMBLOB NULL,
    FOREIGN KEY (id_profesor) REFERENCES profesor (id_profesor)
);

CREATE TABLE tarea (
    id_tarea INT AUTO_INCREMENT PRIMARY KEY,
    id_profesor INT NOT NULL,
    titulo VARCHAR(50) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    fecha_limite DATETIME NOT NULL,
    prioridad VARCHAR(7) NOT NULL,
    completado BOOLEAN NOT NULL DEFAULT FALSE,
    CHECK (prioridad IN ('urgente', 'alta', 'media', 'baja')),
    FOREIGN KEY (id_profesor) REFERENCES profesor (id_profesor)
);
