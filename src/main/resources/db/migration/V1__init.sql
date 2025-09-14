-- SERVICIOS
CREATE TABLE servicio (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(120) NOT NULL UNIQUE,
  descripcion VARCHAR(250),
  precio_base DECIMAL(12,2),
  activo BIT NOT NULL DEFAULT 1,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE motivo (
  id TINYINT PRIMARY KEY,
  nombre VARCHAR(60) NOT NULL
);

CREATE TABLE motivo_servicio (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  motivo_id TINYINT NOT NULL,
  servicio_id BIGINT NOT NULL,
  UNIQUE KEY uq_motivo_servicio (motivo_id, servicio_id),
  CONSTRAINT fk_ms_servicio FOREIGN KEY (servicio_id) REFERENCES servicio(id),
  CONSTRAINT fk_ms_motivo FOREIGN KEY (motivo_id) REFERENCES motivo(id)
);

-- TRATAMIENTOS
CREATE TABLE tratamiento (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(120) NOT NULL UNIQUE,
  descripcion VARCHAR(500),
  duracion_dias INT,
  frecuencia VARCHAR(60),
  via VARCHAR(60),
  activo BIT NOT NULL DEFAULT 1,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE servicio_tratamiento (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  servicio_id BIGINT NOT NULL,
  tratamiento_id BIGINT NOT NULL,
  UNIQUE KEY uq_servicio_tratamiento (servicio_id, tratamiento_id),
  CONSTRAINT fk_st_servicio FOREIGN KEY (servicio_id) REFERENCES servicio(id),
  CONSTRAINT fk_st_trat FOREIGN KEY (tratamiento_id) REFERENCES tratamiento(id)
);

-- FACTURACIÓN / PAGOS
CREATE TABLE factura (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  cliente_id BIGINT,      -- referencia externa (otro equipo)
  cita_id BIGINT,         -- referencia externa (otro equipo)
  estado VARCHAR(20) NOT NULL,
  total DECIMAL(12,2) NOT NULL DEFAULT 0,
  saldo DECIMAL(12,2) NOT NULL DEFAULT 0,
  motivo_anulacion VARCHAR(250),
  usuario_anula_id BIGINT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE factura_detalle (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  factura_id BIGINT NOT NULL,
  servicio_id BIGINT NOT NULL,
  cantidad INT NOT NULL,
  precio_unitario DECIMAL(12,2) NOT NULL,
  descuento DECIMAL(12,2),
  subtotal DECIMAL(12,2) NOT NULL,
  CONSTRAINT fk_fd_factura FOREIGN KEY (factura_id) REFERENCES factura(id),
  CONSTRAINT fk_fd_servicio FOREIGN KEY (servicio_id) REFERENCES servicio(id)
);

CREATE TABLE pago (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  factura_id BIGINT NOT NULL,
  metodo VARCHAR(20) NOT NULL,
  monto DECIMAL(12,2) NOT NULL,
  fecha_pago DATETIME NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_pago_factura FOREIGN KEY (factura_id) REFERENCES factura(id)
);
