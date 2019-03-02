package ru.itpark.documents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {
    private int id;
    private String documentName;
    private String nameSurname;
    private String documentInformation;
    private MultipartFile file;
}
