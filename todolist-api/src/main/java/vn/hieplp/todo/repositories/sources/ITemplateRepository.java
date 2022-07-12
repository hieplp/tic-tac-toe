package vn.hieplp.todo.repositories.sources;

import vn.hieplp.todo.common.models.TemplateModel;
import vn.hieplp.todo.repositories.base.IBaseRepository;

public interface ITemplateRepository extends IBaseRepository {
    TemplateModel getTemplate(String templateName, Integer sendVia);
}
