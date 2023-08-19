package org.cargain.inventory.business.concretes;

import org.cargain.common.events.inventory.ModelDeletedEvent;
import org.cargain.common.utils.kafka.producer.KafkaProducer;
import org.cargain.common.utils.mappers.ModelMapperService;
import org.cargain.inventory.business.abstracts.ModelService;
import org.cargain.inventory.business.dto.requests.create.CreateModelRequest;
import org.cargain.inventory.business.dto.requests.update.UpdateModelRequest;
import org.cargain.inventory.business.dto.responses.create.CreateModelResponse;
import org.cargain.inventory.business.dto.responses.get.GetAllModelsResponse;
import org.cargain.inventory.business.dto.responses.get.GetModelResponse;
import org.cargain.inventory.business.dto.responses.update.UpdateModelResponse;
import org.cargain.inventory.business.rules.ModelBusinessRules;
import org.cargain.inventory.entities.Model;
import org.cargain.inventory.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService
{
    private final ModelRepository repository;
    private final ModelMapperService mapper;
    private final ModelBusinessRules rules;
    private final KafkaProducer producer;

    @Override
    public List<GetAllModelsResponse> getAll()
    {
        var models = repository.findAll();
        var response = models
                .stream()
                .map(model -> mapper.forResponse().map(model, GetAllModelsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetModelResponse getById(UUID id)
    {
        rules.checkIfModelExists(id);
        var model = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(model, GetModelResponse.class);

        return response;
    }

    @Override
    public CreateModelResponse add(CreateModelRequest request)
    {
        var model = mapper.forRequest().map(request, Model.class);
        model.setId(null);
        repository.save(model);
        var response = mapper.forResponse().map(model, CreateModelResponse.class);

        return response;
    }

    @Override
    public UpdateModelResponse update(UUID id, UpdateModelRequest request)
    {
        rules.checkIfModelExists(id);
        var model = mapper.forRequest().map(request, Model.class);
        model.setId(id);
        repository.save(model);
        var response = mapper.forResponse().map(model, UpdateModelResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id)
    {
        rules.checkIfModelExists(id);
        repository.deleteById(id);

        producer.sendMessage(new ModelDeletedEvent(id), "model-deleted");
    }
}
