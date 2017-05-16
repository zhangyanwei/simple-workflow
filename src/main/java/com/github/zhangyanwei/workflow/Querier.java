package com.github.zhangyanwei.workflow;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface Querier {
    Pageable query(Map<String, String> params);
}
