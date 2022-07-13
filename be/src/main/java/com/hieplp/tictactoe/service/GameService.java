package com.hieplp.tictactoe.service;

import com.google.inject.Inject;
import com.hieplp.tictactoe.common.enums.StaticEnums;
import com.hieplp.tictactoe.common.exception.CommonException;
import com.hieplp.tictactoe.common.exception.GameException;
import com.hieplp.tictactoe.common.model.UserModel;
import com.hieplp.tictactoe.common.util.JsonConverter;
import com.hieplp.tictactoe.interceptor.TokenInterceptor;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tictactoe.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 23/06/2022
 * Time: 15:43
 */
public class GameService extends GameServiceGrpc.GameServiceImplBase {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final Map<String, GameModel> gameMap;
    private final Map<String, Map<String, StreamObserver<GameModel>>> gameStreamMap;

    @Inject
    public GameService(Map<String, GameModel> gameMap,
                       Map<String, Map<String, StreamObserver<GameModel>>> gameStreamMap) {
        this.gameMap = gameMap;
        this.gameStreamMap = gameStreamMap;


    }

    @Override
    public void createGame(GameRequest request, StreamObserver<GameModel> responseObserver) {
        try {
            LOGGER.info("Start create game with request {}", JsonConverter.toJson(request));

            UserModel userModel = TokenInterceptor.USER_MODEL_KEY.get();
            GameModel gameModel = createGame(userModel);

            Map<String, StreamObserver<GameModel>> players = new HashMap<>();
            players.put(userModel.getUserId(), responseObserver);
            gameStreamMap.put(gameModel.getGameId(), players);

            responseObserver.onNext(gameModel);
        } catch (Exception e) {
            LOGGER.error("Error when create game: {}", e.getMessage());
            responseObserver.onError(e);
        }
    }

    @Override
    public void exitGame(GameRequest request, StreamObserver<GameModel> responseObserver) {
        LOGGER.info("Start exit game with request {}", JsonConverter.toJson(request));

        UserModel userModel = TokenInterceptor.USER_MODEL_KEY.get();
        gameStreamMap.get(request.getGameId()).remove(userModel.getUserId());

        responseObserver.onNext(gameMap.get(request.getGameId()));
        responseObserver.onCompleted();
    }

    @Override
    public void joinGame(GameRequest request, StreamObserver<GameModel> responseObserver) {
        try {
            LOGGER.info("Start join game with request {}", JsonConverter.toJson(request));

            UserModel userModel = TokenInterceptor.USER_MODEL_KEY.get();
            GameModel gameModel = joinGame(request.getGameId(), userModel, responseObserver);

            onNextGameResponse(gameModel);
        } catch (CommonException.NotFoundException e) {
            LOGGER.error("Not found error: {}", e.getMessage());
            responseObserver.onError(Status.NOT_FOUND.withDescription("Game not found").asException());
        } catch (GameException.OnGoingException e) {
            LOGGER.error("Game is going on error: {}", e.getMessage());
            responseObserver.onError(Status.PERMISSION_DENIED.withDescription("Game is going on").asException());
        } catch (GameException.JoinOwnGameException e) {
            LOGGER.error("Join own game error: {}", e.getMessage());
            responseObserver.onError(Status.ALREADY_EXISTS.withDescription("Join own game error").asException());
        } catch (Exception e) {
            LOGGER.error("Error when join game: {}", e.getMessage());
            responseObserver.onError(e);
        }
    }

    @Override
    public void playGame(GameRequest request, StreamObserver<GameModel> responseObserver) {
        try {
            LOGGER.info("Start play game with request {}", JsonConverter.toJson(request));

            UserModel userModel = TokenInterceptor.USER_MODEL_KEY.get();
            GameModel gameModel = playGame(request.getGameId(), userModel.getUserId(), request.getRowIndex(), request.getColIndex());
            if (StaticEnums.GameStatus.FINISHED.getStatus().equals(gameModel.getGameStatus())) {
                onCompletedGameResponse(gameModel);
            } else {
                onNextGameResponse(gameModel);
            }

            responseObserver.onNext(gameModel);
            responseObserver.onCompleted();
        } catch (CommonException.NotFoundException e) {
            LOGGER.error("Not found error: {}", e.getMessage());
            responseObserver.onError(Status.NOT_FOUND.withDescription("Game not found").asException());
        } catch (GameException.NotOnGoingException e) {
            LOGGER.error("Game is not going on error: {}", e.getMessage());
            responseObserver.onError(Status.UNAVAILABLE.withDescription("Game is not going on").asException());
        } catch (GameException.NotUserTurnException e) {
            LOGGER.error("Game is not going on error: {}", e.getMessage());
            responseObserver.onError(Status.PERMISSION_DENIED.withDescription("Not user turn").asException());
        } catch (Exception e) {
            LOGGER.error("Error when play game: {}", e.getMessage());
            responseObserver.onError(e);
        }
    }

