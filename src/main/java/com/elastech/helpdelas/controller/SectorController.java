package com.elastech.helpdelas.controller;
import org.springframework.ui.Model;
import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.dtos.UserDTO;
import com.elastech.helpdelas.service.SectorService;
import com.elastech.helpdelas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
public class SectorController {

    @Autowired
    private UserService userService;
    @Autowired
    private SectorService sectorService;

    @PostMapping("/registrar-setor")
    public String save(SectorDTO dto) {
        this.sectorService.save(dto);
        return "/dashboard-admin";
    }

    @GetMapping("/registrar-setor")
    public String save(@AuthenticationPrincipal UserDetails userDetails, Model model){
        UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", userDb.getName());
        return "sector/register-sector";
    }

    @PutMapping(value = "/editar-setor/{id}")
    public SectorDTO updateById(@PathVariable Long id, @RequestBody SectorDTO dto) {
        return this.sectorService.updateById(dto, id);

    }

    @GetMapping(value = "/seletar-setor/{id}")
    public SectorDTO deleteById(@PathVariable Long id) {
        return sectorService.deleteById(id);
    }

    @GetMapping(value = "/listar-setor")
    public List<SectorDTO> findAll() {
        return this.sectorService.findAll();
    }

    @GetMapping(value = "/visualizar-setor/{id}")
    public SectorDTO findById(@PathVariable Long id) {
        return this.sectorService.findById(id);
    }

}