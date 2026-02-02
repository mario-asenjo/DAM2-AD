package com.masenjo.springpersonal.controller;

import com.masenjo.springpersonal.model.Entrenador;
import com.masenjo.springpersonal.service.EntrenadorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/entrenadores")
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    public EntrenadorController(EntrenadorService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("entrenadores", entrenadorService.obtenerTodos());
        return "entrenadores/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("actionUrl", "/entrenadores");
        model.addAttribute("entrenador", new Entrenador());
        model.addAttribute("modo", "crear");
        return "entrenadores/form";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("entrenador") Entrenador entrenador,
                        BindingResult br,
                        Model model) {
        if (br.hasErrors()) {
            model.addAttribute("modo", "crear");
            return "entrenadores/form";
        }
        entrenadorService.guardar(entrenador);
        return "redirect:/entrenadores";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Entrenador entrenador = entrenadorService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Entrenador no encontrado"));
        model.addAttribute("actionUrl", "/entrenadores/" + id);
        model.addAttribute("entrenador", entrenador);
        model.addAttribute("modo", "editar");
        return "entrenadores/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("entrenador") Entrenador entrenador,
                             BindingResult br,
                             Model model) {
        if (br.hasErrors()) {
            model.addAttribute("modo", "editar");
            return "entrenadores/form";
        }
        entrenador.setId(id);
        entrenadorService.guardar(entrenador);
        return "redirect:/entrenadores";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        entrenadorService.eliminar(id);
        return "redirect:/entrenadores";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Entrenador entrenador = entrenadorService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Entrenador no encontrado"));
        model.addAttribute("entrenador", entrenador);
        return "entrenadores/detalle";
    }

}
