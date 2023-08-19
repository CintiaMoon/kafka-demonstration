package org.cargain.inventory.api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.cargain.common.utils.dto.responses.CarClientResponse;
import org.cargain.common.utils.dto.responses.ClientResponse;
import org.cargain.inventory.business.abstracts.CarService;
import org.cargain.inventory.business.dto.requests.create.CreateCarRequest;
import org.cargain.inventory.business.dto.requests.update.UpdateCarRequest;
import org.cargain.inventory.business.dto.responses.create.CreateCarResponse;
import org.cargain.inventory.business.dto.responses.get.GetAllCarsResponse;
import org.cargain.inventory.business.dto.responses.get.GetCarResponse;
import org.cargain.inventory.business.dto.responses.update.UpdateCarResponse;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cars")
public class CarsController {
    private final CarService service;

    @GetMapping
    public List<GetAllCarsResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetCarResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCarResponse add(@Valid @RequestBody CreateCarRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateCarResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateCarRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/check-car-available/{id}")
    public ClientResponse checkIfCarAvailable(@PathVariable UUID id)
    {
         return service.checkIfCarAvailable(id);
    }

    @GetMapping("/get-car-for-invoice/{carId}")
    public CarClientResponse getCarForInvoice(@PathVariable UUID carId)
    {
        return service.getCarForInvoice(carId);
    }
}
