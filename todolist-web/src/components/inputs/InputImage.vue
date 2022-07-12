<template>
  <div class="input">
    <input
      id="image-picker"
      :disabled="isDisabled"
      accept="image/*"
      type="file"
      @change="changeImage"
    />
    <div class="placeholder">
      <label v-if="image" class="active" for="image-picker">
        {{ image.name }}
      </label>
      <label v-else for="image-picker">
        {{ this.placeholder }}
      </label>
    </div>
    <div class="input__icon">
      <span class="material-icons"> image </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "InputImage",
  props: {
    modelValue: { type: Object, required: false },
    placeholder: { type: String, required: false },
    isDisabled: { type: Boolean, required: false },
  },
  data() {
    return {
      image: null,
    };
  },
  methods: {
    changeImage(event) {
      this.image = event.target.files[0];
      this.$emit("update:modelValue", this.image);
    },

    formatFileName(fileName) {
      if (fileName.length < 25) {
        return fileName;
      }
      return fileName.substring(0, 25) + "...";
    },
  },
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
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }

    color: $light-gray-color;
    font-weight: $font-weight-primary;
  }

  input {
    width: 0.1px;
    height: 0.1px;
    opacity: 0;
    overflow: hidden;
    position: absolute;
    z-index: -1;
  }

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

  &:hover {
    border-color: $light-orange-color;

    .input__icon > span {
      color: $light-orange-color;
      border-color: $light-orange-color;
    }
  }
}
</style>
