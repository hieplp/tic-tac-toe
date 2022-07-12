<template>
  <div class="loading">
    <div class="loader"></div>
  </div>
</template>

<script>
export default {
  name: "UILoading",
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style";

.loading {
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 0, 0.9);

  .loader {
    margin: auto;
    border: 10px solid #f3f3f3;
    border-radius: 50%;
    border-top-color: $orange-color;
    width: 120px;
    height: 120px;
    -webkit-animation: spin 1.5s linear infinite; /* Safari */
    animation: spin 1.5s linear infinite;
  }
}

/* Safari */
@-webkit-keyframes spin {
  0% {
    -webkit-transform: rotate(0deg);
  }
  100% {
    -webkit-transform: rotate(360deg);
  }
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>
