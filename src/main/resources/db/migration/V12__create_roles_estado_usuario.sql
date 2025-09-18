-- V12__create_roles_estado_usuario.sql

-- Tablas base
CREATE TABLE IF NOT EXISTS rol (
    IdRol TINYINT NOT NULL AUTO_INCREMENT,
    Nombre VARCHAR(45) NOT NULL,
    PRIMARY KEY (IdRol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS estado (
    IdEstado TINYINT NOT NULL AUTO_INCREMENT,
    Nombre VARCHAR(45) NOT NULL,
    PRIMARY KEY (IdEstado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla usuario (depende de rol y estado)
CREATE TABLE IF NOT EXISTS usuario (
    UsuarioId INT NOT NULL AUTO_INCREMENT,
    RolId TINYINT NOT NULL,
    EstadoId TINYINT NOT NULL,
    NickName VARCHAR(60) NOT NULL,
    Correo VARCHAR(120) NOT NULL,
    Clave VARCHAR(255) NOT NULL,
    NombreCompleto VARCHAR(120) NOT NULL,
    Dui VARCHAR(20),
    Telefono VARCHAR(32),
    Direccion VARCHAR(200),
    FechaNacimiento DATE,
    PRIMARY KEY (UsuarioId),
    CONSTRAINT fk_usuario_rol
        FOREIGN KEY (RolId) REFERENCES rol(IdRol),
    CONSTRAINT fk_usuario_estado
        FOREIGN KEY (EstadoId) REFERENCES estado(IdEstado),
    INDEX idx_usuario_rol (RolId),
    INDEX idx_usuario_estado (EstadoId),
    UNIQUE KEY uq_usuario_correo (Correo),
    UNIQUE KEY uq_usuario_nickname (NickName)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
