<template>
  <div v-if="visible" id="myModal" class="modal">
    <span class="close" @click="isVisible(false)">&times;</span>
    <img id="img01" :alt="caption" :src="image" class="modal-content" />
    <div class="caption">{{ this.caption }}</div>
  </div>
</template>

<script>
export default {
  name: "ImageModal",
  props: {
    image: { type: String, required: true },
    caption: { type: String, required: false },
  },
  data() {
    return {
      visible: false,
    };
  },
  methods: {
    isVisible(visible) {
      this.visible = visible;
    },
    closeModal() {
      this.isVisible(false);
    },
  },
};
</script>

<style lang="scss" scoped>
/* Modal Content (image) */
.modal-content {
  margin: auto;
  display: block;
  width: 80%;
  max-width: 700px;
}

/* Caption of Modal Image */
.caption {
  margin: auto;
  display: block;
  width: 80%;
  max-width: 700px;
  text-align: center;
  color: #ccc;
  padding: 10px 0;
  height: 150px;
}

/* Add Animation */
.modal-content,
.caption {
  -webkit-animation-name: zoom;
  -webkit-animation-duration: 0.6s;
  animation-name: zoom;
  animation-duration: 0.6s;
}

@-webkit-keyframes zoom {
  from {
    -webkit-transform: scale(0);
  }
  to {
    -webkit-transform: scale(1);
  }
}

@keyframes zoom {
  from {
    transform: scale(0);
  }
  to {
    transform: scale(1);
  }
}

/* The Close Button */
.close {
  position: absolute;
  top: 15px;
  right: 35px;
  color: #f1f1f1;
  font-size: 40px;
  font-weight: bold;
  transition: 0.3s;
}

.close:hover,
.close:focus {
  color: #bbb;
  text-decoration: none;
  cursor: pointer;
}

/* 100% Image Width on Smaller Screens */
@media only screen and (max-width: 700px) {
  .modal-content {
    width: 100%;
  }
}
</style>
