<template>
  <div class="note">
    <div class="title">{{ this.note.noteTitle }}</div>
    <div class="content">
      {{ this.note.noteDescription }}
    </div>
    <div class="date">{{ this.formatDate(this.note.createdAt) }}</div>

    <span v-if="this.note.isPinned" class="material-icons"> push_pin </span>
  </div>
</template>

<script>
import moment from "moment";

export default {
  name: "ui-note",
  props: {
    note: { type: Object, required: true },
  },
  methods: {
    formatDate(date) {
      return moment.unix(date / 1000).format("DD/MM/YYYY");
    },
  },
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style.scss";

.note {
  padding: 10px;
  color: #fff;
  border-radius: 8px;
  background-color: $orange-color;
  margin-bottom: 10px;

  .title {
    font-weight: 600;
  }

  .date {
    position: absolute;
    bottom: 0;
  }

  span {
    position: absolute;
    bottom: 5px;
    right: 5px;
  }
}
</style>
