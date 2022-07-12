/*
 * This file is generated by jOOQ.
 */
package vn.hieplp.todo.repositories.generates.tables.records;


import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import vn.hieplp.todo.repositories.generates.tables.Template;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TemplateRecord extends UpdatableRecordImpl<TemplateRecord> implements Record4<String, Integer, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Create a detached TemplateRecord
     */
    public TemplateRecord() {
        super(Template.TEMPLATE);
    }

    /**
     * Create a detached, initialised TemplateRecord
     */
    public TemplateRecord(String templatename, Integer sendvia, String title, String content) {
        super(Template.TEMPLATE);

        setTemplatename(templatename);
        setSendvia(sendvia);
        setTitle(title);
        setContent(content);
    }

    /**
     * Getter for <code>todo.template.templateName</code>.
     */
    public String getTemplatename() {
        return (String) get(0);
    }

    /**
     * Setter for <code>todo.template.templateName</code>.
     */
    public void setTemplatename(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>todo.template.sendVia</code>.
     */
    public Integer getSendvia() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>todo.template.sendVia</code>.
     */
    public void setSendvia(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>todo.template.title</code>.
     */
    public String getTitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>todo.template.title</code>.
     */
    public void setTitle(String value) {
        set(2, value);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * Getter for <code>todo.template.content</code>.
     */
    public String getContent() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * Setter for <code>todo.template.content</code>.
     */
    public void setContent(String value) {
        set(3, value);
    }

    @Override
    public Record2<String, Integer> key() {
        return (Record2) super.key();
    }

    @Override
    public Row4<String, Integer, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<String, Integer, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Template.TEMPLATE.TEMPLATENAME;
    }

    @Override
    public Field<Integer> field2() {
        return Template.TEMPLATE.SENDVIA;
    }

    @Override
    public Field<String> field3() {
        return Template.TEMPLATE.TITLE;
    }

    @Override
    public Field<String> field4() {
        return Template.TEMPLATE.CONTENT;
    }

    @Override
    public String component1() {
        return getTemplatename();
    }

    @Override
    public Integer component2() {
        return getSendvia();
    }

    @Override
    public String component3() {
        return getTitle();
    }

    @Override
    public String component4() {
        return getContent();
    }

    @Override
    public String value1() {
        return getTemplatename();
    }

    @Override
    public Integer value2() {
        return getSendvia();
    }

    @Override
    public String value3() {
        return getTitle();
    }

    @Override
    public String value4() {
        return getContent();
    }

    @Override
    public TemplateRecord value1(String value) {
        setTemplatename(value);
        return this;
    }

    @Override
    public TemplateRecord value2(Integer value) {
        setSendvia(value);
        return this;
    }

    @Override
    public TemplateRecord value3(String value) {
        setTitle(value);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    @Override
    public TemplateRecord value4(String value) {
        setContent(value);
        return this;
    }

    @Override
    public TemplateRecord values(String value1, Integer value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }
}