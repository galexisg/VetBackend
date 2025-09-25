package com.Vet.VetBackend.mascotas.web.dto;

import com.Vet.VetBackend.mascotas.domain.Mascota.Sexo;
import com.Vet.VetBackend.mascotas.domain.Mascota.Alergia;
import java.math.BigDecimal;
import java.time.LocalDate;


public class MascotaRes {

        private Integer id;
        private String nombre;
        private String foto;
        private Alergia alergia;
        private LocalDate nacimiento;
        private Sexo sexo;
        private BigDecimal peso;
        private Integer usuarioId;
        private Integer razaId;

        // Getters y Setters

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
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

        public Integer getUsuarioId() {
            return usuarioId;
        }

        public void setUsuarioId(Integer usuarioId) {
            this.usuarioId = usuarioId;
        }

        public Integer getRazaId() {
            return razaId;
        }

        public void setRazaId(Integer razaId) {
            this.razaId = razaId;
        }
}
