/**
 * @fileoverview gRPC-Web generated client stub for tictactoe
 * @enhanceable
 * @public
 */

// GENERATED CODE -- DO NOT EDIT!


/* eslint-disable */
// @ts-nocheck


const grpc = {};
grpc.web = require('grpc-web');

const proto = {};
proto.tictactoe = require('./GameService_pb.js');

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?grpc.web.ClientOptions} options
 * @constructor
 * @struct
 * @final
 */
proto.tictactoe.GameServiceClient =
    function (hostname, credentials, options) {
        if (!options) options = {};
        options.format = 'text';

        /**
         * @private @const {!grpc.web.GrpcWebClientBase} The client
         */
        this.client_ = new grpc.web.GrpcWebClientBase(options);

        /**
         * @private @const {string} The hostname
         */
        this.hostname_ = hostname;

    };


/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?grpc.web.ClientOptions} options
 * @constructor
 * @struct
 * @final
 */
proto.tictactoe.GameServicePromiseClient =
    function (hostname, credentials, options) {
        if (!options) options = {};
        options.format = 'text';

        /**
         * @private @const {!grpc.web.GrpcWebClientBase} The client
         */
        this.client_ = new grpc.web.GrpcWebClientBase(options);

        /**
         * @private @const {string} The hostname
         */
        this.hostname_ = hostname;

    };


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.tictactoe.GameRequest,
 *   !proto.tictactoe.GameModel>}
 */
const methodDescriptor_GameService_createGame = new grpc.web.MethodDescriptor(
    '/tictactoe.GameService/createGame',
    grpc.web.MethodType.SERVER_STREAMING,
    proto.tictactoe.GameRequest,
    proto.tictactoe.GameModel,
    /**
     * @param {!proto.tictactoe.GameRequest} request
     * @return {!Uint8Array}
     */
    function (request) {
        return request.serializeBinary();
    },
    proto.tictactoe.GameModel.deserializeBinary
);


/**
 * @param {!proto.tictactoe.GameRequest} request The request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!grpc.web.ClientReadableStream<!proto.tictactoe.GameModel>}
 *     The XHR Node Readable Stream
 */
proto.tictactoe.GameServiceClient.prototype.createGame =
    function (request, metadata) {
        return this.client_.serverStreaming(this.hostname_ +
            '/tictactoe.GameService/createGame',
            request,
            metadata || {},
            methodDescriptor_GameService_createGame);
    };


/**
 * @param {!proto.tictactoe.GameRequest} request The request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!grpc.web.ClientReadableStream<!proto.tictactoe.GameModel>}
 *     The XHR Node Readable Stream
 */
proto.tictactoe.GameServicePromiseClient.prototype.createGame =
    function (request, metadata) {
        return this.client_.serverStreaming(this.hostname_ +
            '/tictactoe.GameService/createGame',
            request,
            metadata || {},
            methodDescriptor_GameService_createGame);
    };


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.tictactoe.GameRequest,
 *   !proto.tictactoe.GameModel>}
 */
const methodDescriptor_GameService_exitGame = new grpc.web.MethodDescriptor(
    '/tictactoe.GameService/exitGame',
    grpc.web.MethodType.UNARY,
    proto.tictactoe.GameRequest,
    proto.tictactoe.GameModel,
    /**
     * @param {!proto.tictactoe.GameRequest} request
     * @return {!Uint8Array}
     */
    function (request) {
        return request.serializeBinary();
    },
    proto.tictactoe.GameModel.deserializeBinary
);


/**
 * @param {!proto.tictactoe.GameRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.RpcError, ?proto.tictactoe.GameModel)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.tictactoe.GameModel>|undefined}
 *     The XHR Node Readable Stream
 */
proto.tictactoe.GameServiceClient.prototype.exitGame =
    function (request, metadata, callback) {
        return this.client_.rpcCall(this.hostname_ +
            '/tictactoe.GameService/exitGame',
            request,
            metadata || {},
            methodDescriptor_GameService_exitGame,
            callback);
    };


/**
 * @param {!proto.tictactoe.GameRequest} request The
 *     request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.tictactoe.GameModel>}
 *     Promise that resolves to the response
 */
proto.tictactoe.GameServicePromiseClient.prototype.exitGame =
    function (request, metadata) {
        return this.client_.unaryCall(this.hostname_ +
            '/tictactoe.GameService/exitGame',
            request,
            metadata || {},
            methodDescriptor_GameService_exitGame);
    };


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.tictactoe.GameRequest,
 *   !proto.tictactoe.GameModel>}
 */
