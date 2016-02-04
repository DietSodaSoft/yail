package com.dietsodasoftware.yail.xmlrpc.service.funnel;

import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by wendel.schultz on 2/1/16.
 */
// the toString for this looks like:
// [{msg=, funnelId=68, goalId=10, success=true, flowStop=[Ljava.lang.Object;@2000cb86, flowStart=[Ljava.lang.Object;@d9987de}]
public class AchieveGoalResponse {

    private final List<AchieveGoalResult> goalResults;

    private final Object [] rawModels;
    AchieveGoalResponse(Object [] models ) {
        this.rawModels = models;
        this.goalResults = fromXmlRpcStruct(models);
    }

    public List<AchieveGoalResult> getGoalResults() {
        return goalResults;
    }

    public int count() {
        return goalResults.size();
    }

    private static List<AchieveGoalResult> fromXmlRpcStruct(Object [] models) {

        List<AchieveGoalResult> results = ListFactory.quickLinkedList();
        for(Object model: models) {
            results.add(new AchieveGoalResult((Map<String, Object>) model));
        }

        return Collections.unmodifiableList(results);
    }

    public String toString() {
        return new StringBuilder(AchieveGoalResponse.class.getSimpleName())
                .append("[")
                .append("count=").append(count())
                .append(", goalResults=").append(getGoalResults())
                .append("]")
                .toString();
    }
}
