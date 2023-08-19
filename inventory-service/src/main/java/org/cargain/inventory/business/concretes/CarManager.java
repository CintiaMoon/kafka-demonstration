package org.cargain.inventory.business.concretes;

import org.cargain.common.events.inventory.CarCreatedEvent;
import org.cargain.common.events.inventory.CarDeletedEvent;
import org.cargain.common.utils.dto.responses.CarClientResponse;
import org.cargain.common.utils.dto.responses.ClientResponse;
import org.cargain.common.utils.exceptions.BusinessException;
import org.cargain.common.utils.kafka.producer.KafkaProducer;
import org.cargain.common.utils.mappers.ModelMapperService;
import org.cargain.inventory.business.abstracts.CarService;
import org.cargain.inventory.business.dto.requests.create.CreateCarRequest;
import org.cargain.inventory.business.dto.requests.update.UpdateCarRequest;
import org.cargain.inventory.business.dto.responses.create.CreateCarResponse;
import org.cargain.inventory.business.dto.responses.get.GetAllCarsResponse;
import org.cargain.inventory.business.dto.responses.get.GetCarResponse;
import org.cargain.inventory.business.dto.responses.update.UpdateCarResponse;
import org.cargain.inventory.business.rules.CarBusinessRules;
import org.cargain.inventory.entities.Car;
import org.cargain.inventory.entities.enums.State;
import org.cargain.inventory.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CarManager implements CarService
{
    private final CarRepository repository;
    private final ModelMapperService mapper;
    private final CarBusinessRules rules;
    private final KafkaProducer producer;

    @Override
    public List<GetAllCarsResponse> getAll()
    {
        var cars = repository.findAll();
        var response = cars
                .stream()
                .map(car -> mapper.forResponse().map(car, GetAllCarsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetCarResponse getById(UUID id)
    {
        rules.checkIfCarExists(id);
        var car = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(car, GetCarResponse.class);

        return response;
    }

    @Override
    public CarClientResponse getCarForInvoice(UUID id)
    {
        rules.checkIfCarExists(id);
        var car = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(car, CarClientResponse.class);
        response.setSuccess(true);

        return response;
    }

    @Override
    public CreateCarResponse add(CreateCarRequest request)
    {
        var car = mapper.forRequest().map(request, Car.class);
        car.setId(UUID.randomUUID());
        car.setState(State.AVAILABLE);
        var createdCar = repository.save(car);

        //car created event -> refactor, sendKafkaCarCreatedEvent
        sendKafkaCarCreatedEvent(createdCar);

        var response = mapper.forResponse().map(createdCar, CreateCarResponse.class);
        return response;
    }

    @Override
    public UpdateCarResponse update(UUID id, UpdateCarRequest request)
    {
        rules.checkIfCarExists(id);
        var car = mapper.forRequest().map(request, Car.class);
        car.setId(id);
        repository.save(car);
        var response = mapper.forResponse().map(car, UpdateCarResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id)
    {
        rules.checkIfCarExists(id);
        repository.deleteById(id);

        sendKafkaCarDeletedEvent(id);
    }

    @Override
    public ClientResponse checkIfCarAvailable(UUID id)
    {
        var response = new ClientResponse();
        validateCarAvailability(id, response);

        return response;
    }

    @Override
    public void changeStateByCarId(State state, UUID id)
    {
        repository.changeStateByCarId(state, id);
    }

    private void sendKafkaCarCreatedEvent(Car createdCar)
    {
        var event = mapper.forResponse().map(createdCar, CarCreatedEvent.class);
        producer.sendMessage(event, "car-created");
    }

    private void sendKafkaCarDeletedEvent(UUID id)
    {
        producer.sendMessage( new CarDeletedEvent(id), "car-deleted");
    }

    private void validateCarAvailability(UUID id, ClientResponse response)
    {
        try
        {
            rules.checkIfCarExists(id);
            rules.checkCarAvailability(id);
            response.setSuccess(true);
        }
        catch(BusinessException exception)
        {
            response.setSuccess(false);
            response.setMessage(exception.getMessage());
        }
    }
}
