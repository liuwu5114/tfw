package com.tongtech.biz.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.text.NumberFormat;
import java.util.List;

/**
 * DiskCheckScheduler
 *
 * @author Created by ivan on 2020/11/8 .
 * @version 1.0
 */
@Component
@Slf4j
public class DiskCheckScheduler {
  // 每5分钟更新一次
  @Scheduled(cron = "0 0/5 * * * ?")
  public void refreshSwitch() {
    SystemInfo si = new SystemInfo();
    OperatingSystem os = si.getOperatingSystem();
    List<OSFileStore> fsArray = os.getFileSystem().getFileStores();
    long totalSpace = 0L;
    long usedSpace = 0L;
    for (OSFileStore fs : fsArray) {
      if (os.toString().toLowerCase().contains("linux")) {
        if (!fs.getVolume().toLowerCase().startsWith("/dev")) {
          continue;
        }
      }
      totalSpace = fs.getTotalSpace() + totalSpace;
      usedSpace = fs.getUsableSpace() + usedSpace;
    }
    Double usage = 1 - (usedSpace * 1.0 / totalSpace);

    NumberFormat num = NumberFormat.getPercentInstance();
    log.info("磁盘使用情况:" + num.format(usage));
  }
}
