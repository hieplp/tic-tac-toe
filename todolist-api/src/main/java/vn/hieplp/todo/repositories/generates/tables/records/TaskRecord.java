/*
 * This file is generated by jOOQ.
 */
package vn.hieplp.todo.repositories.generates.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;
import vn.hieplp.todo.repositories.generates.tables.Task;

import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TaskRecord extends UpdatableRecordImpl<TaskRecord> implements Record11<String, String, String, String, LocalDateTime, String, Integer, String, LocalDateTime, String, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Create a detached TaskRecord
     */
    public TaskRecord() {
        super(Task.TASK);
    }

    /**
     * Create a detached, initialised TaskRecord
     */
    public TaskRecord(String taskid, String userid, String tasktitle, String taskdescription, LocalDateTime taskdate, String taskimages, Integer taskstatus, String createdby, LocalDateTime createdat, String modifiedby, LocalDateTime modifiedat) {
        super(Task.TASK);

        setTaskid(taskid);
        setUserid(userid);
        setTasktitle(tasktitle);
        setTaskdescription(taskdescription);
        setTaskdate(taskdate);
        setTaskimages(taskimages);
        setTaskstatus(taskstatus);
        setCreatedby(createdby);
        setCreatedat(createdat);
        setModifiedby(modifiedby);
        setModifiedat(modifiedat);
    }

    /**
     * Getter for <code>todo.task.taskId</code>.
     */
    public String getTaskid() {
        return (String) get(0);
    }

    /**
     * Setter for <code>todo.task.taskId</code>.
     */
    public void setTaskid(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>todo.task.userId</code>.
     */
    public String getUserid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>todo.task.userId</code>.
     */
    public void setUserid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>todo.task.taskTitle</code>.
     */
    public String getTasktitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>todo.task.taskTitle</code>.
     */
    public void setTasktitle(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>todo.task.taskDescription</code>.
     */
    public String getTaskdescription() {
        return (String) get(3);
    }

    /**
     * Setter for <code>todo.task.taskDescription</code>.
     */
    public void setTaskdescription(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>todo.task.taskDate</code>.
     */
    public LocalDateTime getTaskdate() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>todo.task.taskDate</code>.
     */
    public void setTaskdate(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>todo.task.taskImages</code>.
     */
    public String getTaskimages() {
        return (String) get(5);
    }

    /**
     * Setter for <code>todo.task.taskImages</code>.
     */
    public void setTaskimages(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>todo.task.taskStatus</code>.
     */
    public Integer getTaskstatus() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>todo.task.taskStatus</code>.
     */
    public void setTaskstatus(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>todo.task.createdBy</code>.
     */
    public String getCreatedby() {
        return (String) get(7);
    }

    /**
     * Setter for <code>todo.task.createdBy</code>.
     */
    public void setCreatedby(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>todo.task.createdAt</code>.
     */
    public LocalDateTime getCreatedat() {
        return (LocalDateTime) get(8);
    }

    /**
     * Setter for <code>todo.task.createdAt</code>.
     */
    public void setCreatedat(LocalDateTime value) {
        set(8, value);
    }

    /**
     * Getter for <code>todo.task.modifiedBy</code>.
     */
    public String getModifiedby() {
        return (String) get(9);
    }

    /**
     * Setter for <code>todo.task.modifiedBy</code>.
     */
    public void setModifiedby(String value) {
        set(9, value);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * Getter for <code>todo.task.modifiedAt</code>.
     */
    public LocalDateTime getModifiedat() {
        return (LocalDateTime) get(10);
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * Setter for <code>todo.task.modifiedAt</code>.
     */
    public void setModifiedat(LocalDateTime value) {
        set(10, value);
    }

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    @Override
    public Row11<String, String, String, String, LocalDateTime, String, Integer, String, LocalDateTime, String, LocalDateTime> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    @Override
    public Row11<String, String, String, String, LocalDateTime, String, Integer, String, LocalDateTime, String, LocalDateTime> valuesRow() {
        return (Row11) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Task.TASK.TASKID;
    }

    @Override
    public Field<String> field2() {
        return Task.TASK.USERID;
    }

    @Override
    public Field<String> field3() {
        return Task.TASK.TASKTITLE;
    }

    @Override
    public Field<String> field4() {
        return Task.TASK.TASKDESCRIPTION;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return Task.TASK.TASKDATE;
    }

    @Override
    public Field<String> field6() {
        return Task.TASK.TASKIMAGES;
    }

    @Override
    public Field<Integer> field7() {
        return Task.TASK.TASKSTATUS;
    }

    @Override
    public Field<String> field8() {
        return Task.TASK.CREATEDBY;
    }

    @Override
    public Field<LocalDateTime> field9() {
        return Task.TASK.CREATEDAT;
    }

    @Override
    public Field<String> field10() {
        return Task.TASK.MODIFIEDBY;
    }

    @Override
    public Field<LocalDateTime> field11() {
        return Task.TASK.MODIFIEDAT;
    }

    @Override
    public String component1() {
        return getTaskid();
    }

    @Override
    public String component2() {
        return getUserid();
    }

    @Override
    public String component3() {
        return getTasktitle();
    }

    @Override
    public String component4() {
        return getTaskdescription();
    }

    @Override
    public LocalDateTime component5() {
        return getTaskdate();
    }

    @Override
    public String component6() {
        return getTaskimages();
    }

    @Override
    public Integer component7() {
        return getTaskstatus();
    }

    @Override
    public String component8() {
        return getCreatedby();
    }

    @Override
    public LocalDateTime component9() {
        return getCreatedat();
    }

    @Override
    public String component10() {
        return getModifiedby();
    }

    @Override
    public LocalDateTime component11() {
        return getModifiedat();
    }

    @Override
    public String value1() {
        return getTaskid();
    }

    @Override
    public String value2() {
        return getUserid();
    }

    @Override
    public String value3() {
        return getTasktitle();
    }

    @Override
    public String value4() {
        return getTaskdescription();
    }

    @Override
    public LocalDateTime value5() {
        return getTaskdate();
    }

    @Override
    public String value6() {
        return getTaskimages();
    }

    @Override
    public Integer value7() {
        return getTaskstatus();
    }

    @Override
    public String value8() {
        return getCreatedby();
    }

    @Override
    public LocalDateTime value9() {
        return getCreatedat();
    }

    @Override
    public String value10() {
        return getModifiedby();
    }

    @Override
    public LocalDateTime value11() {
        return getModifiedat();
    }

    @Override
    public TaskRecord value1(String value) {
        setTaskid(value);
        return this;
    }

    @Override
    public TaskRecord value2(String value) {
        setUserid(value);
        return this;
    }

    @Override
    public TaskRecord value3(String value) {
        setTasktitle(value);
        return this;
    }

    @Override
    public TaskRecord value4(String value) {
        setTaskdescription(value);
        return this;
    }

    @Override
    public TaskRecord value5(LocalDateTime value) {
        setTaskdate(value);
        return this;
    }

    @Override
    public TaskRecord value6(String value) {
        setTaskimages(value);
        return this;
    }

    @Override
    public TaskRecord value7(Integer value) {
        setTaskstatus(value);
        return this;
    }

    @Override
    public TaskRecord value8(String value) {
        setCreatedby(value);
        return this;
    }

    @Override
    public TaskRecord value9(LocalDateTime value) {
        setCreatedat(value);
        return this;
    }

    @Override
    public TaskRecord value10(String value) {
        setModifiedby(value);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    @Override
    public TaskRecord value11(LocalDateTime value) {
        setModifiedat(value);
        return this;
    }

    @Override
    public TaskRecord values(String value1, String value2, String value3, String value4, LocalDateTime value5, String value6, Integer value7, String value8, LocalDateTime value9, String value10, LocalDateTime value11) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        return this;
    }
}
