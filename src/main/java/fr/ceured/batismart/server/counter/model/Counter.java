package fr.ceured.batismart.server.counter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Counter {
    private String id;

    private Integer value;
    private String userId;
}
