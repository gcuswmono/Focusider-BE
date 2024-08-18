package mono.focusider.domain.quiz.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuizAttempt is a Querydsl query type for QuizAttempt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuizAttempt extends EntityPathBase<QuizAttempt> {

    private static final long serialVersionUID = -726638602L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuizAttempt quizAttempt = new QQuizAttempt("quizAttempt");

    public final mono.focusider.global.domain.QBaseTimeEntity _super = new mono.focusider.global.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final QQuiz quiz;

    public final EnumPath<mono.focusider.domain.quiz.type.QuizStatusType> quizStatusType = createEnum("quizStatusType", mono.focusider.domain.quiz.type.QuizStatusType.class);

    public QQuizAttempt(String variable) {
        this(QuizAttempt.class, forVariable(variable), INITS);
    }

    public QQuizAttempt(Path<? extends QuizAttempt> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuizAttempt(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuizAttempt(PathMetadata metadata, PathInits inits) {
        this(QuizAttempt.class, metadata, inits);
    }

    public QQuizAttempt(Class<? extends QuizAttempt> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.quiz = inits.isInitialized("quiz") ? new QQuiz(forProperty("quiz"), inits.get("quiz")) : null;
    }

}

