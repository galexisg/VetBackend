-- V17__create_cita_historial.sql
-- Catálogo de estados de cita + Cita + Historial (con FKs seguras e idempotentes)

-- 1) Catálogo: estado de la cita
CREATE TABLE IF NOT EXISTS cita_estado (
  cita_estado_id TINYINT NOT NULL AUTO_INCREMENT,
  nombre         VARCHAR(40) NOT NULL,
  PRIMARY KEY (cita_estado_id),
  UNIQUE KEY uk_cita_estado_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2) CITA
CREATE TABLE IF NOT EXISTS cita (
  cita_id         BIGINT NOT NULL AUTO_INCREMENT,
  mascota_id      INT NULL,
  usuario_id      INT NULL,
  veterinario_id  INT NULL,
  motivo_id       INT NULL,   -- FK a motivo_cita.id
  cita_estado_id  TINYINT NULL,
  historial_id    BIGINT NULL,
  factura_id      BIGINT NULL,
  consulta_id     BIGINT NULL,
  tipo            ENUM('Normal','Emergencia','Control') DEFAULT 'Normal',
  fecha_hora      DATETIME NOT NULL,
  observaciones   VARCHAR(250) NULL,
  PRIMARY KEY (cita_id),

  KEY idx_cita_mascota     (mascota_id),
  KEY idx_cita_usuario     (usuario_id),
  KEY idx_cita_veterinario (veterinario_id),
  KEY idx_cita_motivo      (motivo_id),
  KEY idx_cita_estado      (cita_estado_id),
  KEY idx_cita_historial   (historial_id),

  CONSTRAINT fk_cita__mascota
    FOREIGN KEY (mascota_id)     REFERENCES mascota(mascota_id),
  CONSTRAINT fk_cita__usuario
    FOREIGN KEY (usuario_id)     REFERENCES usuario(usuario_id),
  CONSTRAINT fk_cita__veterinario
    FOREIGN KEY (veterinario_id) REFERENCES veterinario(veterinario_id),
  CONSTRAINT fk_cita__estado
    FOREIGN KEY (cita_estado_id) REFERENCES cita_estado(cita_estado_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 3) HISTORIAL CLÍNICO
CREATE TABLE IF NOT EXISTS historial (
  historial_id   BIGINT NOT NULL AUTO_INCREMENT,
  mascota_id     INT NOT NULL,
  cita_id        BIGINT NOT NULL,
  veterinario_id INT NOT NULL,
  diagnostico    VARCHAR(500) NOT NULL,
  creado_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (historial_id),

  KEY idx_historial_mascota     (mascota_id),
  KEY idx_historial_cita        (cita_id),
  KEY idx_historial_veterinario (veterinario_id),

  CONSTRAINT fk_historial__mascota
    FOREIGN KEY (mascota_id)     REFERENCES mascota(mascota_id),
  CONSTRAINT fk_historial__cita
    FOREIGN KEY (cita_id)        REFERENCES cita(cita_id),
  CONSTRAINT fk_historial__veterinario
    FOREIGN KEY (veterinario_id) REFERENCES veterinario(veterinario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 4) FK cita -> historial (solo si NO existe; evita duplicados)
SELECT COUNT(*) INTO @fk_hist_exists
FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 'cita'
  AND CONSTRAINT_NAME = 'fk_cita__historial'
  AND CONSTRAINT_TYPE = 'FOREIGN KEY';

SET @sql_hist :=
  IF(@fk_hist_exists = 0,
     'ALTER TABLE cita ADD CONSTRAINT fk_cita__historial FOREIGN KEY (historial_id) REFERENCES historial(historial_id)',
     'SELECT 1');
PREPARE sh FROM @sql_hist; EXECUTE sh; DEALLOCATE PREPARE sh;

-- 5) Alinear tipo y crear FK de cita.motivo_id -> motivo_cita(id) solo si no existe

-- ¿Ya existe el FK?
SELECT COUNT(*) INTO @fk_motivo_exists
FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 'cita'
  AND CONSTRAINT_NAME = 'fk_cita__motivo'
  AND CONSTRAINT_TYPE = 'FOREIGN KEY';

-- Tipo exacto de motivo_cita.id (por ejemplo INT, INT UNSIGNED, etc.)
SELECT COLUMN_TYPE INTO @motivo_pk_type
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 'motivo_cita'
  AND COLUMN_NAME = 'id'
LIMIT 1;

-- Alinear tipo de cita.motivo_id si aún no hay FK
SET @sql_align :=
  IF(@fk_motivo_exists = 0 AND @motivo_pk_type IS NOT NULL,
     CONCAT('ALTER TABLE cita MODIFY motivo_id ', @motivo_pk_type, ' NULL'),
     'SELECT 1');
PREPARE s1 FROM @sql_align; EXECUTE s1; DEALLOCATE PREPARE s1;

-- Crear FK si no existe
SET @sql_fk :=
  IF(@fk_motivo_exists = 0,
     'ALTER TABLE cita ADD CONSTRAINT fk_cita__motivo FOREIGN KEY (motivo_id) REFERENCES motivo_cita(id)',
     'SELECT 1');
PREPARE s2 FROM @sql_fk; EXECUTE s2; DEALLOCATE PREPARE s2;
