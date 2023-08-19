package org.cargain.inventory.business.rules;

import org.cargain.common.utils.exceptions.BusinessException;
import org.cargain.inventory.entities.enums.State;
import org.cargain.inventory.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CarBusinessRules
{
    private final CarRepository repository;

    public void checkIfCarExists(UUID id)
    {
        if (!repository.existsById(id))
        {
            // TODO: BusinessException
            throw new RuntimeException("CAR_NOT_EXISTS");
        }
    }

    public void checkCarAvailability(UUID id)
    {
        var car = repository.findById(id).orElseThrow();
        if(!car.getState().equals(State.AVAILABLE))
            throw new BusinessException("CAR_NOT_AVAILABLE");
    }
}
