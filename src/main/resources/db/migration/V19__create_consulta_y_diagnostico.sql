-- V19__create_consulta_y_diagnostico.sql
-- Crea diagnostico, consulta, consulta_diagnostico
-- y agrega FK cita.consulta_id -> consulta.consulta_id (idempotente y segura)

-- =====================================
-- Tabla: diagnostico
-- =====================================
CREATE TABLE IF NOT EXISTS diagnostico (
    diagnostico_id BIGINT NOT NULL AUTO_INCREMENT,
    nombre         VARCHAR(225) NOT NULL,
    descripcion    TEXT NULL,
    PRIMARY KEY (diagnostico_id),
    UNIQUE KEY uk_diagnostico_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- =====================================
-- Tabla: consulta
-- =====================================
CREATE TABLE IF NOT EXISTS consulta (
    consulta_id     BIGINT NOT NULL AUTO_INCREMENT,
    historial_id    BIGINT NOT NULL,
    observaciones   TEXT NULL,
    recomendaciones TEXT NULL,
    PRIMARY KEY (consulta_id),
    KEY idx_consulta_historial (historial_id),
    CONSTRAINT fk_consulta_historial
        FOREIGN KEY (historial_id) REFERENCES historial(historial_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- =====================================
-- Tabla: consulta_diagnostico (N:M)
-- =====================================
CREATE TABLE IF NOT EXISTS consulta_diagnostico (
    id             BIGINT NOT NULL AUTO_INCREMENT,
    consulta_id    BIGINT NOT NULL,
    diagnostico_id BIGINT NOT NULL,
    observacion    TEXT NULL,
    PRIMARY KEY (id),
    KEY idx_cd_consulta (consulta_id),
    KEY idx_cd_diagnostico (diagnostico_id),
    CONSTRAINT fk_cd_consulta
        FOREIGN KEY (consulta_id)    REFERENCES consulta(consulta_id),
    CONSTRAINT fk_cd_diagnostico
        FOREIGN KEY (diagnostico_id) REFERENCES diagnostico(diagnostico_id),
    -- Evita diagnósticos duplicados por consulta
    UNIQUE KEY uk_cd_consulta_diag (consulta_id, diagnostico_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- =====================================
-- Alinear tipo de cita.consulta_id y limpiar datos huérfanos
-- =====================================
-- Asegura que cita.consulta_id sea BIGINT NULL (coincide con consulta.consulta_id)
ALTER TABLE cita MODIFY consulta_id BIGINT NULL;

-- Si hay valores en cita.consulta_id que no existen en consulta, pásalos a NULL
UPDATE cita c
LEFT JOIN consulta co ON co.consulta_id = c.consulta_id
SET c.consulta_id = NULL
WHERE c.consulta_id IS NOT NULL
  AND co.consulta_id IS NULL;

-- =====================================
-- Agregar FK en cita.consulta_id -> consulta.consulta_id (solo si no existe)
-- =====================================
SELECT COUNT(*) INTO @fk_cita_consulta_exists
FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 'cita'
  AND CONSTRAINT_NAME = 'fk_cita__consulta'
  AND CONSTRAINT_TYPE = 'FOREIGN KEY';

SET @sql_fk_cita_consulta :=
  IF(@fk_cita_consulta_exists = 0,
     'ALTER TABLE cita ADD CONSTRAINT fk_cita__consulta
        FOREIGN KEY (consulta_id)
        REFERENCES consulta(consulta_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE',
     'SELECT 1');

PREPARE stmt FROM @sql_fk_cita_consulta;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- (Opcional) Índice explícito. MySQL crea uno al añadir la FK, pero si lo prefieres con nombre propio:
-- CREATE INDEX idx_cita_consulta ON cita(consulta_id);