const methodDescriptor_GameService_joinGame = new grpc.web.MethodDescriptor(
    '/tictactoe.GameService/joinGame',
    grpc.web.MethodType.SERVER_STREAMING,
    proto.tictactoe.GameRequest,
    proto.tictactoe.GameModel,
    /**
     * @param {!proto.tictactoe.GameRequest} request
     * @return {!Uint8Array}
     */
    function (request) {
        return request.serializeBinary();
    },
    proto.tictactoe.GameModel.deserializeBinary
);


/**
 * @param {!proto.tictactoe.GameRequest} request The request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!grpc.web.ClientReadableStream<!proto.tictactoe.GameModel>}
 *     The XHR Node Readable Stream
 */
proto.tictactoe.GameServiceClient.prototype.joinGame =
    function (request, metadata) {
        return this.client_.serverStreaming(this.hostname_ +
            '/tictactoe.GameService/joinGame',
            request,
            metadata || {},
            methodDescriptor_GameService_joinGame);
    };


/**
 * @param {!proto.tictactoe.GameRequest} request The request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!grpc.web.ClientReadableStream<!proto.tictactoe.GameModel>}
 *     The XHR Node Readable Stream
 */
proto.tictactoe.GameServicePromiseClient.prototype.joinGame =
    function (request, metadata) {
        return this.client_.serverStreaming(this.hostname_ +
            '/tictactoe.GameService/joinGame',
            request,
            metadata || {},
            methodDescriptor_GameService_joinGame);
    };


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.tictactoe.GameRequest,
 *   !proto.tictactoe.GameModel>}
 */
const methodDescriptor_GameService_playGame = new grpc.web.MethodDescriptor(
    '/tictactoe.GameService/playGame',
    grpc.web.MethodType.UNARY,
    proto.tictactoe.GameRequest,
    proto.tictactoe.GameModel,
    /**
     * @param {!proto.tictactoe.GameRequest} request
     * @return {!Uint8Array}
     */
    function (request) {
        return request.serializeBinary();
    },
    proto.tictactoe.GameModel.deserializeBinary
);


/**
 * @param {!proto.tictactoe.GameRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.RpcError, ?proto.tictactoe.GameModel)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.tictactoe.GameModel>|undefined}
 *     The XHR Node Readable Stream
 */
proto.tictactoe.GameServiceClient.prototype.playGame =
    function (request, metadata, callback) {
        return this.client_.rpcCall(this.hostname_ +
            '/tictactoe.GameService/playGame',
            request,
            metadata || {},
            methodDescriptor_GameService_playGame,
            callback);
    };


/**
 * @param {!proto.tictactoe.GameRequest} request The
 *     request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.tictactoe.GameModel>}
 *     Promise that resolves to the response
 */
proto.tictactoe.GameServicePromiseClient.prototype.playGame =
    function (request, metadata) {
        return this.client_.unaryCall(this.hostname_ +
            '/tictactoe.GameService/playGame',
            request,
            metadata || {},
            methodDescriptor_GameService_playGame);
    };


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.tictactoe.GameRequest,
 *   !proto.tictactoe.GameModel>}
 */
const methodDescriptor_GameService_getGame = new grpc.web.MethodDescriptor(
    '/tictactoe.GameService/getGame',
    grpc.web.MethodType.SERVER_STREAMING,
    proto.tictactoe.GameRequest,
    proto.tictactoe.GameModel,
    /**
     * @param {!proto.tictactoe.GameRequest} request
     * @return {!Uint8Array}
     */
    function (request) {
        return request.serializeBinary();
    },
    proto.tictactoe.GameModel.deserializeBinary
);


/**
 * @param {!proto.tictactoe.GameRequest} request The request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!grpc.web.ClientReadableStream<!proto.tictactoe.GameModel>}
 *     The XHR Node Readable Stream
 */
proto.tictactoe.GameServiceClient.prototype.getGame =
    function (request, metadata) {
        return this.client_.serverStreaming(this.hostname_ +
            '/tictactoe.GameService/getGame',
            request,
            metadata || {},
            methodDescriptor_GameService_getGame);
    };


/**
 * @param {!proto.tictactoe.GameRequest} request The request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!grpc.web.ClientReadableStream<!proto.tictactoe.GameModel>}
 *     The XHR Node Readable Stream
 */
proto.tictactoe.GameServicePromiseClient.prototype.getGame =
    function (request, metadata) {
        return this.client_.serverStreaming(this.hostname_ +
            '/tictactoe.GameService/getGame',
            request,
            metadata || {},
            methodDescriptor_GameService_getGame);
    };


module.exports = proto.tictactoe;

