package com.projetospring.projetoSpring.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Define a classe como uma entidade JPA e a associa a uma tabela no banco de dados.
 * O nome da tabela será "tb_usuario".
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_usuario")
public class UserModel {

    @Id // Define que este campo será a chave primária da tabela.
    @GeneratedValue(generator = "UUID") // Gera automaticamente um UUID para cada novo usuário.
    private UUID id; // Identificador único do usuário.

    // Atributos da tabela
    private String name;
    private String email;
    private String password;

    // Métodos getters e setters para acessar e modificar os atributos.

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
