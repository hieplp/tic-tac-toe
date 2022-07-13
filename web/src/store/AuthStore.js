import { defineStore } from "pinia";
import { CreateTempUserRequest } from "@/proto/AuthService_pb";
import { API_HOST } from "@/common/config";
import { AuthServiceClient } from "@/proto/AuthService_grpc_web_pb"
import { JWT, USER } from "@/common/constants";
import { useCookies } from "vue3-cookies";

export const useAuthStore = defineStore({
    id: "authStore",
    state: () => ({
        authClient: null,
        user: {
            username: ""
        }
    }),
    actions: {
        connectClient() {
            if (!this.authClient) {
                this.authClient = new AuthServiceClient(API_HOST, null, null);
            }
            console.log("Auth client connected");
        },

        createUser() {
            let request = new CreateTempUserRequest();
            request.setUsername(this.user.username);
            this.authClient.createTempUser(request, {}, (error, response) => {
                if (response) {
                    let responseObj = response.toObject();

                    localStorage.setItem(USER.USERID, responseObj.userid);
                    localStorage.setItem(USER.USERNAME, responseObj.username);

                    const { cookies } = useCookies();
                    cookies.set(JWT.TOKEN, responseObj.token.token, new Date(responseObj.token.expiredat))
                } else {
                    console.error("An error has occurred", error);
                }
            });
        }
    }
})