<template>
  <div ref="loadMore">
    <slot></slot>
    <div ref="lastRow"></div>
  </div>
</template>

<script>
export default {
  name: "LoadMore",
  props: {
    loadData: { type: Function, required: true },
  },
  mounted() {
    window.addEventListener("scroll", this.onScroll);
  },
  unmounted() {
    window.removeEventListener("scroll", this.onScroll);
  },
  methods: {
    onScroll() {
      let element = this.$refs.loadMore;
      if (element.getBoundingClientRect().bottom < window.innerHeight + 50) {
        this.loadData();
      }
    },
  },
};
</script>

<style scoped></style>
