package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Talks;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalksRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.TalksRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.List;

public class TalksService {
    private final TalksRepository talkRepository = new TalksRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public void sendMessage(User user, long targetSourceId, String text) {
        Talks talk = new Talks();
        talk.setSourceUserId(user.getId());
        talk.setTargetUserId(targetSourceId);
        talk.setText(text);
        talkRepository.save(talk);
    }

    public void validateTalks(Talks talks, long targetSourceId, String text, User currentUser) throws ValidationException {
        try {
            talks.setTargetUserId(Long.parseLong(String.valueOf(targetSourceId)));
        } catch (NumberFormatException e) {
            throw new ValidationException("TargetUserId is invalid");
        }

        if (talks.getSourceUserId() != currentUser.getId()) {
            throw new ValidationException("Source id and current user id are not equal");
        }

        if (userRepository.find(talks.getTargetUserId()) == null) {
            throw new ValidationException("no such user with Id" + talks.getTargetUserId());
        }
        if (talks.getSourceUserId() == talks.getTargetUserId()) {
            throw new ValidationException("you can't send message to yourself");
        }
        if (Strings.isNullOrEmpty(text) || text.isBlank()) {
            throw new ValidationException("you can't send the empty text");
        }
        if (text.length() > 255) {
            throw new ValidationException("text is too long");
        }
    }

    public List<Talks> findTalks(User user) {
        return talkRepository.findByUserId(user.getId());
    }
}