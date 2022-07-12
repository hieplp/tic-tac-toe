<template>
  <div
    class="container"
    :class="{
      'small-schedules': homeStore.isMinimized,
      'full-schedules': !homeStore.isMinimized,
      notes: !isSchedules,
    }"
  >
    <div class="top-nav">
      <div class="logo">TODO</div>
      <div class="right">
        <span class="material-icons" @click="logout"> logout </span>
      </div>
    </div>

    <div class="fixed-item">
      <div class="tabs">
        <div
          :class="{
            active: isSchedules,
          }"
          class="tab"
          @click="changeTab(true)"
        >
          Schedule
        </div>
        <div></div>
        <div
          :class="{
            active: !isSchedules,
          }"
          class="tab"
          @click="changeTab(false)"
        >
          Note
        </div>
      </div>
      <Calendar :is-minimized="homeStore.isMinimized" v-if="isSchedules" />
    </div>
    <div ref="firstRow" class="spacing"></div>
    <!--Schedules-->
    <Schedules v-if="isSchedules" />
    <!--Notes-->
    <Notes v-if="!isSchedules"></Notes>
    <!--Create-->
    <div class="create-button" @click="handleCreateBtnClick">
      <span class="material-icons content"> add </span>
    </div>
  </div>
</template>

<script>
import Notes from "../components/notes/Notes.vue";
import Calendar from "../components/Calendar.vue";
import Schedules from "../components/schedules/Schedules.vue";
import { useHomeStore } from "../stores/home.store";
import jwtService from "../services/jwt.service";

export default {
  name: "HomeView",
  components: {
    Schedules,
    Notes,
    Calendar,
  },
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      vm.isSchedules = !from.path.includes("notes");
    });
  },
  mounted() {
    window.addEventListener("scroll", this.handleMinimizeCalendar);
  },
  unmounted() {
    window.removeEventListener("scroll", this.handleMinimizeCalendar);
  },
  data() {
    return {
      isSchedules: true,
    };
  },
  setup: function () {
    const homeStore = useHomeStore();

    return {
      homeStore,
    };
  },
  methods: {
    changeTab(isSchedules) {
      this.isSchedules = isSchedules;
    },

    handleCreateBtnClick() {
      if (this.isSchedules) {
        this.$router.push({ path: "/tasks/create" });
      } else {
        this.$router.push({ path: "/notes/create" });
      }
    },

    handleMinimizeCalendar() {
      if (!this.isSchedules) {
        return;
      }
      let firstRow = this.$refs.firstRow;
      let lastIndex = firstRow.getBoundingClientRect().bottom;
      if (this.homeStore.isMinimized) {
        this.homeStore.isMinimized = lastIndex < 250;
      } else {
        this.homeStore.isMinimized = lastIndex < 500;
      }
    },

    logout() {
      jwtService.destroyToken();
      jwtService.destroyRefreshToken();
      this.$router.push({ name: "login" });
    },
  },
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style.scss";

.container.small-schedules {
  padding-top: 250px;
}

.container.full-schedules {
  padding-top: 500px;
}

.container.notes {
  padding-top: 120px !important;
}

.fixed-item {
  position: fixed;
  top: 100px;
  z-index: 2;

  width: 100%;
  border-bottom-left-radius: 20px;
  border-bottom-right-radius: 20px;

  padding-bottom: 10px;
  background-color: white;
}

.top-nav {
  position: fixed;
  top: 0;
  width: 100%;
  height: 100px;
  z-index: 2;
  background-color: #ffffff;
  padding-left: 10%;
  padding-right: 8%;
  display: grid;
  grid-template-columns: 10% 90%;

  .logo {
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    color: $orange-color;
    font-weight: 800;
    font-size: 20px;
  }

  .right {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: right;
    color: $light-gray-color;
  }
}

.tabs {
  width: 85vw;
  height: 40px;
  margin: auto;

  display: grid;
  grid-template-columns: 48% 1% 48%;
  align-items: center;
  justify-content: center;

  background-color: $light-orange-color;
  border-radius: 4px;

  .tab {
    height: 32px;
    text-align: center;
    align-items: center;

    font-weight: 600;
    font-size: 16px;
    line-height: 32px;
    color: #fff;

    border-radius: 4px;
    cursor: pointer;

    &:hover {
      background-color: $orange-color;
    }
  }

  .tab.active {
    background-color: $orange-color;
  }
}

.create-button {
  position: fixed;
  bottom: 10px;
  right: 10px;
  height: 50px;
  width: 50px;
  border-radius: 50%;
  background-color: $orange-color;
  cursor: pointer;

  box-shadow: 0 0 5px 0 #cccccc;

  .content {
    height: 100%;
    width: 100%;
    text-align: center;
    margin: auto;
    border-radius: 50%;
    line-height: 50px;
    font-size: 30px;
    color: #fff;
  }
}
</style>
