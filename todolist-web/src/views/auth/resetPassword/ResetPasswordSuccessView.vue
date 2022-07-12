<template>
  <success-component>
    <h3>Password was changed successfully!</h3>
  </success-component>
</template>

<script>
import SuccessComponent from "../../../components/SuccessComponent.vue";

export default {
  name: "reset-password-success",
  components: { SuccessComponent },
  methods: {
    backToLogin() {
      this.$router.push({ name: "login" });
    },
  },
};
</script>

<style lang="scss" scoped>
@import "../../../assets/css/style";

h3 {
  text-align: center;
  margin-bottom: 20px;

  span {
    color: $orange-color;
    font-weight: 600;
  }
}
</style>
