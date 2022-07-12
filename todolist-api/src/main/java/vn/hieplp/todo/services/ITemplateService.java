package vn.hieplp.todo.services;

import vn.hieplp.todo.common.models.TemplateModel;

import java.util.List;
import java.util.Map;

public interface ITemplateService {
    List<TemplateModel> getAllTemplates();

    TemplateModel getTemplate(String templateName, Integer sendVia);

    void sendEmail(String email, String templateName, Map<String, String> params);

    void sendEmail(String email, String templateName);
}
