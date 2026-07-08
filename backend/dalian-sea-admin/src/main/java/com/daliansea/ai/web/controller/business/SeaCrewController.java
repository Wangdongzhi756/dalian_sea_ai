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
import com.daliansea.ai.system.domain.SeaCrew;
import com.daliansea.ai.system.service.ISeaCrewService;

/**
 * 船员档案操作处理
 *
 * @author daliansea
 */
@RestController
@RequestMapping("/business/crew")
public class SeaCrewController extends BaseController
{
    @Autowired
    private ISeaCrewService crewService;

    @PreAuthorize("@ss.hasPermi('business:crew:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeaCrew crew)
    {
        startPage();
        List<SeaCrew> list = crewService.selectCrewList(crew);
        return getDataTable(list);
    }

    @Log(title = "船员管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('business:crew:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SeaCrew crew)
    {
        List<SeaCrew> list = crewService.selectCrewList(crew);
        ExcelUtil<SeaCrew> util = new ExcelUtil<SeaCrew>(SeaCrew.class);
        util.exportExcel(response, list, "船员数据");
    }

    @PreAuthorize("@ss.hasPermi('business:crew:query')")
    @GetMapping(value = "/{crewId}")
    public AjaxResult getInfo(@PathVariable Long crewId)
    {
        return success(crewService.selectCrewById(crewId));
    }

    @PreAuthorize("@ss.hasPermi('business:crew:add')")
    @Log(title = "船员管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SeaCrew crew)
    {
        if (!crewService.checkCrewCodeUnique(crew))
        {
            return error("新增船员'" + crew.getCrewName() + "'失败，船员编码已存在");
        }
        crew.setCreateBy(getUsername());
        return toAjax(crewService.insertCrew(crew));
    }

    @PreAuthorize("@ss.hasPermi('business:crew:edit')")
    @Log(title = "船员管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SeaCrew crew)
    {
        if (!crewService.checkCrewCodeUnique(crew))
        {
            return error("修改船员'" + crew.getCrewName() + "'失败，船员编码已存在");
        }
        crew.setUpdateBy(getUsername());
        return toAjax(crewService.updateCrew(crew));
    }

    @PreAuthorize("@ss.hasPermi('business:crew:remove')")
    @Log(title = "船员管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{crewIds}")
    public AjaxResult remove(@PathVariable Long[] crewIds)
    {
        return toAjax(crewService.deleteCrewByIds(crewIds));
    }
}
