package com.projetospring.projetoSpring.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.projetospring.projetoSpring.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component // Marca esse filtro como um componente do Spring
public class FilterAuth extends OncePerRequestFilter {

    @Autowired
    IUserRepository userRepository; // Injeta o repositório para consultar o usuário

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Intercepta todas as requisições HTTP antes de chegarem no controller.
        // Permite analisar ou modificar a requisição (ex: autenticação).
        // 'request' = dados da requisição.
        // 'response' = resposta que será enviada.
        // 'filterChain' = continua o fluxo da requisição.

        var servletPath = request.getServletPath();

        // Aplica a autenticação somente no endpoint /carro/novo
        if (servletPath.equals("/carro/novo")) {
            var authorization = request.getHeader("Authorization");

            // Verifica se o cabeçalho Authorization está presente e correto
            if (authorization == null || !authorization.startsWith("Basic ")) {
                response.sendError(401, "Cabeçalho Authorization ausente ou malformado");
                return;
            }

            // Remove o prefixo "Basic " e decodifica o base64
            var authEncoded = authorization.substring("Basic".length()).trim();
            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
            var authString = new String(authDecoded);

            // Divide a string em username e password
            String[] credenciais = authString.split(":");
            if (credenciais.length != 2) {
                response.sendError(401, "Credenciais inválidas");
                return;
            }

            String username = credenciais[0];
            String password = credenciais[1];

            // Busca o usuário no banco
            var user = this.userRepository.findByname(username);
            if (user == null) {
                response.sendError(401, "Usuário inexistente");
                return;
            }

            // Verifica se a senha está correta usando o BCrypt
            var senhaVerifica = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if (senhaVerifica.verified) {
                // Se a senha estiver correta, adiciona o ID do usuário à requisição
                request.setAttribute("id", user.getId());

                // Só agora libera a requisição para o controller
                filterChain.doFilter(request, response);
            } else {
                response.sendError(401, "Senha incorreta");
            }

        } else {
            // Para qualquer outro endpoint que não seja /carro/novo, a requisição passa normalmente
            filterChain.doFilter(request, response);
        }
    }
}
