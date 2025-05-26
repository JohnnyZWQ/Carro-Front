// package com.projetospring.projetoSpring.user;

// import at.favre.lib.crypto.bcrypt.BCrypt;
// import jakarta.servlet.http.HttpServletRequest;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.UUID;

// @RestController // Indica que esta classe é um controlador REST.
// @RequestMapping("/user") // Define o endpoint base para todas as requisições deste controlador.
// public class UserAPIController {

//     @Autowired // Injeta automaticamente a implementação de IUserRepository.
//     private IUserRepository userRepository;

//     /**
//      * Endpoint para criar um novo usuário.
//      * @return ResponseEntity com status de criação ou erro se o usuário já existir.
//      */
//     @PostMapping("/novo")
//     private ResponseEntity criarUsuario(@RequestBody UserModel userModel, HttpServletRequest request) {
//         var usuarioExiste = this.userRepository.findByname(userModel.getName()); // Verifica se o usuário já existe.

//         if (usuarioExiste != null) {
//             // Retorna erro caso o usuário já esteja cadastrado.
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!"); 
//         } else {
//             // Criptografa a senha antes de salvar no banco.
//             var senhaHash = BCrypt.withDefaults()
//                     .hashToString(12, userModel.getPassword().toCharArray());
//             userModel.setPassword(senhaHash);

//             // Criptografa o email antes de salvar no banco.
//             var emailHash = BCrypt.withDefaults()
//                     .hashToString(12, userModel.getEmail().toCharArray());
//             userModel.setEmail(emailHash);

//             var criado = this.userRepository.save(userModel); // Salva o usuário no banco de dados.
//             return ResponseEntity.status(HttpStatus.CREATED).body(criado); // Retorna status de criação com os dados do usuário.
//         }
//     }

//     /**
//      * Endpoint para listar todos os usuários cadastrados.
//      * @return Lista de usuários.
//      */
//     @GetMapping("/listarUser")
//     public List<UserModel> listarUsuarios() {
//         List<UserModel> usuariosCadastrados = userRepository.findAll(); // Busca todos os usuários no banco.
//         return usuariosCadastrados;
//     }

//     /**
//      * Endpoint para atualizar um usuário existente.
//      * @param userModel Objeto atualizado do usuário.
//      * @return ResponseEntity com o usuário atualizado ou erro se não encontrado.
//      */

//     @PutMapping("/atualizar/{id}")
//     public ResponseEntity atualizaUser(@PathVariable UUID id, @RequestBody UserModel userModel) {
//         var usuarioExistente = userRepository.findById(id); // Busca o usuário pelo ID.

//         if (usuarioExistente.isEmpty()) {
//             // Retorna erro caso o usuário não seja encontrado.
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
//         }

//         var usuario = usuarioExistente.get();

//         // Atualiza os dados recebidos
//         usuario.setName(userModel.getName());

//         // Criptografa e atualiza a nova senha
//         var senhaHash = BCrypt.withDefaults()
//                 .hashToString(12, userModel.getPassword().toCharArray());
//         usuario.setPassword(senhaHash);

//         // Criptografa e atualiza o novo email
//         var emailHash = BCrypt.withDefaults()
//                 .hashToString(12, userModel.getEmail().toCharArray());
//         usuario.setEmail(emailHash);

//         var atualizado = userRepository.save(usuario); // Salva as alterações no banco.
//         return ResponseEntity.ok(atualizado); // Retorna os dados atualizados.
//     }


//     /**
//      * Endpoint para deletar um usuário pelo ID.
//      * @param id Identificador único do usuário a ser removido.
//      */
//     @DeleteMapping("/delete/{id}")
//     public void deletaUser(@PathVariable UUID id) {
//         userRepository.deleteById(id); // Remove o usuário do banco de dados.
//     }
// }
