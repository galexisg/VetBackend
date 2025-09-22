-- V25__update_servicio_table_for_enum.sql
-- Actualiza la tabla servicio para usar columna 'estado' en vez de 'activo'
-- Idempotente: solo actúa si falta/sobra la columna

-- 1) Borrar 'activo' si existe
SET @col := (
  SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'servicio'
    AND COLUMN_NAME = 'activo'
);
SET @sql := IF(@col > 0,
  'ALTER TABLE servicio DROP COLUMN activo',
  'SELECT 1'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 2) Agregar 'estado' si NO existe
SET @col := (
  SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'servicio'
    AND COLUMN_NAME = 'estado'
);
SET @sql := IF(@col = 0,
  'ALTER TABLE servicio ADD COLUMN estado VARCHAR(50) NOT NULL DEFAULT ''ACTIVO''',
  'SELECT 1'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 3) Normalizar posibles NULL (por si existieran)
UPDATE servicio SET estado = 'ACTIVO' WHERE estado IS NULL;
