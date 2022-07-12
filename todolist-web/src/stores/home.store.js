import { defineStore } from "pinia";

export const useHomeStore = defineStore({
  id: "homeStore",
  state: () => ({
    isLoading: false,
    selectedDate: null,
    fromDate: null,
    toDate: null,
    isMinimized: false,
  }),
  actions: {},
});
