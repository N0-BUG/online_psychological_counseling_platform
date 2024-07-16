package com.pcs.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.pcs.common.Result;
import com.pcs.entity.Reservation;
import com.pcs.service.ReservationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * (Reservation)表控制层
 *
 * @author makejava
 * @since 2024-04-13 11:13:22
 */
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    /**
     * 服务对象
     */
    @Resource
    private ReservationService reservationService;

    @GetMapping("/again")
    public Result selectAgain(String reservationUser, String reservationConsultant) {
        return reservationService.selectAgain(reservationUser, reservationConsultant);
    }

    @PostMapping("/res")
    public Result res(@RequestBody Reservation reservation) {
        return reservationService.res(reservation);
    }

    @GetMapping("/resRecord")
    public Result resRecord(String reservationUser, @RequestParam(required = false) String search) {
        return reservationService.resRecord(reservationUser, search);
    }

    @PostMapping("/updateRes")
    public Result updateRes(@RequestBody JsonNode jsonNode) {
        String reservationUser = jsonNode.get("reservationUser").asText();
        String reservationConsultant = jsonNode.get("reservationConsultant").asText();
        String reservationTime = jsonNode.get("reservationTime").asText();
        String reservationMessage = jsonNode.get("reservationMessage").asText();
        return reservationService.updateRes(reservationUser, reservationConsultant,
                reservationTime, reservationMessage);
    }

    @DeleteMapping("/cancelRes")
    public Result cancelRes(String reservationUser, String reservationConsultant) {
        return reservationService.cancelRes(reservationUser, reservationConsultant);
    }

    @PostMapping("/againRes")
    public Result againRes(@RequestBody JsonNode jsonNode) {
        String reservationUser = jsonNode.get("reservationUser").asText();
        String reservationConsultant = jsonNode.get("reservationConsultant").asText();
        String reservationTime = jsonNode.get("reservationTime").asText();
        return reservationService.againRes(reservationUser, reservationConsultant, reservationTime);
    }

    @GetMapping("/cRecord")
    public Result cRecord(String username, @RequestParam(required = false) String search) {
        return reservationService.cRecord(username, search);
    }

    @PostMapping("/agreeRes")
    public Result agreeRes(@RequestBody JsonNode jsonNode) {
        String reservationUser = jsonNode.get("reservationUser").asText();
        String username = jsonNode.get("username").asText();
        return reservationService.agreeRes(reservationUser, username);
    }

    @PostMapping("/refuseRes")
    public Result refuseRes(@RequestBody JsonNode jsonNode) {
        String reservationUser = jsonNode.get("reservationUser").asText();
        String username = jsonNode.get("username").asText();
        return reservationService.refuseRes(reservationUser, username);
    }

    @PostMapping("/overRes")
    public Result overRes(@RequestBody JsonNode jsonNode) {
        String reservationUser = jsonNode.get("reservationUser").asText();
        String reservationConsultant = jsonNode.get("reservationConsultant").asText();
        return reservationService.overRes(reservationUser, reservationConsultant);
    }
}

