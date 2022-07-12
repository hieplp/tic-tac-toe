<template>
  <div class="container back">
    <TopNav>
      <div class="right">
        <span
          :class="{
            orange: note.isPinned === IsPinned.PINNED,
          }"
          class="material-icons item"
          @click="handlePinNote"
        >
          push_pin
        </span>
      </div>
    </TopNav>

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
    <UILoading v-if="isLoading" />

    <div class="note">
      <div class="title">
        <InputText
          v-model="note.noteTitle"
          :error-message="error.titleMsg"
          :is-error="error.title"
          placeholder="Title"
          type="text"
        />
      </div>
      <div class="content">
        <InputTextarea
          v-model="note.noteDescription"
          placeholder="Note"
          rows="1000"
        />
      </div>
      <div class="button orange" @click="createNote">Save</div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useNoteStore } from "../../stores/note.store";
import { IsPinned } from "../../common/enum/static.enum";
import { NOTE_CREATE_TEXT } from "../../common/text/error.text";
import { isNotValidWithMinLength } from "../../common/util/validation.util";

const noteStore = useNoteStore();
const router = useRouter();

/**
 ******************************
 * @DATA
 ******************************
 */
const error = ref({
  title: false,
  titleMsg: NOTE_CREATE_TEXT.INVALID_TITLE,
});

const note = ref({
  noteTitle: "",
  noteDescription: "",
  isPinned: 0,
});

const modal = ref({
  isVisible: false,
  btnText: "CLOSE",
  content: NOTE_CREATE_TEXT.CREATE_FAILED,
});

const isLoading = ref(false);

/**
 ******************************
 * @API_METHODS
 ******************************
 */
function createNote() {
  isLoading.value = true;
  if (isNotValid()) {
    return;
  }

  noteStore
    .createNote(note.value)
    .then(() => {
      router.push({ name: "home" });
    })
    .catch(() => {
      modal.value.isVisible = true;
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
  error.value.title = isNotValidWithMinLength(note.value.noteTitle, 1);
  error.value.titleMsg = NOTE_CREATE_TEXT.INVALID_TITLE;

  return error.value.title;
}

function handlePinNote() {
  note.value.isPinned =
    note.value.isPinned === IsPinned.PINNED
      ? IsPinned.NOT_PINNED
      : IsPinned.PINNED;
}
</script>

<script>
import InputText from "../../components/inputs/InputText.vue";
import InputTextarea from "../../components/inputs/InputTextarea.vue";
import UIModal from "../../components/modals/UIModal.vue";
import UILoading from "../../components/UILoading.vue";
import TopNav from "../../components/TopNav.vue";

export default {
  name: "CreateNoteView",
  components: {
    InputText,
    InputTextarea,
    TopNav,
    UIModal,
    UILoading,
  },
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style.scss";
</style>
