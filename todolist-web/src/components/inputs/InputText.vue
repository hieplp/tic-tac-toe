<template>
  <div>
    <div class="input">
      <input
        v-model="inputValue"
        :disabled="isDisabled"
        :placeholder="placeholder"
        :type="type"
      />
    </div>
    <div v-if="isError" class="input-error">{{ this.errorMessage }}</div>
  </div>
</template>

<script>
export default {
  name: "InputText",
  props: {
    modelValue: { type: String, required: false },
    placeholder: { type: String, required: false },
    type: { type: String, required: true },
    isDisabled: { type: Boolean, required: false },
    errorMessage: { type: String, required: false },
    isError: { type: Boolean, required: false },
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
</style>
