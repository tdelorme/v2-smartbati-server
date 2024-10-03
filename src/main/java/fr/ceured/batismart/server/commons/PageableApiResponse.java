package fr.ceured.batismart.server.commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageableApiResponse<T> extends ApiResponse<T> {

    @JsonProperty("total_count")
    private long totalCount;

}
