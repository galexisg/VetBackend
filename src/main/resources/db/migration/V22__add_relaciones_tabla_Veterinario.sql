-- Agregar columnas una por una
ALTER TABLE veterinario ADD COLUMN servicio_id BIGINT NULL;
ALTER TABLE veterinario ADD COLUMN especialidad_id INT NULL;
ALTER TABLE veterinario ADD COLUMN usuario_id INT NULL;

-- Relación uno a uno: Veterinario → Usuario
ALTER TABLE veterinario
    ADD CONSTRAINT fk_veterinario_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

-- Relación muchos a uno con especialidad
ALTER TABLE veterinari
    ADD CONSTRAINT fk_veterinario_especialidad
    FOREIGN KEY (especialidad_id) REFERENCES especialidad(especialidad_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

-- Relación muchos a uno con servicio
ALTER TABLE veterinario
    ADD CONSTRAINT fk_veterinario_servicio
    FOREIGN KEY (servicio_id) REFERENCES servicio(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;
