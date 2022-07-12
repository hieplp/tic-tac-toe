import { createRouter, createWebHistory } from "vue-router";
import { AuthService } from "../services/auth.service";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    // Home
    {
      path: "/home",
      name: "home",
      component: () => import("../views/HomeView.vue"),
    },
    // Auth
    {
      path: "/",
      name: "default",
      component: () => import("../views/auth/LoginView.vue"),
    },
    {
      path: "/login",
      name: "login",
      component: () => import("../views/auth/LoginView.vue"),
    },
    {
      path: "/register",
      name: "register",
      component: () => import("../views/auth/RegisterView.vue"),
    },
    {
      path: "/forgot-password",
      name: "forgotPassword",
      component: () =>
        import("../views/auth/forgotPassword/ForgotPasswordView.vue"),
    },
    {
      path: "/reset-password/:requestId",
      name: "resetPassword",
      component: () =>
        import("../views/auth/resetPassword/ResetPasswordView.vue"),
    },
    {
      path: "/request-sent",
      name: "requestSent",
      component: () =>
        import("../views/auth/forgotPassword/RequestSentView.vue"),
    },
    {
      path: "/tasks/create",
      name: "createTask",
      component: () => import("../views/tasks/CreateTaskView.vue"),
    },
    {
      path: "/tasks/:taskId",
      name: "taskDetail",
      component: () => import("../views/tasks/TaskDetailView.vue"),
    },
    // Notes
    {
      path: "/notes/:noteId",
      name: "noteDetail",
      component: () => import("../views/notes/NoteDetailView.vue"),
    },
    {
      path: "/notes/create",
      name: "createNote",
      component: () => import("../views/notes/CreateNoteView.vue"),
    },
    // About
    {
      path: "/about",
      name: "about",
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("../views/AboutView.vue"),
    },
  ],
});

router.beforeEach((to, from, next) => {
  if (
    to.name !== "login" &&
    to.name !== "register" &&
    to.name !== "forgotPassword" &&
    to.name !== "resetPassword" &&
    to.name !== "requestSent" &&
    !AuthService.isLoggedIn()
  ) {
    next({
      path: "login",
      replace: true,
    });
  } else if (
    (to.name === "login" ||
      to.name === "register" ||
      to.name === "resetPassword" ||
      to.name === "requestSent") &&
    AuthService.isLoggedIn()
  ) {
    next({
      path: "home",
      replace: true,
    });
  } else {
    next();
  }
});

export default router;
