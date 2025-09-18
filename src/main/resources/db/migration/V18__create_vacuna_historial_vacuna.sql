-- V18__create_vacuna_historial_vacuna.sql
-- Catálogo de vacunas + historial de vacunas

-- 1) Tabla vacuna
CREATE TABLE IF NOT EXISTS vacuna (
  vacuna_id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(80) NOT NULL,
  PRIMARY KEY (vacuna_id),
  UNIQUE KEY uk_vacuna_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2) Tabla historial_vacuna
CREATE TABLE IF NOT EXISTS historial_vacuna (
  historial_vacuna_id INT NOT NULL AUTO_INCREMENT,
  fecha DATE NOT NULL,
  lote_medicamento_id INT DEFAULT NULL,
  mascota_id INT NOT NULL,
  medicamento_id INT DEFAULT NULL,
  observacion VARCHAR(200) DEFAULT NULL,
  veterinario_id INT NOT NULL,
  vacuna_id INT NOT NULL,
  PRIMARY KEY (historial_vacuna_id),

  KEY idx_histvac_vacuna (vacuna_id),
  KEY idx_histvac_mascota (mascota_id),
  KEY idx_histvac_veterinario (veterinario_id),
  KEY idx_histvac_medicamento (medicamento_id),
  KEY idx_histvac_lote (lote_medicamento_id),

  CONSTRAINT fk_histvac__vacuna
    FOREIGN KEY (vacuna_id) REFERENCES vacuna (vacuna_id),
  CONSTRAINT fk_histvac__mascota
    FOREIGN KEY (mascota_id) REFERENCES mascota (mascota_id),
  CONSTRAINT fk_histvac__veterinario
    FOREIGN KEY (veterinario_id) REFERENCES veterinario (veterinario_id),
  CONSTRAINT fk_histvac__medicamento
    FOREIGN KEY (medicamento_id) REFERENCES medicamento (id),
  CONSTRAINT fk_histvac__lote
    FOREIGN KEY (lote_medicamento_id) REFERENCES lote_medicamento (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
