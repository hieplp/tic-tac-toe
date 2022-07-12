<template>
  <div class="forgot-password">
    <div class="forgot-password__logo div-center">
      <img alt="todolist logo" src="../assets/images/Union.png" srcset="" />
    </div>
    <div class="forgot-password__inputs">
      <slot></slot>
      <!--Button-->
      <div class="button orange" @click="backToLogin">BACK</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "success-component",
  methods: {
    backToLogin() {
      this.$router.push({ name: "login" });
    },
  },
};
</script>

<style lang="scss" scoped>
@import "../assets/css/style";

.forgot-password {
  height: 100vh;

  &__logo {
    height: 80%;
  }

  &__inputs {
    height: 20%;
    width: 100%;

    h3 {
      text-align: center;
      margin-bottom: 20px;

      span {
        color: $orange-color;
        font-weight: 600;
      }
    }
  }
}
</style>
