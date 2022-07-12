import { defineStore } from "pinia";
import { NoteService } from "../services/note.service";

export const useNoteStore = defineStore({
  id: "noteStore",
  state: () => ({
    notes: [],
    total: 0,
    note: {},
  }),
  getters: {
    isOutOfNotes: (state) => state.notes.length >= state.total,
    isNotesEmpty: (state) => state.notes.length === 0,
    isNoteEmpty: (state) => state.note === {},
  },
  actions: {
    async getNoteList(request) {
      const response = await NoteService.getNoteList(request);
      this.notes = response.data.list;
      this.total = response.data.total;
    },

    async loadMoreNote(request) {
      const response = await NoteService.getNoteList(request);
      Array.prototype.push.apply(this.notes, response.data.list);
      this.total = response.data.total;
    },

    async getNoteDetail(noteId) {
      const response = await NoteService.getNoteDetail(noteId);
      this.note = response.data;
    },

    updateNote(note) {
      return new Promise((resolve, reject) => {
        NoteService.updateNote(note)
          .then((result) => {
            this.note = result.data;
            resolve(result);
          })
          .catch((e) => {
            reject(e);
          });
      });
    },

    createNote(note) {
      return new Promise((resolve, reject) => {
        NoteService.createNote(note)
          .then((result) => {
            this.note = result.data;
            resolve(result);
          })
          .catch((e) => {
            reject(e);
          });
      });
    },

    updateNoteIsPinned(noteId, isPinned) {
      return new Promise((resolve, reject) => {
        NoteService.updateNoteIsPinned(noteId, isPinned)
          .then((result) => {
            this.note = result.data;
            resolve(result);
          })
          .catch((e) => {
            reject(e);
          });
      });
    },

    updateNoteStatus(noteId, noteStatus) {
      return new Promise((resolve, reject) => {
        NoteService.updateNoteStatus(noteId, noteStatus)
          .then((result) => {
            this.note = result.data;
            resolve(result);
          })
          .catch((e) => {
            reject(e);
          });
      });
    },
  },
});
