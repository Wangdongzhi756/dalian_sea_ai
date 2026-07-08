package com.daliansea.ai.system.mapper;

import java.util.List;
import com.daliansea.ai.system.domain.SeaContentPublish;

/**
 * 内容发布 数据层
 *
 * @author daliansea
 */
public interface SeaContentPublishMapper
{
    public List<SeaContentPublish> selectContentList(SeaContentPublish content);

    public SeaContentPublish selectContentById(Long contentId);

    public int insertContent(SeaContentPublish content);

    public int updateContent(SeaContentPublish content);

    public int deleteContentByIds(Long[] contentIds);
}
