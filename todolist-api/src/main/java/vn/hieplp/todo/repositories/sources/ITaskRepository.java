package vn.hieplp.todo.repositories.sources;

import vn.hieplp.todo.common.models.TaskModel;
import vn.hieplp.todo.common.request.task.GetTaskListRequest;
import vn.hieplp.todo.common.response.CommonGetResponse;
import vn.hieplp.todo.repositories.base.IBaseRepository;
import vn.hieplp.todo.repositories.generates.tables.records.TaskRecord;

import java.util.List;
import java.util.Map;

public interface ITaskRepository extends IBaseRepository {
    TaskRecord getTaskRecord(String taskId, String userId);

    TaskModel getTaskModel(String taskId);

    TaskModel getTaskModel(String taskId, String userId);

    CommonGetResponse getTaskList(GetTaskListRequest request);

    List<Long> getTaskStatusSetByDate(Long fromDate, Long toDate, String userId);

    Map<Long, List<TaskModel>> getTaskMap(List<Long> taskDate, String userId);
}
