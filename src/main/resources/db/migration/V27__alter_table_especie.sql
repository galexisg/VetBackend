-- Cambiamos EspecieId a especie_id, asegurando que sea INT para que sea compatible con raza
ALTER TABLE especie CHANGE EspecieId especie_id TINYINT NOT NULL AUTO_INCREMENT;

-- Reajustamos la primary key
ALTER TABLE especie DROP PRIMARY KEY, ADD PRIMARY KEY (especie_id);

-- Agregamos la columna en raza, con el mismo tipo
ALTER TABLE raza ADD COLUMN especie_id TINYINT ;

-- Creamos la foreign key
ALTER TABLE raza ADD CONSTRAINT fk_raza_especie
    FOREIGN KEY (especie_id) REFERENCES especie(especie_id);
