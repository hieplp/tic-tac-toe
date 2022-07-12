<template>
  <div class="calendar">
    <div class="year">
      {{ calendar.monthStr }} {{ calendar.year }}
      <div class="btn prev" @click="changeMonth('prev')">&lt;</div>
      <div class="btn next" @click="changeMonth('next')">&gt;</div>
    </div>
    <div class="days">
      <div class="day">SUN</div>
      <div class="day">MON</div>
      <div class="day">TUE</div>
      <div class="day">WED</div>
      <div class="day">THU</div>
      <div class="day">FRI</div>
      <div class="day">SAT</div>
    </div>
    <div class="dates">
      <div
        v-for="(calendarDate, index) in calendar.dates"
        :key="index"
        :class="setClass(calendarDate)"
        @click="selectDate(calendarDate.date)"
      >
        {{ calendarDate.date.getDate() }}
        <div v-if="calendarDate.isActive" class="circle"></div>
        <div v-if="calendarDate.hasTask" class="dot"></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useTaskStore } from "../stores/task.store";
import { onMounted, ref, toRefs, watch } from "vue";
import { useHomeStore } from "../stores/home.store";

const taskStore = useTaskStore();
const homeStore = useHomeStore();

const props = defineProps({
  isMinimized: Boolean,
});

watch(
  () => props.isMinimized,
  (currentValue) => {
    minimize(currentValue);
  }
);

onMounted(async () => {
  if (homeStore.selectedDate === null) {
    homeStore.selectedDate = new Date();
  }

  calendar.value.month = homeStore.selectedDate.getMonth();
  calendar.value.monthStr = getMonthName(homeStore.selectedDate.getMonth());
  calendar.value.year = homeStore.selectedDate.getFullYear();

  // Load task status
  await loadTasks();

  minimize(isMinimized.value);
});

// watch(
//   () => isMinimized,
//   (first, second) => {
//     console.log(123);
//   }
// );

// watch(isMinimized, (currentValue, oldValue) => {
//   console.log(currentValue, oldValue);
// });

/**
 *
 ******************************
 * @DATA
 ******************************
 */

const { isMinimized } = toRefs(props);

const calendar = ref({
  month: 0,
  monthStr: "",
  year: 0,
  dates: [],
});

/**
 ******************************
 * @API_METHODS
 ******************************
 */

async function getTaskStatus() {
  const firstDate = new Date(calendar.value.year, calendar.value.month, 1);
  const lastDate = new Date(calendar.value.year, calendar.value.month + 1, 0);

  homeStore.fromDate = firstDate;
  homeStore.toDate = lastDate;

  await taskStore.getTaskStatusSet(firstDate.getTime(), lastDate.getTime());
}

async function getTaskMap() {
  await taskStore.getTaskMap(
    homeStore.fromDate.getTime(),
    homeStore.toDate.getTime()
  );
}

/**
 ******************************
 * @METHODS
 ******************************
 */
function hasTask(date) {
  return taskStore.taskStatusSet.has(date.getTime());
}

function initDates() {
  let totalDatesInMonth = new Date(
    calendar.value.year,
    calendar.value.month + 1,
    0
  ).getDate();

  let startDayInMonth = new Date(
    calendar.value.year,
    calendar.value.month,
    1
  ).getDay();

  let lastDateOfPrevMonth = new Date(
    calendar.value.year,
    calendar.value.month,
    0
  ).getDate();

  for (let i = startDayInMonth - 1; i >= 0; i--) {
    let date = new Date(
      calendar.value.year,
      calendar.value.month - 1,
      lastDateOfPrevMonth - i
    );
    calendar.value.dates.push({
      date: date,
      isActive: isActive(date, homeStore.selectedDate),
      hasTask: hasTask(date),
    });
  }

  for (let i = 0; i < totalDatesInMonth; i++) {
    let date = new Date(calendar.value.year, calendar.value.month, i + 1);
    calendar.value.dates.push({
      date: date,
      isActive: isActive(date, homeStore.selectedDate),
      hasTask: hasTask(date),
    });
  }

  let datesOfNextMonth = 7 * 6 - calendar.value.dates.length;
  for (let i = 0; i < datesOfNextMonth; i++) {
    let date = new Date(calendar.value.year, calendar.value.month + 1, i + 1);
    calendar.value.dates.push({
      date: date,
      isActive: isActive(date, homeStore.selectedDate),
      hasTask: hasTask(date),
    });
  }
}

