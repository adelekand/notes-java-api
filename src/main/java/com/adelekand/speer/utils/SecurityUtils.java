package com.adelekand.speer.utils;

import com.adelekand.speer.model.User;
import com.adelekand.speer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityUtils {
    private final UserRepository userRepository;

    public Optional<String> getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken
                || authentication.getPrincipal() == null) {
            return Optional.empty();
        }
        String currentUserId = authentication.getName();
        return Optional.of(currentUserId);
    }

    public User getUser() {
        var username = getCurrentUserId().orElseThrow();
        return userRepository.findByUsername(username).orElseThrow();
    }
}
