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
proto.tictactoe = require('./AuthService_pb.js');

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?grpc.web.ClientOptions} options
 * @constructor
 * @struct
 * @final
 */
proto.tictactoe.AuthServiceClient =
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
proto.tictactoe.AuthServicePromiseClient =
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
 *   !proto.tictactoe.CreateTempUserRequest,
 *   !proto.tictactoe.CreateTempUserResponse>}
 */
const methodDescriptor_AuthService_createTempUser = new grpc.web.MethodDescriptor(
    '/tictactoe.AuthService/createTempUser',
    grpc.web.MethodType.UNARY,
    proto.tictactoe.CreateTempUserRequest,
    proto.tictactoe.CreateTempUserResponse,
    /**
     * @param {!proto.tictactoe.CreateTempUserRequest} request
     * @return {!Uint8Array}
     */
    function (request) {
        return request.serializeBinary();
    },
    proto.tictactoe.CreateTempUserResponse.deserializeBinary
);


/**
 * @param {!proto.tictactoe.CreateTempUserRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.RpcError, ?proto.tictactoe.CreateTempUserResponse)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.tictactoe.CreateTempUserResponse>|undefined}
 *     The XHR Node Readable Stream
 */
proto.tictactoe.AuthServiceClient.prototype.createTempUser =
    function (request, metadata, callback) {
        return this.client_.rpcCall(this.hostname_ +
            '/tictactoe.AuthService/createTempUser',
            request,
            metadata || {},
            methodDescriptor_AuthService_createTempUser,
            callback);
    };


/**
 * @param {!proto.tictactoe.CreateTempUserRequest} request The
 *     request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.tictactoe.CreateTempUserResponse>}
 *     Promise that resolves to the response
 */
proto.tictactoe.AuthServicePromiseClient.prototype.createTempUser =
    function (request, metadata) {
        return this.client_.unaryCall(this.hostname_ +
            '/tictactoe.AuthService/createTempUser',
            request,
            metadata || {},
            methodDescriptor_AuthService_createTempUser);
    };


module.exports = proto.tictactoe;

