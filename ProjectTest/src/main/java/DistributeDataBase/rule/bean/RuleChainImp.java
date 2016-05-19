package DistributeDataBase.rule.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import DistributeDataBase.rule.ruleengine.entities.abstractentities.RuleChain;
import DistributeDataBase.rule.ruleengine.rule.ListAbstractResultRule;

public class RuleChainImp
  implements RuleChain
{
  public static final int NO_INDEX = -1;
  private volatile boolean isInited = false;

  private boolean isDatabaseRuleChain = false;
  private List<Set<String>> requiredArgumentSortByLevel;
  protected List<ListAbstractResultRule> listResultRule;

  public ListAbstractResultRule getRuleByIndex(int index)
  {
    if (index != -1) {
      return getListRuleByIndexInternal(index);
    }
    return null;
  }

  private ListAbstractResultRule getListRuleByIndexInternal(int index)
  {
    ListAbstractResultRule listRule = (ListAbstractResultRule)this.listResultRule.get(index);
    return listRule;
  }

  public List<Set<String>> getRequiredArgumentSortByLevel()
  {
    if (!this.isInited) {
      throw new IllegalStateException("not inited ");
    }
    return this.requiredArgumentSortByLevel;
  }

  public void init() {
    if (!this.isInited) {
      if (this.listResultRule == null) {
        throw new IllegalArgumentException("没有输入规则");
      }
      this.requiredArgumentSortByLevel = new ArrayList(this.listResultRule.size());
      for (ListAbstractResultRule listRule : this.listResultRule) {
        listRule.initRule();
        Set parameterSet = listRule.getParameters();
        Set argStringSet = new HashSet(parameterSet.size());
        for (AdvancedParameter keyAndAtomIncValue : parameterSet) {
          argStringSet.add(keyAndAtomIncValue.key);
        }
        this.requiredArgumentSortByLevel.add(argStringSet);
      }

      this.isInited = true;
    }
  }

  public List<ListAbstractResultRule> getListResultRule() {
    return this.listResultRule;
  }

  public void setListResultRule(List<ListAbstractResultRule> listResultRule) {
    this.listResultRule = listResultRule;
  }

  public void setDatabaseRuleChain(boolean isDatabaseRuleChain) {
    this.isDatabaseRuleChain = isDatabaseRuleChain;
  }

  public boolean isDatabaseRuleChain() {
    return this.isDatabaseRuleChain;
  }

  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof RuleChain)) {
      return false;
    }
    RuleChain targetRuleChain = (RuleChain)obj;
    List listResultRules = targetRuleChain.getListResultRule();
    boolean isDatabase = targetRuleChain.isDatabaseRuleChain();
    if ((isDatabase == this.isDatabaseRuleChain) && (this.listResultRule == listResultRules)) {
      return true;
    }
    return false;
  }

  public int hashCode()
  {
    int result = 19;
    result = 31 * result + this.listResultRule.hashCode();
    result = 31 * result + (this.isDatabaseRuleChain ? 0 : 1);
    return result;
  }
}