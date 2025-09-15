-- Aceptar servicio O tratamiento en la misma tabla (una sola fila por ítem)
ALTER TABLE factura_detalle
  MODIFY servicio_id BIGINT NULL,
  ADD COLUMN tratamiento_id BIGINT NULL AFTER servicio_id,
  ADD KEY fk_fd_tratamiento (tratamiento_id),
  ADD CONSTRAINT fk_fd_tratamiento FOREIGN KEY (tratamiento_id) REFERENCES tratamiento(id);

-- Regla XOR: exactamente uno de los dos (MySQL 8 respeta CHECK)
ALTER TABLE factura_detalle
  ADD CONSTRAINT chk_fd_item_unico
  CHECK ( (servicio_id IS NULL) <> (tratamiento_id IS NULL) );

-- Congelar la descripción visible al cliente (por si cambia el catálogo)
ALTER TABLE factura_detalle
  ADD COLUMN descripcion_item VARCHAR(200) NULL AFTER tratamiento_id;

-- Asegurar precio al momento (tu flujo ya usa precio_unitario obligatorio)
-- subtotal lo calcula la app o un trigger si decides
