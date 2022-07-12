<template>
  <div class="forgot-password">
    <TopNav />
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

    <div class="forgot-password__logo div-center">
      <img
        alt="todolist logo"
        src="../../../assets/images/Union.png"
        srcset=""
      />
    </div>
    <div class="forgot-password__inputs">
      <InputText
        v-model="email"
        :error-message="error.emailMsg"
        :is-error="error.email"
        placeholder="Email"
        type="email"
      />
      <!--Button-->
      <div class="button orange" @click="sendForgotPasswordRequest">SEND</div>
    </div>
  </div>
</template>

<script>
// Component
import InputText from "../../../components/inputs/InputText.vue";
import UIModal from "../../../components/modals/UIModal.vue";
import TopNav from "../../../components/TopNav.vue";
import { defineComponent } from "vue";
import { useAuthStore } from "../../../stores/auth.store";
// Util
import { isNotValidEmail } from "../../../common/util/validation.util";
import { FORGOT_PASSWORD_TEXT } from "../../../common/text/error.text";
import router from "../../../router";
import { ResponseCode } from "../../../common/enum/static.enum";

export default defineComponent({
  name: "ForgotPasswordView",
  components: {
    TopNav,
    InputText,
    UIModal,
  },
  data() {
    return {
      email: "",
      modal: {
        isVisible: false,
        btnText: "CLOSE",
        content: FORGOT_PASSWORD_TEXT.REQUEST_SENT,
      },
      error: {
        email: false,
        emailMsg: FORGOT_PASSWORD_TEXT.INVALID_EMAIL,
      },
    };
  },
  setup() {
    const authStore = useAuthStore();

    async function sendForgotPasswordRequest() {
      if (this.isNotValid()) {
        return;
      }
      // Check if email exists
      if (!(await authStore.doesEmailExist(this.email))) {
        this.setEmailError();
        return;
      }
      // Send forgot password email
      authStore
        .sendForgotPasswordRequest(this.email)
        .then((result) => {
          console.log(result);
          router.push({ name: "requestSent", params: { email: this.email } });
        })
        .catch((e) => {
          if (e.code === ResponseCode.CONFLICTED) {
            this.openModal(FORGOT_PASSWORD_TEXT.REQUEST_SENT);
          }
        });
    }

    return { sendForgotPasswordRequest };
  },

  methods: {
    isNotValid() {
      this.error.email = isNotValidEmail(this.email);
      this.error.emailMsg = FORGOT_PASSWORD_TEXT.INVALID_EMAIL;

      return this.error.email;
    },
    setEmailError() {
      this.error.email = true;
      this.error.emailMsg = FORGOT_PASSWORD_TEXT.EMAIL_NOT_FOUND;
    },
    openModal(content) {
      this.modal.isVisible = true;
      this.modal.content = content;
    },
  },
});
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
  }
}
</style>
