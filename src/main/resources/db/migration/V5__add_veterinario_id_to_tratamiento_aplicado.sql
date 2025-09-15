ALTER TABLE tratamiento_aplicado
  ADD COLUMN veterinario_id BIGINT NULL;

CREATE INDEX idx_ta_veterinario ON tratamiento_aplicado (veterinario_id);
