package fr.ceured.batismart.server.counter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("counter")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CounterEntity {

    @Id
    private String id;

    private Integer value;
    private String userId;

}
