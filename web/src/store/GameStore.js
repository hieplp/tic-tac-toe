import { defineStore } from "pinia";
import { API_HOST } from "@/common/config";
import { JWT } from "@/common/constants";
import { GameServiceClient } from "@/proto/GameService_grpc_web_pb";
import { GameRequest } from "@/proto/GameService_pb";
import { useCookies } from "vue3-cookies";
import { CodeResponse, Status } from "@/common/enum/CodeResponse";
import { ErrorModel } from "@/common/model/error.model";

export const useGameStore = defineStore({
    id: "gameStore",
    state: () => ({
        gameModel: null,
        gameClient: null,
    }),
    actions: {
        connectClient() {
            if (!this.gameClient) {
                this.gameClient = new GameServiceClient(API_HOST, null, null);
            }
            console.log("Game client connected");
        },

        create() {
            return new Promise((resolve, reject) => {

                const { cookies } = useCookies();
                if (!cookies.isKey(JWT.TOKEN)) {
                    reject(new ErrorModel(CodeResponse.FORBIDDEN))
                }

                let gameRequest = new GameRequest();
                this.gameClient.createGame(gameRequest, { token: cookies.get(JWT.TOKEN) })
                    .on("data", data => {
                        this.gameModel = data.toObject();
                        resolve(this.gameModel);
                    })
                    .on("status", status => {
                        if (status.code === Status.UNAUTHENTICATED) {
                            reject(new ErrorModel(CodeResponse.FORBIDDEN));
                        }
                    })
            })
        },

        joinGame(gameId) {
            return new Promise((resolve, reject) => {

                const { cookies } = useCookies();
                if (!cookies.isKey(JWT.TOKEN)) {
                    reject(new ErrorModel(CodeResponse.FORBIDDEN))
                }

                let gameRequest = new GameRequest();
                gameRequest.setGameid(gameId);
                this.gameClient.joinGame(gameRequest, { token: cookies.get(JWT.TOKEN) })
                    .on("data", data => {
                        this.gameModel = data.toObject();
                        resolve(this.gameModel);
                    })
                    .on("status", status => {
                        switch (status.code) {
                            case Status.UNAUTHENTICATED:
                                reject(new ErrorModel(CodeResponse.FORBIDDEN));
                                break;
                            case Status.NOT_FOUND:
                                reject(new ErrorModel(CodeResponse.NOT_FOUND));
                                break;
                            case Status.PERMISSION_DENIED:
                                reject(new ErrorModel(CodeResponse.GAME_IS_GOING_ON));
                                break;
                            case Status.ALREADY_EXISTS:
                                reject(new ErrorModel(CodeResponse.JOIN_OWN_GAME));
                                break;
                        }

                    });
            });
        },

        playGame(rowIndex, colIndex) {
            return new Promise((resolve, reject) => {

                const { cookies } = useCookies();
                if (!cookies.isKey(JWT.TOKEN)) {
                    reject(new ErrorModel(CodeResponse.FORBIDDEN))
                }

                let gameRequest = new GameRequest();
                gameRequest.setGameid(this.gameModel.gameid);
                gameRequest.setRowindex(rowIndex);
                gameRequest.setColindex(colIndex);
                this.gameClient.playGame(gameRequest, { token: cookies.get(JWT.TOKEN) }, (err, response) => {
                    if (err) {
                        switch (err.code) {
                            case Status.UNAVAILABLE:
                                reject(new ErrorModel(CodeResponse.GAME_IS_NOT_GOING_ON));
                                break;
                            case Status.PERMISSION_DENIED:
                                reject(new ErrorModel(CodeResponse.NOT_USER_TURN));
                                break;
                            case Status.NOT_FOUND:
                                reject(new ErrorModel(CodeResponse.NOT_FOUND));
                                break
                        }
                    } else {
                        this.gameModel = response.toObject();
                        resolve(this.gameModel);
                    }
                });
            })
        },

        getGame(gameId) {
            return new Promise((resolve, reject) => {

                const { cookies } = useCookies();
                if (!cookies.isKey(JWT.TOKEN)) {
                    reject(new ErrorModel(CodeResponse.FORBIDDEN))
                }

                let gameRequest = new GameRequest();
                gameRequest.setGameid(gameId);
                this.gameClient.getGame(gameRequest, { token: cookies.get(JWT.TOKEN) })
                    .on("data", data => {
                        this.gameModel = data.toObject();
                        resolve(data.toObject());
                    })
                    .on("status", status => {
                        if (status.code === Status.NOT_FOUND) {
                            reject(new ErrorModel(CodeResponse.NOT_FOUND));
                        }
                    });
            })
        }
    },
})