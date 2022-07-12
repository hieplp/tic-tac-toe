<template>
  <div class="note shimmer">
    <div class="stoke shimmer-animate title"></div>
    <div class="stoke shimmer-animate content"></div>
    <div class="stoke shimmer-animate date"></div>
  </div>
</template>

<script>
export default {
  name: "NoteShimmer",
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style";

.note.shimmer {
  height: 100px;
  border: 1px solid #e6e6e6;
  padding: 8px;
  border-radius: 8px;
  margin-bottom: 10px;

  .stoke {
    margin-bottom: 2%;
  }

  .title {
    height: 20%;
    width: 60%;
  }

  .content {
    height: 40%;
  }

  .date {
    width: 40%;
    height: 20%;
  }
}
</style>
