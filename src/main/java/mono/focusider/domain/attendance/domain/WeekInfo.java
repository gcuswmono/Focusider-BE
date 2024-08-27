package mono.focusider.domain.attendance.domain;

import jakarta.persistence.*;
import lombok.*;
import mono.focusider.domain.article.domain.Reading;
import mono.focusider.global.domain.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class WeekInfo extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "week_info")
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "month", nullable = false)
    private int month;
    @Column(name = "week", nullable = false)
    private int week;

    @OneToMany(mappedBy = "weekInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Reading> readings = new ArrayList<>();

    public static WeekInfo of(String title, int year, int month, int week) {
        return WeekInfo
                .builder()
                .title(title)
                .year(year)
                .month(month)
                .week(week)
                .build();
    }
}
