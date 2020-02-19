package cn.wind.controller;

import cn.wind.config.Constants;
import cn.wind.config.JsonConfig;
import cn.wind.config.PageSupport;
import cn.wind.pojo.Bill;
import cn.wind.pojo.Provider;
import cn.wind.pojo.User;
import cn.wind.service.bill.BillService;
import cn.wind.service.provider.ProviderService;
import com.mysql.jdbc.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Wind
 * @see 2020-02-19 18:38
 */
@Controller
@RequestMapping("/bill")
public class BillController {
    private Logger logger = Logger.getLogger(BillController.class);

    @Resource
    private BillService billService;
    @Resource
    private ProviderService providerService;

    @RequestMapping(value="/list")
    public String getBillList(Model model, String queryProductName, String queryProviderId, String queryIsPayment, String pageIndex){
        Integer _queryProviderId = null;
        Integer _queryIsPayment = null;
        List<Bill> billList = null;
        List<Provider> providerList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;

        if(queryProductName == null){
            queryProductName = "";
        }
        if(queryProviderId != null && !queryProviderId.equals("")){
            _queryProviderId = Integer.parseInt(queryProviderId);
        }
        if(queryIsPayment != null && !queryIsPayment.equals("")){
            _queryIsPayment = Integer.parseInt(queryIsPayment);
        }

        if(pageIndex != null){
            try{
                currentPageNo = Integer.valueOf(pageIndex);
            }catch(NumberFormatException e){
                return "redirect:/syserror";
            }
        }
        //总数量（表）
        int totalCount = 0;
        try {
            totalCount = billService.findBillCount(queryProductName, _queryProviderId, _queryIsPayment);
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
            billList = billService.findBillList(queryProductName, _queryProviderId, _queryIsPayment, currentPageNo, pageSize);
            providerList = providerService.findProviderList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("billList", billList);
        model.addAttribute("providerList", providerList);
        model.addAttribute("queryProductName", queryProductName);
        model.addAttribute("queryProviderId", queryProviderId);
        model.addAttribute("queryIsPayment", queryIsPayment);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "bill/billlist";
    }

    @GetMapping(value="/add")
    public String addBill(@ModelAttribute("bill") BillController bill){
        return "billadd";
    }

    @PostMapping(value="/addSave")
    public String addBillSave(Bill bill, HttpSession session){
        bill.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        bill.setCreationDate(new Date());
        try {
            if(billService.addBill(bill)){
                return "redirect:/bill/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bill/billadd";
    }

    @GetMapping(value="/update/{id}")
    public String getBillById(@PathVariable String id,Model model,HttpServletRequest request){
        Bill bill = new Bill();
        try {
            bill = billService.findBillById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        model.addAttribute(bill);
        return "bill/billmodify";
    }

    @PostMapping(value="/updateSave")
    public String modifyBillSave(Bill bill, HttpSession session){
        bill.setModifyBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        bill.setModifyDate(new Date());
        try {
            if(billService.updateBill(bill)){
                return "redirect:/bill/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bill/billmodify";
    }

    @GetMapping(value="/providerList")
    @ResponseBody
    public List<Provider> getProviderList(){
        List<Provider> providerList = null;
        try {
            providerList = providerService.findProviderList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("providerList size: " + providerList.size());
        return providerList;
    }

    @GetMapping(value="/delBill")
    @ResponseBody
    public Object delBill(@RequestParam String id){
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(id)){
            resultMap.put("delResult", "notexist");
        }else{
            try {
                if(billService.deleteBillById(Integer.parseInt(id)))
                    resultMap.put("delResult", "true");
                else
                    resultMap.put("delResult", "false");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return JsonConfig.getJson(resultMap);
    }

    @GetMapping(value="/view/{id}")
    public String view(@PathVariable String id, Model model, HttpServletRequest request){
        logger.debug("view id===================== "+id);
        Bill bill = new Bill();
        try {
            bill = billService.findBillById(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        model.addAttribute(bill);
        return "bill/billview";
    }
}
