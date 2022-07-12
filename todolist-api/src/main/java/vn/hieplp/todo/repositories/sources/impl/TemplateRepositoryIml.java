package vn.hieplp.todo.repositories.sources.impl;

import vn.hieplp.todo.common.exceptions.DatabaseException;
import vn.hieplp.todo.common.models.TemplateModel;
import vn.hieplp.todo.repositories.base.BaseRepositoryImpl;
import vn.hieplp.todo.repositories.base.CustomDSLContext;
import vn.hieplp.todo.repositories.sources.ITemplateRepository;

import static vn.hieplp.todo.repositories.generates.Tables.TEMPLATE;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 06/05/2022
 * Time: 11:05
 */
public class TemplateRepositoryIml extends BaseRepositoryImpl implements ITemplateRepository {
    @Override
    public TemplateModel getTemplate(String templateName, Integer sendVia) {
        LOGGER.info("Get template by templateName {} and sendVia {}", templateName, sendVia);
        try (CustomDSLContext context = getDslContext()) {
            return context.select(TEMPLATE.fields())
                    .from(TEMPLATE)
                    .where(TEMPLATE.TEMPLATENAME.eq(templateName)
                            .and(TEMPLATE.SENDVIA.eq(sendVia)))
                    .fetchInto(TemplateModel.class)
                    .get(0);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.error("Template with templateName {} and sendVia {} is not found", templateName, sendVia);
            throw new DatabaseException.NotFoundException("Template not found");
        } catch (Exception e) {
            LOGGER.error("Error when get template by templateName and sendVia: {}", e.getMessage());
            throw new DatabaseException.QueryException("Error when get template by templateName and sendVia");
        }
    }
}
