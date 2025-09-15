-- Normaliza datos actuales antes de convertir
UPDATE factura SET estado = UPPER(TRIM(estado)) WHERE estado IS NOT NULL;

-- Mapea valores vacíos o nulos a PENDIENTE
UPDATE factura SET estado = 'PENDIENTE' WHERE estado IS NULL OR estado = '';

ALTER TABLE factura
  MODIFY COLUMN estado ENUM('PENDIENTE','PARCIAL','PAGADA','ANULADA')
  NOT NULL DEFAULT 'PENDIENTE';
