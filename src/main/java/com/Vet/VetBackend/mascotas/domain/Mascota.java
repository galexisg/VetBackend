package com.Vet.VetBackend.mascotas.domain;

import com.Vet.VetBackend.raza.domain.Raza;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "mascota")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mascota_id")
    private Integer id;

    //@ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "usuario_id", nullable = false)
    //private Usuario usuario;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "raza_id", nullable = false)
//    private Raza raza;

    @Column(name = "foto", length = 255)
    private String foto;

    @Column(name = "nombre", length = 80, nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "alergia", columnDefinition = "ENUM('ninguna','pulgas','alimento','polen','ácaros del polvo','medicamentos')")
    private Alergia alergia = Alergia.ninguna;

    @Column(name = "nacimiento")
    private LocalDate nacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", columnDefinition = "ENUM('m','h')", nullable = false)
    private Sexo sexo;

    @Column(name = "peso", precision = 5, scale = 2)
    private BigDecimal peso;

    // Relaciones indirectas
    // @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Cita> citas;

    //@OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Historial> historiales;

    // @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<HistorialVacuna> historialVacunas;

    // Enums
    public enum Sexo { m, h }
    public enum Alergia { ninguna, pulgas, alimento, polen, ácaros_del_polvo, medicamentos }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //public Usuario getUsuario() {
    //    return usuario;
    //}

//public void setUsuario(Usuario usuario) {
//    this.usuario = usuario;
    // }

//    public Raza getRaza() {
//        return raza;
//    }

//    public void setRaza(Raza raza) {
//        this.raza = raza;
//    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Alergia getAlergia() {
        return alergia;
    }

    public void setAlergia(Alergia alergia) {
        this.alergia = alergia;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    //public List<Cita> getCitas() {
    //   return citas;
    //}

    //public void setCitas(List<Cita> citas) {
    //    this.citas = citas;
    //}

    //public List<Historial> getHistoriales() {
    //    return historiales;
    //}

    // public void setHistoriales(List<Historial> historiales) {
    //  this.historiales = historiales;
    // }

    //public List<HistorialVacuna> getHistorialVacunas() {
    //  return historialVacunas;
    //}

    // public void setHistorialVacunas(List<HistorialVacuna> historialVacunas) {
    //  this.historialVacunas = historialVacunas;
    //}
}

