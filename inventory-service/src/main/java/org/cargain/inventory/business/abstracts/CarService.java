package org.cargain.inventory.business.abstracts;

import org.cargain.common.utils.dto.responses.CarClientResponse;
import org.cargain.common.utils.dto.responses.ClientResponse;
import org.cargain.inventory.business.dto.requests.create.CreateCarRequest;
import org.cargain.inventory.business.dto.requests.update.UpdateCarRequest;
import org.cargain.inventory.business.dto.responses.create.CreateCarResponse;
import org.cargain.inventory.business.dto.responses.get.GetAllCarsResponse;
import org.cargain.inventory.business.dto.responses.get.GetCarResponse;
import org.cargain.inventory.business.dto.responses.update.UpdateCarResponse;
import org.cargain.inventory.entities.enums.State;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<GetAllCarsResponse> getAll();
    GetCarResponse getById(UUID id);
    CarClientResponse getCarForInvoice(UUID id);
    CreateCarResponse add(CreateCarRequest request);
    UpdateCarResponse update(UUID id, UpdateCarRequest request);
    void delete(UUID id);
    ClientResponse checkIfCarAvailable(UUID id);
    void changeStateByCarId(State state, UUID id);
}
