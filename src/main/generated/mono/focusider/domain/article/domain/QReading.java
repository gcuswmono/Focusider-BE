package mono.focusider.domain.article.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReading is a Querydsl query type for Reading
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReading extends EntityPathBase<Reading> {

    private static final long serialVersionUID = -281232885L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReading reading = new QReading("reading");

    public final mono.focusider.global.domain.QBaseTimeEntity _super = new mono.focusider.global.domain.QBaseTimeEntity(this);

    public final QArticle article;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final mono.focusider.domain.member.domain.QMember member;

    public QReading(String variable) {
        this(Reading.class, forVariable(variable), INITS);
    }

    public QReading(Path<? extends Reading> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReading(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReading(PathMetadata metadata, PathInits inits) {
        this(Reading.class, metadata, inits);
    }

    public QReading(Class<? extends Reading> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new QArticle(forProperty("article")) : null;
        this.member = inits.isInitialized("member") ? new mono.focusider.domain.member.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

