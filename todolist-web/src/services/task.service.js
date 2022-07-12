import { ErrorWrapper, ResponseWrapper } from "./util";
import {
  getWithToken,
  postWithToken,
  putWithToken,
} from "../common/util/axios.util";
import { ApiConfig } from "../common/config";

export class TaskService {
  /**
   ******************************
   * @API
   ******************************
   */
  static async create(task) {
    try {
      const response = await postWithToken(ApiConfig.TASK, task);
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }

  static async update(task) {
    try {
      const response = await putWithToken(
        `${ApiConfig.TASK}/${task.taskId}`,
        task
      );
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }

  static async getTaskStatusSet(fromDate, toDate) {
    try {
      const response = await getWithToken(ApiConfig.TASK + "/status/set", {
        fromDate: fromDate,
        toDate: toDate,
      });
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }

  static async getTaskMap(fromDate, toDate) {
    try {
      const response = await getWithToken(ApiConfig.TASK + "/date/map", {
        fromDate: fromDate,
        toDate: toDate,
      });
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }

  static async getTaskDetail(taskId) {
    try {
      const response = await getWithToken(ApiConfig.TASK + "/" + taskId);
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }

  static async updateTaskStatus(taskId, taskStatus) {
    try {
      const response = await putWithToken(
        ApiConfig.TASK + "/" + taskId + "/status",
        {
          taskStatus: taskStatus,
        }
      );
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }

  static async updateTask(task) {
    try {
      const response = await putWithToken(ApiConfig.TASK, task);
      return new ResponseWrapper(response, response.data.data);
    } catch (e) {
      throw new ErrorWrapper(e);
    }
  }
}
