package fr.ceured.batismart.server.counter.service;

import fr.ceured.batismart.server.authentication.model.User;
import fr.ceured.batismart.server.counter.entity.CounterEntity;
import fr.ceured.batismart.server.counter.repository.CounterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounterService {

    private final CounterRepository counterRepository;

    public Integer getNextValueForUser(User user) {
        CounterEntity counterEntity = counterRepository.findByUserId(user.getId())
                .orElse(new CounterEntity(null, 0, user.getId()));

        Integer currentValue = counterEntity.getValue();
        ++currentValue;
        counterEntity.setValue(currentValue);

        counterRepository.save(counterEntity);

        return currentValue;
    }
}
