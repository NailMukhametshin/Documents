package ru.itpark.documents.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itpark.documents.dto.DocumentDto;
import ru.itpark.documents.entity.DocumentEntity;
import ru.itpark.documents.exception.DocumentNameIsNullException;
import ru.itpark.documents.exception.DocumentNotFoundException;
import ru.itpark.documents.exception.UnsupportedFileContentTypeException;
import ru.itpark.documents.exception.UploadFileException;
import ru.itpark.documents.repository.DocumentRepository;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentService {
    private final DocumentRepository repository;
    private final Path uploadPath;

    public DocumentService(
            DocumentRepository repository,
            @Value("${spring.resources.static-locations}") String uploadPath
    ) {
        this.repository = repository;
        this.uploadPath = Path.of(URI.create(uploadPath)).resolve("media");
        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<DocumentEntity> getAll() {
        return repository.findAll();
    }

    public DocumentEntity getById(int id) {
        return repository.findById(id)
                .orElseThrow(DocumentNotFoundException::new);
    }

    public DocumentEntity getByIdOrEmpty(int id) {
        return repository.findById(id)
                .orElse(new DocumentEntity());
    }

    public void save(DocumentDto item) {
        DocumentEntity entity = getByIdOrEmpty(item.getId());
        entity.setDocumentName(item.getDocumentName());
        entity.setNameSurname(item.getNameSurname());
        entity.setDocumentInformation(item.getDocumentInformation());

        if (item.getDocumentName().length() == 0) {
            throw new DocumentNameIsNullException();
        }

        MultipartFile file = item.getFile();
        if (!file.isEmpty() && file.getContentType() != null) {
            String ext;
            if (file.getContentType().equals(MediaType.IMAGE_PNG_VALUE)) {
                ext = ".png";
            } else if (file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE)) {
                ext = ".jpg";
            } else {
                throw new UnsupportedFileContentTypeException();
            }

            String name = UUID.randomUUID().toString() + ext;

            try {
                file.transferTo(uploadPath.resolve(name));

                if (entity.getPath() != null) {
                    Files.deleteIfExists(uploadPath.resolve(entity.getPath()));
                }
            } catch (IOException e) {
                throw new UploadFileException();
            }

            entity.setPath(name);
        }

        repository.save(entity);
    }

    public void removeById(int id) {
        repository.deleteById(id);
    }

    public List<DocumentEntity> findByName(String documentName) {
        return repository.findAllByDocumentNameContainsIgnoreCaseOrderByNameSurnameDesc(documentName);
    }

}