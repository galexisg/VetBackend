-- Renombra la columna 'observacion' a 'observaciones' manteniendo el tipo
ALTER TABLE tratamiento_aplicado
  CHANGE COLUMN observacion observaciones VARCHAR(500);
