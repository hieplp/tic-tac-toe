<template>
  <div class="register">
    <top-nav />

    <div class="register__logo div-center">
      <img alt="todolist logo" src="../../assets/images/Union.png" srcset="" />
    </div>
    <div class="register__inputs">
      <InputText
        v-model="user.email"
        :error-message="userError.emailMsg"
        :is-error="userError.email"
        placeholder="Email"
        type="email"
      />
      <InputText
        v-model="user.fullName"
        :error-message="userError.fullNameMsg"
        :is-error="userError.fullName"
        placeholder="Full Name"
        type="text"
      />
      <InputPassword
        v-model="user.password"
        :error-message="userError.passwordMsg"
        :is-error="userError.password"
        placeholder="Password"
      />
      <InputPassword
        v-model="user.confirmPassword"
        :error-message="userError.confirmPasswordMessage"
        :is-error="userError.confirmPassword"
        placeholder="Confirm Password"
      />
      <!--Button-->
      <div class="button orange" @click="register">SIGN UP</div>
      <!--Don't have an account? Sign up-->
      <div class="text-div center light-gray">
        Have an account?
        <router-link class="orange text-decoration none" to="/login">
          Log in
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import InputText from "../../components/inputs/InputText.vue";
import InputPassword from "../../components/inputs/InputPassword.vue";
import { encryptPassword } from "../../common/util/encrypt.util";
import {
  isNotValidEmail,
  isNotValidWithMinAndMaxLength,
  isNotValidWithMinLength,
  notEqual,
} from "../../common/util/validation.util";
import { useAuthStore } from "../../stores/auth.store";
import { useRouter } from "vue-router";
import { defineComponent } from "vue";
import { REGISTER_TEXT } from "../../common/text/error.text";
import TopNav from "../../components/TopNav.vue";

export default defineComponent({
  name: "register-view",
  components: {
    TopNav,
    InputPassword,
    InputText,
  },
  data() {
    return {
      user: {
        email: "",
        fullName: "",
        password: "",
        confirmPassword: "",
      },
      userError: {
        email: false,
        emailMsg: REGISTER_TEXT.INVALID_EMAIL,
        fullName: false,
        fullNameMsg: REGISTER_TEXT.INVALID_FULL_NAME,
        password: false,
        passwordMsg: REGISTER_TEXT.INVALID_PASSWORD,
        confirmPassword: false,
        confirmPasswordMessage: REGISTER_TEXT.INVALID_CONFIRM_PASSWORD,
      },
    };
  },
  setup() {
    const authStore = useAuthStore();
    const router = useRouter();

    /**
     ******************************
     * @METHODS
     ******************************
     */
    async function register() {
      // Validate request
      if (this.isNotValid()) {
        return;
      }
      // Check if email exists or not
      if (await authStore.doesEmailExist(this.user.email)) {
        this.setDuplicatedEmail();
        return;
      }

      // Encrypt password with MD5 then RSA
      let encryptedPassword = encryptPassword(this.user.password);
      authStore
        .register({
          email: this.user.email,
          fullName: this.user.fullName,
          password: encryptedPassword,
        })
        .then((result) => {
          console.log(result);
          router.push({ path: "/home" });
        })
        .catch((e) => {
          // TODO: Open
          console.log(e);
        });
    }

    return {
      register,
    };
  },
  methods: {
    isNotValid() {
      this.userError.email = isNotValidEmail(this.user.email);
      this.userError.password = isNotValidWithMinLength(this.user.password, 6);
      this.userError.fullName = isNotValidWithMinAndMaxLength(
        this.user.fullName,
        6,
        100
      );
      this.userError.confirmPassword = notEqual(
        this.user.password,
        this.user.confirmPassword
      );

      return (
        this.userError.email ||
        this.userError.password ||
        this.userError.confirmPassword
      );
    },
    setDuplicatedEmail() {
      this.userError.email = true;
      this.userError.emailMsg = REGISTER_TEXT.DUPLICATED_EMAIl;
    },
  },
});
</script>

<style lang="scss" scoped>
@import "../../assets/css/style";

.register {
  height: 100vh;

  &__logo {
    height: 50%;
  }

  &__inputs {
    height: 40%;
    width: 100%;
  }
}
</style>
