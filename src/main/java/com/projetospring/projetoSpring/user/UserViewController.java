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
        return "redirect:/user/inicio"; 
    }

    // Busca usuário por ID e exibe no formulário para edição
    @GetMapping("/listar")
    public String buscarUsuario(@RequestParam("iduser") UUID iduser, Model model) {
        Optional<UserModel> usuario = iuserRepository.findById(iduser);
        if (usuario.isPresent()) {
            model.addAttribute("UserModel", usuario.get());
            model.addAttribute("name", usuario.get().getName());
            model.addAttribute("password", usuario.get().getPassword());
            model.addAttribute("email", usuario.get().getEmail());
        } else {
            model.addAttribute("UserModel", new UserModel());
            model.addAttribute("notFound", true);
        }
        return "atualizarUser";
    }

    @GetMapping("/atualizarUser")
    public ModelAndView Atualizar(){
      ModelAndView mv= new ModelAndView("atualizarUser");
        mv.addObject("UserModel", new UserModel());
      return mv;  
    }

    // Atualiza dados do usuário
    @PostMapping("/atualizar")
    public String atualizarUsuario(@ModelAttribute("UserModel") UserModel userModel) {
        var hashSenha = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(hashSenha);
        iuserRepository.save(userModel);
        return "redirect:/user/listar?id=" + userModel.getId();
    }

    @GetMapping("/excluirUser")
    public ModelAndView Exluir(){
        ModelAndView mv= new ModelAndView("excluirUser");
        mv.addObject("UserModel", new UserModel());
        return mv;
    }
   
    @GetMapping("/pesquisarUser")
    public ModelAndView Pesquisar(){
        ModelAndView mv= new ModelAndView( "pesquisarUser");
        mv.addObject("UserModel", new UserModel());
        return mv;
    }

}
