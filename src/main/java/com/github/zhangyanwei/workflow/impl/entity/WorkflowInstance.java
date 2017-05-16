package com.github.zhangyanwei.workflow.impl.entity;

import com.google.common.collect.Lists;
import com.github.zhangyanwei.workflow.impl.entity.converter.ListConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.collect.Lists.newLinkedList;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Table(name = "x_workflow_instance")
@Entity
public class WorkflowInstance extends Base {

    private static final long serialVersionUID = -2093238370357575839L;

    @NotNull
    @Column(name = "name", updatable = false)
    private String name;

    @NotNull
    @Column(name = "related_data")
    private Long relatedData;

    @NotNull
    @Column(name = "related_data_type")
    @Enumerated(value = STRING)
    private Enum<?> relatedDataType;

    @Convert(converter = ListConverter.class)
    @Column(name = "next_steps")
    private List<String> nextSteps;

    @Convert(converter = ListConverter.class)
    @Column(name = "passed_steps")
    private List<String> passedSteps;

    @NotNull
    @ManyToOne(fetch = LAZY)
    private User user;

    @NotNull
    @Column(name = "creation_time", updatable = false)
    private Date creationTime;

    @Column(name = "finish_time")
    private Date finishTime;

    @OneToMany(mappedBy = "workflow", fetch = LAZY, cascade = REMOVE)
    private List<StepInstance> steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRelatedData() {
        return relatedData;
    }

    public void setRelatedData(Long relatedData) {
        this.relatedData = relatedData;
    }

    public Enum<?> getRelatedDataType() {
        return relatedDataType;
    }

    public void setRelatedDataType(Enum<?> relatedDataType) {
        this.relatedDataType = relatedDataType;
    }

    public List<String> getNextSteps() {
        if (com.github.zhangyanwei.utils.Lists.isNullOrEmpty(nextSteps)) {
            return new ArrayList<>();
        }

        return Lists.newArrayList(nextSteps);
    }

    public void setNextSteps(List<String> nextStep) {
        this.nextSteps = nextStep;
    }

    public List<String> getPassedSteps() {
        return passedSteps == null ? newLinkedList() : new LinkedList<>(passedSteps);
    }

    public void setPassedSteps(List<String> passedSteps) {
        this.passedSteps = new LinkedList<>(passedSteps);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public List<StepInstance> getSteps() {
        return steps;
    }

    public void setSteps(List<StepInstance> steps) {
        this.steps = steps;
    }

    public void addPassedStep(String step) {
        if (this.passedSteps == null) {
            this.passedSteps = new LinkedList<>();
        }

        this.passedSteps.add(step);
    }
}
