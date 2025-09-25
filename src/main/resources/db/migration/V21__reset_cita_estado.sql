-- 🔍 1. Verificar si existe la FK antes de eliminarla
SET @fk_name := (
  SELECT CONSTRAINT_NAME
  FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'cita'
    AND REFERENCED_TABLE_NAME = 'cita_estado'
    AND COLUMN_NAME = 'cita_estado_id'
  LIMIT 1
);

-- 🔻 2. Eliminar la FK si existe
SET @drop_fk := IF(@fk_name IS NOT NULL,
  CONCAT('ALTER TABLE cita DROP FOREIGN KEY ', @fk_name),
  'SELECT 1');

PREPARE stmt FROM @drop_fk;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 🔄 3. Truncar la tabla de estados
TRUNCATE TABLE cita_estado;

-- 🔁 4. Insertar valores válidos del enum
INSERT INTO cita_estado (nombre) VALUES
  ('PENDIENTE'),
  ('CONFIRMADA'),
  ('CANCELADA'),
  ('COMPLETADA');

-- 🔃 5. Reiniciar el contador de AUTO_INCREMENT
ALTER TABLE cita_estado AUTO_INCREMENT = 1;

-- 🔗 6. Restaurar la FK
ALTER TABLE cita
  ADD CONSTRAINT fk_cita__estado
  FOREIGN KEY (cita_estado_id) REFERENCES cita_estado(cita_estado_id)
  ON DELETE SET NULL
  ON UPDATE CASCADE;
