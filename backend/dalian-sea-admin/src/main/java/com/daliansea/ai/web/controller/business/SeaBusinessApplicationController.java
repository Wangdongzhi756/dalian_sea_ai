package com.daliansea.ai.web.controller.business;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.daliansea.ai.common.annotation.Log;
import com.daliansea.ai.common.core.controller.BaseController;
import com.daliansea.ai.common.core.domain.AjaxResult;
import com.daliansea.ai.common.core.page.TableDataInfo;
import com.daliansea.ai.common.enums.BusinessType;
import com.daliansea.ai.common.utils.StringUtils;
import com.daliansea.ai.common.utils.poi.ExcelUtil;
import com.daliansea.ai.system.domain.SeaBusinessApplication;
import com.daliansea.ai.system.domain.SeaBusinessNotice;
import com.daliansea.ai.system.domain.SeaBusinessProgress;
import com.daliansea.ai.system.service.ISeaBusinessApplicationService;

/**
 * 业务申请操作处理
 *
 * @author daliansea
 */
@RestController
@RequestMapping("/business/application")
public class SeaBusinessApplicationController extends BaseController
{
    @Autowired
    private ISeaBusinessApplicationService applicationService;

    @PreAuthorize("@ss.hasPermi('business:application:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeaBusinessApplication application)
    {
        startPage();
        List<SeaBusinessApplication> list = applicationService.selectApplicationList(application);
        return getDataTable(list);
    }

    @Log(title = "业务申请", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('business:application:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SeaBusinessApplication application)
    {
        List<SeaBusinessApplication> list = applicationService.selectApplicationList(application);
        ExcelUtil<SeaBusinessApplication> util = new ExcelUtil<SeaBusinessApplication>(SeaBusinessApplication.class);
        util.exportExcel(response, list, "业务申请数据");
    }

    @PreAuthorize("@ss.hasPermi('business:application:query')")
    @GetMapping(value = "/{applicationId}")
    public AjaxResult getInfo(@PathVariable Long applicationId)
    {
        return success(applicationService.selectApplicationById(applicationId));
    }

    @PreAuthorize("@ss.hasPermi('business:application:query')")
    @GetMapping(value = "/{applicationId}/progress")
    public AjaxResult progress(@PathVariable Long applicationId)
    {
        List<SeaBusinessProgress> list = applicationService.selectProgressList(applicationId);
        return success(list);
    }

    @PreAuthorize("@ss.hasPermi('business:application:list')")
    @GetMapping("/notice/list")
    public TableDataInfo noticeList(SeaBusinessNotice notice)
    {
        startPage();
        List<SeaBusinessNotice> list = applicationService.selectNoticeList(notice);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('business:application:edit')")
    @PutMapping("/notice/{noticeIds}/read")
    public AjaxResult readNotice(@PathVariable Long[] noticeIds)
    {
        return toAjax(applicationService.readNoticeByIds(noticeIds));
    }

    @PreAuthorize("@ss.hasPermi('business:application:add')")
    @Log(title = "业务申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SeaBusinessApplication application)
    {
        if (StringUtils.isNotBlank(application.getApplicationNo()) && !applicationService.checkApplicationNoUnique(application))
        {
            return error("新增业务申请失败，申请单号已存在");
        }
        application.setCreateBy(getUsername());
        return toAjax(applicationService.insertApplication(application));
    }

    @PreAuthorize("@ss.hasPermi('business:application:edit')")
    @Log(title = "业务申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SeaBusinessApplication application)
    {
        if (StringUtils.isNotBlank(application.getApplicationNo()) && !applicationService.checkApplicationNoUnique(application))
        {
            return error("修改业务申请失败，申请单号已存在");
        }
        application.setUpdateBy(getUsername());
        return toAjax(applicationService.updateApplication(application));
    }

    @PreAuthorize("@ss.hasPermi('business:application:audit')")
    @Log(title = "业务申请审核", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody SeaBusinessApplication application)
    {
        application.setAuditBy(getUsername());
        application.setUpdateBy(getUsername());
        return toAjax(applicationService.auditApplication(application));
    }

    @PreAuthorize("@ss.hasPermi('business:application:remove')")
    @Log(title = "业务申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{applicationIds}")
    public AjaxResult remove(@PathVariable Long[] applicationIds)
    {
        return toAjax(applicationService.deleteApplicationByIds(applicationIds));
    }
}
