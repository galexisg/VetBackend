package com.Vet.VetBackend.raza.domain;

//import com.Vet.VetBackend.especie.domain.Especie;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "raza")
public class Raza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RazaId")
    private Integer id; // Usamos Integer para mayor compatibilidad con Hibernate
// Cuando la charon termine sus clases correspondientes la michel va a descomentar este codigo que esta comentado y va a eliminar el no lleva manytoone

    //    @ManyToOne
//    @JoinColumn(name = "EspecieId", nullable = false, foreignKey = @ForeignKey(name = "fk_raza__especie"))
//    private Especie especie;
    // Dejamos la relación como simple columna, sin @ManyToOne por ahora
    @Column(name = "EspecieId", nullable = false)
    private Integer especieId;

    @Column(name = "Nombre", length = 60, nullable = false)
    private String nombre;
}
