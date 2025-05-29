package com.projetospring.projetoSpring.car;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_carros")
public class CarModel {

    @Id // Define o campo como chave primária da tabela.
    @GeneratedValue(generator = "UUID") // Gera um UUID automaticamente para o campo.
    private UUID idCarro;
    
    private String nome;
    private String ano;
    private String modelo;
    private String potencia;

    public UUID getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(UUID idCarro) {
        this.idCarro = idCarro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPotencia() {
        return potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }
}