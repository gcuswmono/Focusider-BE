package mono.focusider.domain.member.domain;

import jakarta.persistence.*;
import lombok.*;
import mono.focusider.domain.attendance.domain.Attendance;
import mono.focusider.domain.auth.dto.req.SignupReqDto;
import mono.focusider.domain.category.domain.MemberCategory;
import mono.focusider.domain.file.domain.File;
import mono.focusider.domain.member.type.MemberGenderType;
import mono.focusider.domain.member.type.MemberRole;
import mono.focusider.domain.member.type.converter.MemberGenderTypeConverter;
import mono.focusider.domain.member.type.converter.MemberRoleConverter;
import mono.focusider.global.domain.BaseTimeEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_id", nullable = false)
    private String accountId;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "geneder", nullable = false)
    @Convert(converter = MemberGenderTypeConverter.class)
    private MemberGenderType gender;
    @Column(name = "birthday", nullable = false)
    private LocalDate birthDate;
    @Column(name = "level", nullable = false)
    private Integer level;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_image_id")
    private File profileImageFile;

    @Column(name = "member_role", nullable = false)
    @Convert(converter = MemberRoleConverter.class)
    private MemberRole memberRole;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MemberCategory> memberCategories = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Attendance> attendances = new ArrayList<>();

    public static Member createMember(SignupReqDto signupReqDto, File profileImageFile, String password) {
        return Member
                .builder()
                .accountId(signupReqDto.accountId())
                .password(password)
                .name(signupReqDto.name())
                .gender(signupReqDto.gender())
                .birthDate(signupReqDto.birthday())
                .profileImageFile(profileImageFile)
                .level(0)
                .memberRole(MemberRole.ROLE_MEMBER)
                .build();
    }

    public void addMemberCategory(MemberCategory memberCategory) {
        this.memberCategories.add(memberCategory);
    }

    public void updateMemberLevel(Integer level) {
        this.level = level;
    }

    public void updateMemberInfo(File profileImageFile, String name) {
        this.profileImageFile = profileImageFile;
        this.name = name;
    }
}