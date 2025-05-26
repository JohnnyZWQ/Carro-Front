package com.projetospring.projetoSpring.car; // Define o pacote onde esta classe está localizada.
import jakarta.persistence.Entity; // Importa a anotação para marcar a classe como uma entidade do JPA.
import jakarta.persistence.GeneratedValue; // Importa a anotação para gerar valores automaticamente.
import jakarta.persistence.Id; // Importa a anotação que define o atributo como chave primária.

import java.util.UUID; // Importa a classe UUID para identificadores únicos.

@Entity(name = "tb_carros")
public class CarModel {

    @Id // Define o campo como chave primária da tabela.
    @GeneratedValue(generator = "UUID") // Gera um UUID automaticamente para o campo.
    private UUID idCarro;

    private String nome;
    private String senha;  

    private UUID id;

    // Métodos getters e setters:

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdCarro() {
        return idCarro;
    }

    public void setIdCarro(UUID idCarro) {
        this.idCarro = idCarro;
    }
}
