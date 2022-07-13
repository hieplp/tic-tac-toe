<template>
  <div class="loading">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "UILoading"
}
</script>

<style lang="scss" scoped>
@import "src/assets/css/style";

.loading {
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 50px;
  color: white;
  font-family: 'Montserrat', sans-serif;
  font-weight: 800;

  //background-color: red;

  span {
    display: inline-block;
    margin: 0 -.05em;
  }
}


</style>