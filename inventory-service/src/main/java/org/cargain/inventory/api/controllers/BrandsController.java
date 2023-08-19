package org.cargain.inventory.api.controllers;

import org.cargain.inventory.business.abstracts.BrandService;
import org.cargain.inventory.business.dto.requests.create.CreateBrandRequest;
import org.cargain.inventory.business.dto.requests.update.UpdateBrandRequest;
import org.cargain.inventory.business.dto.responses.create.CreateBrandResponse;
import org.cargain.inventory.business.dto.responses.get.GetAllBrandsResponse;
import org.cargain.inventory.business.dto.responses.get.GetBrandResponse;
import org.cargain.inventory.business.dto.responses.update.UpdateBrandResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/brands")
public class BrandsController {
    private final BrandService service;

    @GetMapping
    public List<GetAllBrandsResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetBrandResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBrandResponse add(@Valid @RequestBody CreateBrandRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateBrandResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateBrandRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
