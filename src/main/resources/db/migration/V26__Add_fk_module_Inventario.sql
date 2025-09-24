-- ============================================
-- V26__Add_fk_module_Inventario.sql
-- Estilo: agregar columnas una por una y luego FKs
-- ============================================

-- 1️⃣ Tabla dispensa
ALTER TABLE dispensa ADD COLUMN usuario_id INT NULL;

ALTER TABLE dispensa
    ADD CONSTRAINT fk_dispensa_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

-- 2️⃣ Tabla movimiento_detalle
ALTER TABLE movimiento_detalle ADD COLUMN usuario_id INT NULL;

ALTER TABLE movimiento_detalle
    ADD CONSTRAINT fk_mov_det_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

-- 3️⃣ Tabla compra
ALTER TABLE compra ADD COLUMN usuario_id INT NULL;

ALTER TABLE compra
    ADD CONSTRAINT fk_compra_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

-- 4️⃣ Tabla movimientos_inventario
ALTER TABLE movimientos_inventario ADD COLUMN usuario_id INT NULL;

ALTER TABLE movimientos_inventario
    ADD CONSTRAINT fk_mov_inv_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

-- 5️⃣ Tabla inventario
ALTER TABLE inventario ADD COLUMN medicamento_id INT NULL;
ALTER TABLE inventario ADD COLUMN almacen_id INT NULL;

ALTER TABLE inventario
    ADD CONSTRAINT fk_inv_medicamento
    FOREIGN KEY (medicamento_id) REFERENCES medicamento(Id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE inventario
    ADD CONSTRAINT fk_inv_almacen
    FOREIGN KEY (almacen_id) REFERENCES almacen(Id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

-- 6️⃣ Tabla compra_detalle
ALTER TABLE compra_detalle DROP COLUMN producto_id;
