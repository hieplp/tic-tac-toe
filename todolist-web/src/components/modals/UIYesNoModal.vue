<template>
  <div class="modal">
    <div class="modal__content">
      <div class="image">
        <img alt="" src="@/assets/images/Union-2.png" />
      </div>
      <div class="content">{{ this.$props.content }}</div>
      <div class="btn-group">
        <div class="button red" @click="onNoClick">NO</div>
        <div class="button green" @click="onYesClick">YES</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ui-yes-no-modal",
  props: {
    content: { type: String, required: true },
    onNoClick: { type: Function, required: true },
    onYesClick: { type: Function, required: true },
  },
};
</script>

<style lang="scss" scoped>
@import "src/assets/css/style";

.modal {
  &__content {
    width: 90%;
    height: 50vh;
    margin: auto;
    padding-top: 5%;
    padding-bottom: 5%;
    background-color: #ffffff;
    border-radius: 10px;

    .image {
      margin: auto;
      width: 50%;
      padding-bottom: -50%;

      img {
        width: 100%;
        height: 100%;
      }
    }

    .content {
      text-align: center;
      margin-top: 15%;
      height: 25%;
    }

    .btn-group {
      position: absolute;
      width: 100%;
      bottom: 0;
    }
  }
}
</style>
