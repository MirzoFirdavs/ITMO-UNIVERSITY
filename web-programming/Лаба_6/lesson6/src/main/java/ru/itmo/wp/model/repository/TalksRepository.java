package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Talks;

import java.util.List;

public interface TalksRepository {
    void save(Talks talk);
    List<Talks> findByUserId(long sourceUserId);
}
