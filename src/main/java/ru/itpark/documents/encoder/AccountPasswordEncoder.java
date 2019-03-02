package ru.itpark.documents.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountPasswordEncoder extends BCryptPasswordEncoder {
}
