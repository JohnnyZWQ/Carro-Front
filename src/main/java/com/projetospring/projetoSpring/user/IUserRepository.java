package com.projetospring.projetoSpring.user;
import java.util.UUID; 
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface IUserRepository extends JpaRepository<UserModel, UUID> {

    /**
     * Método para buscar um usuário pelo nome.
     * O Spring Data JPA interpreta automaticamente este método e gera a consulta correspondente.
     * @return Um objeto UserModel correspondente ao nome informado.
     */
    UserModel findByname(String name);
    Optional<UserModel> findByName(String name);


}

