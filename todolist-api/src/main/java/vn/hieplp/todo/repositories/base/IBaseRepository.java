package vn.hieplp.todo.repositories.base;

import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectSelectStep;
import org.jooq.Table;

import java.sql.SQLException;

public interface IBaseRepository {
    CustomDSLContext getDslContext() throws SQLException;

    void save(Record record);

    void updateNotNull(Record record);

    int count(SelectSelectStep<?> select, Table<?> table, Condition condition);

    int count(Table<?> table);
}