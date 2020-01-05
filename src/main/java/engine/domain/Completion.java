package engine.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "completion")
@Data
@NoArgsConstructor
public class Completion {
    @Id
    @GeneratedValue
    private long id;

    @CreationTimestamp
    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Completion(Quiz quiz, User user) {
        this.quiz = quiz;
        this.user = user;

    }
}
