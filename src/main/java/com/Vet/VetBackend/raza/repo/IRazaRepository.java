package com.Vet.VetBackend.raza.repo;

import com.Vet.VetBackend.raza.domain.Raza;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IRazaRepository extends JpaRepository<Raza, Byte> {

    @EntityGraph(attributePaths = {"especie"})
    List<Raza> findAll();

    @EntityGraph(attributePaths = {"especie"})
    Page<Raza> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"especie"})
    Optional<Raza> findById(Byte id);
}
