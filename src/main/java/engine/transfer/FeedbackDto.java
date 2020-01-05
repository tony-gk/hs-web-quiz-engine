package engine.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class FeedbackDto {
    @JsonProperty
    private boolean success;
    @JsonProperty
    private String feedback;

    private FeedbackDto(){}

    public static FeedbackDto rightAnswer() {
        FeedbackDto fb = new FeedbackDto();
        fb.success = true;
        fb.feedback = "Congratulations, you're right!";
        return fb;
    }
    public static FeedbackDto wrongAnswer() {
        FeedbackDto fb = new FeedbackDto();
        fb.success = false;
        fb.feedback = "Wrong answer! Please, try again.";
        return fb;
    }
}

