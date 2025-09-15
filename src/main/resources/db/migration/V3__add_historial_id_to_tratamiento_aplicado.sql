-- Agrega la columna nueva
ALTER TABLE tratamiento_aplicado
  ADD COLUMN historial_id BIGINT NULL;

-- Índice para consultas por historial
CREATE INDEX idx_ta_historial ON tratamiento_aplicado (historial_id);


