package com.daliansea.ai.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daliansea.ai.common.utils.StringUtils;
import com.daliansea.ai.system.domain.SeaContentTemplate;
import com.daliansea.ai.system.mapper.SeaContentTemplateMapper;
import com.daliansea.ai.system.service.ISeaContentTemplateService;

/**
 * 文案模板 服务层处理
 *
 * @author daliansea
 */
@Service
public class SeaContentTemplateServiceImpl implements ISeaContentTemplateService
{
    @Autowired
    private SeaContentTemplateMapper templateMapper;

    @Override
    public List<SeaContentTemplate> selectTemplateList(SeaContentTemplate template)
    {
        return templateMapper.selectTemplateList(template);
    }

    @Override
    public SeaContentTemplate selectTemplateById(Long templateId)
    {
        return templateMapper.selectTemplateById(templateId);
    }

    @Override
    public int insertTemplate(SeaContentTemplate template)
    {
        normalizeTemplate(template);
        return templateMapper.insertTemplate(template);
    }

    @Override
    public int updateTemplate(SeaContentTemplate template)
    {
        normalizeTemplate(template);
        return templateMapper.updateTemplate(template);
    }

    @Override
    public int deleteTemplateByIds(Long[] templateIds)
    {
        return templateMapper.deleteTemplateByIds(templateIds);
    }

    private void normalizeTemplate(SeaContentTemplate template)
    {
        template.setStatus(StringUtils.defaultIfBlank(template.getStatus(), "0"));
        if (template.getSortOrder() == null)
        {
            template.setSortOrder(0);
        }
    }
}
