<template>
  <div>
    <div class="input">
      <input v-model="inputValue" :disabled="isDisabled" type="date" />
      <div class="input__icon">
        <span class="material-icons"> calendar_month </span>
      </div>
    </div>
    <div v-if="isError" class="input-error">{{ errorMessage }}</div>
  </div>
</template>

<script>
export default {
  name: "InputDate",
  props: {
    modelValue: { type: String, required: false },
    placeholder: { type: String, required: false },
    isDisabled: { type: Boolean, required: false },
    errorMessage: { type: String, required: false },
    isError: { type: Boolean, required: false },
  },
  data() {
    return {
      image: null,
    };
  },
  computed: {
    inputValue: {
      get() {
        return this.modelValue;
      },
      set(value) {
        console.log(value);
        this.$emit("update:modelValue", value);
      },
    },
  },
  methods: {},
};
</script>

<style lang="scss" scoped>
@import "../../assets/css/style";

.input {
  cursor: pointer !important;

  .placeholder {
    label {
      display: block;
      height: 100%;
      width: 100%;
      color: inherit;
      font-weight: inherit;
      cursor: inherit;
      line-height: 50px;
      text-align: left;
    }

    .active {
      color: #000;
      font-weight: 700;
    }

    color: $light-gray-color;
    font-weight: $font-weight-primary;
  }

  input[type="date"]::-webkit-calendar-picker-indicator {
    color: rgba(0, 0, 0, 0);
    opacity: 1;
    display: block;
    background: none;
    width: 20px;
    height: 20px;
    border-width: thin;
  }

  &__icon {
    position: absolute;
    top: 12px;
    right: 10px;
    z-index: -1;

    span {
      color: $light-gray-color;
      cursor: pointer;
    }
  }

  &:hover {
    border-color: $light-orange-color;

    .input__icon > span {
      color: $light-orange-color;
      border-color: $light-orange-color;
    }
  }
}
</style>
