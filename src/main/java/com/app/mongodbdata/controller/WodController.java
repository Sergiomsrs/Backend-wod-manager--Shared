package com.app.mongodata.controller;


import com.app.mongodata.dao.IWodDao;
import com.app.mongodata.model.Wod;
import com.app.mongodata.service.IWodService;
import com.app.mongodata.statistics.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })


@RestController
@RequestMapping("/list")
public class WodController {
    @Autowired
    private IWodService service;
    @Autowired
    private IWodDao dao;
    @Autowired
    private StatisticsService statisticsService;
    @PostMapping
    public Wod guardarWod(@RequestBody Wod wod) {
        return service.guardar(wod);
    }

    @GetMapping("/listar")
    public List<Wod> listarTodos() {
        return service.buscar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wod> buscarWodPorId(@PathVariable String id) {
        Wod wod = service.burcarPorId(id);

        if (wod != null) {
            return new ResponseEntity<>(wod, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ultimoId")
    public String obtenerUltimoId() {
        // Usar el método del repositorio para obtener el último ID
        Optional<Wod> ultimaEntrada = dao.findTopByOrderByIdDesc();

        if (ultimaEntrada.isPresent()) {
            return ultimaEntrada.get().getId();
        } else {
            return "No hay entradas en la base de datos.";
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public void delete(@PathVariable("id") String id) {
        service.eliminar(id);
    }

    @GetMapping("/total")
    public long obtenerRecuentoTotal() {
        return statisticsService.obtenerRecuentoTotal();
    }

    @GetMapping("/mostrepeat")
    public String getMostRepeatedMovement() {
        return statisticsService.obtenerMovimientoMasRepetido();
    }


    @GetMapping("/listarMov")
    public List<String> listarTodosLosMovimientos() {
        return statisticsService.obtenerTodosLosMovimientos();
    }

    @GetMapping("/buscar/{mov}")
    public List<Wod> buscarRegistrosConPushUp(@PathVariable("mov") String mov) {
        return statisticsService.buscarRegistrosConPushUp(mov);
    }





}
