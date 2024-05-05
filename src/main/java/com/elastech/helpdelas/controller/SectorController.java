package com.elastech.helpdelas.controller;


import com.elastech.helpdelas.dtos.SectorDTO;
import com.elastech.helpdelas.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @RequestMapping(value = "/registersector", method = RequestMethod.GET)
    public SectorDTO save(@RequestBody SectorDTO dto) {
        return this.sectorService.save(dto);

    }

    @RequestMapping(value = "/editsector/{id}", method = RequestMethod.PUT)
    public SectorDTO updateById(@PathVariable Long id, @RequestBody SectorDTO dto) {
        return this.sectorService.updateById(dto, id);
    }

    @RequestMapping(value = "/deletesector/{id}", method = RequestMethod.GET)
    public SectorDTO deleteById(@PathVariable Long id) {
        return sectorService.deleteById(id);
    }

    @RequestMapping(value = "/listsectors", method = RequestMethod.GET)
    public List<SectorDTO> findAll() {
        return this.sectorService.findAll();
    }

    @RequestMapping(value = "/viewsector/{id}", method = RequestMethod.GET)
    public SectorDTO findById(@PathVariable Long id) {
        return this.sectorService.findById(id);
    }

}

