package ru.itpark.documents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itpark.documents.entity.AccountEntity;

import java.util.Optional;

public interface AccountRepository
        extends JpaRepository<AccountEntity, Integer> {
    Optional<AccountEntity> findByUsername(String username);
}
