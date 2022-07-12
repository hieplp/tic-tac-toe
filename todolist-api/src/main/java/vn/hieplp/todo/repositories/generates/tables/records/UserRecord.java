/*
 * This file is generated by jOOQ.
 */
package vn.hieplp.todo.repositories.generates.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import vn.hieplp.todo.repositories.generates.tables.User;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class UserRecord extends UpdatableRecordImpl<UserRecord> implements Record4<String, String, String, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Create a detached UserRecord
     */
    public UserRecord() {
        super(User.USER);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(String userid, String email, String fullname, Integer userstatus) {
        super(User.USER);

        setUserid(userid);
        setEmail(email);
        setFullname(fullname);
        setUserstatus(userstatus);
    }

    /**
     * Getter for <code>todo.user.userId</code>.
     */
    public String getUserid() {
        return (String) get(0);
    }

    /**
     * Setter for <code>todo.user.userId</code>.
     */
    public void setUserid(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>todo.user.email</code>.
     */
    public String getEmail() {
        return (String) get(1);
    }

    /**
     * Setter for <code>todo.user.email</code>.
     */
    public void setEmail(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>todo.user.fullName</code>.
     */
    public String getFullname() {
        return (String) get(2);
    }

    /**
     * Setter for <code>todo.user.fullName</code>.
     */
    public void setFullname(String value) {
        set(2, value);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * Getter for <code>todo.user.userStatus</code>.
     */
    public Integer getUserstatus() {
        return (Integer) get(3);
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * Setter for <code>todo.user.userStatus</code>.
     */
    public void setUserstatus(Integer value) {
        set(3, value);
    }

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    @Override
    public Row4<String, String, String, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<String, String, String, Integer> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return User.USER.USERID;
    }

    @Override
    public Field<String> field2() {
        return User.USER.EMAIL;
    }

    @Override
    public Field<String> field3() {
        return User.USER.FULLNAME;
    }

    @Override
    public Field<Integer> field4() {
        return User.USER.USERSTATUS;
    }

    @Override
    public String component1() {
        return getUserid();
    }

    @Override
    public String component2() {
        return getEmail();
    }

    @Override
    public String component3() {
        return getFullname();
    }

    @Override
    public Integer component4() {
        return getUserstatus();
    }

    @Override
    public String value1() {
        return getUserid();
    }

    @Override
    public String value2() {
        return getEmail();
    }

    @Override
    public String value3() {
        return getFullname();
    }

    @Override
    public Integer value4() {
        return getUserstatus();
    }

    @Override
    public UserRecord value1(String value) {
        setUserid(value);
        return this;
    }

    @Override
    public UserRecord value2(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public UserRecord value3(String value) {
        setFullname(value);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    @Override
    public UserRecord value4(Integer value) {
        setUserstatus(value);
        return this;
    }

    @Override
    public UserRecord values(String value1, String value2, String value3, Integer value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }
}