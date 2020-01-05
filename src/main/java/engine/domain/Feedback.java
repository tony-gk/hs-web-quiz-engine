package engine.domain;

import lombok.Data;

@Data
public class Feedback {
    private boolean success;
    private String feedback;

    private Feedback(){}

    public static Feedback rightAnswer() {
        Feedback fb = new Feedback();
        fb.success = true;
        fb.feedback = "Congratulations, you're right!";
        return fb;
    }
    public static Feedback wrongAnswer() {
        Feedback fb = new Feedback();
        fb.success = false;
        fb.feedback = "Wrong answer! Please, try again.";
        return fb;
    }
}

