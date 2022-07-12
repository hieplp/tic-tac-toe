import { defineStore } from "pinia";
import { TaskService } from "../services/task.service";

export const useTaskStore = defineStore({
  id: "taskStore",
  state: () => ({
    tasks: [],
    task: null,
    taskStatusSet: new Set(),
    taskMap: {},
    isLoading: false,
  }),
  getters: {},
  actions: {
    createTask(task) {
      return new Promise((resolve, reject) => {
        TaskService.create(task)
          .then((result) => {
            this.task = result.data;
            resolve(result);
          })
          .catch((e) => {
            reject(e);
          });
      });
    },

    async getTaskStatusSet(fromDate, toDate) {
      const response = await TaskService.getTaskStatusSet(fromDate, toDate);
      this.taskStatusSet = new Set(response.data);
    },

    async getTaskMap(fromDate, toDate) {
      const response = await TaskService.getTaskMap(fromDate, toDate);
      this.taskMap = response.data;
    },

    async getTaskDetail(taskId) {
      const response = await TaskService.getTaskDetail(taskId);
      this.task = response.data;
    },

    async updateTaskStatus(taskId, taskStatus) {
      return new Promise((resolve, reject) => {
        TaskService.updateTaskStatus(taskId, taskStatus)
          .then((rs) => {
            this.task = rs.data;
            resolve(rs);
          })
          .catch((e) => {
            reject(e);
          });
      });
    },

    async updateTask(task) {
      return new Promise((resolve, reject) => {
        TaskService.updateTask(task)
          .then((rs) => {
            this.task = rs.data;
            resolve(rs);
          })
          .catch((e) => reject(e));
      });
    },
  },
});
