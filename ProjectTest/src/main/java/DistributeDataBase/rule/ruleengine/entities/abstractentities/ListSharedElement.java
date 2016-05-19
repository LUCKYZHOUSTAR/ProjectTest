/**
 * 
 */
package DistributeDataBase.rule.ruleengine.entities.abstractentities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: ListSharedElement 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午7:14:26 
*  
*/
public abstract class ListSharedElement extends SharedElement {
    protected static final Logger                  log              = LoggerFactory
                                                                        .getLogger(ListSharedElement.class);
    public DEFAULT_LIST_RESULT_STRAGETY            defaultListResultStragety;
    protected List<String>                         defaultListResult;
    protected RuleChain                            listResultRule;
    protected Map<String, ? extends SharedElement> subSharedElement = Collections.emptyMap();

    public void registeRule(Set<RuleChain> ruleSet) {
        registeSubSharedElement(ruleSet);

        registeCurrentSharedElement(ruleSet);
    }

    private void registeCurrentSharedElement(Set<RuleChain> ruleSet) {
        if (this.listResultRule != null)
            ruleSet.add(this.listResultRule);
    }

    private void registeSubSharedElement(Set<RuleChain> ruleSet) {
        for (SharedElement sharedElement : this.subSharedElement.values())
            if ((sharedElement instanceof ListSharedElement))
                ((ListSharedElement) sharedElement).registeRule(ruleSet);
    }

    public void setSubSharedElement(Map<String, ? extends SharedElement> subSharedElement) {
        this.subSharedElement = subSharedElement;
    }

    public void init() {
        Map subSharedElements = fillNullSubSharedElementWithEmptyList();

        initDefaultSubSharedElementsListRule();

        setChildIdByUsingMapKey(subSharedElements);

        super.init();
    }

    private void setChildIdByUsingMapKey(Map<String, ? extends SharedElement> subSharedElements) {
        for (Map.Entry sharedElement : subSharedElements.entrySet()) {
            ((SharedElement) sharedElement.getValue()).setId((String) sharedElement.getKey());
            initOneSubSharedElement((SharedElement) sharedElement.getValue());
        }
    }

    protected Map<String, ? extends SharedElement> fillNullSubSharedElementWithEmptyList() {
        if (this.subSharedElement == null) {
            this.subSharedElement = Collections.emptyMap();
        }
        return this.subSharedElement;
    }

    protected void initOneSubSharedElement(SharedElement sharedElement) {
        sharedElement.init();
    }

    protected void initDefaultSubSharedElementsListRule() {
        if (this.defaultListResultStragety == null) {
            if (log.isDebugEnabled()) {
                log.debug("default stragety is null ,use none stragety .");
            }
            this.defaultListResultStragety = DEFAULT_LIST_RESULT_STRAGETY.NONE;
        }

        switch (this.defaultListResultStragety.ordinal()) {
            case 1:
                buildFullTableKeysList();
                break;
            case 2:
                if ((this.listResultRule == null)
                    || (this.listResultRule.getListResultRule() == null)
                    || (this.listResultRule.getListResultRule().isEmpty())) {
                    if (this.subSharedElement.size() == 1) {
                        log.warn(new StringBuilder()
                            .append(
                                "NONE stragety ,current element has only one SubElement,use full table stragety! subElement is ")
                            .append(this.subSharedElement).toString());

                        buildFullTableKeysList();
                    } else {
                        log.warn(new StringBuilder()
                            .append(
                                "NONE stragety ,current element has more than one SubElement,use empty default stragety! subElement is ")
                            .append(this.subSharedElement).toString());

                        this.defaultListResult = Collections.emptyList();
                    }
                } else {
                    this.defaultListResult = Collections.emptyList();
                }
                break;
            default:
                throw new IllegalArgumentException("不能处理的类型");
        }
    }

    protected void buildFullTableKeysList() {
        int subSharedElementSize = 0;
        if (this.subSharedElement == null) {
            this.subSharedElement = Collections.emptyMap();
        }
        subSharedElementSize = this.subSharedElement.size();
        this.defaultListResult = new ArrayList(subSharedElementSize);
        if (log.isDebugEnabled()) {
            log.debug("use full table stragety, default keys are :");
        }
        StringBuilder sb = new StringBuilder();
        for (String key : this.subSharedElement.keySet()) {
            sb.append(key).append("|");
            this.defaultListResult.add(key);
        }
        if (log.isDebugEnabled())
            log.debug(sb.toString());
    }

    public Map<String, ? extends SharedElement> getSubSharedElements() {
        return this.subSharedElement;
    }

    public DEFAULT_LIST_RESULT_STRAGETY getDefaultListResultStragety() {
        return this.defaultListResultStragety;
    }

    public void setDefaultListResultStragety(DEFAULT_LIST_RESULT_STRAGETY defaultListResultStragety) {
        this.defaultListResultStragety = defaultListResultStragety;
    }

    public String toString() {
        return new StringBuilder().append("ListSharedElement [defaultListResult=")
            .append(this.defaultListResult).append(", defaultListResultStragety=")
            .append(this.defaultListResultStragety).append(", listResultRule=")
            .append(this.listResultRule).append(", subSharedElement=")
            .append(this.subSharedElement).append("]").toString();
    }

    public static enum DEFAULT_LIST_RESULT_STRAGETY {
        FULL_TABLE_SCAN,

        NONE;
    }
}
