package com.jiaxwu.jrpc.framework.core.server;


import com.jiaxwu.jrpc.framework.interfaces.DataService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiaxwu
 * @create 2022/4/12 23:27
 */
public class DataServiceImpl implements DataService {
    @Override
    public String sendData(String body) {
        System.out.println("[DataService] receive: " + body);
        return "success";
    }

    @Override
    public List<String> getList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("val1");
        list.add("val2");
        list.add("val3");
        return list;
    }
}
