package ru.itpark.documents.service;

import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itpark.documents.entity.AccountEntity;
import ru.itpark.documents.repository.AccountRepository;

@Primary
@Service

public class AccountService implements UserDetailsService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repository.findByUsername(s)
                .orElseThrow(() -> new UsernameNotFoundException(s));
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    public boolean hasAuthority(String authority) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(o -> o.getAuthority().equals(authority))
                ;
    }

    public boolean isOwned(int id) {
        AccountEntity entity = (AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return entity.getId() == 1;
    }
}
