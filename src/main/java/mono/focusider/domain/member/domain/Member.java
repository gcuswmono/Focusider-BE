package mono.focusider.domain.member.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import mono.focusider.domain.attendance.domain.Attendance;
import mono.focusider.domain.auth.dto.req.SignupRequestDto;
import mono.focusider.domain.member.type.MemberRole;
import mono.focusider.domain.member.type.converter.MemberRoleConverter;
import mono.focusider.global.domain.BaseTimeEntity;

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
    private String gender;
    @Column(name = "birthday", nullable = false)
    private LocalDate birthDate;
    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "member_role", nullable = false)
    @Convert(converter = MemberRoleConverter.class)
    private MemberRole memberRole;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    @Builder.Default
    private List<Attendance> attendances = new ArrayList<>();

    public static Member createMember(SignupRequestDto signupRequestDto, String password) {
        return Member
                .builder()
                .accountId(signupRequestDto.accountId())
                .password(password)
                .name(signupRequestDto.name())
                .gender(signupRequestDto.gender())
                .birthDate(signupRequestDto.birthday())
                .level(0)
                .memberRole(MemberRole.ROLE_MEMBER)
                .build();
    }
}