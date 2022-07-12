<template>
  <div class="timeline">
    <div class="date">
      <div class="content">{{ new Date(date).getDate() }}</div>
    </div>
    <div class="line"></div>
  </div>
</template>

<script>
export default {
  name: "TimelineComponent",
  props: {
    date: { type: Number, required: true },
  },
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style.scss";
</style>
