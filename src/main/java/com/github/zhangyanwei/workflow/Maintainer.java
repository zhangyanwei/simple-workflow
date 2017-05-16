package com.github.zhangyanwei.workflow;

import com.github.zhangyanwei.exception.WorkflowException;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface Maintainer<R> {
    Pageable query(Long userId, Map<String, String> params) throws WorkflowException;
    R retrieve(Long userId, Long id, Map<String, String> params) throws WorkflowException;
    void delete(Long userId, Long id) throws WorkflowException;
    void edit(Long userId, Long id, String request) throws WorkflowException;
}
