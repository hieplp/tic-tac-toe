/*
 * This file is generated by jOOQ.
 */
package vn.hieplp.todo.repositories.generates.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    private String noteid;
    private String notetitle;
    private String notedescription;
    private Integer notestatus;
    private String userid;
    private String createdby;
    private LocalDateTime createdat;
    private String modifiedby;
    private LocalDateTime modifiedat;
    private Integer ispinned;

    public Note() {
    }

    public Note(Note value) {
        this.noteid = value.noteid;
        this.notetitle = value.notetitle;
        this.notedescription = value.notedescription;
        this.notestatus = value.notestatus;
        this.userid = value.userid;
        this.createdby = value.createdby;
        this.createdat = value.createdat;
        this.modifiedby = value.modifiedby;
        this.modifiedat = value.modifiedat;
        this.ispinned = value.ispinned;
    }

    public Note(
            String noteid,
            String notetitle,
            String notedescription,
            Integer notestatus,
            String userid,
            String createdby,
            LocalDateTime createdat,
            String modifiedby,
            LocalDateTime modifiedat,
            Integer ispinned
    ) {
        this.noteid = noteid;
        this.notetitle = notetitle;
        this.notedescription = notedescription;
        this.notestatus = notestatus;
        this.userid = userid;
        this.createdby = createdby;
        this.createdat = createdat;
        this.modifiedby = modifiedby;
        this.modifiedat = modifiedat;
        this.ispinned = ispinned;
    }

    /**
     * Getter for <code>todo.note.noteId</code>.
     */
    public String getNoteid() {
        return this.noteid;
    }

    /**
     * Setter for <code>todo.note.noteId</code>.
     */
    public void setNoteid(String noteid) {
        this.noteid = noteid;
    }

    /**
     * Getter for <code>todo.note.noteTitle</code>.
     */
    public String getNotetitle() {
        return this.notetitle;
    }

    /**
     * Setter for <code>todo.note.noteTitle</code>.
     */
    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    /**
     * Getter for <code>todo.note.noteDescription</code>.
     */
    public String getNotedescription() {
        return this.notedescription;
    }

    /**
     * Setter for <code>todo.note.noteDescription</code>.
     */
    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }

    /**
     * Getter for <code>todo.note.noteStatus</code>.
     */
    public Integer getNotestatus() {
        return this.notestatus;
    }

    /**
     * Setter for <code>todo.note.noteStatus</code>.
     */
    public void setNotestatus(Integer notestatus) {
        this.notestatus = notestatus;
    }

    /**
     * Getter for <code>todo.note.userId</code>.
     */
    public String getUserid() {
        return this.userid;
    }

    /**
     * Setter for <code>todo.note.userId</code>.
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * Getter for <code>todo.note.createdBy</code>.
     */
    public String getCreatedby() {
        return this.createdby;
    }

    /**
     * Setter for <code>todo.note.createdBy</code>.
     */
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    /**
     * Getter for <code>todo.note.createdAt</code>.
     */
    public LocalDateTime getCreatedat() {
        return this.createdat;
    }

    /**
     * Setter for <code>todo.note.createdAt</code>.
     */
    public void setCreatedat(LocalDateTime createdat) {
        this.createdat = createdat;
    }

    /**
     * Getter for <code>todo.note.modifiedBy</code>.
     */
    public String getModifiedby() {
        return this.modifiedby;
    }

    /**
     * Setter for <code>todo.note.modifiedBy</code>.
     */
    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    /**
     * Getter for <code>todo.note.modifiedAt</code>.
     */
    public LocalDateTime getModifiedat() {
        return this.modifiedat;
    }

    /**
     * Setter for <code>todo.note.modifiedAt</code>.
     */
    public void setModifiedat(LocalDateTime modifiedat) {
        this.modifiedat = modifiedat;
    }

    /**
     * Getter for <code>todo.note.isPinned</code>.
     */
    public Integer getIspinned() {
        return this.ispinned;
    }

    /**
     * Setter for <code>todo.note.isPinned</code>.
     */
    public void setIspinned(Integer ispinned) {
        this.ispinned = ispinned;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Note (");

        sb.append(noteid);
        sb.append(", ").append(notetitle);
        sb.append(", ").append(notedescription);
        sb.append(", ").append(notestatus);
        sb.append(", ").append(userid);
        sb.append(", ").append(createdby);
        sb.append(", ").append(createdat);
        sb.append(", ").append(modifiedby);
        sb.append(", ").append(modifiedat);
        sb.append(", ").append(ispinned);

        sb.append(")");
        return sb.toString();
    }
}