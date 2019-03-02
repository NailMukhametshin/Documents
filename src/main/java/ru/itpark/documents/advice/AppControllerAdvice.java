package ru.itpark.documents.advice;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itpark.documents.exception.DocumentNameIsNullException;
import ru.itpark.documents.exception.DocumentNotFoundException;
import ru.itpark.documents.exception.UnsupportedFileContentTypeException;
import ru.itpark.documents.exception.UploadFileException;

@ControllerAdvice
public class AppControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DocumentNotFoundException.class)
    public String handleDocumentNotFound(Model model) {
        model.addAttribute("message", "Документ не найден");
        return "error";
    }

    @ExceptionHandler(UploadFileException.class)
    public String handleUploadFile(Model model) {
        model.addAttribute("message", "Не удалось загрузить файл");
        return "error";
    }

    @ExceptionHandler(UnsupportedFileContentTypeException.class)
    public String handleUnsupportedFileContentType(
            Model model) {
        model.addAttribute("message", "Расширение файла не поддерживается");
        return "error";
    }

    @ExceptionHandler(DocumentNameIsNullException.class)
    public String handleDocumentNameIsNull(
            Model model) {
        model.addAttribute("message", "Пустое поле 'Имя документа'");
        return "error";
    }
}
