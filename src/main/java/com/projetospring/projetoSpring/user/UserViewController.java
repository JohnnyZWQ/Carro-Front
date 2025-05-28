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

     // Página inicial da aplicação (pode ser dashboard ou home)
    @GetMapping("/inicio")
    public ModelAndView PaginaInicial(){
    ModelAndView mv= new ModelAndView("index");
    mv.addObject("UserModel", new UserModel());
    return mv;
}

    @GetMapping("/usuarioCRUD")
    public ModelAndView userCrud(){
    ModelAndView mv= new ModelAndView("usuarioCRUD");
    mv.addObject("UserModel", new UserModel());
    return mv;
}

    // Exibe formulário para cadastro de usuário
    @GetMapping("/cadastrar")
    public ModelAndView cadastroForm() {
        ModelAndView mv = new ModelAndView("cadastrarUser");
        mv.addObject("UserModel", new UserModel());
        return mv;
    }

    // Processa cadastro do usuário
    @PostMapping("/salvar")
    public String cadastro(UserModel userModel) {
        var hashSenha = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(hashSenha);
        iuserRepository.save(userModel);
        return "redirect:/user/cadastrarUser"; 
    }


    @GetMapping("/atualizarUser")
    public ModelAndView exibirFormularioAtualizacao() {
    ModelAndView mv = new ModelAndView("atualizarUser");
    mv.addObject("UserModel", new UserModel());
    return mv;
}

    // Busca usuário por ID e preenche o formulário
    @GetMapping("/buscarPorId")
    public String buscarPorId(@RequestParam("id") UUID id, Model model) {
    Optional<UserModel> usuarioOpt = iuserRepository.findById(id);
    if (usuarioOpt.isPresent()) {
        model.addAttribute("UserModel", usuarioOpt.get());
    } else {
        model.addAttribute("UserModel", new UserModel());
        model.addAttribute("erro", "Usuário não encontrado para o ID informado.");
    }
    return "atualizarUser";
}

    // Atualiza os dados e criptografa a senha
    @PostMapping("/atualizarUser")
    public String atualizarUsuario(@ModelAttribute("UserModel") UserModel userModel) {
    if (userModel.getPassword() != null && !userModel.getPassword().isBlank()) {
        String hashSenha = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(hashSenha);
    }
    iuserRepository.save(userModel);
    return "redirect:/user/cadastrarUser";
}



    @GetMapping("/excluirUser")
    public ModelAndView Exluir(){
        ModelAndView mv= new ModelAndView("excluirUser");
        mv.addObject("UserModel", new UserModel());
        return mv;
    }
   
    @PostMapping("/excluir")
    public String excluirUsuario(@RequestParam("id") UUID id) {
    iuserRepository.deleteById(id);
    return "redirect:/user/cadastrarUser";
}

    @GetMapping("/pesquisarUser")
    public ModelAndView Pesquisar(){
        ModelAndView mv= new ModelAndView( "pesquisarUser");
        mv.addObject("UserModel", new UserModel());
        return mv;
    }

    @GetMapping("/pesquisarNome")
public String pesquisarNome(@RequestParam("name") String name, Model model) {
    Optional<UserModel> usuarioOpt = iuserRepository.findByName(name);
    if (usuarioOpt.isPresent()) {
        model.addAttribute("UserModel", usuarioOpt.get());
    } else {
        model.addAttribute("erro", "Usuário não encontrado com esse nome.");
    }
    return "pesquisarUser";
}




}
