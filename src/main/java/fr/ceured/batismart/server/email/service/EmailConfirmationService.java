package fr.ceured.batismart.server.email.service;

import fr.ceured.batismart.server.authentication.model.User;
import fr.ceured.batismart.server.email.entity.EmailConfirmationEntity;
import fr.ceured.batismart.server.email.exception.EmailConfirmationNotFoundException;
import fr.ceured.batismart.server.email.mapper.EmailConfirmationMapper;
import fr.ceured.batismart.server.email.model.EmailConfirmation;
import fr.ceured.batismart.server.email.repository.EmailConfirmaitonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class EmailConfirmationService {

    @Value("${spring.mail.username}")
    private String from;

    private final EmailConfirmaitonRepository emailConfirmaitonRepository;
    private final JavaMailSender javaMailSender;
    private final EmailConfirmationMapper mapper;


    public void sendConfirmationRegister(User user) {

        EmailConfirmationEntity emailConfirmation = new EmailConfirmationEntity();
        emailConfirmation.setEmail(user.getEmail());
        emailConfirmation.setCreatedAt(LocalDateTime.now());

        EmailConfirmationEntity saved = emailConfirmaitonRepository.save(emailConfirmation);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(user.getEmail());
        message.setSubject("Confirmation de votre enregistrement");
        message.setText("Il faut cliquer sur ce lien "+generateConfirmationLink(saved.getId())+" pour valider l'inscription");
        javaMailSender.send(message);

    }

    private String generateConfirmationLink(String token) {
        return String.format("<a href=http://localhost:8080/confirm-email?token=%s>Confirm Email</a>", token);
    }

    public EmailConfirmation getById(String id) {
        return emailConfirmaitonRepository.findById(id)
                .map(mapper::mapToDto)
                .orElseThrow(EmailConfirmationNotFoundException::new);
    }

    public void addVerificationDate(final String id) {
        EmailConfirmation emailConfirmation = getById(id);

        emailConfirmation.setVerificationDate(LocalDateTime.now());

        upsert(emailConfirmation);

    }

    public EmailConfirmation upsert(final EmailConfirmation emailConfirmation) {
        return mapper.mapToDto(emailConfirmaitonRepository.save(mapper.mapToEntity(emailConfirmation)));
    }
}
