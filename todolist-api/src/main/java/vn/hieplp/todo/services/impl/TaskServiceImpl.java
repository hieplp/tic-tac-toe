package vn.hieplp.todo.services.impl;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.constant.StaticEnum;
import vn.hieplp.todo.common.models.TaskModel;
import vn.hieplp.todo.common.request.task.*;
import vn.hieplp.todo.common.response.CommonGetResponse;
import vn.hieplp.todo.common.response.task.GetTaskListByDayResponse;
import vn.hieplp.todo.common.utils.DateTimes;
import vn.hieplp.todo.common.utils.Generator;
import vn.hieplp.todo.common.utils.JsonConverter;
import vn.hieplp.todo.repositories.generates.tables.records.TaskRecord;
import vn.hieplp.todo.repositories.sources.ITaskRepository;
import vn.hieplp.todo.services.ITaskService;

import java.util.List;
import java.util.Map;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 28/04/2022
 * Time: 11:12
 */
public class TaskServiceImpl implements ITaskService {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final ITaskRepository taskRepository;

    @Inject
    public TaskServiceImpl(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskModel createTask(CreateTaskRequest request) {
        LOGGER.info("Create task with request {}", JsonConverter.toJson(request));
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.from(request);
        taskRecord.setTaskid(Generator.generateRandomString(8));
        taskRecord.setTaskstatus(StaticEnum.TaskStatus.ACTIVE.getStatus());
        taskRecord.setUserid(request.getCreatedBy());
        taskRecord.setCreatedby(request.getCreatedBy());
        taskRecord.setCreatedat(DateTimes.getCurrentLocalDateTime());
        taskRecord.setModifiedby(request.getCreatedBy());
        taskRecord.setModifiedat(DateTimes.getCurrentLocalDateTime());
        taskRepository.save(taskRecord);

        return this.getTaskModel(taskRecord.getTaskid());
    }

    @Override
    public TaskModel updateTask(UpdateTaskRequest request) {
        LOGGER.info("Update task with request {}", JsonConverter.toJson(request));

        TaskRecord taskRecord = taskRepository.getTaskRecord(request.getTaskId(), request.getUpdatedBy());
        taskRecord.from(request);
        taskRecord.setModifiedby(request.getUpdatedBy());
        taskRecord.setModifiedat(DateTimes.getCurrentLocalDateTime());
        taskRepository.updateNotNull(taskRecord);

        return this.getTaskModel(taskRecord.getTaskid());
    }

    @Override
    public TaskModel updateTaskStatus(UpdateTaskStatusRequest request) {
        LOGGER.info("Update task status with request: {}", JsonConverter.toJson(request));

        StaticEnum.TaskStatus taskStatus = StaticEnum.TaskStatus.safeValueOf(request.getTaskStatus());
        TaskRecord taskRecord = taskRepository.getTaskRecord(request.getTaskId(), request.getUpdatedBy());
        taskRecord.setTaskstatus(taskStatus.getStatus());
        taskRecord.setModifiedby(request.getUpdatedBy());
        taskRecord.setModifiedat(DateTimes.getCurrentLocalDateTime());
        taskRepository.updateNotNull(taskRecord);

        return this.getTaskModel(taskRecord.getTaskid());
    }

    @Override
    public CommonGetResponse getTaskList(GetTaskListRequest request) {
        LOGGER.info("Get task list with request {}", JsonConverter.toJson(request));
        return taskRepository.getTaskList(request);
    }

    @Override
    public GetTaskListByDayResponse getTaskListByDay(GetTaskListByDayRequest request) {
        LOGGER.info("Get task list by date with request {}", JsonConverter.toJson(request));
        return null;
    }

    @Override
    public List<Long> getTaskStatusSetByDate(Long fromDate, Long toDate, String userId) {
        LOGGER.info("Get task status set by fromDate {}, toDate {} and userId {}", fromDate, toDate, userId);
        return taskRepository.getTaskStatusSetByDate(fromDate, toDate, userId);
    }

    @Override
    public TaskModel getTaskModel(String taskId, String userId) {
        LOGGER.info("Get task model with taskId {} and userId {}", taskId, userId);
        return taskRepository.getTaskModel(taskId, userId);
    }

    @Override
    public TaskModel getTaskModel(String taskId) {
        LOGGER.info("Get task model with taskId {}", taskId);
        return taskRepository.getTaskModel(taskId);
    }

    @Override
    public TaskModel deleteTask(String taskId, String userId) {
        LOGGER.info("Delete task with taskId {} and userId {}", taskId, userId);
        UpdateTaskStatusRequest request = UpdateTaskStatusRequest.builder()
                .taskId(taskId)
                .updatedBy(userId)
                .taskStatus(StaticEnum.TaskStatus.DELETED.getStatus())
                .build();
        return this.updateTaskStatus(request);
    }

    @Override
    public Map<Long, List<TaskModel>> getTaskMap(Long fromDate, Long toDate, String userId) {
        LOGGER.info("Get task map by date with fromDate {}, toDate {} and userId {}", fromDate, toDate, userId);
        List<Long> taskDate = this.getTaskStatusSetByDate(fromDate, toDate, userId);
        return taskRepository.getTaskMap(taskDate, userId);
    }
}
