package vn.hieplp.todo.repositories.sources;

import vn.hieplp.todo.common.models.UserModel;
import vn.hieplp.todo.common.request.auth.RegisterRequest;
import vn.hieplp.todo.repositories.base.IBaseRepository;
import vn.hieplp.todo.repositories.generates.tables.records.PasswordRecord;
import vn.hieplp.todo.repositories.generates.tables.records.UserRecord;

public interface IUserRepository extends IBaseRepository {
    void register(RegisterRequest request);

    UserModel getUserModel(String userId);

    UserModel getUserModelByEmail(String email);

    UserRecord getUserRecord(String userId);

    UserRecord getUserRecordByEmail(String email);

    PasswordRecord getPasswordRecord(String userId);

    void updatePassword(String userId, String password);

    boolean doesEmailExist(String email);
}
