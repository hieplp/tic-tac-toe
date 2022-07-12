import { defineStore } from "pinia";
import { AuthService } from "../services/auth.service";

export const useAuthStore = defineStore({
  id: "authStore",
  state: () => ({
    user: {
      userId: "",
      email: "",
      fullName: "",
      userStatus: "",
    },
    token: {
      token: "",
      expireAt: 0,
    },
  }),
  getters: {},
  actions: {
    register(user) {
      return new Promise((resolve, reject) => {
        AuthService.register(user)
          .then((result) => {
            this.user = result.data.user;
            this.token = result.data.token;
            resolve(result);
          })
          .catch((e) => {
            reject(e);
          });
      });
    },
    login(email, password) {
      return new Promise((resolve, reject) => {
        AuthService.login({ email, password })
          .then((result) => {
            this.user = result.data.user;
            this.token = result.data.token;
            resolve(result);
          })
          .catch((e) => {
            reject(e);
          });
      });
    },
    async doesEmailExist(email) {
      let data = await AuthService.doesEmailExist(email);
      return data.data;
    },
    sendForgotPasswordRequest(email) {
      return new Promise((resolve, reject) => {
        AuthService.sendForgotPasswordRequest(email)
          .then((result) => {
            this.user.email = email;
            resolve(result);
          })
          .catch((e) => {
            reject(e);
          });
      });
    },
    async isRequestValid(requestId) {
      let data = await AuthService.isRequestValid(requestId);
      return data.data;
    },
    resetPassword(requestId, password) {
      return new Promise((resolve, reject) => {
        AuthService.resetPassword(requestId, password)
          .then((result) => {
            resolve(result);
          })
          .catch((e) => {
            reject(e);
          });
      });
    },
  },
});
