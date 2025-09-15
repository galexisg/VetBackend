-- Tabla de tratamientos aplicados en una cita
CREATE TABLE tratamiento_aplicado (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  cita_id BIGINT NULL,              -- referencia externa (otro equipo); sin FK de momento
  tratamiento_id BIGINT NOT NULL,   -- FK a nuestra tabla tratamiento
  estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE', -- PENDIENTE | ACTIVO | FINALIZADO
  observacion VARCHAR(500),
  fecha_aplicacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_ta_tratamiento FOREIGN KEY (tratamiento_id) REFERENCES tratamiento(id)
);

-- Índices útiles
CREATE INDEX idx_ta_cita ON tratamiento_aplicado (cita_id);
CREATE INDEX idx_ta_tratamiento ON tratamiento_aplicado (tratamiento_id);
CREATE INDEX idx_ta_estado ON tratamiento_aplicado (estado);
