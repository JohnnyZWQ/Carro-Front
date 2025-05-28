package com.projetospring.projetoSpring.car; // Define o pacote onde esta classe está localizada.

import jakarta.persistence.Entity; // Importa a anotação para marcar a classe como uma entidade do JPA.
import jakarta.persistence.GeneratedValue; // Importa a anotação para gerar valores automaticamente.
import jakarta.persistence.Id; // Importa a anotação que define o atributo como chave primária.
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID; // Importa a classe UUID para identificadores únicos.

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