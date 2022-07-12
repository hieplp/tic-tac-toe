<template>
  <div class="container back">
    <TopNav>
      <div class="right"></div>
    </TopNav>

    <UILoading v-if="isLoading" />

    <div class="task">
      <div class="title">
        <InputText
          v-model="task.taskTitle"
          placeholder="Title"
          type="text"
          :is-error="error.title"
          :error-message="error.titleMsg"
        />
      </div>
      <div class="description">
        <InputTextarea
          v-model="task.taskDescription"
          placeholder="Description"
        />
      </div>
      <div class="date">
        <InputDate
          v-model="taskDate"
          placeholder="Date"
          :is-error="error.date"
          :error-message="error.dateMsg"
        />
      </div>
      <div class="image">
        <InputImage v-model="image" placeholder="Add Image (Optional)" />
      </div>
      <div class="button orange" @click="create">CREATE</div>
    </div>
  </div>
</template>

<script setup>
import { useTaskStore } from "../../stores/task.store";
import { FileService } from "../../services/file.service";
import { ref } from "vue";
import { TASK_CREATE_TEXT } from "../../common/text/error.text";
import { isNotValidWithMinLength } from "../../common/util/validation.util";
import { useRouter } from "vue-router";

const taskStore = useTaskStore();

const router = useRouter();

/**
 ******************************
 * @DATA
 ******************************
 */
const task = ref({
  taskTitle: "",
  taskDescription: "",
  taskDate: 0,
  taskImages: "",
});

const error = ref({
  title: false,
  titleMsg: TASK_CREATE_TEXT.INVALID_TITLE,
  date: false,
  dateMsg: TASK_CREATE_TEXT.INVALID_DATE,
});

const image = ref({});
const taskDate = ref("");
const isLoading = ref(false);

/**
 ******************************
 * @API_METHODS
 ******************************
 */
async function create() {
  if (isNotValid()) {
    return;
  }
  if (image.value) {
    const response = await FileService.uploadImage(image.value);
    task.value.taskImages = response.data.toString();
  }
  isLoading.value = true;
  task.value.taskDate = new Date(taskDate.value).getTime();
  taskStore
    .createTask(task.value)
    .then(() => {
      router.push({ name: "home" });
    })
    .catch((e) => {
      console.log(e);
    })
    .finally(() => {
      isLoading.value = false;
    });
}

/**
 ******************************
 * @PRIVATE_METHODS
 ******************************
 */
function isNotValid() {
  error.value.title = isNotValidWithMinLength(task.value.taskTitle, 1);
  error.value.date = isNotValidWithMinLength(taskDate.value, 1);

  return error.value.title || error.value.date;
}
</script>

<script>
import InputText from "../../components/inputs/InputText.vue";
import InputTextarea from "../../components/inputs/InputTextarea.vue";
import InputImage from "../../components/inputs/InputImage.vue";
import InputDate from "../../components/inputs/InputDate.vue";
import TopNav from "../../components/TopNav.vue";
import UILoading from "../../components/UILoading.vue";

export default {
  name: "CreateTaskView",
  components: {
    InputText,
    InputTextarea,
    InputImage,
    InputDate,
    TopNav,
    UILoading,
  },
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style.scss";

.container {
  height: 100vh;
}
</style>
