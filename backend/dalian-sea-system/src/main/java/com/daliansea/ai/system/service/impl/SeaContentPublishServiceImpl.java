package com.daliansea.ai.system.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.daliansea.ai.common.exception.ServiceException;
import com.daliansea.ai.common.utils.StringUtils;
import com.daliansea.ai.system.domain.SeaAiCallLog;
import com.daliansea.ai.system.domain.SeaContentPublish;
import com.daliansea.ai.system.domain.SeaContentTemplate;
import com.daliansea.ai.system.domain.SeaVessel;
import com.daliansea.ai.system.mapper.SeaAiCallLogMapper;
import com.daliansea.ai.system.mapper.SeaContentPublishMapper;
import com.daliansea.ai.system.mapper.SeaContentTemplateMapper;
import com.daliansea.ai.system.mapper.SeaVesselMapper;
import com.daliansea.ai.system.service.ISeaContentPublishService;

/**
 * 内容发布 服务层处理
 *
 * @author daliansea
 */
@Service
public class SeaContentPublishServiceImpl implements ISeaContentPublishService
{
    private static final Long DEFAULT_TENANT_ID = 1L;

    private static final String DEFAULT_PROVIDER = "local";

    private static final String DEFAULT_MODEL = "copy-template-v1";

    @Autowired
    private SeaContentPublishMapper contentMapper;

    @Autowired
    private SeaVesselMapper vesselMapper;

    @Autowired
    private SeaContentTemplateMapper templateMapper;

    @Autowired
    private SeaAiCallLogMapper callLogMapper;

    @Override
    public List<SeaContentPublish> selectContentList(SeaContentPublish content)
    {
        return contentMapper.selectContentList(content);
    }

    @Override
    public SeaContentPublish selectContentById(Long contentId)
    {
        return contentMapper.selectContentById(contentId);
    }

    @Override
    public int insertContent(SeaContentPublish content)
    {
        normalizeContent(content);
        return contentMapper.insertContent(content);
    }

    @Override
    public int updateContent(SeaContentPublish content)
    {
        normalizeContent(content);
        return contentMapper.updateContent(content);
    }

    @Override
    public int deleteContentByIds(Long[] contentIds)
    {
        return contentMapper.deleteContentByIds(contentIds);
    }

    @Override
    @Transactional
    public SeaContentPublish generateContent(SeaContentPublish content)
    {
        long start = System.currentTimeMillis();
        normalizeContent(content);
        fillVesselInfo(content);
        content.setTitle(StringUtils.defaultIfBlank(content.getTitle(), buildTitle(content)));
        SeaContentTemplate template = selectTemplate(content);
        String prompt = buildPrompt(content, template);
        String answer = buildCopy(content, template);
        content.setContent(answer);
        content.setPublishStatus("draft");
        content.setPushStatus("pending");
        content.setAiProvider(DEFAULT_PROVIDER);
        content.setAiModel(DEFAULT_MODEL);
        contentMapper.insertContent(content);
        recordAiLog(content, prompt, answer, System.currentTimeMillis() - start);
        return contentMapper.selectContentById(content.getContentId());
    }

    @Override
    public int publishContent(Long contentId, String username)
    {
        SeaContentPublish content = contentMapper.selectContentById(contentId);
        if (StringUtils.isNull(content))
        {
            throw new ServiceException("内容不存在");
        }
        content.setPublishStatus("published");
        content.setPushStatus("success");
        content.setPushMessage("已记录为模拟推流/发布成功，待接入真实公众号、视频号或直播推流接口");
        content.setPublishTime(new Date());
        content.setUpdateBy(username);
        return contentMapper.updateContent(content);
    }

    private void normalizeContent(SeaContentPublish content)
    {
        if (content.getTenantId() == null)
        {
            content.setTenantId(DEFAULT_TENANT_ID);
        }
        content.setContentType(StringUtils.defaultIfBlank(content.getContentType(), "wechat_article"));
        content.setPublishChannel(StringUtils.defaultIfBlank(content.getPublishChannel(), "captain_wechat"));
        content.setTone(StringUtils.defaultIfBlank(content.getTone(), "专业可信"));
        content.setPublishStatus(StringUtils.defaultIfBlank(content.getPublishStatus(), "draft"));
        content.setPushStatus(StringUtils.defaultIfBlank(content.getPushStatus(), "pending"));
    }