    @Override
    public void getGame(GameRequest request, StreamObserver<GameModel> responseObserver) {
        LOGGER.info("Start get game with request {}", JsonConverter.toJson(request));

        UserModel userModel = TokenInterceptor.USER_MODEL_KEY.get();
        // Check if user is game player
        // If yes, replace old response observer with new response observer
        if (gameMap.containsKey(request.getGameId())
                && gameStreamMap.get(request.getGameId()).containsKey(userModel.getUserId())) {
            gameStreamMap.get(request.getGameId()).put(userModel.getUserId(), responseObserver);
            responseObserver.onNext(gameMap.get(request.getGameId()));
        } else {
            LOGGER.error("Game not found error");
            responseObserver.onError(Status.NOT_FOUND.asException());
        }
    }

    private GameModel createGame(UserModel userModel) {
        // Init game items
        GameItemList gameItemList = GameItemList.newBuilder()
                .addItems(GameItemModel.newBuilder()
                        .setItemId(0)
                        .setItemType(StaticEnums.GameItemType.UNKNOWN.getType())
                        .build())
                .addItems(GameItemModel.newBuilder()
                        .setItemId(1)
                        .setItemType(StaticEnums.GameItemType.UNKNOWN.getType())
                        .build())
                .addItems(GameItemModel.newBuilder()
                        .setItemId(2)
                        .setItemType(StaticEnums.GameItemType.UNKNOWN.getType())
                        .build())
                .build();
        Map<Integer, GameItemList> gameItems = new HashMap<>();
        gameItems.put(0, gameItemList);
        gameItems.put(1, gameItemList);
        gameItems.put(2, gameItemList);

        GameModel gameModel = GameModel.newBuilder()
                .setGameId(UUID.randomUUID().toString())
                .setOwnerId(userModel.getUserId())
                .setOwnerName(userModel.getUserName())
                .setGameStatus(StaticEnums.GameStatus.NOT_STARTED.getStatus())
                .setGameTurn(StaticEnums.GameItemType.OWNER.getType())
                .putAllGameItems(gameItems)
                .build();
        gameMap.put(gameModel.getGameId(), gameModel);

        return gameModel;
    }

    private GameModel joinGame(String gameId, UserModel userModel, StreamObserver<GameModel> responseObserver) {
        if (!gameMap.containsKey(gameId)) {
            throw new CommonException.NotFoundException("Not found game with gameId " + gameId);
        }

        // Check if game is going on or not
        GameModel gameModel = gameMap.get(gameId);
        if (!StaticEnums.GameStatus.NOT_STARTED.getStatus().equals(gameModel.getGameStatus())) {
            throw new GameException.OnGoingException("Game is not started");
        }

        // Check if user joins own his game
        if (userModel.getUserId().equals(gameModel.getOwnerId())) {
            throw new GameException.JoinOwnGameException("Join own game error");
        }

        // Update game opponent, gameStatus
        GameModel newGameModel = gameModel.toBuilder()
                .setOpponentId(userModel.getUserId())
                .setOpponentName(userModel.getUserName())
                .setGameStatus(StaticEnums.GameStatus.ON_GOING.getStatus())
                .build();
        gameMap.put(gameModel.getGameId(), newGameModel);

        // Update response stream map
        gameStreamMap.get(gameId).put(userModel.getUserId(), responseObserver);

        return newGameModel;
    }

