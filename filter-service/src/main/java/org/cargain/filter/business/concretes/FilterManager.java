package org.cargain.filter.business.concretes;

import org.cargain.common.utils.mappers.ModelMapperService;
import org.cargain.filter.business.abstracts.FilterService;
import org.cargain.filter.business.dto.responses.GetAllFiltersResponse;
import org.cargain.filter.business.dto.responses.GetFilterResponse;
import org.cargain.filter.entities.Filter;
import org.cargain.filter.repository.FilterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
//@RequiredArgsConstructor -> final yazılanlar static ile eşitlenir, final olmayanlar...
public class FilterManager implements FilterService
{
    private final FilterRepository repository;
    private ModelMapperService mapper;

    @Override
    public List<GetAllFiltersResponse> getAll()
    {
        var filters = repository.findAll();
        var response = filters
                .stream()
                .map(filter -> mapper.forResponse().map(filter, GetAllFiltersResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetFilterResponse getById(UUID id)
    {
        var filter = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(filter, GetFilterResponse.class);

        return response;
    }

    @Override
    public void add(Filter filter)
    {
        repository.save(filter);
    }

    @Override
    public void delete(UUID id)
    {
        repository.deleteById(id);
    }


    @Override
    public void deleteAllByBrandId(UUID brandId)
    {
        repository.deleteAllByBrandId(brandId);
    }

    @Override
    public void deleteByCarId(UUID carId)
    {
        repository.deleteByCarId(carId);
    }

    @Override
    public void deleteAllByModelId(UUID modelId)
    {
        repository.deleteAllByModelId(modelId);
    }
    @Override
    public Filter getByCarId(UUID carId)
    {
        return repository.findByCarId(carId);
    }
}
