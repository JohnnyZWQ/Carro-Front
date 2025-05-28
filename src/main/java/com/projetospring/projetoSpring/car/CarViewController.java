package com.projetospring.projetoSpring.car;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/carro")
public class CarViewController {
    
    @Autowired
    private ICarRepository icarRepository;

    @GetMapping("/carroCRUD")
    public ModelAndView PaginaInicial(){
    ModelAndView mv= new ModelAndView("carros/carroCRUD");
    mv.addObject("CarModel", new CarModel());
    mv.addObject("cars", icarRepository.findAll()); // <-- Envia lista de usuários
    return mv;
    }

    // Exibe formulário para cadastro de carro
    @GetMapping("/cadastrarCarro")
    public ModelAndView cadastroForm() {
        ModelAndView mv = new ModelAndView("carros/cadastrarCarro");
        mv.addObject("CarModel", new CarModel());
        return mv;
    }

    // Processa cadastro do carro
    @PostMapping("/salvar")
    public String cadastro(CarModel carModel) {
        icarRepository.save(carModel);
        return "redirect:/carro/carroCRUD"; 
    }


    @GetMapping("/atualizarCarro")
    public ModelAndView exibirFormularioAtualizacao() {
    ModelAndView mv = new ModelAndView("carros/atualizarCarro");
    mv.addObject("CarModel", new CarModel());
    return mv;
}

    // Busca carro por ID e preenche o formulário
    @GetMapping("/buscarPorId")
    public String buscarPorId(@RequestParam("idCarro") String idCarroStr, Model model) {
    UUID idCarro = null;
    try {
        idCarro = UUID.fromString(idCarroStr);
    } catch (IllegalArgumentException e) {
        // Valor não é UUID válido, considerar como "não encontrado"
        model.addAttribute("erro", "Carro não encontrado para o ID informado.");
        model.addAttribute("CarModel", new CarModel());
        return "carros/atualizarCarro";
    }

    Optional<CarModel> carroOpt = icarRepository.findById(idCarro);
    if (carroOpt.isPresent()) {
        model.addAttribute("CarModel", carroOpt.get());
    } else {
        model.addAttribute("CarModel", new CarModel());
        model.addAttribute("erro", "Carro não encontrado para o ID informado.");
    }
    return "carros/atualizarCarro";
}

    // Atualiza os dados e criptografa a senha
    @PostMapping("/atualizarCarro")
    public String atualizarcarro(CarModel carModel) {
        icarRepository.save(carModel);
    return "redirect:/carro/carroCRUD";
}


    @GetMapping("/excluirCarro")
    public ModelAndView Exluir(){
        ModelAndView mv= new ModelAndView("carros/excluirCarro");
        mv.addObject("CarModel", new CarModel());
        return mv;
    }
   
    @PostMapping("/excluir")
    public String excluircarro(@RequestParam("idCarro") UUID idCarro) {
    icarRepository.deleteById(idCarro);
    return "redirect:/carro/carroCRUD";
}

    @GetMapping("/pesquisarCarro")
    public ModelAndView Pesquisar(){
        ModelAndView mv= new ModelAndView( "carros/pesquisarCarro");
        mv.addObject("CarModel", new CarModel());
        return mv;
    }

    @GetMapping("/pesquisarNome")
    public String pesquisarNome(@RequestParam("nome") String nome, Model model) {
    Optional<CarModel> carroOpt = icarRepository.findBynome(nome);
    if (carroOpt.isPresent()) {
        model.addAttribute("CarModel", carroOpt.get());
    } else {
        model.addAttribute("erro", "Carro não encontrado com esse nome.");
    }
    return "carros/pesquisarCarro";
}
}
