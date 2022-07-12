<template>
  <div class="notes">
    <LoadMore :load-data="loadMoreNote">
      <div class="search"></div>
      <Note
        v-for="(note, index) in noteStore.notes"
        :key="note.noteId"
        :note="note"
        @click="goToDetail(note, index)"
      />
      <div v-if="isLoading">
        <NoteShimmer v-for="i in 5" :key="i" />
      </div>
    </LoadMore>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useNoteStore } from "../../stores/note.store";
import router from "../../router";

const noteStore = useNoteStore();
/**
 ******************************
 * @DATA
 ******************************
 */
const isLoading = ref(false);
const request = ref({});

/**
 ******************************
 * @API_METHODS
 ******************************
 */
// eslint-disable-next-line no-unused-vars
async function getNoteList() {
  isLoading.value = true;
  noteStore.notes = [];
  await noteStore.getNoteList(request.value);
  isLoading.value = false;
}

async function loadMoreNote() {
  if (noteStore.isOutOfNotes || isLoading.value) {
    return;
  }

  isLoading.value = true;
  request.value.from = noteStore.notes.length;
  await noteStore.loadMoreNote(request.value);
  isLoading.value = false;
}

/**
 ******************************
 * @PRIVATE_METHODS
 ******************************
 */
function goToDetail(note, index) {
  router.push({
    name: "noteDetail",
    params: {
      noteId: note.noteId,
      index: index,
    },
  });
}
</script>

<script>
import Note from "./Note.vue";
import NoteShimmer from "./NoteShimmer.vue";
import LoadMore from "../LoadMore.vue";
import { NOTE_REQUEST, ORDER_BY } from "../../common/request/api.request";

export default {
  name: "NotesComponent",
  components: {
    Note,
    NoteShimmer,
    LoadMore,
  },
  async mounted() {
    this.request = {
      from: 0,
      limit: 5,
      order: NOTE_REQUEST.MODIFIED_AT,
      by: ORDER_BY.DESC,
    };
    await this.getNoteList();
  },
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style.scss";

.notes {
  width: 90%;
  margin-left: auto;
  margin-right: auto;
  margin-top: 40px;
}
</style>
