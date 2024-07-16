package com.pcs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pcs.common.Result;
import com.pcs.entity.Reservation;

/**
 * @author pcs
 * @description 针对表【reservation】的数据库操作Service
 * @createDate 2024-04-11 21:51:08
 */
public interface ReservationService extends IService<Reservation> {
    /*查看是否预约过*/
    Result selectAgain(String reservationUser, String reservationConsultant);

    /*预约*/
    Result res(Reservation reservation);

    /*查看我的预约记录*/
    Result resRecord(String reservationUser, String search);

    /*更新预约记录*/
    Result updateRes(String reservationUser, String reservationConsultant,
                     String reservationTime, String reservationMessage);

    /*取消预约*/
    Result cancelRes(String reservationUser, String reservationConsultant);

    /*重新预约*/
    Result againRes(String reservationUser, String reservationConsultant,
                    String reservationTime);

    /*对我的预约*/
    Result cRecord(String username, String search);

    /*同意*/
    Result agreeRes(String reservationUser, String username);

    /*拒绝*/
    Result refuseRes(String reservationUser, String username);

    /*结束咨询*/
    Result overRes(String reservationUser, String reservationConsultant);
}
