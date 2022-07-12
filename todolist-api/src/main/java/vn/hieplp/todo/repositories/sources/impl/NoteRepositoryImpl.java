package vn.hieplp.todo.repositories.sources.impl;

import org.jooq.Condition;
import org.jooq.impl.DSL;
import vn.hieplp.todo.common.constant.StaticEnum;
import vn.hieplp.todo.common.exceptions.DatabaseException;
import vn.hieplp.todo.common.models.NoteModel;
import vn.hieplp.todo.common.request.note.GetNoteListRequest;
import vn.hieplp.todo.common.response.CommonGetResponse;
import vn.hieplp.todo.common.utils.JsonConverter;
import vn.hieplp.todo.common.utils.SortUtil;
import vn.hieplp.todo.repositories.base.BaseRepositoryImpl;
import vn.hieplp.todo.repositories.base.CustomDSLContext;
import vn.hieplp.todo.repositories.generates.tables.records.NoteRecord;
import vn.hieplp.todo.repositories.sources.INoteRepository;

import static vn.hieplp.todo.repositories.generates.Tables.NOTE;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 10:59
 */
public class NoteRepositoryImpl extends BaseRepositoryImpl implements INoteRepository {
    @Override
    public NoteModel getNoteModel(String noteId) {
        LOGGER.info("Start get note model with noteId {}", noteId);
        try (CustomDSLContext context = getDslContext()) {
            return context.select(NOTE.fields())
                    .from(NOTE)
                    .where(NOTE.NOTEID.eq(noteId))
                    .fetchInto(NoteModel.class)
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Note model with noteId {} is not found", noteId);
            throw new DatabaseException.NotFoundException("Note model not found");
        } catch (Exception e) {
            LOGGER.error("Error when get note model with noteId: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error when get note model");
        }
    }

    @Override
    public NoteModel getNoteModel(String noteId, String userId) {
        LOGGER.info("Start get note model with noteId {} and userId {}", noteId, userId);
        try (CustomDSLContext context = getDslContext()) {
            return context.select(NOTE.fields())
                    .from(NOTE)
                    .where(NOTE.NOTEID.eq(noteId)
                            .and(NOTE.USERID.eq(userId)))
                    .fetchInto(NoteModel.class)
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Note model with noteId {} and userId {} is not found", noteId, userId);
            throw new DatabaseException.NotFoundException("Note model not found");
        } catch (Exception e) {
            LOGGER.error("Error when get note model with noteId and userId: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error when get note model");
        }
    }

    @Override
    public NoteRecord getNoteRecord(String noteId) {
        LOGGER.info("Start get note record with noteId {}", noteId);
        try (CustomDSLContext context = getDslContext()) {
            return context.selectFrom(NOTE)
                    .where(NOTE.NOTEID.eq(noteId))
                    .fetchInto(NoteRecord.class)
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Note record with noteId {} is not found", noteId);
            throw new DatabaseException.NotFoundException("Note record not found");
        } catch (Exception e) {
            LOGGER.error("Error when get note record with noteId: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error when get note record");
        }
    }

    @Override
    public NoteRecord getNoteRecord(String noteId, String userId) {
        LOGGER.info("Start get note record with noteId {} and userId {}", noteId, userId);
        try (CustomDSLContext context = getDslContext()) {
            return context.selectFrom(NOTE)
                    .where(NOTE.NOTEID.eq(noteId)
                            .and(NOTE.USERID.eq(userId)))
                    .fetchInto(NoteRecord.class)
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Note record with noteId {} and userId {} is not found", noteId, userId);
            throw new DatabaseException.NotFoundException("Note record not found");
        } catch (Exception e) {
            LOGGER.error("Error when get note record with noteId and userId: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error when get note record");
        }
    }

    @Override
    public CommonGetResponse getNoteList(GetNoteListRequest request) {
        LOGGER.info("Start get note list with request {}", JsonConverter.toJson(request));
        try (CustomDSLContext context = getDslContext()) {
            Condition condition = DSL.trueCondition()
                    .and(NOTE.NOTESTATUS.ne(StaticEnum.NoteStatus.DELETED.getStatus()))
                    .and(NOTE.USERID.eq(request.getUserId()));

            condition = getFilterCondition(condition, request.getFilterBy(), request.getFilterValue());
            condition = getSearchCondition(condition, request.getSearchBy(), request.getSearchValue());

            return CommonGetResponse.builder()
                    .list(context.select(NOTE.fields())
                            .from(NOTE)
                            .where(condition)
                            .orderBy(NOTE.ISPINNED.desc(), SortUtil.generateSortField(request.getOrder(), request.getBy(), NOTE.NOTEID.asc()))
                            .limit(request.getFrom(), request.getLimit() == 0 ? count(NOTE) : request.getLimit())
                            .fetchInto(NoteModel.class))
                    .total(count(context.select(NOTE.NOTEID), NOTE, condition))
                    .build();
        } catch (Exception e) {
            LOGGER.error("Error when get note list: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error when get note list");
        }
    }
}
