package com.github.zhangyanwei.workflow.impl.entity;

import com.github.zhangyanwei.workflow.impl.entity.converter.MapConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

import static javax.persistence.FetchType.LAZY;

@Table(name = "x_step_instance")
@Entity
public class StepInstance extends Base {

    private static final long serialVersionUID = -3644915615123986673L;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "workflow", updatable = false)
    private WorkflowInstance workflow;

    @NotNull
    @Column(name = "name", updatable = false)
    private String name;

    @NotNull
    @ManyToOne(fetch = LAZY)
    private User user;

    @Convert(converter = MapConverter.class)
    @Column(name = "additions", updatable = false)
    private Map<String, String> additions;

    @NotNull
    @Column(name = "evaluation_time", updatable = false)
    private Date executeTime;

    public WorkflowInstance getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkflowInstance workflow) {
        this.workflow = workflow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<String, String> getAdditions() {
        return additions;
    }

    public void setAdditions(Map<String, String> additions) {
        this.additions = additions;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }
}
