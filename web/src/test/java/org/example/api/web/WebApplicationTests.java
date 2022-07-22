package org.example.api.web;

import org.example.api.common.io.MonitorUtil;
import org.example.api.model.dto.OrderDto;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.SigarException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

@SpringBootTest
class WebApplicationTests {

    @Test
    void contextLoads() throws SigarException {

        FileSystem fslist[] = MonitorUtil.getInstance().getSigar().getFileSystemList();
        System.out.println("??????????");

        for (FileSystem fileSystem : fslist) {

            System.out.println(MonitorUtil.getInstance().disk_info(fileSystem.getDirName()));

        }


    }
    @Test
    void obj_to_xml(){

        OrderDto orderDto = new OrderDto();
        final Class<? extends OrderDto> aClass = orderDto.getClass();
        final Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {

//            System.out.println(field.getName());
            final Class<?> type = field.getType();
//            System.out.println(type.getName());
//            System.out.println(String.format(" {field: '%s', title: '%s'},",field.getName(),field.getName()));
            System.out.println(String.format("<result property=\"%s\" column=\"%s\" javaType=\"%s\"/>",field.getName(),field.getName(),type.getName()));
//
        }
    }
}
