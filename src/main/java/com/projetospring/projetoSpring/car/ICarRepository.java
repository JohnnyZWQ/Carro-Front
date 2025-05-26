package com.projetospring.projetoSpring.car;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/*
 * Interface responsável pela comunicação com o banco de dados para a entidade CarModel.
 * Estende JpaRepository, que já fornece métodos prontos como save, findAll, deleteById, etc.
 */
public interface ICarRepository extends JpaRepository<CarModel, UUID> {

    /*
     * Método personalizado que retorna um carro pelo nome.
     * Spring Data JPA entende o nome "findByNome" e cria a consulta automaticamente.
     */
    CarModel findByNome(String nome);
}
