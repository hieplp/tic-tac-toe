package vn.hieplp.todo.repositories.sources.impl;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;
import vn.hieplp.todo.common.constant.StaticEnum;
import vn.hieplp.todo.common.exceptions.DatabaseException;
import vn.hieplp.todo.common.models.TaskModel;
import vn.hieplp.todo.common.request.task.GetTaskListRequest;
import vn.hieplp.todo.common.response.CommonGetResponse;
import vn.hieplp.todo.common.utils.JsonConverter;
import vn.hieplp.todo.common.utils.SortUtil;
import vn.hieplp.todo.repositories.base.BaseRepositoryImpl;
import vn.hieplp.todo.repositories.base.CustomDSLContext;
import vn.hieplp.todo.repositories.generates.tables.records.TaskRecord;
import vn.hieplp.todo.repositories.sources.ITaskRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static vn.hieplp.todo.repositories.generates.Tables.NOTE;
import static vn.hieplp.todo.repositories.generates.Tables.TASK;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 11:00
 */
public class TaskRepositoryImpl extends BaseRepositoryImpl implements ITaskRepository {

    @Override
    public TaskRecord getTaskRecord(String taskId, String userId) {
        LOGGER.info("Get task record with taskId {} and userId {}", taskId, userId);
        try (CustomDSLContext context = getDslContext()) {
            return context.selectFrom(TASK)
                    .where(TASK.TASKID.eq(taskId)
                            .and(TASK.USERID.eq(userId)))
                    .fetchInto(TaskRecord.class)
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Task record with taskId {} and userId {} is not found", taskId, userId);
            throw new DatabaseException.NotFoundException("Task record not found");
        } catch (Exception e) {
            LOGGER.error("Error when get task record: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error when get task record");
        }
    }

    @Override
    public TaskModel getTaskModel(String taskId) {
        LOGGER.info("Get task model with taskId {}", taskId);
        try (CustomDSLContext context = getDslContext()) {
            return context.select(TASK.fields())
                    .from(TASK)
                    .where(TASK.TASKID.eq(taskId))
                    .fetchInto(TaskModel.class)
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Task model with taskId {} is not found", taskId);
            throw new DatabaseException.NotFoundException("Task model not found");
        } catch (Exception e) {
            LOGGER.info("Error when get task model with taskId: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error when get task model with taskId");
        }
    }

    @Override
    public TaskModel getTaskModel(String taskId, String userId) {
        LOGGER.info("Get task model with taskId {} and  userId {}", taskId, userId);
        try (CustomDSLContext context = getDslContext()) {
            return context.select(TASK.fields())
                    .from(TASK)
                    .where(TASK.TASKID.eq(taskId)
                            .and(TASK.USERID.eq(userId)))
                    .fetchInto(TaskModel.class)
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Task model with taskId {} and userId {} is not found", taskId, userId);
            throw new DatabaseException.NotFoundException("Task model not found");
        } catch (Exception e) {
            LOGGER.info("Error when get task model with taskId and userId: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error when get task model with taskId and userId");
        }
    }

    @Override
    public CommonGetResponse getTaskList(GetTaskListRequest request) {
        LOGGER.info("Get task list with request {}", JsonConverter.toJson(request));
        try (CustomDSLContext context = getDslContext()) {
            Condition condition = DSL.trueCondition()
                    .and(TASK.TASKSTATUS.eq(StaticEnum.TaskStatus.DELETED.getStatus()))
                    .and(TASK.USERID.eq(request.getUserId()));

            condition = getFilterCondition(condition, request.getFilterBy(), request.getFilterValue());
            condition = getSearchCondition(condition, request.getSearchBy(), request.getSearchValue());

            return CommonGetResponse.builder()
                    .list(context.select(TASK.fields())
                            .from(TASK)
                            .where(condition)
                            .orderBy(SortUtil.generateSortField(request.getOrder(), request.getBy(), TASK.TASKID.asc()))
                            .limit(request.getFrom(), request.getLimit() == 0 ? count(TASK) : request.getLimit())
                            .fetchInto(TaskModel.class))
                    .total(count(context.select(TASK.TASKID), NOTE, condition))
                    .build();
        } catch (Exception e) {
            LOGGER.error("Error when get task list: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error when get task list");
        }
    }

    @Override
    public List<Long> getTaskStatusSetByDate(Long fromDate, Long toDate, String userId) {
        LOGGER.info("Get task status map by fromDate {}, toDate{} and userId {}", fromDate, toDate, userId);
        try (CustomDSLContext context = getDslContext()) {
            Field<?> taskDate = DSL.cast(TASK.TASKDATE, Date.class);
            return context.select(taskDate)
                    .from(TASK)
                    .where(TASK.TASKDATE.greaterOrEqual(new Timestamp(fromDate).toLocalDateTime())
                            .and(TASK.USERID.eq(userId))
                            .and(TASK.TASKDATE.lessOrEqual(new Timestamp(toDate).toLocalDateTime()))
                            .and(TASK.TASKSTATUS.ne(StaticEnum.TaskStatus.DELETED.getStatus())))
                    .groupBy(taskDate)
                    .orderBy(taskDate.asc())
                    .fetchInto(Long.class);
        } catch (Exception e) {
            LOGGER.error("Error when get task status map by date: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error when get task status map by date");
        }
    }

    @Override
    public Map<Long, List<TaskModel>> getTaskMap(List<Long> taskDate, String userId) {
        LOGGER.info("Get task map with set {}", taskDate);
        try (CustomDSLContext context = getDslContext()) {
            Map<Long, List<TaskModel>> taskMap = new HashMap<>();
            taskDate.forEach(date -> {
                LocalDate startOfDay = new Timestamp(date).toLocalDateTime().toLocalDate();
                LocalDateTime endOfDay = startOfDay.plusDays(1).atStartOfDay();

                taskMap.put(date, context.select(TASK.fields())
                        .from(TASK)
                        .where(TASK.USERID.eq(userId)
                                .and(TASK.TASKDATE.between(startOfDay.atStartOfDay(), endOfDay))
                                .and(TASK.TASKSTATUS.ne(StaticEnum.TaskStatus.DELETED.getStatus())))
                        .fetchInto(TaskModel.class));
            });
            return taskMap;
        } catch (Exception e) {
            LOGGER.error("Error when get task map: {}", e.getMessage());
            throw new DatabaseException.QueryException("Unknown error when get task map");
        }
    }
}
