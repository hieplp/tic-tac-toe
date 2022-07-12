package vn.hieplp.todo.services;

import vn.hieplp.todo.common.models.TaskModel;
import vn.hieplp.todo.common.request.task.*;
import vn.hieplp.todo.common.response.CommonGetResponse;
import vn.hieplp.todo.common.response.task.GetTaskListByDayResponse;

import java.util.List;
import java.util.Map;

public interface ITaskService {
    TaskModel createTask(CreateTaskRequest request);

    TaskModel updateTask(UpdateTaskRequest request);

    TaskModel updateTaskStatus(UpdateTaskStatusRequest request);

    CommonGetResponse getTaskList(GetTaskListRequest request);

    GetTaskListByDayResponse getTaskListByDay(GetTaskListByDayRequest request);

    List<Long> getTaskStatusSetByDate(Long fromDate, Long toDate, String userId);

    TaskModel getTaskModel(String taskId, String userId);

    TaskModel getTaskModel(String taskId);

    TaskModel deleteTask(String taskId, String userId);

    Map<Long, List<TaskModel>> getTaskMap(Long fromDate, Long toDate, String userId);
}
