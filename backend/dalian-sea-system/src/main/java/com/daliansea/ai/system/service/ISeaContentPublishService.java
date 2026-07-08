package com.daliansea.ai.system.service;

import java.util.List;
import com.daliansea.ai.system.domain.SeaContentPublish;

/**
 * 内容发布 服务层
 *
 * @author daliansea
 */
public interface ISeaContentPublishService
{
    public List<SeaContentPublish> selectContentList(SeaContentPublish content);

    public SeaContentPublish selectContentById(Long contentId);

    public int insertContent(SeaContentPublish content);

    public int updateContent(SeaContentPublish content);

    public int deleteContentByIds(Long[] contentIds);

    public SeaContentPublish generateContent(SeaContentPublish content);

    public int publishContent(Long contentId, String username);
}
