<template>
  <div class="container back">
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
    <UiYesNoModal
      v-if="isYesNoVisible"
      :on-no-click="handleYesNoModal"
      :on-yes-click="deleteNote"
      content="Do you want to delete this note?"
    />
    <top-nav>
      <div class="right">
        <span
          :class="{
            orange: this.note.isPinned === IsPinned.PINNED,
          }"
          class="material-icons item"
          @click="pinNote"
        >
          push_pin
        </span>
        <span class="material-icons item red" @click="handleYesNoModal">
          delete_forever
        </span>
      </div>
    </top-nav>
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
      <div class="button orange" @click="updateNote(note)">Save</div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from "vue";
import { useRouter } from "vue-router";
import { useNoteStore } from "../../stores/note.store";
import { IsPinned, NoteStatus } from "../../common/enum/static.enum";
import { isNotValidWithMinLength } from "../../common/util/validation.util";
// Text
import { NOTE_CREATE_TEXT, NOTE_TEXT } from "../../common/text/error.text";
import { SUCCESS_NOTE_TEXT } from "../../common/text/success.text";

const noteStore = useNoteStore();
const router = useRouter();

/**
 ******************************
 * @DATA
 ******************************
 */
const note = computed(() => noteStore.note);
const error = ref({
  title: false,
  titleMsg: NOTE_TEXT.INVALID_TITLE,
});
const modal = ref({
  isVisible: false,
  btnText: "CLOSE",
  content: "",
});
const isLoading = ref(false);
const isYesNoVisible = ref(false);

/**
 ******************************
 * @API_METHODS
 ******************************
 */
// eslint-disable-next-line no-unused-vars
async function getNoteDetail(noteId) {
  await noteStore.getNoteDetail(noteId);
}

function updateNote(note) {
  isLoading.value = true;
  if (isNotValid()) {
    return;
  }

  noteStore
    .updateNote(note)
    .then(() => {
      openModal(SUCCESS_NOTE_TEXT.UPDATE_SUCCESSFULLY);
    })
    .catch(() => {
      openModal(NOTE_TEXT.UPDATE_FAILED);
    })
    .finally(() => {
      isLoading.value = false;
    });
}

function pinNote() {
  noteStore
    .updateNoteIsPinned(note.value.noteId, handlePinnedNote())
    .then(() => {})
    .catch(() => {
      this.openModal(NOTE_TEXT.UPDATE_FAILED);
    });
}

function deleteNote() {
  isLoading.value = true;

  noteStore
    .updateNoteStatus(note.value.noteId, NoteStatus.DELETED)
    .then(() => {
      router.push({ name: "home" });
    })
    .catch(() => {
      isLoading.value = false;
      handleYesNoModal();
      openModal(NOTE_TEXT.UPDATE_FAILED);
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

function openModal(content) {
  modal.value.isVisible = true;
  modal.value.content = content;
}

function handlePinnedNote() {
  return note.value.isPinned === IsPinned.PINNED
    ? IsPinned.NOT_PINNED
    : IsPinned.PINNED;
}

function handleYesNoModal() {
  isYesNoVisible.value = !isYesNoVisible.value;
}
</script>

<script>
import InputText from "../../components/inputs/InputText.vue";
import InputTextarea from "../../components/inputs/InputTextarea.vue";
import UILoading from "../../components/UILoading.vue";
import UIModal from "../../components/modals/UIModal.vue";
import TopNav from "../../components/TopNav.vue";
import UiYesNoModal from "../../components/modals/UIYesNoModal.vue";

export default {
  name: "NoteDetailView",
  components: {
    UILoading,
    TopNav,
    UIModal,
    InputText,
    InputTextarea,
    UiYesNoModal,
  },
  mounted() {
    this.getNoteDetail(this.$route.params.noteId);
  },
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style.scss";
</style>
