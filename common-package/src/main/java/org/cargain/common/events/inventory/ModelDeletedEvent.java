package org.cargain.common.events.inventory;

import org.cargain.common.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModelDeletedEvent implements Event
{
    private UUID modelId;
}
