package mono.focusider.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mono.focusider.domain.member.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}