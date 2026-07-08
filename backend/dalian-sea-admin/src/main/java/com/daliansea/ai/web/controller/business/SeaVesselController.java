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
import com.daliansea.ai.system.domain.SeaVessel;
import com.daliansea.ai.system.service.ISeaVesselService;

/**
 * 船舶档案操作处理
 *
 * @author daliansea
 */
@RestController
@RequestMapping("/business/vessel")
public class SeaVesselController extends BaseController
{
    @Autowired
    private ISeaVesselService vesselService;

    @PreAuthorize("@ss.hasPermi('business:vessel:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeaVessel vessel)
    {
        startPage();
        List<SeaVessel> list = vesselService.selectVesselList(vessel);
        return getDataTable(list);
    }

    @Log(title = "船舶管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('business:vessel:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SeaVessel vessel)
    {
        List<SeaVessel> list = vesselService.selectVesselList(vessel);
        ExcelUtil<SeaVessel> util = new ExcelUtil<SeaVessel>(SeaVessel.class);
        util.exportExcel(response, list, "船舶数据");
    }

    @PreAuthorize("@ss.hasPermi('business:vessel:query')")
    @GetMapping(value = "/{vesselId}")
    public AjaxResult getInfo(@PathVariable Long vesselId)
    {
        return success(vesselService.selectVesselById(vesselId));
    }

    @PreAuthorize("@ss.hasPermi('business:vessel:add')")
    @Log(title = "船舶管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SeaVessel vessel)
    {
        if (!vesselService.checkVesselCodeUnique(vessel))
        {
            return error("新增船舶'" + vessel.getVesselName() + "'失败，船舶编码已存在");
        }
        vessel.setCreateBy(getUsername());
        return toAjax(vesselService.insertVessel(vessel));
    }

    @PreAuthorize("@ss.hasPermi('business:vessel:edit')")
    @Log(title = "船舶管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SeaVessel vessel)
    {
        if (!vesselService.checkVesselCodeUnique(vessel))
        {
            return error("修改船舶'" + vessel.getVesselName() + "'失败，船舶编码已存在");
        }
        vessel.setUpdateBy(getUsername());
        return toAjax(vesselService.updateVessel(vessel));
    }

    @PreAuthorize("@ss.hasPermi('business:vessel:remove')")
    @Log(title = "船舶管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{vesselIds}")
    public AjaxResult remove(@PathVariable Long[] vesselIds)
    {
        return toAjax(vesselService.deleteVesselByIds(vesselIds));
    }
}
