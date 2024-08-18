package mono.focusider.domain.quiz.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuiz is a Querydsl query type for Quiz
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuiz extends EntityPathBase<Quiz> {

    private static final long serialVersionUID = -1027048745L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuiz quiz = new QQuiz("quiz");

    public final mono.focusider.global.domain.QBaseTimeEntity _super = new mono.focusider.global.domain.QBaseTimeEntity(this);

    public final ListPath<Choice, QChoice> choices = this.<Choice, QChoice>createList("choices", Choice.class, QChoice.class, PathInits.DIRECT2);

    public final QCommentary commentary;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final ListPath<QuizAttempt, QQuizAttempt> quizAttempts = this.<QuizAttempt, QQuizAttempt>createList("quizAttempts", QuizAttempt.class, QQuizAttempt.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public QQuiz(String variable) {
        this(Quiz.class, forVariable(variable), INITS);
    }

    public QQuiz(Path<? extends Quiz> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuiz(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuiz(PathMetadata metadata, PathInits inits) {
        this(Quiz.class, metadata, inits);
    }

    public QQuiz(Class<? extends Quiz> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.commentary = inits.isInitialized("commentary") ? new QCommentary(forProperty("commentary")) : null;
    }

}

