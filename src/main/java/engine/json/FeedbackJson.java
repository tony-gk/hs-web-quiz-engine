package engine.json;

public class FeedbackJson {
    private boolean success;
    private String feedback;

    private FeedbackJson(){}

    public static FeedbackJson rightAnswer() {
        FeedbackJson fb = new FeedbackJson();
        fb.success = true;
        fb.feedback = "Congratulations, you're right!";
        return fb;
    }
    public static FeedbackJson wrongAnswer() {
        FeedbackJson fb = new FeedbackJson();
        fb.success = false;
        fb.feedback = "Wrong answer! Please, try again.";
        return fb;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

