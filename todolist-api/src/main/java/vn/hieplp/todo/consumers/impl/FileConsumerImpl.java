package vn.hieplp.todo.consumers.impl;

import com.google.inject.Inject;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.config.ConfigInfo;
import vn.hieplp.todo.consumers.IFileConsumer;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 30/05/2022
 * Time: 11:42
 */
public class FileConsumerImpl implements IFileConsumer {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final ConfigInfo configInfo;

    @Inject
    public FileConsumerImpl(ConfigInfo configInfo) {
        this.configInfo = configInfo;
    }

    @Override
    public void handleUploadImage(RoutingContext context) {
        LOGGER.info("Handle upload image");
        String fileName = "";
        for (FileUpload fileUpload : context.fileUploads()) {
            LOGGER.debug("File: {}", fileUpload.uploadedFileName());
            fileName = fileUpload.uploadedFileName().replace(configInfo.getStaticConfig().getDirectory(), "");
            break;
        }
        LOGGER.debug("FILE NAME: {}", fileName);

        context.response().end(fileName);
    }
}
