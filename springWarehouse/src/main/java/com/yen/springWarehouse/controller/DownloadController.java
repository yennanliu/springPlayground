package com.yen.springWarehouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yen.springWarehouse.bean.DownloadStatus;
import com.yen.springWarehouse.service.DownloadStatusService;
import com.yen.springWarehouse.util.DateTimeUtils;
import com.yen.springWarehouse.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    DownloadStatusService downloadStatusService;

    @GetMapping("/list")
    public String list(Map<String, Object> map, @RequestParam(value="pageNo", required=false, defaultValue="1") String pageNoStr) {

        int pageNo;
        // check pageNo
        pageNo = Integer.parseInt(pageNoStr);
        if(pageNo < 1){
            pageNo = 1;
        }
        log.info("pageNo = {}", pageNo);
        Page<DownloadStatus> page = new Page<>(pageNo,3);
        QueryWrapper<DownloadStatus> queryWrapper = new QueryWrapper<>();
        IPage<DownloadStatus> iPage = downloadStatusService.page(page,
                new LambdaQueryWrapper<DownloadStatus>()
                        .orderByAsc(DownloadStatus::getId)
        );

        log.info("iPage.total = {}, iPage.getPages = {} iPage = {}",  iPage.getTotal(), iPage.getPages(), iPage);
        map.put("page", iPage);
        return "download/list_download";
    }

    @GetMapping("/create")
    public String createDownload(){

        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        String timestamp = dateTimeUtils.getCurrentDateYYYYMMDDHHMMSS();

        FileUtil fileUtil = new FileUtil();

        final String prefix = "/Users/yennanliu/SpringPlayground/";
        String fileName = "/" + timestamp + "_report.csv";
        DownloadStatus downloadStatus = new DownloadStatus();
        downloadStatus.setStatus("completed");
        downloadStatus.setDownloadUrl(fileName);
        downloadStatus.setCreateTime(new Date());
        // save file to local // TODO : save it to remote file system (e.g. S3)
        if (fileUtil.writeFile("some data", prefix+fileName)){
            log.info("save File OK");
            // save to DB
            downloadStatusService.save(downloadStatus);
        }else {
            log.info("save File failed");
        }
        return "download/success";
    }

    @GetMapping("/download_report")
    public ResponseEntity<Resource> downloadFile() throws IOException {

        // Load the file from the classpath (assuming it's in the resources/static directory)
        Resource resource = new ClassPathResource("/test.json");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.json");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}
