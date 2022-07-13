<template>
  <div v-if="isGameVisible" class="container center-div">
    <UIModal v-if="gameModel.gamestatus !== GameStatus.ON_GOING">
      <UILoading v-if="gameModel.gamestatus === GameStatus.NOT_STARTED">
        <span>WAIT FOR OPPONENT</span>
      </UILoading>

      <UILoading v-if="gameModel.gamestatus === GameStatus.FINISHED">
        <span v-if="doesUserWin">{{ doesUserWin() }}</span>
      </UILoading>
    </UIModal>

    <div v-if="isGameVisible" class="game">
      <template v-for="(row, rowIndex) in gameModel.gameitemsMap" :key="rowIndex">
        <div v-for="(item, colIndex) in row[1].itemsList" :key="colIndex"
             :class="getItemColor(item)"
             class="item"
             @click="playGame(rowIndex, colIndex)"
        >
          {{ getItemIcon(item.itemtype) }}
        </div>
      </template>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from "vue";
import { GameStatus, UserType } from "@/common/enum/StaticEnum";
import { useGameStore } from "@/store/GameStore";
import { useRoute, useRouter } from "vue-router";
import { CodeResponse } from "@/common/enum/CodeResponse";
import { USER } from "@/common/constants";

const gameStore = useGameStore();

onMounted(() => {
  isGameVisible.value = false;

  // Connect to gRPC server
  gameStore.connectClient();

  if (!gameModel.value) {
    gameStore.getGame(route.params.gameId)
        .then((res) => {
          setUserIcon();
          isGameVisible.value = true;
        })
        .catch((e) => {
          switch (e.error) {
            case CodeResponse.FORBIDDEN:
              router.push({ name: "home" });
              break;
            case CodeResponse.NOT_FOUND:
              router.push({ name: "waiting" });
              break;
          }
        });
  } else {
    setUserIcon();
    isGameVisible.value = true;
  }

  // TODO: close gRPC connection when window is closed or reloaded

})

const router = useRouter();
const route = useRoute();

const user = ref({
  userType: 0,
  userIcon: ""
})
const isGameVisible = ref(false);

const gameModel = computed(() => gameStore.gameModel);

function playGame(rowIndex, colIndex) {
  // if (user.value.userType !== gameModel.value.gameturn) {
  //   return;
  // }

  gameStore.playGame(rowIndex, colIndex)
      .then((response) => {
      })
      .catch((e) => {
        switch (e.error) {
          case CodeResponse.NOT_FOUND:
            router.push({ name: "waiting" });
            break;
          case CodeResponse.GAME_IS_NOT_GOING_ON:
            console.log("game is not going on");
            break;
          case CodeResponse.NOT_USER_TURN:
            console.log("not user turn");
            break;
            l
        }
      })
}

function setUserIcon() {
  user.value.userType = getUserTurn();
  user.value.userIcon = UserType.OWNER === user.value.userType ? "⭘" : "✕";
}

function getItemIcon(itemType) {
  switch (itemType) {
    case UserType.OWNER:
      return "⭘";
    case UserType.OPPONENT:
      return "✕";
    default:
      return user.value.userIcon;
  }
}

function getItemColor(item) {
  let clazz;
  switch (item.itemtype) {
    case UserType.OWNER:
      clazz = "player";
      break;
    case UserType.OPPONENT:
      clazz = "opponent";
      break;
    default:
      clazz = "empty";


      if (!isUserTurn()) {
        clazz += " block";
      } else {
        if (isUserOwner()) {
          clazz += " player";
        } else {
          clazz += " opponent";
        }
      }
  }

  return clazz;
}

function isUserOwner() {
  return gameModel.value.ownerid === localStorage.getItem(USER.USERID);
}

function getUserTurn() {
  if (isUserOwner()) {
    return UserType.OWNER;
  }

  return UserType.OPPONENT;
}

function isUserTurn() {
  return getUserTurn() === gameModel.value.gameturn;
}

function doesUserWin() {
  if (GameStatus.FINISHED !== gameModel.value.gamestatus) {
    return null;
  }

  if (gameModel.value.winnerid.length <= 0) {
    return "DRAW";
  }

  if (gameModel.value.winnerid === localStorage.getItem(USER.USERID)) {
    return "WON";
  } else {
    return "LOST";
  }
}

</script>

<script>
import UIModal from "@/components/UIModal";
import UILoading from "@/components/UILoading";

export default {
  name: 'GameView',
  components: {
    UIModal,
    UILoading
  }
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style.scss";

.container {
  margin: auto;
  width: 100vw;
  height: 80vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.game {
  height: 80vw;
  width: 80vw;
  margin: auto;
  display: grid;

  grid-template-columns: 33.33% 33.33% 33.33%;
  grid-template-rows: 33.33% 33.33% 33.33%;
  column-gap: 2%;
  row-gap: 2%;

  .item {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20vw;
    font-weight: 800;
    color: white;
    cursor: pointer;
    background-color: $color-blue;
    border-radius: 14px;
  }

  .item:hover {
    background-color: red;
  }

  .item.player {
    background-color: $color-yellow;
  }

  .item.opponent {
    background-color: $color-red;
  }

  .item.player.empty {
    background-color: $color-blue;
    color: transparent;

    &:hover {
      background-color: $color-yellow;
      color: white;
    }
  }

  .item.opponent.empty {
    background-color: $color-blue;
    color: transparent;

    &:hover {
      background-color: $color-red;
      color: white;
    }
  }

  .item.block {
    background-color: $color-blue !important;
    color: transparent !important;
  }
}

@media (min-width: 576px) {
  .game {
    height: 80vw;
    width: 80vw;

    .item {
      font-size: 25vw;
    }
  }
}

@media (min-width: 768px) {
  .game {
    height: 60vh;
    width: 60vh;

    .item {
      font-size: 20vw;
    }
  }
}

@media (min-width: 992px) {
  .game {
    height: 60vw;
    width: 60vw;

    .item {
      font-size: 15vw;
    }
  }
}

@media (min-width: 1200px) {
  .game {
    height: 60vh;
    width: 60vh;

    .item {
      font-size: 10vw;
    }
  }
}
</style>