    private void fillVesselInfo(SeaContentPublish content)
    {
        if (content.getVesselId() == null)
        {
            return;
        }
        SeaVessel vessel = vesselMapper.selectVesselById(content.getVesselId());
        if (StringUtils.isNull(vessel))
        {
            throw new ServiceException("关联船舶不存在");
        }
        content.setTenantId(vessel.getTenantId());
        content.setVesselName(vessel.getVesselName());
        content.setCaptainName(StringUtils.defaultIfBlank(content.getCaptainName(), vessel.getCaptainName()));
        content.setCaptainPhone(StringUtils.defaultIfBlank(content.getCaptainPhone(), vessel.getCaptainPhone()));
        if (StringUtils.isBlank(content.getHighlights()))
        {
            content.setHighlights(buildVesselHighlights(vessel));
        }
    }

    private String buildVesselHighlights(SeaVessel vessel)
    {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(vessel.getHomePort()))
        {
            builder.append("母港").append(vessel.getHomePort()).append("；");
        }
        if (StringUtils.isNotBlank(vessel.getServiceArea()))
        {
            builder.append("服务区域").append(vessel.getServiceArea()).append("；");
        }
        if (vessel.getCapacity() != null)
        {
            builder.append("载客").append(vessel.getCapacity()).append("人；");
        }
        return builder.length() > 0 ? builder.toString() : "船舶资料完善、服务稳定、安全规范";
    }

    private String buildTitle(SeaContentPublish content)
    {
        String vesselName = StringUtils.defaultIfBlank(content.getVesselName(), "船长服务");
        return vesselName + "｜" + content.getTopic();
    }

    private SeaContentTemplate selectTemplate(SeaContentPublish content)
    {
        SeaContentTemplate query = new SeaContentTemplate();
        query.setTenantId(content.getTenantId());
        query.setContentType(content.getContentType());
        query.setPublishChannel(content.getPublishChannel());
        return templateMapper.selectDefaultTemplate(query);
    }

    private String buildPrompt(SeaContentPublish content, SeaContentTemplate template)
    {
        if (StringUtils.isNotNull(template))
        {
            return renderTemplate(template.getPromptTemplate(), content);
        }
        return "请生成" + content.getTopic() + "相关文案，类型：" + content.getContentType()
                + "，渠道：" + content.getPublishChannel()
                + "，语气：" + content.getTone()
                + "，亮点：" + StringUtils.defaultString(content.getHighlights());
    }

    private String buildCopy(SeaContentPublish content, SeaContentTemplate template)
    {
        if (StringUtils.isNotNull(template))
        {
            return renderTemplate(template.getPromptTemplate(), content);
        }
        String type = content.getContentType();
        if ("moments".equals(type))
        {
            return buildMomentsCopy(content);
        }
        if ("short_video_script".equals(type))
        {
            return buildVideoScript(content);
        }
        if ("live_stream_script".equals(type))
        {
            return buildLiveStreamScript(content);
        }
        return buildWechatArticle(content);
    }

    private String renderTemplate(String template, SeaContentPublish content)
    {
        return StringUtils.defaultString(template)
                .replace("{title}", StringUtils.defaultString(content.getTitle()))
                .replace("{topic}", StringUtils.defaultString(content.getTopic()))
                .replace("{vesselName}", StringUtils.defaultIfBlank(content.getVesselName(), "船舶"))
                .replace("{captainName}", StringUtils.defaultIfBlank(content.getCaptainName(), "船长"))
                .replace("{captainPhone}", StringUtils.defaultString(content.getCaptainPhone()))
                .replace("{highlights}", StringUtils.defaultIfBlank(content.getHighlights(), "安全、准点、服务细致"))
                .replace("{tone}", StringUtils.defaultIfBlank(content.getTone(), "专业可信"))
                .replace("{contentType}", StringUtils.defaultString(content.getContentType()))
                .replace("{publishChannel}", StringUtils.defaultString(content.getPublishChannel()));
    }

    private void recordAiLog(SeaContentPublish content, String prompt, String answer, long latencyMs)
    {
        SeaAiCallLog log = new SeaAiCallLog();
        log.setTenantId(content.getTenantId());
        log.setScene("content_copy");
        log.setQuestion(StringUtils.substring(prompt, 0, 1000));
        log.setAnswer(StringUtils.substring(answer, 0, 2000));
        log.setProviderName(DEFAULT_PROVIDER);
        log.setModelName(DEFAULT_MODEL);
        log.setRequestTokens(estimateTokens(prompt));
        log.setResponseTokens(estimateTokens(answer));
        log.setTotalTokens(log.getRequestTokens() + log.getResponseTokens());
        log.setLatencyMs(latencyMs);
        log.setSuccessFlag("0");
        log.setCreateBy(content.getCreateBy());
        callLogMapper.insertCallLog(log);
    }

    private int estimateTokens(String text)
    {
        return Math.max(1, StringUtils.defaultString(text).length() / 2);
    }

    private String buildWechatArticle(SeaContentPublish content)
    {
        String vessel = StringUtils.defaultIfBlank(content.getVesselName(), "我们的船舶");
        String captain = StringUtils.defaultIfBlank(content.getCaptainName(), "船长");
        String highlights = StringUtils.defaultIfBlank(content.getHighlights(), "安全、准点、服务细致");
        return "【" + content.getTitle() + "】\n\n"
                + "今天想和大家介绍" + vessel + "的最新服务安排。\n\n"
                + "这次主题是“" + content.getTopic() + "”。我们会继续把安全检查、航前准备、船员协同和游客体验放在前面，确保每一次出航都有清晰流程和可靠保障。\n\n"
                + "服务亮点：\n"
                + "1. " + highlights + "\n"
                + "2. 船长" + captain + "负责航前确认，重点关注天气、航线、载客和设备状态。\n"
                + "3. 如遇临时海况变化，将第一时间调整计划并同步通知。\n\n"
                + "欢迎通过公众号咨询预约，也欢迎关注后续出航动态。";
    }

    private String buildMomentsCopy(SeaContentPublish content)
    {
        String vessel = StringUtils.defaultIfBlank(content.getVesselName(), "船舶");
        return vessel + "今日更新： " + content.getTopic() + "\n"
                + StringUtils.defaultIfBlank(content.getHighlights(), "航前检查完成，服务准备就绪。") + "\n"
                + "安全出航、规范服务，欢迎咨询预约。";
    }

    private String buildVideoScript(SeaContentPublish content)
    {
        String vessel = StringUtils.defaultIfBlank(content.getVesselName(), "船舶");
        return "短视频脚本｜" + content.getTopic() + "\n\n"
                + "开场 3 秒：镜头扫过" + vessel + "，字幕“今天带你看一次标准出航准备”。\n"
                + "中段 15 秒：展示安全检查、设备确认、航线沟通和服务区域。\n"
                + "重点口播：" + StringUtils.defaultIfBlank(content.getHighlights(), "安全是第一位，体验也要稳定。") + "\n"
                + "结尾 5 秒：公众号预约提示，提醒关注出航时间和天气变化。";
    }

    private String buildLiveStreamScript(SeaContentPublish content)
    {
        String vessel = StringUtils.defaultIfBlank(content.getVesselName(), "船舶");
        return "直播推流脚本｜" + content.getTopic() + "\n\n"
                + "1. 开场：介绍" + vessel + "、今日海况和直播主题。\n"
                + "2. 船舶展示：讲解甲板、救生设备、服务区域和乘船注意事项。\n"
                + "3. 互动答疑：回答预约、航线、证照、安全措施等问题。\n"
                + "4. 收尾引导：提醒关注公众号，后续推送出航计划和活动文案。\n\n"
                + "推流备注：" + StringUtils.defaultIfBlank(content.getHighlights(), "推流前确认网络、电量、收音和安全边界。");
    }
}
