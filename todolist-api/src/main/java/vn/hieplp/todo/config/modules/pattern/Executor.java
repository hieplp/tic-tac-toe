package vn.hieplp.todo.config.modules.pattern;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.constant.CodeResponse;
import vn.hieplp.todo.common.exceptions.AuthException;
import vn.hieplp.todo.common.exceptions.CommonException;
import vn.hieplp.todo.common.exceptions.DatabaseException;
import vn.hieplp.todo.common.handler.IAuthHandler;
import vn.hieplp.todo.common.response.RestfulCommonResponse;
import vn.hieplp.todo.common.response.RestfulFailureResponse;
import vn.hieplp.todo.common.response.RestfulSuccessResponse;
import vn.hieplp.todo.common.utils.JsonConverter;
import vn.hieplp.todo.common.utils.States;
import vn.hieplp.todo.config.modules.pattern.model.CommonRequest;
import vn.hieplp.todo.config.modules.pattern.model.HeaderInformation;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 06/05/2022
 * Time: 14:19
 */
public class Executor implements IExecutor {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());
    private final IAuthHandler authHandler;
    private boolean hasBody;
    private boolean isAuth;
    private boolean isParams;
    private Class<?> requestClass = null;
    private Object response;
    private CommonRequest commonRequest;

    @Inject
    public Executor(IAuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @Override
    public IExecutor auth() {
        this.isAuth = true;
        return this;
    }

    @Override
    public IExecutor body() {
        this.hasBody = true;
        return this;
    }

    @Override
    public IExecutor params() {
        this.isParams = true;
        return this;
    }


    @Override
    public IExecutor setRequestClass(Class<?> clazz) {
        this.requestClass = clazz;
        return this;
    }

    @Override
    public IExecutor execute(RoutingContext context, IServiceHandler serviceHandler, IResponseHandler responseHandler) {
        LOGGER.info("Execute request");
        try {
            commonRequest = CommonRequest.builder().headers(getHeaders(context.request().headers())).build();

            if (isAuth) {
                authHandler.validateToken(commonRequest.getHeaders().getToken());
                commonRequest.setHeaders(authHandler.getTokenInformation(commonRequest.getHeaders()));
            }

            if (hasBody) {
                this.executeWithBody(context, serviceHandler, responseHandler);
            } else {
                this.executeWithoutBody(context, serviceHandler, responseHandler);
            }

        } catch (AuthException.InvalidTokenException e) {
            LOGGER.error("Invalid token: {}", e.getMessage());
            unAuthorized(context, e.getMessage());
        } catch (TokenExpiredException e) {
            LOGGER.error("Expired token: {}", e.getMessage());
            unAuthorized(context, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Execute request error caused of {}", e.getMessage());
            internalError(context);
        }
        return this;
    }

    /**
     * Execute without body
     *
     * @param context         routing context
     * @param serviceHandler  service handler
     * @param responseHandler response handler
     */
    private void executeWithoutBody(RoutingContext context, IServiceHandler serviceHandler, IResponseHandler responseHandler) {
        LOGGER.info("Execute request without body");
        try {
            // Set request params
            if (States.isNotNull(requestClass)) {
                JsonObject paramsJObject = JsonConverter.fromMultiMap(context.queryParams());
                commonRequest.setRequest(JsonConverter.fromJson(paramsJObject, requestClass));
            }

            this.response = serviceHandler.handle(commonRequest);
            responseHandler.end(context);
        } catch (Exception e) {
            handleException(context, e);
        }
    }


    /**
     * Execute request with body
     *
     * @param context         routing context
     * @param serviceHandler  service handler
     * @param responseHandler response handler
     */
    private void executeWithBody(RoutingContext context, IServiceHandler serviceHandler, IResponseHandler responseHandler) {
        LOGGER.info("Executing request with body");
        context.request().bodyHandler(buffer -> {
            try {
                commonRequest.setRequest(JsonConverter.fromJson(buffer.toString(), requestClass));
                response = serviceHandler.handle(commonRequest);
                responseHandler.end(context);
            } catch (Exception e) {
                handleException(context, e);
            }
        });

    }

    /**
     * Handle exception.
     *
     * @param context   routing context
     * @param exception exception
     */
    private void handleException(RoutingContext context, Exception exception) {
        try {
            throw exception;
        } catch (AuthException.InvalidTokenException e) {
            LOGGER.error("Invalid token: {}", e.getMessage());
            unAuthorized(context, e.getMessage());
        } catch (AuthException.InvalidPasswordException e) {
            LOGGER.error("Invalid password: {}", e.getMessage());
            unAuthorized(context, e.getMessage());
        } catch (CommonException.ValidationException e) {
            LOGGER.error("Validation error: {}", e.getMessage());
            badRequest(context, e.getMessage());
        } catch (DatabaseException.DuplicateException e) {
            LOGGER.error("Duplicate error: {}", e.getMessage());
            conflict(context);
        } catch (DatabaseException.NotFoundException e) {
            LOGGER.error("Not found error: {}", e.getMessage());
            notFound(context, e.getMessage());
        } catch (DatabaseException.QueryException e) {
            LOGGER.error("Database error: {}", e.getMessage());
            if (e.getMessage().contains("Duplicate")) {
                conflict(context);
            } else {
                internalError(context);
            }
        } catch (Exception e) {
            LOGGER.error("Execute request failed caused of {}", e.getMessage());
            internalError(context);
        }
    }

    /**
     * Get headers from request
     *
     * @param headers headers from request
     * @return header information model
     */
    private HeaderInformation getHeaders(MultiMap headers) {
        JsonObject jsonObject = JsonConverter.fromMultiMap(headers);
        return JsonConverter.fromJson(jsonObject, HeaderInformation.class);
    }

    /**
     * Send back a response with status 200 OK
     *
     * @param context routing context
     */
    @Override
    public void ok(RoutingContext context) {
        if (context.response().ended()) return;

        RestfulCommonResponse response = RestfulSuccessResponse.builder().code(CodeResponse.HttpStatusCode.OK.getCode().toString()).messages(CodeResponse.HttpStatusCode.OK.getMessage()).data(this.response).build();

        context.response().setStatusCode(CodeResponse.HttpStatusCode.OK.getCode()).putHeader("content-type", "application/json").end(JsonConverter.toJson(response));
    }

    /**
     * Send back a response with status 201 CREATED.
     *
     * @param context routing context
     */
    public void created(RoutingContext context) {
        if (context.response().ended()) return;

        RestfulCommonResponse response = RestfulSuccessResponse.builder().code(CodeResponse.HttpStatusCode.CREATE.getCode().toString()).messages(CodeResponse.HttpStatusCode.CREATE.getMessage()).data(this.response).build();

        context.response().setStatusCode(CodeResponse.HttpStatusCode.CREATE.getCode()).putHeader("content-type", "application/json").end(JsonConverter.toJson(response));
    }

    /**
     * Send back a response with status 400 Bad Request.
     *
     * @param context routing context
     * @param message error message
     */
    private void badRequest(RoutingContext context, String message) {
        RestfulCommonResponse response = RestfulFailureResponse.builder().code(CodeResponse.HttpStatusCode.BAD_REQUEST.getCode().toString()).messages(message).build();

        context.response().setStatusCode(CodeResponse.HttpStatusCode.BAD_REQUEST.getCode()).putHeader("content-type", "application/json").end(JsonConverter.toJson(response));
    }

    /**
     * Send back a response with status 401 Unauthorized.
     *
     * @param context routing context
     * @param message error message
     */
    private void unAuthorized(RoutingContext context, String message) {
        RestfulCommonResponse response = RestfulFailureResponse.builder().code(CodeResponse.HttpStatusCode.UNAUTHORIZED.getCode().toString()).messages(message).build();

        context.response().setStatusCode(CodeResponse.HttpStatusCode.UNAUTHORIZED.getCode()).putHeader("content-type", "application/json").end(JsonConverter.toJson(response));
    }

    /**
     * Send back a response with status 404 Not found.
     *
     * @param context routing context
     * @param message error message
     */
    private void notFound(RoutingContext context, String message) {
        RestfulCommonResponse response = RestfulFailureResponse.builder().code(CodeResponse.HttpStatusCode.NOT_FOUND.getCode().toString()).messages(message).build();

        context.response().setStatusCode(CodeResponse.HttpStatusCode.NOT_FOUND.getCode()).putHeader("content-type", "application/json").end(JsonConverter.toJson(response));
    }

    /**
     * Send back a response with status 409 Conflict.
     *
     * @param context routing context
     */
    private void conflict(RoutingContext context) {
        RestfulCommonResponse response = RestfulFailureResponse.builder().code(CodeResponse.HttpStatusCode.CONFLICT.getCode().toString()).messages(CodeResponse.HttpStatusCode.CONFLICT.getMessage()).build();

        context.response().setStatusCode(CodeResponse.HttpStatusCode.CONFLICT.getCode()).putHeader("content-type", "application/json").end(JsonConverter.toJson(response));
    }

    /**
     * Send back a response with status 500 Internal Error.
     *
     * @param context routing context
     */
    private void internalError(RoutingContext context) {
        RestfulCommonResponse response = RestfulFailureResponse.builder().code(CodeResponse.HttpStatusCode.INTERNAL_SERVER.getCode().toString()).messages(CodeResponse.HttpStatusCode.INTERNAL_SERVER.getMessage()).build();

        context.response().setStatusCode(CodeResponse.HttpStatusCode.INTERNAL_SERVER.getCode()).putHeader("content-type", "application/json").end(JsonConverter.toJson(response));
    }
}
