package com.daliansea.ai.system.mapper;

import java.util.List;
import com.daliansea.ai.system.domain.SeaContentTemplate;

/**
 * 文案模板 数据层
 *
 * @author daliansea
 */
public interface SeaContentTemplateMapper
{
    public List<SeaContentTemplate> selectTemplateList(SeaContentTemplate template);

    public SeaContentTemplate selectTemplateById(Long templateId);

    public SeaContentTemplate selectDefaultTemplate(SeaContentTemplate template);

    public int insertTemplate(SeaContentTemplate template);

    public int updateTemplate(SeaContentTemplate template);

    public int deleteTemplateByIds(Long[] templateIds);
}