function minimize(isMinimized) {
  calendar.value.dates = [];
  if (!homeStore.selectedDate) {
    homeStore.selectedDate = new Date();
  }
  if (isMinimized) {
    for (let i = homeStore.selectedDate.getDay(); i > 0; i--) {
      calendar.value.dates.push({
        date: new Date(
          calendar.value.year,
          calendar.value.month,
          homeStore.selectedDate.getDate() - i
        ),
        isActive: false,
      });
    }

    calendar.value.dates.push({
      date: homeStore.selectedDate,
      isActive: true,
    });

    for (let i = 1; i < 7 - homeStore.selectedDate.getDay(); i++) {
      calendar.value.dates.push({
        date: new Date(
          calendar.value.year,
          calendar.value.month,
          homeStore.selectedDate.getDate() + i
        ),
        isActive: false,
      });
    }
  } else {
    initDates();
  }
}

async function changeMonth(type) {
  if ("next" === type) {
    calendar.value.month++;
    if (calendar.value.month >= 12) {
      calendar.value.month = 0;
      calendar.value.year++;
    }
  } else if ("prev" === type) {
    calendar.value.month--;
    if (calendar.value.month < 0) {
      calendar.value.month = 11;
      calendar.value.year--;
    }
  }

  homeStore.selectedDate = new Date(
    calendar.value.year,
    calendar.value.month,
    1
  );
  calendar.value.monthStr = getMonthName(calendar.value.month);

  await loadTasks();

  minimize(isMinimized.value);
}

async function selectDate(date) {
  homeStore.selectedDate = date;
  if (
    date.getFullYear() !== calendar.value.year ||
    date.getMonth() !== calendar.value.month
  ) {
    calendar.value.month = date.getMonth();
    calendar.value.monthStr = getMonthName(date.getMonth());
    calendar.value.year = date.getFullYear();

    await loadTasks();
  }

  this.minimize(isMinimized.value);
}

async function loadTasks() {
  await getTaskStatus();
  await getTaskMap();
}

function getMonthName(month) {
  switch (month) {
    case 0:
      return "JANUARY";
    case 1:
      return "FEBRUARY";
    case 2:
      return "MARCH";
    case 3:
      return "APRIL";
    case 4:
      return "MAY";
    case 5:
      return "JUNE";
    case 6:
      return "JULY";
    case 7:
      return "AUGUST";
    case 8:
      return "SEPTEMBER";
    case 9:
      return "OCTOBER";
    case 10:
      return "NOVEMBER";
    case 11:
      return "DECEMBER";
  }
  return "";
}

function isActive(date, currentDate) {
  return (
    date.getDate() === currentDate.getDate() &&
    date.getMonth() === currentDate.getMonth() &&
    date.getFullYear() === currentDate.getFullYear()
  );
}

function setClass(calendarDate) {
  let className = "date";
  if (calendarDate.isActive) {
    return className + " active";
  } else if (calendarDate.date.getMonth() !== calendar.value.month) {
    return className + " inactive";
  } else if (calendarDate.date.getDay() === 0) {
    return className + " sunday";
  } else {
    return className;
  }
}
</script>

<script>
export default {
  name: "CalendarComponent",
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style.scss";

.calendar {
  width: 90vw;
  margin-left: auto;
  margin-right: auto;
  margin-top: 40px;

  .year {
    position: relative;
    width: 100%;
    text-align: center;
    font-weight: 700;
    font-size: 18px;
    line-height: 100%;
    color: $orange-color;
    text-transform: uppercase;
    //background-color: #2c3e50;

    .btn {
      position: absolute;

      top: 0;
      font-weight: 700;
      color: $light-gray-color;

      &:hover {
        color: $orange-color;
      }
    }

    .prev {
      left: 10px;
    }

    .next {
      right: 10px;
    }
  }

  .days {
    width: 100%;
    margin-top: 30px;
    display: grid;
    grid-template-columns: 14.28% 14.28% 14.28% 14.28% 14.28% 14.28% 14.28%;

    .day {
      font-weight: 600;
      font-size: 16px;
      line-height: 100%;
      text-align: center;
      text-transform: uppercase;

      &:first-child {
        color: $red-color;
      }
    }
  }

  .dates {
    width: 100%;
    margin-top: 20px;
    display: grid;
    grid-template-columns: 14.28% 14.28% 14.28% 14.28% 14.28% 14.28% 14.28%;
    grid-row-gap: 30px;

    .date {
      text-align: center;
      font-weight: 600;
      font-size: 16px;
      line-height: 100%;
      position: relative;

      .circle {
        position: absolute;
        top: -50%;
        left: 20%;
        z-index: -1;
        width: 60%;
        height: 0;
        padding: 60% 0 0;
        border-radius: 50%;
        background-color: $orange-color;
      }

      .dot {
        position: absolute;
        bottom: -100%;
        left: 48%;

        width: 5px;
        height: 5px;
        background-color: $light-orange-color;
        border-radius: 50%;
      }
    }

    .date.inactive {
      color: $light-gray-color;
    }

    .date.active {
      color: #fff;
    }

    .date.sunday {
      color: $red-color;
    }
  }
}
</style>
