package vn.hieplp.todo.services.impl;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.config.ConfigInfo;
import vn.hieplp.todo.common.constant.StaticEnum;
import vn.hieplp.todo.common.models.EmailModel;
import vn.hieplp.todo.common.models.TemplateModel;
import vn.hieplp.todo.common.utils.EmailUtil;
import vn.hieplp.todo.common.utils.JsonConverter;
import vn.hieplp.todo.repositories.sources.ITemplateRepository;
import vn.hieplp.todo.services.ITemplateService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 06/05/2022
 * Time: 10:52
 */
public class TemplateServiceImpl implements ITemplateService {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private final String TEMPLATE_DELIMITER = "$";

    private final ITemplateRepository templateRepository;
    private final ConfigInfo configInfo;

    @Inject
    public TemplateServiceImpl(ITemplateRepository templateRepository, ConfigInfo configInfo) {
        this.templateRepository = templateRepository;
        this.configInfo = configInfo;
    }

    @Override
    public List<TemplateModel> getAllTemplates() {
        LOGGER.info("Get all templates");
        return null;
    }

    @Override
    public TemplateModel getTemplate(String templateName, Integer sendVia) {
        LOGGER.info("Get template by templateName {} and sendVIa {}", templateName, sendVia);
        return templateRepository.getTemplate(templateName, sendVia);
    }

    @Override
    public void sendEmail(String email, String templateName, Map<String, String> params) {
        LOGGER.info("Send email with email {} and templateName {} and params {}", email, templateName, JsonConverter.toJson(params));
        Thread thread = new Thread(() -> {
            TemplateModel template = this.getTemplate(templateName, StaticEnum.SendVia.EMAIL.getType());
            buildTemplate(template, params);
            EmailModel emailModel = EmailModel.builder()
                    .sendTo(email)
                    .title(template.getTitle())
                    .content(template.getContent())
                    .build();
            EmailUtil.send(configInfo.getEmailConfig(), emailModel);
        });
        thread.start();
    }

    @Override
    public void sendEmail(String email, String templateName) {
        LOGGER.info("Send email with email {} and template {}", email, templateName);
        this.sendEmail(email, templateName, new HashMap<>());
    }

    private void buildTemplate(TemplateModel template, Map<String, String> params) {
        LOGGER.info("Build template with params {}", JsonConverter.toJson(params));
        params.forEach((key, value) -> {
            template.setTitle(template.getTitle().replace(TEMPLATE_DELIMITER + key + TEMPLATE_DELIMITER, value));
            template.setContent(template.getContent().replace(TEMPLATE_DELIMITER + key + TEMPLATE_DELIMITER, value));
        });
        LOGGER.warn(JsonConverter.toJson(template));
    }
}
