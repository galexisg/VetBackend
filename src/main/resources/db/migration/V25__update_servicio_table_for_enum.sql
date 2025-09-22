-- V25__update_servicio_table_for_enum.sql
-- Migración para actualizar la tabla servicio.
-- Elimina la columna 'activo' y añade la columna 'estado' para el Enum.

-- Paso 1: Eliminar la columna 'activo'.
ALTER TABLE servicio DROP COLUMN activo;

-- Paso 2: Añadir la nueva columna 'estado'.
-- La columna se inicializa con el valor por defecto 'ACTIVO' para mantener la consistencia.
ALTER TABLE servicio ADD COLUMN estado VARCHAR(255) NOT NULL DEFAULT 'ACTIVO';