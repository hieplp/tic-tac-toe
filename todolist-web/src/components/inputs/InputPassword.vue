<template>
  <div>
    <div class="input">
      <input v-model="inputValue" :placeholder="placeholder" :type="type" />
      <div class="input__icon" @click="viewPassword">
        <span v-if="isVisibility" class="material-icons"> visibility</span>
        <span v-else class="material-icons"> visibility_off </span>
      </div>
    </div>
    <div v-if="isError" class="input-error">{{ this.errorMessage }}</div>
  </div>
</template>

<script>
export default {
  name: "InputPassword",
  props: {
    modelValue: { type: String, required: false },
    placeholder: { type: String, required: false },
    errorMessage: { type: String, required: false },
    isError: { type: Boolean, required: false },
  },
  data() {
    return {
      isVisibility: false,
      type: "password",
    };
  },
  computed: {
    inputValue: {
      get() {
        return this.modelValue;
      },
      set(value) {
        this.$emit("update:modelValue", value);
      },
    },
  },
  methods: {
    viewPassword() {
      this.isVisibility = !this.isVisibility;
      if (this.isVisibility) {
        this.type = "text";
      } else {
        this.type = "password";
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@import "../../assets/css/style";

.input {
  &__icon {
    position: absolute;
    top: 12px;
    right: 10px;

    span {
      color: $light-gray-color;
      cursor: pointer;
    }

    &:hover > span {
      color: $light-orange-color;
    }
  }
}
</style>