    private GameModel playGame(String gameId, String userId, int rowIndex, int colIndex) {

        if (!gameMap.containsKey(gameId)) {
            throw new CommonException.NotFoundException("Game not found");
        }

        // Check if game is going on or not
        GameModel gameModel = gameMap.get(gameId);
        if (!StaticEnums.GameStatus.ON_GOING.getStatus().equals(gameModel.getGameStatus())) {
            throw new GameException.NotOnGoingException("Game is not going on");
        }

        // Check if it is user's turn or not
        Integer userItemType = getUserItemType(userId, gameModel);
        if (gameModel.getGameTurn() != userItemType) {
            throw new GameException.NotUserTurnException("Not user turn");
        }

        // Update game items
        GameItemList.Builder gameItemListBuilder = gameModel.getGameItemsMap().get(rowIndex).toBuilder();
        gameItemListBuilder.setItems(colIndex, GameItemModel.newBuilder()
                .setItemId(colIndex)
                .setItemType(userItemType)
                .build());
        Map<Integer, GameItemList> gameItems = new HashMap<>(gameModel.getGameItemsMap());
        gameItems.put(rowIndex, gameItemListBuilder.build());

        GameModel.Builder gameBuilder = gameModel.toBuilder();

        // Check if all moves are done by players
        // If yes, set game status to finished
        if (areAllMovesDone(gameItems)) {
            gameBuilder
                    .setGameStatus(StaticEnums.GameStatus.FINISHED.getStatus())
                    .setWinnerId(""); // Draw
        }

        // Check if user win or not
        if (checkGameWinner(gameItems, userItemType, rowIndex, colIndex)) {
            gameBuilder
                    .setGameStatus(StaticEnums.GameStatus.FINISHED.getStatus())
                    .setWinnerId(userId);
        }

        // Update game map value
        GameModel newGameModel = gameBuilder
                .setGameTurn(getNextGameTurn(gameModel.getGameTurn()))
                .clearGameItems()
                .putAllGameItems(gameItems)
                .build();
        gameMap.put(gameId, newGameModel);

        return newGameModel;
    }

    /**
     * Get user's item type.
     * If user is game owner, return OWNER(1).
     * If user is game opponent, return OPPONENT(2).
     * Else not supported exception.
     *
     * @param userId    userId
     * @param gameModel game model
     * @return user's item type
     */
    private Integer getUserItemType(String userId, GameModel gameModel) {
        if (userId.equals(gameModel.getOwnerId())) {
            return StaticEnums.GameItemType.OWNER.getType();
        } else if (userId.equals(gameModel.getOpponentId())) {
            return StaticEnums.GameItemType.OPPONENT.getType();
        } else {
            throw new CommonException.NotSupportedException("User is not game player");
        }
    }

    /**
     * Get next turn by current turn
     *
     * @param gameTurn current turn
     * @return next turn
     */
    private Integer getNextGameTurn(Integer gameTurn) {
        return StaticEnums.GameItemType.OWNER.getType().equals(gameTurn)
                ? StaticEnums.GameItemType.OPPONENT.getType() : StaticEnums.GameItemType.OWNER.getType();
    }

    /**
     * Send new game model to all players
     *
     * @param gameModel new game model
     */
    private void onNextGameResponse(GameModel gameModel) {
        gameStreamMap.get(gameModel.getGameId()).forEach((userId, observer) -> {
            observer.onNext(gameModel);
        });
    }

    /**
     * Send new game model to all players. Then end stream.
     *
     * @param gameModel new game model
     */
    private void onCompletedGameResponse(GameModel gameModel) {
        // Send ne
        gameStreamMap.get(gameModel.getGameId()).forEach((userId, observer) -> {
            observer.onNext(gameModel);
            observer.onCompleted();
        });
    }

    /**
     * Check if all game moves are done
     *
     * @param items game moves
     * @return true if all moves are done
     */
    private boolean areAllMovesDone(Map<Integer, GameItemList> items) {
        for (GameItemList itemList : items.values()) {
            for (GameItemModel item : itemList.getItemsList()) {
                if (StaticEnums.GameItemType.UNKNOWN.getType().equals(item.getItemType())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Check if user is winner
     *
     * @param items        game moves
     * @param userItemType userItemType(owner or component)
     * @param rowIndex     row index
     * @param colIndex     column index
     * @return true if user won
     */
    private boolean checkGameWinner(Map<Integer, GameItemList> items, Integer userItemType, int rowIndex, int colIndex) {
        LOGGER.info("Check game winner with rowIndex {} and colIndex {}", rowIndex, colIndex);

        // 00 01 02
        // 10 11 12
        // 20 21 22

        if (items.get(rowIndex).getItems(0).getItemType() == userItemType
                && items.get(rowIndex).getItems(1).getItemType() == userItemType
                && items.get(rowIndex).getItems(2).getItemType() == userItemType) {
            return true;
        }

        if (items.get(0).getItems(colIndex).getItemType() == userItemType
                && items.get(1).getItems(colIndex).getItemType() == userItemType
                && items.get(2).getItems(colIndex).getItemType() == userItemType) {
            return true;
        }

        if (rowIndex == colIndex) {
            if (items.get(0).getItems(0).getItemType() == userItemType
                    && items.get(1).getItems(1).getItemType() == userItemType
                    && items.get(2).getItems(2).getItemType() == userItemType) {
                return true;
            }
        }

        if ((rowIndex + colIndex) == 2) {
            return items.get(0).getItems(2).getItemType() == userItemType
                    & items.get(1).getItems(1).getItemType() == userItemType
                    && items.get(2).getItems(0).getItemType() == userItemType;
        }

        return false;
    }
}
