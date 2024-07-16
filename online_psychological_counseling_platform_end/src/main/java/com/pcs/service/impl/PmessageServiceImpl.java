package com.pcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.common.Result;
import com.pcs.entity.Account;
import com.pcs.entity.Consultant;
import com.pcs.entity.Pmessage;
import com.pcs.mapper.AccountMapper;
import com.pcs.mapper.ConsultantMapper;
import com.pcs.mapper.PmessageMapper;
import com.pcs.service.PmessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author pcs
 * @description 针对表【pmessage】的数据库操作Service实现
 * @createDate 2024-04-20 14:34:56
 */
@Service
public class PmessageServiceImpl extends ServiceImpl<PmessageMapper, Pmessage>
        implements PmessageService {
    @Autowired
    private PmessageMapper pmessageMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private ConsultantMapper consultantMapper;

    @Override
    public Result sendPM(Pmessage pmessage) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        pmessage.setPmTime(currentDate);
        pmessageMapper.insert(pmessage);
        return Result.success();
    }

    @Override
    public Result selectPM(String pmSender, String search) {
        QueryWrapper<Pmessage> queryWrapper = new QueryWrapper<>();
        if (search != null && !search.isEmpty()) {
            queryWrapper.eq("pm_sender", pmSender)
                    .like("pm_recipient", search);
        }
        queryWrapper.eq("pm_sender", pmSender);

        return Result.success(pmessageMapper.selectList(queryWrapper));
    }

    @Override
    public Result selectMy(String username, String search) {
        QueryWrapper<Pmessage> queryWrapper = new QueryWrapper<>();
        Account account = accountMapper.selectOne(new
                QueryWrapper<Account>().eq("username", username));

        Consultant consultant = consultantMapper.selectOne(
                new QueryWrapper<Consultant>().eq("consultant_phone", account.getPhoneNumber()));
        if (search != null && !search.isEmpty()) {
            queryWrapper.eq("pm_recipient", consultant.getConsultantName()).eq("pm_exa", "审核通过")
                    .like("pm_sender", search);

        } else {
            queryWrapper.eq("pm_recipient", consultant.getConsultantName()).eq("pm_exa", "审核通过");
        }

        return Result.success(pmessageMapper.selectList(queryWrapper));
    }

    @Override
    public Result reply(Integer pmId, String pmBack) {
        UpdateWrapper<Pmessage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("pm_id", pmId)
                .set("pm_back", pmBack);
        pmessageMapper.update(null, updateWrapper);
        return Result.success();
    }

    @Override
    public Result selectAll(String search) {
        QueryWrapper<Pmessage> queryWrapper = new QueryWrapper<>();
        if (search != null && !search.isEmpty()) {
            queryWrapper.eq("pm_exa", "未审核")
                    .like("pm_Sender", search);
        }
        queryWrapper.eq("pm_exa", "未审核");
        return Result.success(pmessageMapper.selectList(queryWrapper));
    }

    @Override
    public Result agreePm(Integer pmId) {
        UpdateWrapper<Pmessage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("pm_id", pmId).set("pm_exa", "审核通过");
        pmessageMapper.update(null, updateWrapper);
        return Result.success();
    }

    @Override
    public Result refusePm(Integer pmId) {
        UpdateWrapper<Pmessage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("pm_id", pmId).set("pm_exa", "审核失败");
        pmessageMapper.update(null, updateWrapper);
        return Result.success();
    }

    @Override
    public Result delPm(int id) {
        QueryWrapper<Pmessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pm_id", id);
        pmessageMapper.delete(queryWrapper);
        return Result.success();
    }
}




