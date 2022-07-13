<template>
  <div class="center-div waiting col-11 col-sm-10 col-md-6 col-lg-6 col-xl-3">
    <input v-model="gameId" class="input" placeholder="Enter game id" type="text">
    <h3 v-if="errorMessage" class="red">{{ errorMessage }}</h3>
    <div class="btn blue" @click="joinGame">JOIN</div>
    <div class="btn yellow" @click="createGame">CREATE</div>
  </div>
</template>

<script setup>

import { useGameStore } from "@/store/GameStore";
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { CodeResponse } from "@/common/enum/CodeResponse";
import { useCookies } from "vue3-cookies";
import { JWT } from "@/common/constants";

const gameStore = useGameStore();
const router = useRouter();
const { cookies } = useCookies();

// Data
const gameId = ref("");
const errorMessage = ref(null);

onMounted(() => {
  gameStore.connectClient();
})

function createGame() {
  gameStore.create()
      .then((response) => {
        router.push({
          name: "game", params: {
            gameId: response.gameid
          }
        })
      })
      .catch((e) => {
        handleError(e);
      })
}

function joinGame() {
  if (!(gameId.value != null && gameId.value.length > 0)) {
    return;
  }

  gameStore.joinGame(gameId.value)
      .then((response) => {
        router.push({
          name: "game",
          params: {
            gameId: response.gameid
          }
        })
      })
      .catch((e) => {
        handleError(e);
      })
}

function handleError(e) {
  switch (e.error) {
    case CodeResponse.FORBIDDEN:
      cookies.remove(JWT.TOKEN);
      router.push({ name: "home" });
      break;
    case CodeResponse.NOT_FOUND:
      errorMessage.value = "Game not found";
      break;
    case CodeResponse.GAME_IS_GOING_ON:
      errorMessage.value = "Game is going on";
      break;
    case CodeResponse.JOIN_OWN_GAME:
      errorMessage.value = "Can't join own game";
      break;
  }
}

</script>

<script>
export default {
  name: "WaitGameView"
}
</script>

<style lang="scss" scoped>
@import "src/assets/css/style";

.waiting {
  height: 30vh;
}

</style>