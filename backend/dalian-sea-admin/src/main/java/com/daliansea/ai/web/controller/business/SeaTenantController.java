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
import com.daliansea.ai.common.utils.poi.ExcelUtil;
import com.daliansea.ai.system.domain.SeaTenant;
import com.daliansea.ai.system.service.ISeaTenantService;

/**
 * 租户主体操作处理
 *
 * @author daliansea
 */
@RestController
@RequestMapping("/business/tenant")
public class SeaTenantController extends BaseController
{
    @Autowired
    private ISeaTenantService tenantService;

    @PreAuthorize("@ss.hasPermi('business:tenant:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeaTenant tenant)
    {
        startPage();
        List<SeaTenant> list = tenantService.selectTenantList(tenant);
        return getDataTable(list);
    }

    @Log(title = "租户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('business:tenant:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SeaTenant tenant)
    {
        List<SeaTenant> list = tenantService.selectTenantList(tenant);
        ExcelUtil<SeaTenant> util = new ExcelUtil<SeaTenant>(SeaTenant.class);
        util.exportExcel(response, list, "租户数据");
    }

    @PreAuthorize("@ss.hasPermi('business:tenant:query')")
    @GetMapping(value = "/{tenantId}")
    public AjaxResult getInfo(@PathVariable Long tenantId)
    {
        return success(tenantService.selectTenantById(tenantId));
    }

    @GetMapping("/optionselect")
    public AjaxResult optionselect()
    {
        return success(tenantService.selectTenantOptions());
    }

    @PreAuthorize("@ss.hasPermi('business:tenant:add')")
    @Log(title = "租户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SeaTenant tenant)
    {
        if (!tenantService.checkTenantCodeUnique(tenant))
        {
            return error("新增租户'" + tenant.getTenantName() + "'失败，租户编码已存在");
        }
        tenant.setCreateBy(getUsername());
        return toAjax(tenantService.insertTenant(tenant));
    }

    @PreAuthorize("@ss.hasPermi('business:tenant:edit')")
    @Log(title = "租户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SeaTenant tenant)
    {
        if (!tenantService.checkTenantCodeUnique(tenant))
        {
            return error("修改租户'" + tenant.getTenantName() + "'失败，租户编码已存在");
        }
        tenant.setUpdateBy(getUsername());
        return toAjax(tenantService.updateTenant(tenant));
    }

    @PreAuthorize("@ss.hasPermi('business:tenant:remove')")
    @Log(title = "租户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tenantIds}")
    public AjaxResult remove(@PathVariable Long[] tenantIds)
    {
        return toAjax(tenantService.deleteTenantByIds(tenantIds));
    }
}
