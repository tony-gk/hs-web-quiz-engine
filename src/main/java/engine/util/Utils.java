package engine.util;

import engine.domain.Quiz;
import engine.transfer.QuizDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Utils {
    public static QuizDto buildQuizDtoFromEntity(Quiz q) {
        return QuizDto.builder()
                .id(q.getId())
                .title(q.getTitle())
                .text(q.getText())
                .answer(q.getAnswer())
                .options(q.getOptions())
                .build();
    }

    public static  Quiz buildQuizEntityFromDto(QuizDto q) {
        return Quiz.builder()
                .id(q.getId())
                .title(q.getTitle())
                .text(q.getText())
                .answer(q.getAnswer())
                .options(q.getOptions())
                .build();
    }

    public static Long parseQuizId(String pathId) {
        try {
            return Long.parseLong(pathId);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
        }
    }
}
