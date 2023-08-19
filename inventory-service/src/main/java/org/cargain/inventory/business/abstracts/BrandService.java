package org.cargain.inventory.business.abstracts;

import org.cargain.inventory.business.dto.requests.create.CreateBrandRequest;
import org.cargain.inventory.business.dto.requests.update.UpdateBrandRequest;
import org.cargain.inventory.business.dto.responses.create.CreateBrandResponse;
import org.cargain.inventory.business.dto.responses.get.GetAllBrandsResponse;
import org.cargain.inventory.business.dto.responses.get.GetBrandResponse;
import org.cargain.inventory.business.dto.responses.update.UpdateBrandResponse;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<GetAllBrandsResponse> getAll();
    GetBrandResponse getById(UUID id);
    CreateBrandResponse add(CreateBrandRequest request);
    UpdateBrandResponse update(UUID id, UpdateBrandRequest request);
    void delete(UUID id);
}
