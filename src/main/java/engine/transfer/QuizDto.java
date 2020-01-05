package engine.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
public class QuizDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @JsonProperty
    @NotBlank(message = "Quiz title is required")
    private String title;

    @JsonProperty
    @NotBlank(message = "Quiz tex is required")
    private String text;

    @JsonProperty
    @NotNull
    @Size(min = 2, message = "Quiz options should contain at least 2 items")
    private List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Integer> answer;
}
