package ru.itpark.documents.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class DocumentEntity {

    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String documentName;
    @Column(nullable = false)
    private String nameSurname;
    @Column(nullable = false)
    private String documentInformation;
    private String path;

}
