package ru.itpark.documents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itpark.documents.entity.AccountEntity;
import ru.itpark.documents.entity.DocumentEntity;
import ru.itpark.documents.repository.AccountRepository;
import ru.itpark.documents.repository.DocumentRepository;

import java.util.List;

@SpringBootApplication
public class DocumentsApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(DocumentsApplication.class, args);




        var accountRepository = context.getBean(AccountRepository.class);
        var encoder = context.getBean(PasswordEncoder.class);

        accountRepository.saveAll(
                List.of(
                        new AccountEntity(
                                0,
                                "test",
                                encoder.encode("test"),
                                List.of(
                                        new SimpleGrantedAuthority("EDIT"),
                                        new SimpleGrantedAuthority("REMOVE")
                                ),
                                true,
                                true,
                                true,
                                true
                        )
                )
        );

        var documentRepository = context.getBean(DocumentRepository.class);
        documentRepository.saveAll(List.of(
                new DocumentEntity(0, "Паспорт", "Наиль Мухаметшин", "№ 1234-567890 Выдан отделением УФМС РФ по РТ в Лапландии от 29.02.2019. Код подразделения 123-456. Прописка ул.Разбитых фонарей 7", "passport.jpg"),
                new DocumentEntity(0, "ИНН", "Наиль Мухаметшин", "ИНН № 1234567890, серия 12 № 001234567", "INN.jpg"),
                new DocumentEntity(0, "Мед полис", "Наиль Мухаметшин", "№ 1234 5678 9123 4567", "medicine.jpg")
        ));
    }
}

