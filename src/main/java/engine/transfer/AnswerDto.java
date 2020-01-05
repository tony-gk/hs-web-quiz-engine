package engine.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AnswerDto {
    @JsonProperty
    Set<Integer> answer = new HashSet<>();
}
