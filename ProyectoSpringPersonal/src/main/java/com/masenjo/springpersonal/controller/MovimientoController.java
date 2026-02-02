package com.masenjo.springpersonal.controller;

import com.masenjo.springpersonal.model.Movimiento;
import com.masenjo.springpersonal.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("movimientos", movimientoService.obtenerTodos());
        return "movimientos/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("actionUrl", "/movimientos");
        model.addAttribute("movimiento", new Movimiento());
        model.addAttribute("modo", "crear");
        return "movimientos/form";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute("movimiento") Movimiento movimiento,
                        BindingResult br,
                        Model model) {
        if (br.hasErrors()) {
            model.addAttribute("modo", "crear");
            return "movimientos/form";
        }
        movimientoService.guardar(movimiento);
        return "redirect:/movimientos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Movimiento movimiento = movimientoService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado"));
        model.addAttribute("movimiento", movimiento);
        model.addAttribute("modo", "editar");
        model.addAttribute("actionUrl", "/movimientos/" + id);
        return "movimientos/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id,
                             @Valid @ModelAttribute("movimiento") Movimiento movimiento,
                             BindingResult br,
                             Model model) {
        if (br.hasErrors()) {
            model.addAttribute("modo", "editar");
            return "movimientos/form";
        }
        movimiento.setId(id);
        movimientoService.guardar(movimiento);
        return "redirect:/movimientos";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        movimientoService.eliminar(id);
        return "redirect:/movimientos";
    }
}
