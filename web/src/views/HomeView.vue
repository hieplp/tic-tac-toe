<template>
  <div class="home center-div col-11 col-sm-10 col-md-6 col-lg-6 col-xl-3">
    <input v-model="user.username" class="input" name="username" placeholder="Enter username" type="text">
    <div class="btn blue" @click="createUser">NEXT</div>
  </div>
</template>

<script setup>
import { computed, onMounted } from "vue";
import { useAuthStore } from "@/store/AuthStore";
import { useRouter } from "vue-router";
import { useCookies } from "vue3-cookies";
import { JWT } from "@/common/constants";

const authStore = useAuthStore();
const router = useRouter();
const { cookies } = useCookies();

onMounted(() => {
  if (cookies.isKey(JWT.TOKEN)) {
    router.push({ name: "waiting" });
  }
  authStore.connectClient();
});

const user = computed(() => authStore.user);

function createUser() {
  if (!user.value.username) {
    return;
  }

  authStore.createUser();
  router.push({ name: "waiting" });
}

</script>

<script>
export default {
  name: 'HomeView',
  components: {},
};
</script>

<style lang="scss" scoped>
@import "/src/assets/css/style.scss";

.home {
  height: 20vh;

}
</style>
