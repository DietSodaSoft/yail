package com.dietsodasoftware.yail.xmlrpc.service.funnel;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wendel.schultz on 2/4/16.
 *
 * If a contact was somewhere else in the campaign, and this "moves" it, the objects here say which flow was interrupted
 * and which flow was started.
 */

// {msg=, funnelId=68, goalId=10, success=true, flowStop=[Ljava.lang.Object;@2000cb86, flowStart=[Ljava.lang.Object;@d9987de}
public class AchieveGoalResult {

    private final Map<String, Object> rawModel;

    private final List<FlowStatus> flowStart;
    private final List<FlowStatus> flowStop;

    AchieveGoalResult(Map<String, Object> model) {
        this.rawModel = model;
        this.flowStart = createFlowStatusList(rawModel.get("flowStart"));
        this.flowStop = createFlowStatusList(rawModel.get("flowStop"));
    }

    public String getMessage() {
        return (String) rawModel.get("msg");
    }

    public Integer getFunnelId() {
        return (Integer) rawModel.get("funnelId");
    }

    public Integer getGoalId() {
        return (Integer) rawModel.get("goalId");
    }

    public Boolean isSuccess() {
        return (Boolean) rawModel.get("success");
    }

    public Map<String, Object> getRawModel() {
        return rawModel;
    }

    public List<FlowStatus> getFlowStarts() {
        return flowStart;
    }

    public List<FlowStatus> getFlowStops() {
        return flowStop;
    }

    public String toString() {
        return new StringBuilder(AchieveGoalResult.class.getSimpleName())
                .append("[")
                .append("message=").append(getMessage())
                .append(", funnelId=").append(getFunnelId())
                .append(", goalId=").append(getGoalId())
                .append(", success=").append(isSuccess())
                .append(", flowStart=").append(getFlowStarts())
                .append(", flowStops=").append(getFlowStops())
                .append("]")
                .toString();
    }

    private static List<FlowStatus> createFlowStatusList(Object rawStatuses) {
        List<FlowStatus> statuses = new LinkedList<FlowStatus>();
        if(rawStatuses != null && rawStatuses instanceof Object[]) {
            for(Object rawStatus: (Object[]) rawStatuses) {
                statuses.add(new FlowStatus((Map<String, Object>) rawStatus));
            }
        }

        return statuses;
    }
}
