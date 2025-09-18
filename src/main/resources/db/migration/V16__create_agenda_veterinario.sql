-- V16__create_agenda_veterinario.sql
-- Tablas: estadodia, dia, veterinario, especialidad, bloque_horario, detalle_horario_veterinario

-- 1) Estado del día (catálogo)
CREATE TABLE IF NOT EXISTS estadodia (
  estado_dia_id INT NOT NULL AUTO_INCREMENT,
  estado ENUM('Activo','Inactivo') NOT NULL DEFAULT 'Activo' COMMENT 'Estado del día',
  PRIMARY KEY (estado_dia_id),
  UNIQUE KEY uk_estadodia_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 2) Día (Lunes, Martes, ...)
CREATE TABLE IF NOT EXISTS dia (
  dia_id INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(20) NOT NULL,
  estado_dia_id INT NOT NULL,
  PRIMARY KEY (dia_id),
  UNIQUE KEY uk_dia_nombre (nombre),
  KEY idx_dia_estado (estado_dia_id),
  CONSTRAINT fk_dia__estadodia
    FOREIGN KEY (estado_dia_id) REFERENCES estadodia(estado_dia_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 3) Veterinario
CREATE TABLE IF NOT EXISTS veterinario (
  veterinario_id INT NOT NULL AUTO_INCREMENT,
  numero_licencia VARCHAR(60) NOT NULL,
  estado ENUM('Activo','Inactivo') NOT NULL DEFAULT 'Activo' COMMENT 'Estado del veterinario',
  PRIMARY KEY (veterinario_id),
  UNIQUE KEY uk_veterinario_licencia (numero_licencia)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 4) Especialidad (catálogo)
CREATE TABLE IF NOT EXISTS especialidad (
  especialidad_id INT NOT NULL AUTO_INCREMENT,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  nombre VARCHAR(100) NOT NULL,
  PRIMARY KEY (especialidad_id),
  UNIQUE KEY uk_especialidad_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 5) Bloque horario (franjas)
CREATE TABLE IF NOT EXISTS bloque_horario (
  bloque_horario_id INT NOT NULL AUTO_INCREMENT,
  inicio TIME(6) NOT NULL,
  fin    TIME(6) NOT NULL,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (bloque_horario_id),
  CHECK (inicio < fin)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 6) Detalle de horario por veterinario y día
CREATE TABLE IF NOT EXISTS detalle_horario_veterinario (
  detalle_horario_veterinario_id INT NOT NULL AUTO_INCREMENT,
  bloque_horario_id INT NOT NULL,
  dia_id            INT NOT NULL,
  veterinario_id    INT NOT NULL,
  PRIMARY KEY (detalle_horario_veterinario_id),

  KEY idx_dhv_bloque (bloque_horario_id),
  KEY idx_dhv_dia    (dia_id),
  KEY idx_dhv_vet    (veterinario_id),

  -- evita duplicar el mismo bloque para el mismo vet y día
  UNIQUE KEY uk_dhv_vet_dia_bloque (veterinario_id, dia_id, bloque_horario_id),

  CONSTRAINT fk_dhv__bloque
    FOREIGN KEY (bloque_horario_id) REFERENCES bloque_horario(bloque_horario_id),
  CONSTRAINT fk_dhv__dia
    FOREIGN KEY (dia_id) REFERENCES dia(dia_id),
  CONSTRAINT fk_dhv__veterinario
    FOREIGN KEY (veterinario_id) REFERENCES veterinario(veterinario_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
