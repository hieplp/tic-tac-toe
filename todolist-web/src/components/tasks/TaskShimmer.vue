<template>
  <div class="task shimmer">
    <div class="stoke shimmer-animate title"></div>
    <div class="stoke shimmer-animate content"></div>
    <div class="stoke shimmer-animate createdAt"></div>
  </div>
</template>

<script>
export default {
  name: "TaskShimmer",
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style";

.task.shimmer {
  border: 1px solid #e6e6e6;
  padding: 8px;
  border-radius: 8px;
  margin-bottom: 10px;
  background-color: transparent !important;

  .stoke {
  }

  .title {
    height: 20%;
    width: 60%;
  }

  .content {
    height: 40%;
  }

  .createdAt {
    width: 40%;
    height: 20%;
    bottom: 10px !important;
  }
}
</style>
