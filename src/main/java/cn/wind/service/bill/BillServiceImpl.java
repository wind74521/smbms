package cn.wind.service.bill;

import cn.wind.dao.bill.BillDao;
import cn.wind.pojo.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wind
 * @see 2020-02-19 10:56
 */
@Service("billService")
@Scope
public class BillServiceImpl implements BillService {

    @Autowired
    private BillDao billDao;

    @Override
    public List<Bill> findBillList(String productName, Integer providerId, Integer isPayment, Integer currentPageNo, Integer pageSize) {
        currentPageNo = (currentPageNo-1)*pageSize;
        return billDao.findBillList(productName, providerId, isPayment, currentPageNo, pageSize);
    }

    @Override
    public int findBillCount(String productName, Integer providerId, Integer isPayment) {
        return billDao.findBillCount(productName,providerId,isPayment);
    }

    @Override
    public boolean addBill(Bill bill) {
        return billDao.addBill(bill) != 0;
    }

    @Override
    public Bill findBillById(Integer id) {
        return billDao.findBillById(id);
    }

    @Override
    public boolean updateBill(Bill bill) {
        return billDao.updateBill(bill) != 0;
    }

    @Override
    public boolean deleteBillById(Integer delId) {
        return billDao.deleteBillById(delId) != 0;
    }
}
