package mono.focusider.domain.quiz.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommentary is a Querydsl query type for Commentary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentary extends EntityPathBase<Commentary> {

    private static final long serialVersionUID = 2134076075L;

    public static final QCommentary commentary = new QCommentary("commentary");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCommentary(String variable) {
        super(Commentary.class, forVariable(variable));
    }

    public QCommentary(Path<? extends Commentary> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommentary(PathMetadata metadata) {
        super(Commentary.class, metadata);
    }

}

