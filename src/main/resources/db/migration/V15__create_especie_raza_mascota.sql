-- V15__create_especie_raza_mascota.sql

-- ======================
-- 1) ESPECIE
-- ======================
CREATE TABLE IF NOT EXISTS especie (
  EspecieId TINYINT NOT NULL AUTO_INCREMENT,
  Nombre     VARCHAR(60) NOT NULL,
  PRIMARY KEY (EspecieId),
  UNIQUE KEY uk_especie_nombre (Nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ======================
-- 2) RAZA
-- ======================
CREATE TABLE IF NOT EXISTS raza (
  RazaId     TINYINT NOT NULL AUTO_INCREMENT,
  EspecieId  TINYINT NOT NULL,
  Nombre     VARCHAR(60) NOT NULL,
  PRIMARY KEY (RazaId),
  KEY idx_raza_especie (EspecieId),
  UNIQUE KEY uk_raza_especie_nombre (EspecieId, Nombre),
  CONSTRAINT fk_raza__especie
    FOREIGN KEY (EspecieId) REFERENCES especie(EspecieId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ======================
-- 3) MASCOTA
-- ======================
CREATE TABLE IF NOT EXISTS mascota (
  mascota_id  INT NOT NULL AUTO_INCREMENT,
  raza_id     TINYINT NOT NULL,
  usuario_id  INT NOT NULL,
  foto        VARCHAR(255) DEFAULT NULL,
  nombre      VARCHAR(80) NOT NULL,
  alergia     ENUM('ninguna','pulgas','alimento','polen','ácaros del polvo','medicamentos')
              DEFAULT 'ninguna',
  nacimiento  DATE DEFAULT NULL,
  sexo        ENUM('m','h') NOT NULL,
  peso        DECIMAL(5,2) DEFAULT NULL,
  activo      BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (mascota_id),

  KEY idx_mascota_raza    (raza_id),
  KEY idx_mascota_usuario (usuario_id),

  CONSTRAINT fk_mascota__raza
    FOREIGN KEY (raza_id)    REFERENCES raza(RazaId),
  CONSTRAINT fk_mascota__usuario
    FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- (Opcional) evita nombres duplicados de mascota por usuario:
-- CREATE UNIQUE INDEX uk_mascota_usuario_nombre ON mascota(usuario_id, nombre);
