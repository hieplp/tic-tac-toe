/*
 * This file is generated by jOOQ.
 */
package vn.hieplp.todo.repositories.generates.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    private String templatename;
    private Integer sendvia;
    private String title;
    private String content;

    public Template() {
    }

    public Template(Template value) {
        this.templatename = value.templatename;
        this.sendvia = value.sendvia;
        this.title = value.title;
        this.content = value.content;
    }

    public Template(
            String templatename,
            Integer sendvia,
            String title,
            String content
    ) {
        this.templatename = templatename;
        this.sendvia = sendvia;
        this.title = title;
        this.content = content;
    }

    /**
     * Getter for <code>todo.template.templateName</code>.
     */
    public String getTemplatename() {
        return this.templatename;
    }

    /**
     * Setter for <code>todo.template.templateName</code>.
     */
    public void setTemplatename(String templatename) {
        this.templatename = templatename;
    }

    /**
     * Getter for <code>todo.template.sendVia</code>.
     */
    public Integer getSendvia() {
        return this.sendvia;
    }

    /**
     * Setter for <code>todo.template.sendVia</code>.
     */
    public void setSendvia(Integer sendvia) {
        this.sendvia = sendvia;
    }

    /**
     * Getter for <code>todo.template.title</code>.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Setter for <code>todo.template.title</code>.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for <code>todo.template.content</code>.
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Setter for <code>todo.template.content</code>.
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Template (");

        sb.append(templatename);
        sb.append(", ").append(sendvia);
        sb.append(", ").append(title);
        sb.append(", ").append(content);

        sb.append(")");
        return sb.toString();
    }
}