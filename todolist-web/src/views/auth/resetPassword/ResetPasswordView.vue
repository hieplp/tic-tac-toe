<template>
  <div class="reset-password">
    <div class="reset-password__logo div-center">
      <img
        alt="todolist logo"
        src="../../../assets/images/Union.png"
        srcset=""
      />
    </div>
    <div class="reset-password__inputs">
      <InputPassword
        v-model="password"
        :error-message="error.passwordMsg"
        :is-error="error.password"
        placeholder="Password"
      />
      <InputPassword
        v-model="confirmPassword"
        :error-message="error.confirmPasswordMsg"
        :is-error="error.confirmPassword"
        placeholder="Confirm Password"
      />
      <!--Button-->
      <div class="button orange" @click="resetPassword">CHANGE PASSWORD</div>
    </div>
  </div>
</template>

<script>
import InputPassword from "../../../components/inputs/InputPassword.vue";
import { defineComponent } from "vue";
import { useAuthStore } from "../../../stores/auth.store";
import { RESET_PASSWORD_TEXT } from "../../../common/text/error.text";
import {
  isNotValidWithMinLength,
  notEqual,
} from "../../../common/util/validation.util";
import { encryptPassword } from "../../../common/util/encrypt.util";

export default defineComponent({
  name: "ResetPasswordView",
  components: {
    InputPassword,
  },
  data() {
    return {
      password: "",
      confirmPassword: "",
      error: {
        password: false,
        passwordMsg: RESET_PASSWORD_TEXT.INVALID_PASSWORD,
        confirmPassword: false,
        confirmPasswordMsg: RESET_PASSWORD_TEXT.PASSWORD_NOT_MATCH,
      },
    };
  },
  setup() {
    const authStore = useAuthStore();

    async function isRequestValid(requestId) {
      return await authStore.isRequestValid(requestId);
    }

    function resetPassword() {
      if (this.isNotValid()) {
        return;
      }
      // Encrypt password with MD5 then RSA
      let encryptedPassword = encryptPassword(this.password);
      // Reset password
      authStore
        .resetPassword(this.$route.params.requestId, encryptedPassword)
        .then((result) => {
          console.log(result);
          this.$router.push({ name: "login" });
        })
        .catch((e) => {
          console.log(e);
        });
    }

    return {
      isRequestValid,
      resetPassword,
    };
  },
  methods: {
    isNotValid() {
      this.error.password = isNotValidWithMinLength(this.password, 6);
      this.error.passwordMsg = RESET_PASSWORD_TEXT.INVALID_PASSWORD;
      this.error.confirmPassword = notEqual(
        this.password,
        this.confirmPassword
      );
      this.error.confirmPasswordMsg = RESET_PASSWORD_TEXT.PASSWORD_NOT_MATCH;

      return this.error.password || this.error.confirmPassword;
    },
  },
  async mounted() {
    console.log(this.$route.params.requestId);
    if (!(await this.isRequestValid(this.$route.params.requestId))) {
      await this.$router.push({ name: "login" });
    }
  },
});
</script>

<style lang="scss" scoped>
@import "../../../assets/css/style";

.reset-password {
  height: 100vh;

  &__logo {
    height: 70%;
  }

  &__inputs {
    height: 30%;
    width: 100%;
  }
}
</style>
