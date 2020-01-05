package engine.controller;

import engine.domain.Quiz;
import engine.transfer.AnswerDto;
import engine.transfer.CompletionDto;
import engine.transfer.FeedbackDto;
import engine.service.CompletionService;
import engine.service.QuizService;
import engine.transfer.QuizDto;
import engine.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class QuizApi {

    private final QuizService quizService;
    private final CompletionService completionService;

    @Autowired
    public QuizApi(QuizService quizService, CompletionService completionService) {
        this.quizService = quizService;
        this.completionService = completionService;
    }

    @GetMapping(value = "/api/quizzes/{id}")
    public QuizDto getQuiz(@PathVariable String id) {
        return Utils.buildQuizDtoFromEntity(quizService.getById(Utils.parseQuizId(id)));
    }

    @GetMapping("/api/quizzes")
    public Page<QuizDto> getQuizzes(@RequestParam(name = "page", defaultValue = "0") int pageNumber) {
        return quizService.getPage(pageNumber)
                .map(Utils::buildQuizDtoFromEntity);
    }

    @GetMapping("/api/quizzes/completed")
    public Page<CompletionDto> getCompletions(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @Autowired Principal principal
    ) {
        return completionService.getPage(pageNumber, principal.getName())
                .map(completion ->
                        new CompletionDto(completion.getQuiz().getId(), completion.getCompletedAt())
                );
    }

    @PostMapping(value = "/api/quizzes/{id}/solve")
    public FeedbackDto solveQuiz(@PathVariable String id, @RequestBody AnswerDto answer, Principal principal) {
        if (quizService.solve(Utils.parseQuizId(id), answer.getAnswer(), principal.getName())) {
            return FeedbackDto.rightAnswer();
        } else {
            return FeedbackDto.wrongAnswer();
        }
    }

    @PostMapping("/api/quizzes")
    public QuizDto createNew(@RequestBody @Valid QuizDto quizDto, Principal principal) {
        Quiz quiz = Utils.buildQuizEntityFromDto(quizDto);
        quizService.save(quiz, principal.getName());
        return Utils.buildQuizDtoFromEntity(quiz);
    }

    @DeleteMapping("/api/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable String id, Principal principal) {
        quizService.delete(Utils.parseQuizId(id), principal.getName());
    }

    @PatchMapping("/api/quizzes/{id}")
    public void updateQuiz(@PathVariable String id, @RequestBody Quiz quiz, Principal principal) {
        quizService.update(Utils.parseQuizId(id), quiz, principal.getName());
    }

}
