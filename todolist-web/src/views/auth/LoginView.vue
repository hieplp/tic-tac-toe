<template>
  <div class="login">
    <UIModal
      v-if="modal.isVisible"
      :btn-text="modal.btnText"
      :content="modal.content"
      :on-click="
        () => {
          this.modal.isVisible = false;
        }
      "
    />
    <div class="login__logo div-center">
      <img alt="todolist logo" src="../../assets/images/Union.png" srcset="" />
    </div>
    <div class="login__inputs">
      <InputText
        v-model="user.email"
        :error-message="userError.emailMsg"
        :is-error="userError.email"
        placeholder="Email"
        type="email"
      />
      <InputPassword
        v-model="user.password"
        :error-message="userError.passwordMsg"
        :is-error="userError.password"
        placeholder="Password"
        type="password"
      />
      <!--Forgot your password-->
      <div class="text-div right">
        <router-link
          class="text-decoration none light-gray"
          to="/forgot-password"
        >
          Forgot Password?
        </router-link>
      </div>

      <!--Button-->
      <div class="button orange" @click="login">SIGN IN</div>
      <!--Don't have an account? Sign up-->
      <div class="text-div center light-gray">
        Don't have an account?
        <router-link class="orange text-decoration none" to="/register">
          Sign up
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import InputText from "../../components/inputs/InputText.vue";
import InputPassword from "../../components/inputs/InputPassword.vue";
import { defineComponent } from "vue";
import { useAuthStore } from "../../stores/auth.store";
import {
  isNotValidEmail,
  isNotValidWithMinLength,
} from "../../common/util/validation.util";
import { encryptPassword } from "../../common/util/encrypt.util";
import router from "../../router";
import { LOGIN_TEXT } from "../../common/text/error.text";
import UIModal from "../../components/modals/UIModal.vue";
import { ResponseCode } from "../../common/enum/static.enum";

export default defineComponent({
  name: "LoginView",
  components: {
    UIModal,
    InputText,
    InputPassword,
  },
  data() {
    return {
      modal: {
        isVisible: false,
        btnText: "CLOSE",
        content: LOGIN_TEXT.WRONG_PASSWORD,
      },
      user: {
        email: "",
        password: "",
      },
      userError: {
        email: false,
        emailMsg: LOGIN_TEXT.INVALID_EMAIL,
        password: false,
        passwordMsg: LOGIN_TEXT.PASSWORD_REQUIRED,
      },
    };
  },
  setup() {
    const authStore = useAuthStore();

    /**
     ******************************
     * @METHODS
     ******************************
     */
    async function login() {
      if (this.isNotValid()) {
        return;
      }
      // Check if email exists or not
      if (!(await authStore.doesEmailExist(this.user.email))) {
        this.setEmailError();
        return;
      }
      // Encrypt password with MD5 then RSA
      let encryptedPassword = encryptPassword(this.user.password);
      authStore
        .login(this.user.email, encryptedPassword)
        .then(() => {
          router.push({ path: "/home" });
        })
        .catch((e) => {
          if (e.code === ResponseCode.UNAUTHORIZED) {
            this.openModal(LOGIN_TEXT.WRONG_PASSWORD);
          }
        });
    }

    return {
      login,
    };
  },
  methods: {
    isNotValid() {
      this.userError.email = isNotValidEmail(this.user.email);
      this.userError.emailMsg = LOGIN_TEXT.INVALID_EMAIL;
      this.userError.password = isNotValidWithMinLength(this.user.password, 6);
      this.userError.passwordMsg = LOGIN_TEXT.PASSWORD_REQUIRED;

      return this.userError.email || this.userError.password;
    },
    setEmailError() {
      this.userError.email = true;
      this.userError.emailMsg = LOGIN_TEXT.EMAIL_NOT_FOUND;
    },
    openModal(content) {
      this.modal.isVisible = true;
      this.modal.content = content;
    },
  },
});
</script>

<style lang="scss" scoped>
@import "../../assets/css/style";

.login {
  height: 100vh;

  &__logo {
    height: 60%;
  }

  &__inputs {
    height: 40%;
    width: 100%;
  }
}
</style>
