package com.projetospring.projetoSpring.car;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Define que essa classe é um controller REST que escutará requisições a partir do endpoint /carro.
 */
@RestController
@RequestMapping("/carro")
public class CarController {

    @Autowired
    private ICarRepository carRepository;

    /**
     * Endpoint POST para cadastrar um novo carro.
     * Se o modelo do carro já estiver cadastrado, retorna erro.
     * Caso contrário, criptografa a senha, salva o carro no banco e retorna o carro criado.
     */
    @PostMapping("/novo")
    private ResponseEntity criarCarro(@RequestBody CarModel carModel, HttpServletRequest request) {
        var carroExiste = this.carRepository.findByNome(carModel.getNome());

        if (carroExiste != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Carro já cadastrado");
        } else {
            var senhaHash = BCrypt.withDefaults()
                    .hashToString(12, carModel.getSenha().toCharArray());
            carModel.setSenha(senhaHash);

            var criado = this.carRepository.save(carModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        }
    }

    /**
     * Endpoint GET para listar todos os carros cadastrados.
     * @return Lista com todos os carros do banco.
     */
    @GetMapping("/listarCar")
    public List<CarModel> listarCarros() {
        List<CarModel> carrosCadastrados = carRepository.findAll();
        return carrosCadastrados;
    }

    /**
     * Endpoint PUT para atualizar os dados de um carro existente.
     * Só atualiza se o carro existir, e a senha será recriptografada.
     */
    @PutMapping("/atualizar/{id}")
    public ResponseEntity atualizarCarro(@PathVariable UUID id, @RequestBody CarModel carModel) {
        Optional<CarModel> carroExistente = carRepository.findById(id);

        if (carroExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carro não encontrado");
        }

        CarModel carroAtual = carroExistente.get();

        carroAtual.setNome(carModel.getNome());

        // Criptografa a nova senha apenas se foi alterada
        if (!carModel.getSenha().isBlank()) {
            var senhaHash = BCrypt.withDefaults()
                    .hashToString(12, carModel.getSenha().toCharArray());
            carroAtual.setSenha(senhaHash);
        }

        var atualizado = carRepository.save(carroAtual);
        return ResponseEntity.status(HttpStatus.OK).body(atualizado);
    }

    /**
     * Endpoint DELETE para excluir um carro pelo ID.
     * @param id UUID do carro a ser deletado.
     */
    @DeleteMapping("/delete/{id}")
    public void deletarCarro(@PathVariable UUID id) {
        carRepository.deleteById(id);
    }
}
