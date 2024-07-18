package mono.focusider.domain.auth.dto;

public class UsernameAvailabilityResponseDto {
    private boolean available;

    public UsernameAvailabilityResponseDto(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}