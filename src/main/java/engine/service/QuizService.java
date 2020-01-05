package engine.service;

import engine.domain.Completion;
import engine.domain.Quiz;
import engine.domain.User;
import engine.repository.CompletionRepository;
import engine.repository.QuizRepository;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final CompletionRepository completionRepository;

    @Autowired
    public QuizService(CompletionService completionService, QuizRepository quizRepository, UserRepository userRepository, CompletionRepository completionRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.completionRepository = completionRepository;
    }

    public Page<Quiz> getPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10);
        return quizRepository.findAll(pageable);
    }

    public Quiz getById(long id) {
        return quizRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));
    }

    public void save(Quiz quiz, String authorEmail) {
        User author = userRepository.findByEmail(authorEmail);
        if (author == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User not found");
        }
        quiz.setAuthor(author);
        quizRepository.save(quiz);
    }

    public void delete(Long id, String userEmail) {
        Quiz quiz = getById(id);
        if (!userEmail.equals(quiz.getAuthor().getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }
        quizRepository.delete(quiz);
    }

    public void update(Long id, Quiz quiz, String userEmail) {
        Quiz quizFromDb = getById(id);
        if (!userEmail.equals(quizFromDb.getAuthor().getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }
        quizFromDb.setTitle(quiz.getTitle());
        quizFromDb.setText(quiz.getText());
        quizFromDb.setOptions(quiz.getOptions());
        quizFromDb.setAnswer(quiz.getAnswer());
    }

    public boolean solve(Long id, Set<Integer> answer, String userEmail) {
        Quiz quiz = getById(id);
        if (quiz.getAnswer().equals(answer)) {
            User user = userRepository.findByEmail(userEmail);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User not found");
            }
            completionRepository.save(new Completion(quiz, user));
            return true;
        } else {
            return false;
        }
    }
}
