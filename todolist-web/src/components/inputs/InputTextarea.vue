<template>
  <div class="input text-area">
    <textarea
      v-model="inputValue"
      :disabled="isDisabled"
      :placeholder="placeholder"
    />
  </div>
</template>

<script>
export default {
  name: "InputTextarea",
  props: {
    modelValue: { type: String, required: false },
    placeholder: { type: String, required: false },
    isDisabled: { type: Boolean, required: false },
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
};
</script>

<style lang="scss" scoped>
@import "../../assets/css/style";

.text-area {
  height: 100% !important;
  padding-top: 10px;

  textarea {
    &::placeholder {
    }
  }
}
</style>
