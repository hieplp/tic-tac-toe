<template>
  <div class="top-nav">
    <div class="back" @click="back">
      <span class="material-icons"> arrow_back </span>
    </div>
    <div class="right">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "TopNav",
  methods: {
    back() {
      this.$router.go(-1);
    },
  },
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style.scss";

.top-nav {
  position: fixed;
  top: 0;
  height: $nav-bar-height;
  padding-left: 5%;
  padding-right: 5%;
  width: 100%;
  display: grid;
  grid-template-columns: 10% 90%;
  background-color: #ffffff;
  z-index: 1;
  box-shadow: 0 5px 10px -7px #6c6c6c, 5px 5px 15px 5px rgba(0, 0, 0, 0);

  .back {
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    color: $light-gray-color;

    &:hover > span {
      color: $orange-color;
    }
  }

  .right {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: right;
    color: $light-gray-color;
  }
}
</style>
