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
proto.tictactoe = require('./AuthTokenService_pb.js');

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?grpc.web.ClientOptions} options
 * @constructor
 * @struct
 * @final
 */
proto.tictactoe.AuthTokenServiceClient =
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
proto.tictactoe.AuthTokenServicePromiseClient =
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
 *   !proto.tictactoe.LogoutRequest,
 *   !proto.tictactoe.LogoutResponse>}
 */
const methodDescriptor_AuthTokenService_logout = new grpc.web.MethodDescriptor(
    '/tictactoe.AuthTokenService/logout',
    grpc.web.MethodType.UNARY,
    proto.tictactoe.LogoutRequest,
    proto.tictactoe.LogoutResponse,
    /**
     * @param {!proto.tictactoe.LogoutRequest} request
     * @return {!Uint8Array}
     */
    function (request) {
        return request.serializeBinary();
    },
    proto.tictactoe.LogoutResponse.deserializeBinary
);


/**
 * @param {!proto.tictactoe.LogoutRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.RpcError, ?proto.tictactoe.LogoutResponse)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.tictactoe.LogoutResponse>|undefined}
 *     The XHR Node Readable Stream
 */
proto.tictactoe.AuthTokenServiceClient.prototype.logout =
    function (request, metadata, callback) {
        return this.client_.rpcCall(this.hostname_ +
            '/tictactoe.AuthTokenService/logout',
            request,
            metadata || {},
            methodDescriptor_AuthTokenService_logout,
            callback);
    };


/**
 * @param {!proto.tictactoe.LogoutRequest} request The
 *     request proto
 * @param {?Object<string, string>=} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.tictactoe.LogoutResponse>}
 *     Promise that resolves to the response
 */
proto.tictactoe.AuthTokenServicePromiseClient.prototype.logout =
    function (request, metadata) {
        return this.client_.unaryCall(this.hostname_ +
            '/tictactoe.AuthTokenService/logout',
            request,
            metadata || {},
            methodDescriptor_AuthTokenService_logout);
    };


module.exports = proto.tictactoe;

