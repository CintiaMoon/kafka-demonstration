package org.cargain.filter.business.kafka.consumer;

import org.cargain.common.events.inventory.BrandDeletedEvent;
import org.cargain.common.events.inventory.CarCreatedEvent;
import org.cargain.common.events.inventory.CarDeletedEvent;
import org.cargain.common.events.inventory.ModelDeletedEvent;
import org.cargain.common.utils.mappers.ModelMapperService;
import org.cargain.filter.business.abstracts.FilterService;
import org.cargain.filter.entities.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryConsumer
{
    private final FilterService service;
    private final ModelMapperService mapper;

    @KafkaListener
    (
        topics = "car-created",
        groupId = "car-create"
    )
    public void consume(CarCreatedEvent event)
    {
        var filter = mapper.forRequest().map(event, Filter.class);
        service.add(filter);
        log.info("Car created event consumed {}", event);
    }

    @KafkaListener
    (
            topics = "car-deleted",
            groupId = "car-delete"
    )
    public void consume(CarDeletedEvent event)
    {
        service.deleteByCarId(event.getCarId());
        log.info("Car deleted event consumed {}", event);
    }

    @KafkaListener
    (
            topics = "model-deleted",
            groupId = "model-delete"
    )
    public void consume(ModelDeletedEvent event)
    {
        service.deleteAllByModelId(event.getModelId());
        log.info("Model deleted event consumed {}", event);
    }

    @KafkaListener
    (
            topics = "brand-deleted",
            groupId = "brand-delete"
    )
    public void consume(BrandDeletedEvent event)
    {
        service.deleteAllByBrandId(event.getBrandId());
        log.info("Brand deleted event consumed {}", event);
    }
}
