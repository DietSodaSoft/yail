package com.dietsodasoftware.yail.xmlrpc.service.funnel;

import java.util.Map;

/**
 * Created by wendel.schultz on 2/4/16.
 */
public class FlowStatus {

    private boolean success;

    private String msg;

    private Long flowId;

    private final Map<String, Object> model;

    public FlowStatus(Map<String, Object> flowStatus) {
        this.model = flowStatus;
    }

    public Boolean isSuccess() {
        return (Boolean) model.get("success");
    }

    public Integer getFlowId() {
        return (Integer) model.get("flowId");
    }

    public String getMessage() {
        return (String) model.get("msg");
    }

    public String toString() {
        return new StringBuilder(FlowStatus.class.getSimpleName())
                .append("[")
                .append("message=").append(getMessage())
                .append(", flowId=").append(getFlowId())
                .append(", success=").append(isSuccess())
                .append("]")
                .toString();
    }
}
