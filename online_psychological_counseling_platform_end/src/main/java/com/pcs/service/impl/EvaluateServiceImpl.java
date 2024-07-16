package com.pcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.common.Result;
import com.pcs.entity.Account;
import com.pcs.entity.Consultant;
import com.pcs.entity.Evaluate;
import com.pcs.mapper.AccountMapper;
import com.pcs.mapper.ConsultantMapper;
import com.pcs.mapper.EvaluateMapper;
import com.pcs.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author pcs
 * @description 针对表【evaluate】的数据库操作Service实现
 * @createDate 2024-04-11 21:51:08
 */
@Service
public class EvaluateServiceImpl extends ServiceImpl<EvaluateMapper, Evaluate>
        implements EvaluateService {
    @Autowired
    private EvaluateMapper evaluateMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private ConsultantMapper consultantMapper;

    @Override
    public Result selectByName(String evaluated) {
        return Result.success(evaluateMapper.selectList(
                new QueryWrapper<Evaluate>().eq("evaluated", evaluated).eq("evaluate_exa", "审核通过")));
    }

    @Override
    public Result selectByUser(String evaluate, String search) {
        QueryWrapper<Evaluate> queryWrapper = new QueryWrapper<>();

        if (search != null && !search.isEmpty()) {
            queryWrapper.eq("evaluate", evaluate)
                    .like("evaluated", search);

        } else {
            queryWrapper.eq("evaluate", evaluate);
        }
        return Result.success(evaluateMapper.selectList(queryWrapper));
    }

    @Override
    public Result delEva(int id) {
        QueryWrapper<Evaluate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("evaluate_id", id);
        evaluateMapper.delete(queryWrapper);
        return Result.success();
    }

    @Override
    public Result selectEva(String username, String search) {
        QueryWrapper<Evaluate> queryWrapper = new QueryWrapper<>();
        Account account = accountMapper.selectOne(new
                QueryWrapper<Account>().eq("username", username));

        Consultant consultant = consultantMapper.selectOne(
                new QueryWrapper<Consultant>().eq("consultant_phone", account.getPhoneNumber()));
        if (search != null && !search.isEmpty()) {
            queryWrapper.eq("evaluated", consultant.getConsultantName()).eq("evaluate_exa", "审核通过")
                    .like("evaluate", search);
        }
        queryWrapper.eq("evaluated", consultant.getConsultantName()).eq("evaluate_exa", "审核通过");
        return Result.success(evaluateMapper.selectList(queryWrapper));
    }

    @Override
    public Result selectAll(String search) {
        QueryWrapper<Evaluate> queryWrapper = new QueryWrapper<>();
        if (search != null && !search.isEmpty()) {
            queryWrapper.eq("evaluate_exa", "未审核")
                    .like("evaluate", search);
        }
        queryWrapper.eq("evaluate_exa", "未审核");
        return Result.success(evaluateMapper.selectList(queryWrapper));
    }

    @Override
    public Result agreeEva(Integer evaluatedId) {
        UpdateWrapper<Evaluate> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("evaluate_id", evaluatedId).set("evaluate_exa", "审核通过");
        evaluateMapper.update(null, updateWrapper);
        return Result.success();
    }

    @Override
    public Result refuseEva(Integer evaluatedId) {
        UpdateWrapper<Evaluate> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("evaluate_id", evaluatedId).set("evaluate_exa", "审核失败");
        evaluateMapper.update(null, updateWrapper);
        return Result.success();
    }

    @Override
    public Result evaluate(Evaluate evaluate) {
        LocalDate currentDate = LocalDate.now();
        evaluate.setEvaluateTime(currentDate);
        evaluateMapper.insert(evaluate);
        return Result.success();
    }

//    @Override
//    public Result againEva(String evaluate, String evaluated) {
//        QueryWrapper<Evaluate> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("evaluate", evaluate).eq("evaluated", evaluated);
//        Evaluate evaluate1 = evaluateMapper.selectOne(queryWrapper);
//        if (evaluate1 != null) {
//            return Result.fail();
//        }
//        return Result.success();
//    }
}




