package org.cargain.inventory.business.abstracts;

import org.cargain.inventory.business.dto.requests.create.CreateModelRequest;
import org.cargain.inventory.business.dto.requests.update.UpdateModelRequest;
import org.cargain.inventory.business.dto.responses.create.CreateModelResponse;
import org.cargain.inventory.business.dto.responses.get.GetAllModelsResponse;
import org.cargain.inventory.business.dto.responses.get.GetModelResponse;
import org.cargain.inventory.business.dto.responses.update.UpdateModelResponse;

import java.util.List;
import java.util.UUID;

public interface ModelService {
    List<GetAllModelsResponse> getAll();
    GetModelResponse getById(UUID id);
    CreateModelResponse add(CreateModelRequest request);
    UpdateModelResponse update(UUID id, UpdateModelRequest request);
    void delete(UUID id);
}
