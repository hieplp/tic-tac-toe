<template>
  <div
    class="task"
    :class="{
      active: TaskStatus.ACTIVE === task.taskStatus,
    }"
  >
    <div class="title">{{ task.taskTitle }}</div>
    <div class="content">{{ task.taskDescription }}</div>
    <div class="createdAt">
      Created at
      {{ moment.unix(task.taskDate / 1000).format("DD/MM/YYYY") }}
    </div>
    <div class="check">
      <span v-if="TaskStatus.ACTIVE === task.taskStatus" class="material-icons">
        check_box_outline_blank
      </span>
      <span v-else class="material-icons"> check_box </span>
    </div>
  </div>
</template>

<script setup>
import { TaskStatus } from "../../common/enum/static.enum";
import moment from "moment";
</script>

<script>
export default {
  name: "TaskComponent",
  props: {
    task: { type: Object, required: true },
  },
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style.scss";
</style>
