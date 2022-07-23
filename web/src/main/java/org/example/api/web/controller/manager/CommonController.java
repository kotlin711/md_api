package org.example.api.web.controller.manager;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import org.example.api.model.entity.*;
import org.example.api.model.result.Result;
import org.example.api.web.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private AdServiceImpl adService;
    @Autowired
    private GiftServiceImpl giftService;
    @Autowired
    private NoticeServiceImpl noticeService;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private PromoServiceImpl promoService;
    @Autowired
    private ProvideServiceImpl provideService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AppServiceImpl appService;

    @Autowired
    private FileServiceImpl fileService;

    @PostMapping("/execl/imp/{type}")
    public Result import_execl(@PathVariable Integer type, @RequestParam("file") MultipartFile file) throws IOException {
        String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        if (suffixName.equals(".xlsx")) {
            Path path = Paths.get(UUID.randomUUID() + ".xlsx");
            file.transferTo(path);
            switch (type) {
                case 1: {

                    List<Admin> objects = EasyExcel.read(path.toFile().getAbsolutePath(), Admin.class, new PageReadListener<Admin>(dataList -> {
                            })).sheet()
                            .doReadSync();
                    adminService.saveBatch(objects);


                    break;
                }

                case 2: {
                    List<Ad> objects = EasyExcel.read(
                                    path.toFile().getAbsolutePath()
                                    , Ad.class, new PageReadListener<Ad>(dataList -> {
                                    })).sheet()
                            .doReadSync();
                    adService.saveBatch(objects);
                    break;
                }
                case 3: {
                    List<Gift> objects = EasyExcel.read(
                                    path.toFile().getAbsolutePath()
                                    , Gift.class, new PageReadListener<Gift>(dataList -> {
                                    })).sheet()
                            .doReadSync();
                    giftService.saveBatch(objects);
                    break;
                }
                case 4: {
                    List<Notice> objects = EasyExcel.read(
                                    path.toFile().getAbsolutePath()
                                    , Notice.class, new PageReadListener<Notice>(dataList -> {
                                    })).sheet()
                            .doReadSync();
                    noticeService.saveBatch(objects);
                    break;
                }
                case 5: {
                    List<Order> objects = EasyExcel.read(
                                    path.toFile().getAbsolutePath()
                                    , Order.class, new PageReadListener<Order>(dataList -> {
                                    })).sheet()
                            .doReadSync();
                    orderService.saveBatch(objects);
                    break;
                }
                case 6: {
                    List<Promo> objects = EasyExcel.read(
                                    path.toFile().getAbsolutePath()
                                    , Promo.class, new PageReadListener<Promo>(dataList -> {
                                    })).sheet()
                            .doReadSync();
                    promoService.saveBatch(objects);
                    break;
                }

                case 7: {
                    List<Provide> objects = EasyExcel.read(
                                    path.toFile().getAbsolutePath()
                                    , Provide.class, new PageReadListener<Provide>(dataList -> {
                                    })).sheet()
                            .doReadSync();
                    provideService.saveBatch(objects);
                    break;
                }
                case 8: {
                    List<User> objects = EasyExcel.read(
                                    path.toFile().getAbsolutePath()
                                    , User.class, new PageReadListener<User>(dataList -> {
                                    })).sheet()
                            .doReadSync();
                    userService.saveBatch(objects);
                    break;
                }
                case 9: {
                    List<MdFile> objects = EasyExcel.read(
                                    path.toFile().getAbsolutePath()
                                    , MdFile.class, new PageReadListener<MdFile>(dataList -> {
                                    })).sheet()
                            .doReadSync();
                    fileService.saveBatch(objects);
                    break;
                }
                case 10: {
                    List<MdApp> objects = EasyExcel.read(
                                    path.toFile().getAbsolutePath()
                                    , MdApp.class, new PageReadListener<MdApp>(dataList -> {
                                    })).sheet()
                            .doReadSync();
                    appService.saveBatch(objects);
                    break;
                }
            }
            Files.delete(path);
            return Result.ok();
        }

        return Result.fail();
    }

    @GetMapping("/execl/exp/{type}")
    public ResponseEntity<byte[]> export(@PathVariable Integer type) throws IOException {
        File file = new File(UUID.randomUUID().toString() + ".file");
        HttpHeaders httpHeaders = new HttpHeaders();
        switch (type) {
            case 1: {
                EasyExcel.write(file, Admin.class).sheet("1").doWrite(adminService.list());
                break;
            }

            case 2: {
                EasyExcel.write(file, Ad.class).sheet("1").doWrite(adService.list());
                break;
            }
            case 3: {
                EasyExcel.write(file, Gift.class).sheet("1").doWrite(giftService.list());
                break;
            }
            case 4: {
                EasyExcel.write(file, Notice.class).sheet("1").doWrite(noticeService.list());
                break;
            }
            case 5: {
                EasyExcel.write(file, Order.class).sheet("1").doWrite(orderService.list());
                break;
            }
            case 6: {
                EasyExcel.write(file, Promo.class).sheet("1").doWrite(promoService.list());
                break;
            }

            case 7: {
                EasyExcel.write(file, Provide.class).sheet("1").doWrite(promoService.list());
                break;
            }
            case 8: {
                EasyExcel.write(file, User.class).sheet("1").doWrite(userService.list());
                break;
            }
            case 9: {
                EasyExcel.write(file, MdFile.class).sheet("1").doWrite(fileService.list());
                break;
            }
            case 10: {
                EasyExcel.write(file, MdApp.class).sheet("1").doWrite(appService.list());
                break;
            }
        }
        byte[] bytes = Files.readAllBytes(Paths.get(file.getPath()));
        file.delete();
        httpHeaders.setContentDisposition(ContentDisposition.attachment()
                .filename("export_data.xlsx")
                .build());
        httpHeaders.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE));
        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.CREATED);
    }


}
