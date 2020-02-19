package cn.wind.controller;

import cn.wind.config.Constants;
import cn.wind.config.JsonConfig;
import cn.wind.config.PageSupport;
import cn.wind.pojo.Provider;
import cn.wind.service.provider.ProviderService;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * @author Wind
 * @see 2020-02-19 18:25
 */
@Controller
@RequestMapping("/provider")
public class ProviderController {
    private Logger logger = Logger.getLogger(ProviderController.class);

    @Autowired
    private ProviderService providerService;

    @RequestMapping(value="/list")
    public String getProviderList(Model model, String queryProCode, String queryProName, String pageIndex){
        List<Provider> providerList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;

        if(queryProCode == null){
            queryProCode = "";
        }
        if(queryProName == null){
            queryProName = "";
        }
        if(pageIndex != null){
            try{
                currentPageNo = Integer.valueOf(pageIndex);
            }catch(NumberFormatException e){
                return "redirect:/provider/sysError";
            }
        }
        //总数量（表）
        int totalCount = 0;
        try {
            totalCount = providerService.findProviderCount(queryProCode,queryProName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //总页数
        PageSupport pages=new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);
        int totalPageCount = pages.getTotalPageCount();
        //控制首页和尾页
        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }
        try {
            providerList = providerService.findProviderList(queryProName,queryProCode,currentPageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("providerList", providerList);
        model.addAttribute("queryProCode", queryProCode);
        model.addAttribute("queryProName", queryProName);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "provider/providerlist";
    }

    @RequestMapping(value="/sysError")
    public String sysError(){
        return "syserror";
    }

    @GetMapping(value="/add")
    public String add(@ModelAttribute("provider") Provider provider){
        return "provider/provideradd";
    }

    @GetMapping(value="/view/{id}")
    public String view(@PathVariable String id, Model model, HttpServletRequest request){
        logger.debug("view id===================== "+id);
        Provider provider = new Provider();
        try {
            provider = providerService.findProviderById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        model.addAttribute(provider);
        return "provider/providerview";
    }

    @GetMapping(value="/update/{id}")
    public String getProviderById(@PathVariable String id,Model model,HttpServletRequest request){
        logger.debug("getProviderById id===================== "+id);
        Provider provider = new Provider();
        try {
            provider = providerService.findProviderById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        model.addAttribute(provider);
        return "provider/providermodify";
    }

    @PostMapping(value="/updateSave")
    public String modifyProviderSave(Provider provider, HttpSession session, HttpServletRequest request, MultipartFile[] multipartFiles){
        return "provider/providermodify";
    }

    @PostMapping(value="/addSave")
    public String addProviderSave(Provider provider,HttpSession session,HttpServletRequest request, MultipartFile[] multipartFiles){
        return "provider/provideradd";
    }

    @PostMapping(value="/del")
    @ResponseBody
    public Object delProviderById(@RequestParam(value="proId") String id){
        logger.debug("delProviderById proid ===================== "+id);
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(!StringUtils.isNullOrEmpty(id)){
            boolean flag = false;
            try {
                flag = providerService.deleteProviderById(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(flag){//删除成功
                resultMap.put("delResult", "true");
            }else{//删除失败
                resultMap.put("delResult", "false");
            }
        }else{
            resultMap.put("delResult", "notexit");
        }
        return JsonConfig.getJson(resultMap);
    }
}
