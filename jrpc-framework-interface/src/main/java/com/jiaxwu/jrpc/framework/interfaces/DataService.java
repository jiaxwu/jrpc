package com.jiaxwu.jrpc.framework.interfaces;

import java.util.List;

/**
 * @author jiaxwu
 * @create 2022/4/12 23:26
 */
public interface DataService {
    String sendData(String body);

    List<String> getList();
}
