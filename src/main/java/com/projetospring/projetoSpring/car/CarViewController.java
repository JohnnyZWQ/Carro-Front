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

    // Página principal do CRUD de carros (lista todos os carros)
    @GetMapping("/carroCRUD")
    public ModelAndView PaginaInicial() {
        ModelAndView mv = new ModelAndView("carros/carroCRUD");
        mv.addObject("CarModel", new CarModel());
        mv.addObject("cars", icarRepository.findAll()); // Envia lista de carros para exibição
        return mv;
    }

    // Exibe formulário para cadastrar um novo carro
    @GetMapping("/cadastrarCarro")
    public ModelAndView cadastroForm() {
        ModelAndView mv = new ModelAndView("carros/cadastrarCarro");
        mv.addObject("CarModel", new CarModel());
        return mv;
    }

    // Processa o cadastro do carro
    @PostMapping("/salvar")
    public String cadastro(CarModel carModel) {
        icarRepository.save(carModel);
        return "redirect:/carro/carroCRUD";
    }

    // Exibe formulário para atualização de um carro existente
    @GetMapping("/atualizarCarro")
    public ModelAndView exibirFormularioAtualizacao() {
        ModelAndView mv = new ModelAndView("carros/atualizarCarro");
        mv.addObject("CarModel", new CarModel());
        return mv;
    }

    // Busca um carro pelo ID e preenche o formulário de atualização
    @GetMapping("/buscarPorId")
    public String buscarPorId(@RequestParam("idCarro") String idCarroStr, Model model) {
        UUID idCarro = null;
        try {
            idCarro = UUID.fromString(idCarroStr);
        } catch (IllegalArgumentException e) {
            // ID inválido, retorna erro
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

    // Atualiza os dados do carro
    @PostMapping("/atualizarCarro")
    public String atualizarcarro(CarModel carModel) {
        icarRepository.save(carModel);
        return "redirect:/carro/carroCRUD";
    }

    // Exibe o formulário para exclusão de carro
    @GetMapping("/excluirCarro")
    public ModelAndView Exluir() {
        ModelAndView mv = new ModelAndView("carros/excluirCarro");
        mv.addObject("CarModel", new CarModel());
        return mv;
    }

    // Exclui um carro com base no ID
    @PostMapping("/excluir")
    public String excluircarro(@RequestParam("idCarro") UUID idCarro) {
        icarRepository.deleteById(idCarro);
        return "redirect:/carro/carroCRUD";
    }

    // Exibe formulário para pesquisar carro por nome
    @GetMapping("/pesquisarCarro")
    public ModelAndView Pesquisar() {
        ModelAndView mv = new ModelAndView("carros/pesquisarCarro");
        mv.addObject("CarModel", new CarModel());
        return mv;
    }

    // Pesquisa um carro pelo nome informado
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

