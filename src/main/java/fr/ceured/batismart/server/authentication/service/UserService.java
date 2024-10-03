package fr.ceured.batismart.server.authentication.service;

import fr.ceured.batismart.server.authentication.entity.UserEntity;
import fr.ceured.batismart.server.authentication.exception.EmailNotFoundException;
import fr.ceured.batismart.server.commons.InvalidInputException;
import fr.ceured.batismart.server.authentication.exception.UserAlreadyExistException;
import fr.ceured.batismart.server.authentication.exception.UserAlreadyVerifiedException;
import fr.ceured.batismart.server.authentication.mapper.UserMapper;
import fr.ceured.batismart.server.authentication.model.User;
import fr.ceured.batismart.server.authentication.repository.UserRepository;
import fr.ceured.batismart.server.email.model.EmailConfirmation;
import fr.ceured.batismart.server.email.service.EmailConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailConfirmationService emailService;
    private final UserMapper mapper;

    public User registerUser(User user) {
        if (user == null || !StringUtils.hasText(user.getEmail()) || !StringUtils.hasText(user.getPassword())) {
            throw new InvalidInputException();
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity saved = userRepository.save(mapper.mapToEntity(user));

        this.emailService.sendConfirmationRegister(user);

        return mapper.mapToDto(saved);
    }

    public boolean verifyUser(String token) {
        EmailConfirmation emailConfirmation = emailService.getById(token);

        Optional<UserEntity> userEntity = userRepository.findByEmail(emailConfirmation.getEmail());

        if (userEntity.isPresent() && null == emailConfirmation.getVerificationDate()) {
            UserEntity user = userEntity.get();
            if (user.isAccountVerified()) {
                throw new UserAlreadyVerifiedException();
            }

            user.setAccountVerified(true);
            user.setActive(true);
            userRepository.save(user);

            emailService.addVerificationDate(token);
        }

        return true;
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(mapper::mapToDto)
                .orElseThrow(EmailNotFoundException::new);
    }

    public User getUserInSecurityConfig() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return this.getByEmail(email);
    }
}
