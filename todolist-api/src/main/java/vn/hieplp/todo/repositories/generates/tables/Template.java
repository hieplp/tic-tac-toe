/*
 * This file is generated by jOOQ.
 */
package vn.hieplp.todo.repositories.generates.tables;


import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import vn.hieplp.todo.repositories.generates.Keys;
import vn.hieplp.todo.repositories.generates.Todo;
import vn.hieplp.todo.repositories.generates.tables.records.TemplateRecord;

import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Template extends TableImpl<TemplateRecord> {

    /**
     * The reference instance of <code>todo.template</code>
     */
    public static final Template TEMPLATE = new Template();
    private static final long serialVersionUID = 1L;
    /**
     * The column <code>todo.template.templateName</code>.
     */
    public final TableField<TemplateRecord, String> TEMPLATENAME = createField(DSL.name("templateName"), SQLDataType.VARCHAR(255).nullable(false), this, "");
    /**
     * The column <code>todo.template.sendVia</code>.
     */
    public final TableField<TemplateRecord, Integer> SENDVIA = createField(DSL.name("sendVia"), SQLDataType.INTEGER.nullable(false), this, "");
    /**
     * The column <code>todo.template.title</code>.
     */
    public final TableField<TemplateRecord, String> TITLE = createField(DSL.name("title"), SQLDataType.CLOB, this, "");
    /**
     * The column <code>todo.template.content</code>.
     */
    public final TableField<TemplateRecord, String> CONTENT = createField(DSL.name("content"), SQLDataType.CLOB, this, "");

    private Template(Name alias, Table<TemplateRecord> aliased) {
        this(alias, aliased, null);
    }

    private Template(Name alias, Table<TemplateRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>todo.template</code> table reference
     */
    public Template(String alias) {
        this(DSL.name(alias), TEMPLATE);
    }

    /**
     * Create an aliased <code>todo.template</code> table reference
     */
    public Template(Name alias) {
        this(alias, TEMPLATE);
    }

    /**
     * Create a <code>todo.template</code> table reference
     */
    public Template() {
        this(DSL.name("template"), null);
    }

    public <O extends Record> Template(Table<O> child, ForeignKey<O, TemplateRecord> key) {
        super(child, key, TEMPLATE);
    }

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TemplateRecord> getRecordType() {
        return TemplateRecord.class;
    }

    @Override
    public Schema getSchema() {
        return Todo.TODO;
    }

    @Override
    public UniqueKey<TemplateRecord> getPrimaryKey() {
        return Keys.KEY_TEMPLATE_PRIMARY;
    }

    @Override
    public List<UniqueKey<TemplateRecord>> getKeys() {
        return Arrays.<UniqueKey<TemplateRecord>>asList(Keys.KEY_TEMPLATE_PRIMARY);
    }

    @Override
    public Template as(String alias) {
        return new Template(DSL.name(alias), this);
    }

    @Override
    public Template as(Name alias) {
        return new Template(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Template rename(String name) {
        return new Template(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Template rename(Name name) {
        return new Template(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<String, Integer, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
