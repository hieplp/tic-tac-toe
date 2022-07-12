<template>
  <div>
    <!-- Schedules shimmer loading-->
    <div class="schedules" v-if="isLoading">
      <div class="schedule" v-for="i in 3" :key="i">
        <TimelineShimmer />
        <div class="tasks">
          <TaskShimmer v-for="j in 3" :key="j" />
        </div>
      </div>
    </div>

    <!-- Schedules list-->
    <div class="schedules" v-else>
      <div class="schedule" v-for="date in taskStore.taskStatusSet" :key="date">
        <Timeline :date="date" />
        <div class="tasks">
          <Task
            v-for="task in taskStore.taskMap[date]"
            :key="task.taskId"
            :task="task"
            @click="goToDetail(task.taskId)"
          />
        </div>
      </div>
    </div>
    <div class="empty-task" v-if="taskStore.taskStatusSet.size === 0">
      Empty task
    </div>

    <div
      v-if="totalTasks <= 6 && totalTasks > 2"
      :style="{
        height: 120 * (6 - totalTasks) + 'px',
      }"
    ></div>
  </div>
</template>
<script setup>
import { useTaskStore } from "../../stores/task.store";
import { computed, onMounted, ref } from "vue";
import { useRouter } from "vue-router";

const taskStore = useTaskStore();
const router = useRouter();

onMounted(async () => {});

/**
 *
 ******************************
 * @DATA
 ******************************
 */

const isLoading = ref(false);
const totalTasks = computed(() => {
  let count = 0;
  for (let date in taskStore.taskMap) {
    count += taskStore.taskMap[date].length;
  }
  return count;
});

/**
 ******************************
 * @API_METHODS
 ******************************
 */

/**
 ******************************
 * @PRIVATE_METHODS
 ******************************
 */
function goToDetail(taskId) {
  router.push({
    name: "taskDetail",
    params: {
      taskId: taskId,
    },
  });
}
</script>

<script>
import Task from "../tasks/Task.vue";
import TaskShimmer from "../tasks/TaskShimmer.vue";
import Timeline from "./Timeline.vue";
import TimelineShimmer from "./TimelineShimmer.vue";

export default {
  name: "SchedulesComponent",
  components: {
    Task,
    TaskShimmer,
    Timeline,
    TimelineShimmer,
  },
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style.scss";

.empty-task {
  margin-top: 150px;
  text-align: center;
  font-weight: 600;
  color: $light-gray-color;
}
</style>
