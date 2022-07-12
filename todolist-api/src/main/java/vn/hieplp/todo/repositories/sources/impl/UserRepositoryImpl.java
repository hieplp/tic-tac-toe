package vn.hieplp.todo.repositories.sources.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import vn.hieplp.todo.common.constant.Constants;
import vn.hieplp.todo.common.constant.StaticEnum;
import vn.hieplp.todo.common.exceptions.DatabaseException;
import vn.hieplp.todo.common.models.UserModel;
import vn.hieplp.todo.common.request.auth.RegisterRequest;
import vn.hieplp.todo.common.utils.Encryption;
import vn.hieplp.todo.common.utils.Generator;
import vn.hieplp.todo.common.utils.JsonConverter;
import vn.hieplp.todo.repositories.base.BaseRepositoryImpl;
import vn.hieplp.todo.repositories.base.CustomDSLContext;
import vn.hieplp.todo.repositories.generates.tables.records.PasswordRecord;
import vn.hieplp.todo.repositories.generates.tables.records.UserRecord;
import vn.hieplp.todo.repositories.sources.IUserRepository;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Locale;

import static vn.hieplp.todo.repositories.generates.Tables.PASSWORD;
import static vn.hieplp.todo.repositories.generates.Tables.USER;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 10:55
 */
public class UserRepositoryImpl extends BaseRepositoryImpl implements IUserRepository {

    private final PrivateKey authPrivateKey;

    @Inject
    public UserRepositoryImpl(@Named(Constants.Token.PRIVATE_AUTH) PrivateKey authPrivateKey) {
        this.authPrivateKey = authPrivateKey;
    }

    @Override
    public void register(RegisterRequest request) {
        LOGGER.info("Register with request {}", JsonConverter.toJson(request));
        try (CustomDSLContext context = getDslContext()) {
            context.transaction(configuration -> {
                // Save to USER table
                UserRecord userRecord = new UserRecord();
                userRecord.from(request);
                userRecord.setEmail(request.getEmail().toLowerCase(Locale.ROOT));
                userRecord.setUserstatus(StaticEnum.UserStatus.ACTIVE.getStatus());
                context.insertInto(USER)
                        .set(userRecord)
                        .execute();
                // Save to PASSWORD table
                PasswordRecord passwordRecord = this.initPasswordRecord(request.getUserId(), request.getPassword());
                context.insertInto(PASSWORD)
                        .set(passwordRecord)
                        .execute();
            });
        } catch (Exception e) {
            LOGGER.error("Error when register caused by {}", e.getMessage());
            throw new DatabaseException.QueryException(e.getMessage());
        }
    }

    @Override
    public UserModel getUserModel(String userId) {
        LOGGER.info("Get user model by userId {}", userId);
        try (CustomDSLContext context = getDslContext()) {
            return context.select(USER.fields())
                    .from(USER)
                    .where(USER.USERID.eq(userId)
                            .and(USER.USERSTATUS.eq(StaticEnum.UserStatus.ACTIVE.getStatus())))
                    .fetchInto(UserModel.class)
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("User model with userId {} is not found", userId);
            throw new DatabaseException.NotFoundException("Not found user model");
        } catch (Exception e) {
            LOGGER.error("Error when get user model by userId: {}", e.getMessage());
            throw new DatabaseException.QueryException(e.getMessage());
        }
    }

    @Override
    public UserModel getUserModelByEmail(String email) {
        LOGGER.info("Get user model by email {}", email);
        try (CustomDSLContext context = getDslContext()) {
            return context.select(USER.fields())
                    .from(USER)
                    .where(USER.EMAIL.equalIgnoreCase(email)
                            .and(USER.USERSTATUS.eq(StaticEnum.UserStatus.ACTIVE.getStatus())))
                    .fetchInto(UserModel.class)
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("User model with email {} is not found", email);
            throw new DatabaseException.NotFoundException("Not found user model");
        } catch (Exception e) {
            LOGGER.error("Error when get user model by email: {}", e.getMessage());
            throw new DatabaseException.QueryException(e.getMessage());
        }
    }

    @Override
    public UserRecord getUserRecord(String userId) {
        return null;
    }

    @Override
    public UserRecord getUserRecordByEmail(String email) {
        LOGGER.info("Get user record by email {}", email);
        try (CustomDSLContext context = getDslContext()) {
            return context.selectFrom(USER)
                    .where(USER.EMAIL.equalIgnoreCase(email))
                    .fetchInto(UserRecord.class)
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("User record with email {} is not found", email);
            throw new DatabaseException.NotFoundException("Not found user record");
        } catch (Exception e) {
            LOGGER.error("Error when get user record by email: {}", e.getMessage());
            throw new DatabaseException.QueryException(e.getMessage());
        }
    }

    @Override
    public PasswordRecord getPasswordRecord(String userId) {
        LOGGER.info("Get password record by userId {}", userId);
        try (CustomDSLContext context = getDslContext()) {
            return context.selectFrom(PASSWORD)
                    .where(PASSWORD.USERID.eq(userId))
                    .fetchInto(PasswordRecord.class)
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Password record with userId {} is not found", userId);
            throw new DatabaseException.NotFoundException("Not found password record");
        } catch (Exception e) {
            LOGGER.error("Error when get password record by userId: {}", e.getMessage());
            throw new DatabaseException.QueryException(e.getMessage());
        }
    }

    @Override
    public void updatePassword(String userId, String password) {
        LOGGER.info("Update password with userId {}", userId);
        try (CustomDSLContext context = getDslContext()) {
            PasswordRecord passwordRecord = this.initPasswordRecord(userId, password);
            context.update(PASSWORD)
                    .set(passwordRecord)
                    .where(PASSWORD.USERID.eq(userId))
                    .execute();
        } catch (Exception e) {
            LOGGER.error("Error when update password: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error when update password");
        }
    }

    @Override
    public boolean doesEmailExist(String email) {
        LOGGER.info("Check if email {} exists", email);
        try (CustomDSLContext context = getDslContext()) {
            return context.fetchExists(context.select(USER.USERID)
                    .from(USER)
                    .where(USER.EMAIL.equalIgnoreCase(email)));
        } catch (Exception e) {
            LOGGER.error("Error when check if email exists: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error whe check if email exists");
        }
    }

    private PasswordRecord initPasswordRecord(String userId, String password) throws NoSuchAlgorithmException {
        PasswordRecord passwordRecord = new PasswordRecord();
        passwordRecord.setUserid(userId);
        passwordRecord.setSalt(Generator.generateSalt());
        passwordRecord.setPassword(Encryption.generatePassword(password, authPrivateKey, passwordRecord.getSalt()));
        return passwordRecord;
    }
}
