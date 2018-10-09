package com.qunchuang.mlshop.schedule;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Curtain
 * @date 2018/4/23 10:52
 */

@Component
public class TimeSchedule {

    /*超时订单设置*/
    @Scheduled(cron = "00 00 00 * * ?")
    public void updateCommunicationStatus() {

    }


}
