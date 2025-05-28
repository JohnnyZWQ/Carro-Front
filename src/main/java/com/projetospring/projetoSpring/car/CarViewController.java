package com.projetospring.projetoSpring.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/carro")
public class CarViewController {
    
    @Autowired
    private ICarRepository icarRepository;

    @GetMapping("/carroCRUD")
    public ModelAndView PaginaInicial(){
    ModelAndView mv= new ModelAndView("carroCRUD");
    mv.addObject("CarModel", new CarModel());
    return mv;
}

}
