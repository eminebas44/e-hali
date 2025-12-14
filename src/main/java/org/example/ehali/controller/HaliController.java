package org.example.ehali.controller;

import org.example.ehali.dto.HaliDTO;
import org.example.ehali.dto.HaliGetirDTO;
import org.example.ehali.entity.Hali;
import org.example.ehali.service.HaliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/halilar")
public class HaliController {

    private final HaliService haliService;

    @Autowired
    public HaliController(HaliService haliService) {
        this.haliService = haliService;
    }

    @GetMapping
    public ResponseEntity<List<HaliGetirDTO>> getAllHalilar() {
        List<HaliGetirDTO> halilar = haliService.findAllHalilarAsDTO();
        return new ResponseEntity<>(halilar, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hali> getHaliById(@PathVariable Long id) {
        return haliService.findById(id)
                .map(hali -> new ResponseEntity<>(hali, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Hali> createHali(@RequestBody HaliDTO haliDTO) {
        try {
            Hali yeniHali = haliService.createHaliFromDTO(haliDTO);
            return new ResponseEntity<>(yeniHali, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hali> updateHali(@PathVariable Long id, @RequestBody HaliDTO haliDTO) {
        try {
            Hali guncelHali = haliService.updateHaliFromDTO(id, haliDTO);
            return new ResponseEntity<>(guncelHali, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteHali(@PathVariable Long id) {
        try {
            haliService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
