-- Asegurar tipos obligatorios
ALTER TABLE diagnostico
  MODIFY COLUMN nombre VARCHAR(150) NOT NULL,
  MODIFY COLUMN descripcion TEXT NOT NULL;

-- =============================
-- Agregar columna cita_id (solo si no existe)
-- =============================
SET @col_exists := (
  SELECT COUNT(*)
  FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'diagnostico'
    AND COLUMN_NAME = 'cita_id'
);
SET @sql := IF(@col_exists = 0,
  'ALTER TABLE diagnostico ADD COLUMN cita_id BIGINT NULL AFTER diagnostico_id',
  'SELECT 1'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- =============================
-- Agregar columna activo (solo si no existe)
-- =============================
SET @col_exists := (
  SELECT COUNT(*)
  FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'diagnostico'
    AND COLUMN_NAME = 'activo'
);
SET @sql := IF(@col_exists = 0,
  'ALTER TABLE diagnostico ADD COLUMN activo TINYINT(1) NOT NULL DEFAULT 1 AFTER descripcion',
  'SELECT 1'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- =============================
-- Agregar columna creado_at (solo si no existe)
-- =============================
SET @col_exists := (
  SELECT COUNT(*)
  FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'diagnostico'
    AND COLUMN_NAME = 'creado_at'
);
SET @sql := IF(@col_exists = 0,
  'ALTER TABLE diagnostico ADD COLUMN creado_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER activo',
  'SELECT 1'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- =============================
-- Crear índice en cita_id (solo si no existe)
-- =============================
SET @idx_exists := (
  SELECT COUNT(*)
  FROM INFORMATION_SCHEMA.STATISTICS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'diagnostico'
    AND INDEX_NAME = 'idx_diagnostico_cita_id'
);
SET @sql := IF(@idx_exists = 0,
  'CREATE INDEX idx_diagnostico_cita_id ON diagnostico (cita_id)',
  'SELECT 1'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- =============================
-- Crear FK hacia cita(cita_id) (solo si no existe)
-- Nota: usa el nombre fk_diagnostico_cita
-- =============================
SET @fk_exists := (
  SELECT COUNT(*)
  FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA = DATABASE()
    AND CONSTRAINT_NAME = 'fk_diagnostico_cita'
);
SET @sql := IF(@fk_exists = 0,
  'ALTER TABLE diagnostico ADD CONSTRAINT fk_diagnostico_cita '
  'FOREIGN KEY (cita_id) REFERENCES cita(cita_id) '
  'ON UPDATE CASCADE ON DELETE RESTRICT',
  'SELECT 1'
);
-- Concatenar las 3 partes del string anterior
SET @sql := CONCAT(
  'ALTER TABLE diagnostico ADD CONSTRAINT fk_diagnostico_cita ',
  'FOREIGN KEY (cita_id) REFERENCES cita(cita_id) ',
  'ON UPDATE CASCADE ON DELETE RESTRICT'
);
-- Solo ejecuta si no existe
SET @sql := IF(@fk_exists = 0, @sql, 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- =============================
-- (Opcional) Cuando TODAS las filas tengan cita_id válido,
-- crea una V25 para forzar NOT NULL. NO lo hagas aquí todavía.
-- =============================
