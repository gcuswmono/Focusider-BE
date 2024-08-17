package mono.focusider.domain.quiz.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChoice is a Querydsl query type for Choice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChoice extends EntityPathBase<Choice> {

    private static final long serialVersionUID = 435985827L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChoice choice = new QChoice("choice");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAnswer = createBoolean("isAnswer");

    public final QQuiz quiz;

    public QChoice(String variable) {
        this(Choice.class, forVariable(variable), INITS);
    }

    public QChoice(Path<? extends Choice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChoice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChoice(PathMetadata metadata, PathInits inits) {
        this(Choice.class, metadata, inits);
    }

    public QChoice(Class<? extends Choice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.quiz = inits.isInitialized("quiz") ? new QQuiz(forProperty("quiz")) : null;
    }

}

