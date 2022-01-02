package ru.itmo.wp.model.repository.impl;

import com.google.common.collect.ImmutableMap;
import ru.itmo.wp.model.domain.Talks;
import ru.itmo.wp.model.repository.TalksRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class TalksRepositoryImpl extends AbstractRepositoryImpl<Talks> implements TalksRepository {

    @Override
    public void save(Talks talk) {
        super.save(talk, ImmutableMap.of(
                "sourceUserId", talk.getSourceUserId(),
                "targetUserId", talk.getTargetUserId(),
                "text", talk.getText()));
    }

    @Override
    public List<Talks> findByUserId(long sourceUserId) {
        return findListWhere("? IN (sourceUserId, targetUserId)", sourceUserId);
    }

    @Override
    protected Talks toEntity(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Talks talk = new Talks();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    talk.setId(resultSet.getLong(i));
                    break;
                case "sourceUserId":
                    talk.setSourceUserId(resultSet.getLong(i));
                    break;
                case "targetUserId":
                    talk.setTargetUserId(resultSet.getLong(i));
                    break;
                case "text":
                    talk.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    talk.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }
        return talk;
    }
}