<template>
  <div class="container back">
    <TopNav>
      <div class="right">
        <span
          class="material-icons item"
          v-if="task !== null && TaskStatus.ACTIVE === task.taskStatus"
          @click="handleCheckbox"
        >
          check_box_outline_blank
        </span>
        <span
          class="material-icons item active"
          v-if="task !== null && TaskStatus.DONE === task.taskStatus"
          @click="handleCheckbox"
        >
          check_box
        </span>
        <span class="material-icons item" @click="handleEditBtn"> edit </span>
        <span class="material-icons item red" @click="handleDeleteConfModal">
          delete_forever
        </span>
      </div>
    </TopNav>

    <UILoading v-if="isLoading" />

    <UIModal
      v-if="modal.isVisible"
      :btn-text="modal.btnText"
      :content="modal.content"
      :on-click="
        () => {
          this.modal.isVisible = false;
        }
      "
    />

    <UIYesNoModal
      v-if="isDelModalVisible"
      :on-no-click="handleDeleteConfModal"
      :on-yes-click="deleteTask"
      content="Do you want to delete this task?"
    />

    <div class="task" v-if="isTaskVisible">
      <div class="title">
        <InputText
          :is-disabled="isDisabled"
          placeholder="Title"
          type="text"
          v-model="task.taskTitle"
          :is-error="error.date"
          :error-message="error.dateMsg"
        />
      </div>
      <div class="description">
        <InputTextarea
          :is-disabled="isDisabled"
          placeholder="Description"
          v-model="task.taskDescription"
        />
      </div>
      <div class="date">
        <InputDate
          :is-disabled="isDisabled"
          placeholder="Date"
          v-model="taskDate"
          :is-error="error.date"
          :error-message="error.dateMsg"
        />
      </div>
      <div class="image">
        <InputImage
          v-show="!isDisabled"
          v-model="taskImage"
          placeholder="Add Image (Optional)"
        />
        <img
          v-if="isDisabled && task.taskImages"
          alt=""
          :src="ImageHost + task.taskImages"
          @click="openImageModal"
        />
        <ImageModal
          v-show="isDisabled"
          ref="imageModal"
          :image="ImageHost + task.taskImages"
        />
      </div>
      <div v-if="!isDisabled" class="button orange" @click="update">UPDATE</div>
      <div v-if="!isDisabled" class="button red" @click="handleEditBtn">
        CANCEL
      </div>
    </div>
  </div>
</template>

<script setup>
import { useTaskStore } from "../../stores/task.store";
import { computed, onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { ImageHost } from "../../common/config";
import moment from "moment";
import { TASK_CREATE_TEXT, TASK_TEXT } from "../../common/text/error.text";
import { isNotValidWithMinLength } from "../../common/util/validation.util";
import { FileService } from "../../services/file.service";
import { TaskStatus } from "../../common/enum/static.enum";
import { SUCCESS_TASK_TEXT } from "../../common/text/success.text";

const taskStore = useTaskStore();
const route = useRoute();

const router = useRouter();

onMounted(async () => {
  isLoading.value = true;
  isTaskVisible.value = false;
  await getTaskDetail();
  isTaskVisible.value = true;
  isLoading.value = false;
});

/**
 ******************************
 * @DATA
 ******************************
 */
const task = computed(() => taskStore.task);
const taskDate = ref(null);
const taskImage = ref(null);

const error = ref({
  title: false,
  titleMsg: TASK_CREATE_TEXT.INVALID_TITLE,
  date: false,
  dateMsg: TASK_CREATE_TEXT.INVALID_DATE,
});

const isLoading = ref(false);
const isTaskVisible = ref(false);
const isDisabled = ref(true);

const imageModal = ref(null);
const modal = ref({
  isVisible: false,
  btnText: "CLOSE",
  content: "",
});
const isDelModalVisible = ref(false);
/**
 ******************************
 * @API_METHODS
 ******************************
 */
async function getTaskDetail() {
  await taskStore.getTaskDetail(route.params.taskId);
  taskDate.value = moment.unix(task.value.taskDate / 1000).format("yyyy-MM-DD");
}

async function update() {
  if (isNotValid()) {
    return;
  }

  if (taskImage.value) {
    const response = await FileService.uploadImage(taskImage.value);
    task.value.taskImages = response.data.toString();
  }
  task.value.taskDate = new Date(taskDate.value).getTime();

  isLoading.value = true;
  taskStore
    .updateTask(task.value)
    .then(() => {
      openModal(SUCCESS_TASK_TEXT.UPDATE_SUCCESSFULLY);
    })
    .catch(() => {
      openModal(TASK_TEXT.UPDATE_FAILED);
    })
    .finally(() => {
      isLoading.value = false;
    });
}

async function updateTaskStatus() {
  isLoading.value = true;
  taskStore
    .updateTaskStatus(task.value.taskId, task.value.taskStatus)
    .then(() => {})
    .catch(() => {
      openModal(TASK_TEXT.UPDATE_FAILED);
    })
    .finally(() => {
      isLoading.value = false;
    });
}

async function deleteTask() {
  isLoading.value = true;
  taskStore
    .updateTaskStatus(task.value.taskId, TaskStatus.DELETED)
    .then(() => {
      router.push({ name: "home" });
    })
    .catch(() => {
      openModal(TASK_TEXT.DELETE_FAILED);
    });
}

/**
 ******************************
 * @PRIVATE_METHODS
 ******************************
 */

function openImageModal() {
  imageModal.value.isVisible(true);
}

function handleEditBtn() {
  isDisabled.value = !isDisabled.value;
}

function isNotValid() {
  error.value.title = isNotValidWithMinLength(task.value.taskTitle, 1);
  error.value.date = isNotValidWithMinLength(taskDate.value, 1);

  return error.value.title || error.value.date;
}

async function handleCheckbox() {
  task.value.taskStatus =
    task.value.taskStatus === TaskStatus.ACTIVE
      ? TaskStatus.DONE
      : TaskStatus.ACTIVE;

  await updateTaskStatus();
}

function openModal(content) {
  modal.value.isVisible = true;
  modal.value.content = content;
}

function handleDeleteConfModal() {
  isDelModalVisible.value = !isDelModalVisible.value;
}
</script>
<script>
import InputText from "../../components/inputs/InputText.vue";
import InputTextarea from "../../components/inputs/InputTextarea.vue";
import InputImage from "../../components/inputs/InputImage.vue";
import InputDate from "../../components/inputs/InputDate.vue";
import ImageModal from "../../components/modals/ImageModal.vue";
import UIModal from "../../components/modals/UIModal.vue";
import UIYesNoModal from "../../components/modals/UIYesNoModal.vue";
import UILoading from "../../components/UILoading.vue";
import TopNav from "../../components/TopNav.vue";

export default {
  name: "TaskDetailView",
  components: {
    InputText,
    InputTextarea,
    InputImage,
    InputDate,
    ImageModal,
    UIYesNoModal,
    UIModal,
    UILoading,
    TopNav,
  },
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style.scss";
</style>
