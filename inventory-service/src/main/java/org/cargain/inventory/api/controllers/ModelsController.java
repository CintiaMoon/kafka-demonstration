package org.cargain.inventory.api.controllers;

import org.cargain.inventory.business.abstracts.ModelService;
import org.cargain.inventory.business.dto.requests.create.CreateModelRequest;
import org.cargain.inventory.business.dto.requests.update.UpdateModelRequest;
import org.cargain.inventory.business.dto.responses.create.CreateModelResponse;
import org.cargain.inventory.business.dto.responses.get.GetAllModelsResponse;
import org.cargain.inventory.business.dto.responses.get.GetModelResponse;
import org.cargain.inventory.business.dto.responses.update.UpdateModelResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/models")
public class ModelsController {
    private final ModelService service;

    @GetMapping
    public List<GetAllModelsResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetModelResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateModelResponse add(@Valid @RequestBody CreateModelRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateModelResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateModelRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
