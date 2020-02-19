package cn.wind.service.bill;

import cn.wind.pojo.Bill;

import java.util.List;

/**
 * @author Wind
 * @see 2020-02-19 10:55
 */
public interface BillService {
    /**
     * 通过条件获取供应商列表-providerList
     * @param productName
     * @param providerId
     * @param isPayment
     * @param currentPageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<Bill> findBillList(String productName, Integer providerId,
                                  Integer isPayment, Integer currentPageNo, Integer pageSize);


    /**
     * 通过条件查询-订单表记录数
     * @param productName
     * @param providerId
     * @param isPayment
     * @return
     * @throws Exception
     */
    int findBillCount(String productName,Integer providerId,Integer isPayment);

    /**
     * 增加订单
     * @param bill
     * @return
     * @throws Exception
     */
    boolean addBill(Bill bill);

    /**
     * 通过id获取Bill
     * @param id
     * @return
     * @throws Exception
     */
    Bill findBillById(Integer id);

    /**
     * 修改订单信息
     * @param bill
     * @return
     * @throws Exception
     */
    boolean updateBill(Bill bill);

    /**
     * 通过id删除订单信息
     * @param delId
     * @return
     * @throws Exception
     */
    boolean deleteBillById(Integer delId);
}
