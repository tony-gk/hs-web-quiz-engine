package engine.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    @JsonProperty
    @Pattern(regexp = ".+@.+\\..+", message = "Email is invalid")
    private String email;

    @JsonProperty
    @NotBlank(message = "Password is required")
    @Size(min = 5, message = "Password should contain at least 5 characters")
    private String password;
}
