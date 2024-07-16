package com.pcs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pcs.common.Result;
import com.pcs.entity.Account;
import com.pcs.entity.Consultant;
import com.pcs.entity.Reservation;
import com.pcs.mapper.AccountMapper;
import com.pcs.mapper.ConsultantMapper;
import com.pcs.mapper.ReservationMapper;
import com.pcs.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author pcs
 * @description 针对表【reservation】的数据库操作Service实现
 * @createDate 2024-04-11 21:51:08
 */
@Service
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation>
        implements ReservationService {
    @Autowired
    private ReservationMapper reservationMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private ConsultantMapper consultantMapper;

    @Override
    public Result selectAgain(String reservationUser, String reservationConsultant) {
        Reservation reservation = reservationMapper.selectOne(
                new QueryWrapper<Reservation>().eq("reservation_user", reservationUser).
                        eq("reservation_consultant", reservationConsultant));
        if (reservation == null) {
            return Result.fail();
        }
        return Result.success();
    }

    @Override
    public Result res(Reservation reservation) {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 定义时间格式为年-月-日 时:分
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // 格式化当前时间为指定格式
        String formattedDateTime = now.format(formatter);
        reservation.setReservationNow(formattedDateTime);
        reservationMapper.insert(reservation);
        return Result.success();
    }

    @Override
    public Result resRecord(String reservationUser, String search) {
        QueryWrapper<Reservation> queryWrapper = new QueryWrapper<>();

        if (search != null && !search.isEmpty()) {
            queryWrapper.eq("reservation_user", reservationUser)
                    .like("reservation_consultant", search);

        } else {
            queryWrapper.eq("reservation_user", reservationUser);
        }

        return Result.success(reservationMapper.selectList(queryWrapper));

    }

    @Override
    public Result updateRes(String reservationUser, String reservationConsultant,
                            String reservationTime, String reservationMessage) {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 定义时间格式为年-月-日 时:分
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // 格式化当前时间为指定格式
        String formattedDateTime = now.format(formatter);
        UpdateWrapper<Reservation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("reservation_user", reservationUser)
                .eq("reservation_consultant", reservationConsultant)
                .set("reservation_time", reservationTime)
                .set("reservation_message", reservationMessage)
                .set("reservation_now", formattedDateTime);
        reservationMapper.update(null, updateWrapper);
        return Result.success();
    }

    @Override
    public Result cancelRes(String reservationUser, String reservationConsultant) {
        QueryWrapper<Reservation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("reservation_user", reservationUser)
                .eq("reservation_consultant", reservationConsultant);
        reservationMapper.delete(queryWrapper);
        return Result.success();
    }

    @Override
    public Result againRes(String reservationUser, String reservationConsultant, String reservationTime) {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 定义时间格式为年-月-日 时:分
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // 格式化当前时间为指定格式
        String formattedDateTime = now.format(formatter);
        UpdateWrapper<Reservation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("reservation_user", reservationUser)
                .eq("reservation_consultant", reservationConsultant)
                .set("reservation_status", "处理中")
                .set("reservation_time", reservationTime)
                .set("reservation_now", formattedDateTime);
        reservationMapper.update(null, updateWrapper);
        return Result.success();
    }

    @Override
    public Result cRecord(String username, String search) {
        QueryWrapper<Reservation> queryWrapper = new QueryWrapper<>();
        Account account = accountMapper.selectOne(new
                QueryWrapper<Account>().eq("username", username));

        Consultant consultant = consultantMapper.selectOne(
                new QueryWrapper<Consultant>().eq("consultant_phone", account.getPhoneNumber()));
        if (search != null && !search.isEmpty()) {
            queryWrapper.eq("reservation_consultant", consultant.getConsultantName()).
                    ne("reservation_status", "预约失败")
                    .like("reservation_user", search);

        } else {
            queryWrapper.eq("reservation_consultant", consultant.getConsultantName())
                    .ne("reservation_status", "预约失败");
        }

        return Result.success(reservationMapper.selectList(queryWrapper));

    }

    @Override
    public Result agreeRes(String reservationUser, String username) {
        Account account = accountMapper.selectOne(new
                QueryWrapper<Account>().eq("username", username));

        Consultant consultant = consultantMapper.selectOne(
                new QueryWrapper<Consultant>().eq("consultant_phone", account.getPhoneNumber()));
        UpdateWrapper<Reservation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("reservation_user", reservationUser)
                .eq("reservation_consultant", consultant.getConsultantName())
                .set("reservation_status", "预约成功");
        reservationMapper.update(null, updateWrapper);
        return Result.success();
    }

    @Override
    public Result refuseRes(String reservationUser, String username) {
        Account account = accountMapper.selectOne(new
                QueryWrapper<Account>().eq("username", username));

        Consultant consultant = consultantMapper.selectOne(
                new QueryWrapper<Consultant>().eq("consultant_phone", account.getPhoneNumber()));
        UpdateWrapper<Reservation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("reservation_user", reservationUser)
                .eq("reservation_consultant", consultant.getConsultantName())
                .set("reservation_status", "预约失败");
        reservationMapper.update(null, updateWrapper);
        return Result.success();
    }

    @Override
    public Result overRes(String reservationUser, String reservationConsultant) {
        UpdateWrapper<Reservation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("reservation_user", reservationUser)
                .eq("reservation_consultant", reservationConsultant)
                .set("reservation_status", "咨询结束");
        reservationMapper.update(null, updateWrapper);
        UpdateWrapper<Consultant> updateWrapper1 = new UpdateWrapper<>();
        updateWrapper1.eq("consultant_name", reservationConsultant).
                setSql("consultant_no = consultant_no + 1");
        consultantMapper.update(null, updateWrapper1);
        return Result.success();
    }
}



