package mono.focusider.domain.auth.dto.res;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record CheckDuplicatedResDto(
        Boolean isDuplicated
) {
    public static CheckDuplicatedResDto of(Boolean isDuplicated) {
        return CheckDuplicatedResDto
                .builder()
                .isDuplicated(isDuplicated)
                .build();
    }
}
