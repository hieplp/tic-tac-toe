import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { createPinia } from "pinia";


createApp(App)
    .use(createPinia())
    .use(router)
    .mount('#app');

// protoc --proto_path=proto/ --js_out=import_style=commonjs,binary:src/proto --grpc-web_out=import_style=commonjs,mode=grpcwebtext:src/proto AuthService.proto AuthTokenService.proto GameService.proto