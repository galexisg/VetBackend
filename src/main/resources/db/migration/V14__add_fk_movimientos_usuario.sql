-- Asegura mismo tipo que la PK
ALTER TABLE movimientos_inventario
  MODIFY usuario_id INT NULL;

-- Agrega el FK correcto
ALTER TABLE movimientos_inventario
  ADD CONSTRAINT fk_mov_inv__usuario
  FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id);
