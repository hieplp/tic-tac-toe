<template>
  <success-component>
    <h3>
      Reset password link was sent to your email:
      <span>{{ this.$route.params.email }}</span>
    </h3>
  </success-component>
</template>

<script>
import SuccessComponent from "../../../components/SuccessComponent.vue";

export default {
  name: "request-sent",
  components: {
    SuccessComponent,
  },
  methods: {
    backToLogin() {
      this.$router.push({ name: "login" });
    },
  },
};
</script>

<style lang="scss" scoped>
@import "../../../assets/css/style";

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
