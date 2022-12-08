package com.implementation.patterns.almacen.controllers;

import com.implementation.patterns.almacen.components.model.tables.pojos.Solicitud;
import com.implementation.patterns.almacen.model.GenerateRequestDto;
import com.implementation.patterns.almacen.model.GenerateRequestResponseDto;
import com.implementation.patterns.almacen.services.AlmacenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AlmacenController {

    @Autowired
    private AlmacenService almacenService;

    @GetMapping("findAll")
    public ResponseEntity<List<Solicitud>> findAll() {
        var holaMundo = almacenService.findAll();
        return new ResponseEntity<>(holaMundo, HttpStatus.OK);
    }

    @PostMapping("generateRequest")
    public ResponseEntity<GenerateRequestResponseDto> generateRequest(@RequestBody GenerateRequestDto generateRequestDto) {
        Solicitud solicitud = new Solicitud();

        solicitud.setDescripcion("Detail for New Request : " + generateRequestDto.getId());
        solicitud.setTitulo("New Request : " + generateRequestDto.getId());
        solicitud.setEstado("New");

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        solicitud.setFecha(localDate);
        solicitud.setIdRegistroExterno(generateRequestDto.getId());
        solicitud.setProcesoOrigen("EndPoint");

        var response = almacenService.generarSolicitudAlmacen(solicitud);
        var result = GenerateRequestResponseDto.builder().id(response).build();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("deliverOrder")
    public ResponseEntity<Boolean> deliverOrder(@RequestBody GenerateRequestDto generateRequestDto) {
        var deliverOrder = almacenService.deliverOrder(generateRequestDto.getId());
        return new ResponseEntity<>(deliverOrder, HttpStatus.OK);
    }
}
