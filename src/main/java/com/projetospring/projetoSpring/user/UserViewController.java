package com.projetospring.projetoSpring.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserViewController {

    @Autowired
    private IUserRepository iuserRepository;

    // Página inicial do sistema (pode servir como dashboard ou home)
    @GetMapping("/inicio")
    public ModelAndView PaginaInicial() {
        ModelAndView mv = new ModelAndView("usuario/index");
        mv.addObject("UserModel", new UserModel());
        return mv;
    }

    // Página que lista todos os usuários cadastrados (CRUD principal)
    @GetMapping("/usuarioCRUD")
    public ModelAndView userCrud() {
        ModelAndView mv = new ModelAndView("usuario/usuarioCRUD");
        mv.addObject("UserModel", new UserModel());
        mv.addObject("users", iuserRepository.findAll()); // Lista de usuários enviados para exibição
        return mv;
    }

    // Exibe o formulário de cadastro de novo usuário
    @GetMapping("/cadastrar")
    public ModelAndView cadastroForm() {
        ModelAndView mv = new ModelAndView("usuario/cadastrarUser");
        mv.addObject("UserModel", new UserModel());
        return mv;
    }

    // Processa o cadastro do usuário (com senha criptografada)
    @PostMapping("/salvar")
    public String cadastro(UserModel userModel) {
        var hashSenha = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(hashSenha);
        iuserRepository.save(userModel);
        return "redirect:/user/usuarioCRUD";
    }

    // Exibe formulário de atualização do usuário (por ID)
    @GetMapping("/atualizarUser")
    public ModelAndView exibirFormularioAtualizacao() {
        ModelAndView mv = new ModelAndView("usuario/atualizarUser");
        mv.addObject("UserModel", new UserModel());
        return mv;
    }

    // Busca usuário por ID e preenche o formulário de atualização
    @GetMapping("/buscarPorId")
    public String buscarPorId(@RequestParam("id") String idStr, Model model) {
        UUID id = null;
        try {
            id = UUID.fromString(idStr);
        } catch (IllegalArgumentException e) {
            // ID inválido, retorna erro
            model.addAttribute("erro", "Usuário não encontrado para o ID informado.");
            model.addAttribute("UserModel", new UserModel());
            return "usuario/atualizarUser";
        }

        Optional<UserModel> usuarioOpt = iuserRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            model.addAttribute("UserModel", usuarioOpt.get());
        } else {
            model.addAttribute("UserModel", new UserModel());
            model.addAttribute("erro", "Usuário não encontrado para o ID informado.");
        }
        return "usuario/atualizarUser";
    }

    // Atualiza dados do usuário com nova senha criptografada (se informada)
    @PostMapping("/atualizarUser")
    public String atualizarUsuario(@ModelAttribute("UserModel") UserModel userModel) {
        if (userModel.getPassword() != null && !userModel.getPassword().isBlank()) {
            String hashSenha = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
            userModel.setPassword(hashSenha);
        }
        iuserRepository.save(userModel);
        return "redirect:/user/usuarioCRUD";
    }

    // Exibe formulário para exclusão de usuário
    @GetMapping("/excluirUser")
    public ModelAndView Exluir() {
        ModelAndView mv = new ModelAndView("usuario/excluirUser");
        mv.addObject("UserModel", new UserModel());
        return mv;
    }

    // Exclui o usuário pelo ID
    @PostMapping("/excluir")
    public String excluirUsuario(@RequestParam("id") UUID id) {
        iuserRepository.deleteById(id);
        return "redirect:/user/usuarioCRUD";
    }

    // Exibe formulário de pesquisa por nome
    @GetMapping("/pesquisarUser")
    public ModelAndView Pesquisar() {
        ModelAndView mv = new ModelAndView("usuario/pesquisarUser");
        mv.addObject("UserModel", new UserModel());
        return mv;
    }

    // Pesquisa usuário pelo nome informado e exibe resultado
    @GetMapping("/pesquisarNome")
    public String pesquisarNome(@RequestParam("name") String name, Model model) {
        Optional<UserModel> usuarioOpt = iuserRepository.findByName(name);
        if (usuarioOpt.isPresent()) {
            model.addAttribute("UserModel", usuarioOpt.get());
        } else {
            model.addAttribute("erro", "Usuário não encontrado com esse nome.");
        }
        return "usuario/pesquisarUser";
    }
}

