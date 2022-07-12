package vn.hieplp.todo.repositories.base;

import com.google.inject.Inject;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import vn.hieplp.todo.common.exceptions.DatabaseException;
import vn.hieplp.todo.common.utils.States;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Pattern;


/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 10:57
 */
public class BaseRepositoryImpl implements IBaseRepository {

    protected final Logger LOGGER = LogManager.getLogger(this.getClass());

    private HikariDataSource dataSource;

    @Inject
    public HikariDataSource getDataSource() {
        return dataSource;
    }

    @Inject
    public void setDataSource(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public CustomDSLContext getDslContext() throws SQLException {
        LOGGER.info("Start get DSL context");
        Settings settings = new Settings();
        settings.setDebugInfoOnStackTrace(true);
        settings.setExecuteLogging(true);
        return new CustomDSLContext(dataSource.getConnection(), SQLDialect.MYSQL, settings);
    }

    @Override
    public void save(Record record) {
        LOGGER.info("Save record");
        try (CustomDSLContext context = getDslContext()) {
            context.insertInto(getTable(record))
                    .set(record)
                    .execute();
        } catch (Exception e) {
            LOGGER.error("Error when save record: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error when save record");
        }
    }

    @Override
    public void updateNotNull(Record record) {
        LOGGER.info("Update not null");
        try (CustomDSLContext context = getDslContext()) {
            Arrays.stream(record.fields()).forEach(field -> {
                record.changed(field, States.isNotNull(record.getValue(field)));
            });
            Table<?> table = getTable(record);
            context.update(table)
                    .set(record)
                    .where(equalKey(table, record))
                    .execute();
        } catch (Exception e) {
            LOGGER.info("Error when save record: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error when update not null record");
        }
    }

    @Override
    public int count(SelectSelectStep<?> select, Table<?> table, Condition condition) {
        try (CustomDSLContext context = getDslContext()) {
            return context.fetchCount(select.from(table).where(condition));
        } catch (Exception e) {
            LOGGER.error("failed to count number of records: " + e.getMessage(), e);
            throw new DatabaseException.QueryException("failed to count number of records: " + e.getMessage(), e);
        }
    }

    @Override
    public int count(Table<?> table) {
        try (CustomDSLContext context = getDslContext()) {
            return context.fetchCount(table);
        } catch (Exception e) {
            LOGGER.error("failed to count number of records: " + e.getMessage(), e);
            throw new DatabaseException.QueryException("failed to count number of records: " + e.getMessage(), e);
        }
    }

    protected Condition getSearchCondition(Condition condition, String searchBy, String searchValue) {
        if (States.isNotNullOrEmpty(searchBy) && States.isNotNullOrEmpty(searchValue)) {
            String[] searchInput = searchBy.split(Pattern.quote("."));
            Field<Object> searchField = DSL.field(DSL.name(searchInput[0], searchInput[1]));
            condition = condition.and(searchField.contains(searchValue));
        }
        return condition;
    }

    protected Condition getFilterCondition(Condition condition, String filterBy, String filterValue) {
        if (States.isNotNull(filterBy) && States.isNotNull(filterValue)) {
            String[] filterInput = filterBy.split(Pattern.quote("."));
            Field<Object> filterField = DSL.field(DSL.name(filterInput[0], filterInput[1]));
            condition = condition.and(filterField.eq(filterValue));
        }
        return condition;
    }

    private Table<?> getTable(Record record) {
        return ((TableRecord<?>) record).getTable();
    }

    private Condition equalKey(Table<?> table, Record record) {
        Condition condition = DSL.trueCondition();
        if (States.isNull(table.getPrimaryKey())) {
            return condition;
        }

        for (TableField field : table.getPrimaryKey().getFields()) {
            condition = condition.and(field.eq(field.getDataType().convert(record.getValue(field.getName()))));
        }
        return condition;
    }
}
