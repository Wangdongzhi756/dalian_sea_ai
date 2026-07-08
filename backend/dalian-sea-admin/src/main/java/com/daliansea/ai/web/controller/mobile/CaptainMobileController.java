package com.daliansea.ai.web.controller.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.daliansea.ai.common.core.controller.BaseController;
import com.daliansea.ai.common.core.domain.AjaxResult;
import com.daliansea.ai.common.core.domain.entity.SysUser;
import com.daliansea.ai.common.exception.ServiceException;
import com.daliansea.ai.common.utils.SecurityUtils;
import com.daliansea.ai.common.utils.StringUtils;
import com.daliansea.ai.system.domain.SeaContentPublish;
import com.daliansea.ai.system.domain.SeaVessel;
import com.daliansea.ai.system.service.ISeaContentPublishService;
import com.daliansea.ai.system.service.ISeaVesselService;

/**
 * 船长移动端操作处理
 *
 * @author daliansea
 */
@RestController
@RequestMapping("/mobile/captain")
public class CaptainMobileController extends BaseController
{
    @Autowired
    private ISeaVesselService vesselService;

    @Autowired
    private ISeaContentPublishService contentService;

    @PreAuthorize("@ss.hasPermi('business:vessel:list')")
    @GetMapping("/vessel/list")
    public AjaxResult vesselList()
    {
        SeaVessel query = new SeaVessel();
        query.setCaptainPhone(currentPhone());
        return success(vesselService.selectVesselList(query));
    }

    @PreAuthorize("@ss.hasPermi('business:vessel:query')")
    @GetMapping("/vessel/{vesselId}")
    public AjaxResult vesselInfo(@PathVariable Long vesselId)
    {
        SeaVessel vessel = checkCaptainVessel(vesselId);
        return success(vessel);
    }

    @PreAuthorize("@ss.hasPermi('business:vessel:edit')")
    @PutMapping("/vessel")
    public AjaxResult updateVessel(@RequestBody SeaVessel vessel)
    {
        SeaVessel old = checkCaptainVessel(vessel.getVesselId());
        vessel.setTenantId(old.getTenantId());
        vessel.setVesselCode(old.getVesselCode());
        vessel.setCaptainPhone(old.getCaptainPhone());
        vessel.setUpdateBy(getUsername());
        return toAjax(vesselService.updateVessel(vessel));
    }

    @PreAuthorize("@ss.hasPermi('business:content:list')")
    @GetMapping("/content/list")
    public AjaxResult contentList()
    {
        SeaContentPublish query = new SeaContentPublish();
        query.setCaptainPhone(currentPhone());
        return success(contentService.selectContentList(query));
    }

    @PreAuthorize("@ss.hasPermi('business:content:generate')")
    @PostMapping("/content/generate")
    public AjaxResult generateContent(@Validated @RequestBody SeaContentPublish content)
    {
        if (content.getVesselId() != null)
        {
            checkCaptainVessel(content.getVesselId());
        }
        SysUser user = getLoginUser().getUser();
        content.setCaptainName(StringUtils.defaultIfBlank(content.getCaptainName(), user.getNickName()));
        content.setCaptainPhone(currentPhone());
        content.setCreateBy(getUsername());
        return success(contentService.generateContent(content));
    }

    @PreAuthorize("@ss.hasPermi('business:content:edit')")
    @PutMapping("/content")
    public AjaxResult updateContent(@RequestBody SeaContentPublish content)
    {
        SeaContentPublish old = checkCaptainContent(content.getContentId());
        if ("published".equals(old.getPublishStatus()))
        {
            return error("已发布文案不能在船长端修改");
        }
        content.setTenantId(old.getTenantId());
        content.setVesselId(old.getVesselId());
        content.setCaptainName(old.getCaptainName());
        content.setCaptainPhone(old.getCaptainPhone());
        content.setPublishStatus(old.getPublishStatus());
        content.setPushStatus(old.getPushStatus());
        content.setAiProvider(old.getAiProvider());
        content.setAiModel(old.getAiModel());
        content.setUpdateBy(getUsername());
        return toAjax(contentService.updateContent(content));
    }

    @PreAuthorize("@ss.hasPermi('business:content:publish')")
    @PutMapping("/content/{contentId}/publish")
    public AjaxResult publishContent(@PathVariable Long contentId)
    {
        checkCaptainContent(contentId);
        return toAjax(contentService.publishContent(contentId, getUsername()));
    }

    private SeaVessel checkCaptainVessel(Long vesselId)
    {
        SeaVessel vessel = vesselService.selectVesselById(vesselId);
        if (StringUtils.isNull(vessel))
        {
            throw new ServiceException("船舶不存在");
        }
        if (!SecurityUtils.isAdmin() && !StringUtils.equals(vessel.getCaptainPhone(), currentPhone()))
        {
            throw new ServiceException("只能管理当前船长自己的船舶");
        }
        return vessel;
    }

    private SeaContentPublish checkCaptainContent(Long contentId)
    {
        SeaContentPublish content = contentService.selectContentById(contentId);
        if (StringUtils.isNull(content) || !StringUtils.equals(content.getCaptainPhone(), currentPhone()))
        {
            throw new ServiceException("只能管理当前船长自己的文案");
        }
        return content;
    }

    private String currentPhone()
    {
        SysUser user = getLoginUser().getUser();
        if (StringUtils.isNull(user) || StringUtils.isBlank(user.getPhonenumber()))
        {
            throw new ServiceException("当前账号未维护手机号，无法匹配船长船舶");
        }
        return user.getPhonenumber();
    }
}
