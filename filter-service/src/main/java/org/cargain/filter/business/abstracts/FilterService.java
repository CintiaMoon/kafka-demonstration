package org.cargain.filter.business.abstracts;

import org.cargain.filter.business.dto.responses.GetAllFiltersResponse;
import org.cargain.filter.business.dto.responses.GetFilterResponse;
import org.cargain.filter.entities.Filter;

import java.util.List;
import java.util.UUID;

public interface FilterService
{
    List<GetAllFiltersResponse> getAll();
    GetFilterResponse getById(UUID id);

    //Ağıdakiler dış dünyaya açık değil
    void add(Filter filter);
    void delete(UUID id);
    void deleteAllByBrandId(UUID brandId);
    void deleteAllByModelId(UUID modelId);
    void deleteByCarId(UUID carId);
    Filter getByCarId(UUID carId);
}
