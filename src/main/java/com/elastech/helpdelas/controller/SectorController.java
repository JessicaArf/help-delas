package com.elastech.helpdelas.controller;
import com.elastech.helpdelas.dtos.TicketDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;


@Controller
public class SectorController {

    @Autowired
    private UserService userService;
    @Autowired
    private SectorService sectorService;

    @PostMapping("/registrar-setor")
    public String save(SectorDTO dto, RedirectAttributes redirectAttributes) throws Exception {
        try{
            this.sectorService.save(dto);
            return "redirect:/dashboard-admin";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/registrar-setor";
        }
    }

    @GetMapping("/registrar-setor")
    public String save(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
        model.addAttribute("name", userDb.getName());
        return "sector/register-sector";
    }

    @GetMapping("/listar-setor")
    public String findAllSector(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDb = userService.getUserByEmail(userDetails.getUsername());
        List<SectorDTO> findAllSector = sectorService.findAllSector();
        model.addAttribute("name", userDb.getName());
        model.addAttribute("findAllSector", findAllSector);
        return "sector/find-sector";
    }

    @GetMapping("/visualizar-setor/{sectorId}")
    public SectorDTO findById(@PathVariable Long sectorId) {
        return this.sectorService.findById(sectorId);
    }

    @GetMapping("/editar-setor/{sectorId}")
    public String showEditSector(@PathVariable Long sectorId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userAdmin = userService.getUserByEmail(userDetails.getUsername());
        SectorDTO sectorDTO = sectorService.findById(sectorId);
        model.addAttribute("name", userAdmin.getEmail());
        model.addAttribute("sector", sectorDTO);
        return "sector/update-sector";
    }

    @PutMapping("/editar-setor/{sectorId}")
    public String SectorUpdate(@PathVariable Long sectorId, @ModelAttribute SectorDTO sector, @AuthenticationPrincipal UserDetails userDetails){
        SectorDTO sectorDTO = sectorService.updateById(sector, sectorId);
        return "redirect:/listar-setor";
    }

    @DeleteMapping("/editar-setor/{sectorId}")
    public String deleteSector(@PathVariable Long  sectorId, Model model, RedirectAttributes redirectAttributes) {
        List<UserDTO> users = userService.showAllUsersWithSector(sectorId);
        try{
            if(users.isEmpty()){
                sectorService.deleteById(sectorId);
                return "redirect:/listar-setor";
            }
        }catch (Exception e){
            redirectAttributes.addAttribute("error", true);
        }
        return "redirect:/listar-setor";
     }
}
