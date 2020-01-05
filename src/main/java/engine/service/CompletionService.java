package engine.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import engine.domain.Completion;
import engine.repository.CompletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompletionService {
    private final CompletionRepository completionRepository;

    @Autowired
    public CompletionService(CompletionRepository completionRepository) {
        this.completionRepository = completionRepository;
    }

    public Page<Completion> getPage(int pageNumber, String userEmail) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("completedAt").descending());
        return completionRepository.findAllByUserEmail(userEmail, pageable);
    }
}
