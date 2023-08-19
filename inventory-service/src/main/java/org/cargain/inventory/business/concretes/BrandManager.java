package org.cargain.inventory.business.concretes;

import org.cargain.common.events.inventory.BrandDeletedEvent;
import org.cargain.common.utils.kafka.producer.KafkaProducer;
import org.cargain.common.utils.mappers.ModelMapperService;
import org.cargain.inventory.business.abstracts.BrandService;
import org.cargain.inventory.business.dto.requests.create.CreateBrandRequest;
import org.cargain.inventory.business.dto.requests.update.UpdateBrandRequest;
import org.cargain.inventory.business.dto.responses.create.CreateBrandResponse;
import org.cargain.inventory.business.dto.responses.get.GetAllBrandsResponse;
import org.cargain.inventory.business.dto.responses.get.GetBrandResponse;
import org.cargain.inventory.business.dto.responses.update.UpdateBrandResponse;
import org.cargain.inventory.business.rules.BrandBusinessRules;
import org.cargain.inventory.entities.Brand;
import org.cargain.inventory.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService
{
    private final BrandRepository repository;
    private final ModelMapperService mapper;
    private final BrandBusinessRules rules;
    private final KafkaProducer producer;

    @Override
    public List<GetAllBrandsResponse> getAll() {
        var brands = repository.findAll();
        var response = brands
                .stream()
                .map(brand -> mapper.forResponse().map(brand, GetAllBrandsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetBrandResponse getById(UUID id) {
        rules.checkIfBrandExists(id);
        var brand = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(brand, GetBrandResponse.class);

        return response;
    }

    @Override
    public CreateBrandResponse add(CreateBrandRequest request) {
        var brand = mapper.forRequest().map(request, Brand.class);
        repository.save(brand);
        var response = mapper.forResponse().map(brand, CreateBrandResponse.class);

        return response;
    }

    @Override
    public UpdateBrandResponse update(UUID id, UpdateBrandRequest request) {
        rules.checkIfBrandExists(id);
        var brand = mapper.forRequest().map(request, Brand.class);
        brand.setId(id);
        repository.save(brand);
        var response = mapper.forResponse().map(brand, UpdateBrandResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfBrandExists(id);
        repository.deleteById(id);

        sendKafkaBrandDeletedEvent(id);
    }

    private void sendKafkaBrandDeletedEvent(UUID id)
    {
        producer.sendMessage(new BrandDeletedEvent(id), "brand-deleted");
    }

}
