-- V13__create_inventory_medication_modules.sql
-- Módulos de inventario, medicamentos y compras

-- Proveedor
CREATE TABLE IF NOT EXISTS proveedor (
  id INT NOT NULL AUTO_INCREMENT,
  direccion VARCHAR(255) DEFAULT NULL,
  email VARCHAR(255) DEFAULT NULL,
  nit VARCHAR(255) DEFAULT NULL,
  nombre VARCHAR(255) DEFAULT NULL,
  notas VARCHAR(255) DEFAULT NULL,
  telefono VARCHAR(255) DEFAULT NULL,
  estadoid INT DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Almacén
CREATE TABLE IF NOT EXISTS almacen (
  id INT NOT NULL AUTO_INCREMENT,
  activo BIT(1) DEFAULT NULL,
  nombre VARCHAR(80) DEFAULT NULL,
  ubicacion VARCHAR(200) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Inventario (stock por ítem)
CREATE TABLE IF NOT EXISTS inventario (
  id INT NOT NULL AUTO_INCREMENT,
  stock_actual DOUBLE NOT NULL,
  stock_maximo DOUBLE NOT NULL,
  stock_minimo DOUBLE NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Medicamento (catálogo)
CREATE TABLE IF NOT EXISTS medicamento (
  id INT NOT NULL AUTO_INCREMENT,
  activo BIT(1) DEFAULT NULL,
  concentracion VARCHAR(255) DEFAULT NULL,
  formafarmacautica VARCHAR(255) DEFAULT NULL,
  nombre VARCHAR(255) DEFAULT NULL,
  requiere_receta BIT(1) DEFAULT NULL,
  temperaturaalm VARCHAR(255) DEFAULT NULL,
  unidad VARCHAR(255) DEFAULT NULL,
  via VARCHAR(255) DEFAULT NULL,
  vidautilmeses INT DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Lote de medicamento
CREATE TABLE IF NOT EXISTS lote_medicamento (
  id INT NOT NULL AUTO_INCREMENT,
  codigo_lote VARCHAR(60) DEFAULT NULL,
  fecha_vencimiento DATE DEFAULT NULL,
  medicamento_id INT DEFAULT NULL,
  observaciones VARCHAR(200) DEFAULT NULL,
  proveedor_id INT DEFAULT NULL,
  PRIMARY KEY (id),
  KEY idx_lote_medicamento_medicamento (medicamento_id),
  KEY idx_lote_medicamento_proveedor (proveedor_id),
  CONSTRAINT fk_lote_medicamento__medicamento
    FOREIGN KEY (medicamento_id) REFERENCES medicamento(id),
  CONSTRAINT fk_lote_medicamento__proveedor
    FOREIGN KEY (proveedor_id) REFERENCES proveedor(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Movimientos de inventario (cabecera)
-- OJO: usuario(UsuarioId) es la PK real en tu proyecto
-- Movimientos de inventario (cabecera)  [SIN FK A USUARIO]
CREATE TABLE IF NOT EXISTS movimientos_inventario (
  id INT NOT NULL AUTO_INCREMENT,
  fecha DATETIME(6) DEFAULT NULL,
  observacion VARCHAR(255) DEFAULT NULL,
  tipo ENUM('ENTRADA','SALIDA') DEFAULT NULL,
  almacen_id INT DEFAULT NULL,
  usuario_id INT DEFAULT NULL,
  PRIMARY KEY (id),
  KEY idx_mov_inv_almacen (almacen_id),
  KEY idx_mov_inv_usuario (usuario_id),
  CONSTRAINT fk_mov_inv__almacen
    FOREIGN KEY (almacen_id) REFERENCES almacen(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Detalle de movimientos de inventario
CREATE TABLE IF NOT EXISTS movimiento_detalle (
  id INT NOT NULL AUTO_INCREMENT,
  cantidad DOUBLE NOT NULL,
  costo_unitario DOUBLE NOT NULL,
  fecha DATETIME(6) DEFAULT NULL,
  almacen_id INT DEFAULT NULL,
  lote_id INT DEFAULT NULL,
  medicamento_id INT DEFAULT NULL,
  movimiento_id INT DEFAULT NULL,
  PRIMARY KEY (id),
  KEY idx_mov_det_almacen (almacen_id),
  KEY idx_mov_det_lote (lote_id),
  KEY idx_mov_det_medicamento (medicamento_id),
  KEY idx_mov_det_movimiento (movimiento_id),
  CONSTRAINT fk_mov_det__almacen
    FOREIGN KEY (almacen_id) REFERENCES almacen(id),
  CONSTRAINT fk_mov_det__lote
    FOREIGN KEY (lote_id) REFERENCES lote_medicamento(id),
  CONSTRAINT fk_mov_det__medicamento
    FOREIGN KEY (medicamento_id) REFERENCES medicamento(id),
  CONSTRAINT fk_mov_det__mov_inv
    FOREIGN KEY (movimiento_id) REFERENCES movimientos_inventario(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dispensa (entrega por prescripción)
CREATE TABLE IF NOT EXISTS dispensa (
  id INT NOT NULL AUTO_INCREMENT,
  almacen_id INT DEFAULT NULL,
  cantidad DECIMAL(12,2) DEFAULT NULL,
  fecha DATETIME(6) DEFAULT NULL,
  lote_id INT DEFAULT NULL,
  prescripcion_detalle_id INT DEFAULT NULL,
  PRIMARY KEY (id),
  KEY idx_dispensa_almacen (almacen_id),
  KEY idx_dispensa_lote (lote_id),
  CONSTRAINT fk_dispensa__almacen
    FOREIGN KEY (almacen_id) REFERENCES almacen(id),
  CONSTRAINT fk_dispensa__lote
    FOREIGN KEY (lote_id) REFERENCES lote_medicamento(id)
  -- prescripcion_detalle_id: agrega FK aquí cuando tengas la tabla objetivo
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Compras
CREATE TABLE IF NOT EXISTS compra (
  id BIGINT NOT NULL AUTO_INCREMENT,
  descripcion VARCHAR(255) DEFAULT NULL,
  fecha DATE DEFAULT NULL,
  proveedor_id INT DEFAULT NULL,               -- alineado a proveedor(id)
  total DOUBLE NOT NULL,
  PRIMARY KEY (id),
  KEY idx_compra_proveedor (proveedor_id),
  CONSTRAINT fk_compra__proveedor
    FOREIGN KEY (proveedor_id) REFERENCES proveedor(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS compra_detalle (
  id BIGINT NOT NULL AUTO_INCREMENT,
  cantidad INT NOT NULL,
  compra_id BIGINT DEFAULT NULL,
  precio DOUBLE NOT NULL,
  producto_id BIGINT DEFAULT NULL,
  PRIMARY KEY (id),
  KEY idx_compra_det_compra (compra_id),
  KEY idx_compra_det_producto (producto_id),
  CONSTRAINT fk_compra_det__compra
    FOREIGN KEY (compra_id) REFERENCES compra(id)
  -- Agrega FK a producto cuando confirmes la tabla correspondiente
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Vinculaciones 1:1 de inventario con almacén y medicamento
CREATE TABLE IF NOT EXISTS almacen_id (
  almacen_id INT DEFAULT NULL,
  id INT NOT NULL,
  PRIMARY KEY (id),
  KEY idx_almacen_id_almacen (almacen_id),
  CONSTRAINT fk_almacen_id__inventario
    FOREIGN KEY (id) REFERENCES inventario(id),
  CONSTRAINT fk_almacen_id__almacen
    FOREIGN KEY (almacen_id) REFERENCES almacen(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS medicamento_id (
  medicamento_id INT DEFAULT NULL,
  id INT NOT NULL,
  PRIMARY KEY (id),
  KEY idx_medicamento_id_medicamento (medicamento_id),
  CONSTRAINT fk_medicamento_id__inventario
    FOREIGN KEY (id) REFERENCES inventario(id),
  CONSTRAINT fk_medicamento_id__medicamento
    FOREIGN KEY (medicamento_id) REFERENCES medicamento(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
