package mono.focusider.domain.file.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class File {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(name = "url", columnDefinition = "TEXT", nullable = false)
    private String url;
    @Column(name = "is_used", nullable = false)
    private Boolean isUsed;

    public static File createFile(String url, Boolean isUsed) {
        return File
                .builder()
                .url(url)
                .isUsed(isUsed)
                .build();
    }

    public void updateUsed(){
        this.isUsed = true;
    }

    public void updateNotUsed(){
        this.isUsed = false;
    }
}
