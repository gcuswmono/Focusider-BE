package mono.focusider.domain.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -89569823L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final mono.focusider.global.domain.QBaseTimeEntity _super = new mono.focusider.global.domain.QBaseTimeEntity(this);

    public final StringPath accountId = createString("accountId");

    public final ListPath<mono.focusider.domain.attendance.domain.Attendance, mono.focusider.domain.attendance.domain.QAttendance> attendances = this.<mono.focusider.domain.attendance.domain.Attendance, mono.focusider.domain.attendance.domain.QAttendance>createList("attendances", mono.focusider.domain.attendance.domain.Attendance.class, mono.focusider.domain.attendance.domain.QAttendance.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final ListPath<mono.focusider.domain.category.domain.MemberCategory, mono.focusider.domain.category.domain.QMemberCategory> memberCategories = this.<mono.focusider.domain.category.domain.MemberCategory, mono.focusider.domain.category.domain.QMemberCategory>createList("memberCategories", mono.focusider.domain.category.domain.MemberCategory.class, mono.focusider.domain.category.domain.QMemberCategory.class, PathInits.DIRECT2);

    public final EnumPath<mono.focusider.domain.member.type.MemberRole> memberRole = createEnum("memberRole", mono.focusider.domain.member.type.MemberRole.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final mono.focusider.domain.file.domain.QFile profileImageFile;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.profileImageFile = inits.isInitialized("profileImageFile") ? new mono.focusider.domain.file.domain.QFile(forProperty("profileImageFile")) : null;
    }

}

