package ru.itpark.documents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itpark.documents.entity.DocumentEntity;

import java.util.List;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Integer> {

    List<DocumentEntity> findAllByDocumentNameContainsIgnoreCaseOrderByNameSurnameDesc(String documentName);
}

