package vn.hieplp.todo.repositories.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultDSLContext;
import vn.hieplp.todo.common.utils.States;

import java.sql.Connection;
import java.sql.SQLException;

public class CustomDSLContext extends DefaultDSLContext implements AutoCloseable {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final Connection connection;
    private final SQLDialect sqlDialect;
    private final Settings settings;

    public CustomDSLContext(Connection connection, SQLDialect dialect, Settings settings) {
        super(connection, dialect, settings);
        this.connection = connection;
        this.sqlDialect = dialect;
        this.settings = settings;
    }

    @Override
    public void close() throws Exception {
        if (States.isNotNull(connection)) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}
