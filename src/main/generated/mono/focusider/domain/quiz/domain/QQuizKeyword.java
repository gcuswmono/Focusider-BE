package mono.focusider.domain.quiz.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuizKeyword is a Querydsl query type for QuizKeyword
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuizKeyword extends EntityPathBase<QuizKeyword> {

    private static final long serialVersionUID = -865817838L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuizKeyword quizKeyword = new QQuizKeyword("quizKeyword");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QKeyword keyword;

    public final QQuiz quiz;

    public QQuizKeyword(String variable) {
        this(QuizKeyword.class, forVariable(variable), INITS);
    }

    public QQuizKeyword(Path<? extends QuizKeyword> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuizKeyword(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuizKeyword(PathMetadata metadata, PathInits inits) {
        this(QuizKeyword.class, metadata, inits);
    }

    public QQuizKeyword(Class<? extends QuizKeyword> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.keyword = inits.isInitialized("keyword") ? new QKeyword(forProperty("keyword")) : null;
        this.quiz = inits.isInitialized("quiz") ? new QQuiz(forProperty("quiz"), inits.get("quiz")) : null;
    }

}

