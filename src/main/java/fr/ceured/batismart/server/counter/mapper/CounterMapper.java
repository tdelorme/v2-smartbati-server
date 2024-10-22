package fr.ceured.batismart.server.counter.mapper;

import fr.ceured.batismart.server.counter.entity.CounterEntity;
import fr.ceured.batismart.server.counter.model.Counter;
import org.mapstruct.Mapper;

@Mapper
public interface CounterMapper {
    CounterEntity toCounterEntity(Counter counter);
    Counter toCounter(CounterEntity counterEntity);
}
