package com.daliansea.ai.system.service;

import java.util.List;
import com.daliansea.ai.system.domain.SeaContentTemplate;

/**
 * 文案模板 服务层
 *
 * @author daliansea
 */
public interface ISeaContentTemplateService
{
    public List<SeaContentTemplate> selectTemplateList(SeaContentTemplate template);

    public SeaContentTemplate selectTemplateById(Long templateId);

    public int insertTemplate(SeaContentTemplate template);

    public int updateTemplate(SeaContentTemplate template);

    public int deleteTemplateByIds(Long[] templateIds);
}
