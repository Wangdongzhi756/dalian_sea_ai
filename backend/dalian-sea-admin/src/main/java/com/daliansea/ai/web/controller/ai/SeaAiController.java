package com.daliansea.ai.web.controller.ai;

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
import com.daliansea.ai.system.domain.SeaAiCallLog;
import com.daliansea.ai.system.domain.SeaAiChatRequest;
import com.daliansea.ai.system.domain.SeaAiKnowledge;
import com.daliansea.ai.system.service.ISeaAiService;

/**
 * AI 能力操作处理
 *
 * @author daliansea
 */
@RestController
@RequestMapping("/ai")
public class SeaAiController extends BaseController
{
    @Autowired
    private ISeaAiService aiService;

    @PreAuthorize("@ss.hasPermi('ai:knowledge:list')")
    @GetMapping("/knowledge/list")
    public TableDataInfo knowledgeList(SeaAiKnowledge knowledge)
    {
        startPage();
        List<SeaAiKnowledge> list = aiService.selectKnowledgeList(knowledge);
        return getDataTable(list);
    }

    @Log(title = "AI知识库", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('ai:knowledge:export')")
    @PostMapping("/knowledge/export")
    public void knowledgeExport(HttpServletResponse response, SeaAiKnowledge knowledge)
    {
        List<SeaAiKnowledge> list = aiService.selectKnowledgeList(knowledge);
        ExcelUtil<SeaAiKnowledge> util = new ExcelUtil<SeaAiKnowledge>(SeaAiKnowledge.class);
        util.exportExcel(response, list, "AI知识库数据");
    }

    @PreAuthorize("@ss.hasPermi('ai:knowledge:query')")
    @GetMapping("/knowledge/{knowledgeId}")
    public AjaxResult getKnowledge(@PathVariable Long knowledgeId)
    {
        return success(aiService.selectKnowledgeById(knowledgeId));
    }

    @PreAuthorize("@ss.hasPermi('ai:knowledge:add')")
    @Log(title = "AI知识库", businessType = BusinessType.INSERT)
    @PostMapping("/knowledge")
    public AjaxResult addKnowledge(@Validated @RequestBody SeaAiKnowledge knowledge)
    {
        knowledge.setCreateBy(getUsername());
        return toAjax(aiService.insertKnowledge(knowledge));
    }

    @PreAuthorize("@ss.hasPermi('ai:knowledge:edit')")
    @Log(title = "AI知识库", businessType = BusinessType.UPDATE)
    @PutMapping("/knowledge")
    public AjaxResult editKnowledge(@Validated @RequestBody SeaAiKnowledge knowledge)
    {
        knowledge.setUpdateBy(getUsername());
        return toAjax(aiService.updateKnowledge(knowledge));
    }

    @PreAuthorize("@ss.hasPermi('ai:knowledge:remove')")
    @Log(title = "AI知识库", businessType = BusinessType.DELETE)
    @DeleteMapping("/knowledge/{knowledgeIds}")
    public AjaxResult removeKnowledge(@PathVariable Long[] knowledgeIds)
    {
        return toAjax(aiService.deleteKnowledgeByIds(knowledgeIds));
    }

    @PreAuthorize("@ss.hasPermi('ai:callLog:list')")
    @GetMapping("/callLog/list")
    public TableDataInfo callLogList(SeaAiCallLog callLog)
    {
        startPage();
        List<SeaAiCallLog> list = aiService.selectCallLogList(callLog);
        return getDataTable(list);
    }

    @Log(title = "AI调用日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('ai:callLog:export')")
    @PostMapping("/callLog/export")
    public void callLogExport(HttpServletResponse response, SeaAiCallLog callLog)
    {
        List<SeaAiCallLog> list = aiService.selectCallLogList(callLog);
        ExcelUtil<SeaAiCallLog> util = new ExcelUtil<SeaAiCallLog>(SeaAiCallLog.class);
        util.exportExcel(response, list, "AI调用日志数据");
    }

    @PreAuthorize("@ss.hasPermi('ai:callLog:query')")
    @GetMapping("/callLog/{logId}")
    public AjaxResult getCallLog(@PathVariable Long logId)
    {
        return success(aiService.selectCallLogById(logId));
    }

    @PreAuthorize("@ss.hasPermi('ai:callLog:remove')")
    @Log(title = "AI调用日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/callLog/{logIds}")
    public AjaxResult removeCallLog(@PathVariable Long[] logIds)
    {
        return toAjax(aiService.deleteCallLogByIds(logIds));
    }

    @PreAuthorize("@ss.hasPermi('ai:assistant:ask')")
    @Log(title = "AI助手问答", businessType = BusinessType.OTHER)
    @PostMapping("/assistant/ask")
    public AjaxResult ask(@Validated @RequestBody SeaAiChatRequest request)
    {
        request.setCreateBy(getUsername());
        return success(aiService.ask(request));
    }
}
