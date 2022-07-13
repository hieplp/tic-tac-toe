import { createRouter, createWebHistory } from 'vue-router';
import { useCookies } from "vue3-cookies";
import { JWT } from "@/common/constants";

const routes = [
    {
        path: '/',
        name: 'home',
        component: () => import('../views/HomeView.vue'),
    },
    {
        path: '/game/:gameId',
        name: 'game',
        component: () => import('../views/GameView.vue'),
    },
    {
        path: '/waiting',
        name: 'waiting',
        component: () => import('../views/WaitGameView'),
    },
    {
        path: '/about',
        name: 'about',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue'),
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

router.beforeEach((to, from, next) => {
    const { cookies } = useCookies();
    // For redirect from home to waiting game smoothly
    if (from.name === "home") {
        next();
    } else if (to.name !== "home" && !cookies.isKey(JWT.TOKEN)) {
        next({
            path: "/",
            replace: true
        })
    } else {
        next();
    }
});

export default router;
