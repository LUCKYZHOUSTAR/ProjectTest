package DistributeDataBase.rule.config.beans;

import java.util.Map;

public class AppRule {
    public static final String DBINDEX_SUFFIX_READ  = "_r";
    public static final String DBINDEX_SUFFIX_WRITE = "_w";
    private ShardRule          masterRule;
    private ShardRule          slaveRule;
    private ShardRule          readwriteRule;

    public void init() {
        if (this.readwriteRule == null) {
            return;
        }
        if ((this.slaveRule != null) || (this.masterRule != null))
            throw new IllegalArgumentException("readwriteRule 不能和masterRule或slaveRule同时配置");
        try {
            this.masterRule = this.readwriteRule.clone();
            this.slaveRule = this.readwriteRule.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalArgumentException("should not happen!!", e);
        }
        addDbIndexSuffix(this.masterRule, "_w");
        addDbIndexSuffix(this.slaveRule, "_r");
    }

    private void addDbIndexSuffix(ShardRule shardRule, String suffix) {
        for (TableRule tbr : shardRule.getTableRules().values()) {
            String[] dbIndexes = tbr.getDbIndexArray();
            for (int i = 0; i < dbIndexes.length; i++)
                dbIndexes[i] = (dbIndexes[i] + suffix);
        }
    }

    public ShardRule getMasterRule() {
        return this.masterRule;
    }

    public void setMasterRule(ShardRule masterRule) {
        this.masterRule = masterRule;
    }

    public ShardRule getSlaveRule() {
        return this.slaveRule;
    }

    public void setSlaveRule(ShardRule slaveRule) {
        this.slaveRule = slaveRule;
    }

    public ShardRule getReadwriteRule() {
        return this.readwriteRule;
    }

    public void setReadwriteRule(ShardRule readwriteRule) {
        this.readwriteRule = readwriteRule;
    }

    public boolean equals(Object obj) {
        if (this.readwriteRule != null) {
            return false;
        }
        AppRule appRule = (AppRule) obj;
        ShardRule masterRule = appRule.getMasterRule();
        ShardRule slaveRule = appRule.getSlaveRule();
        if ((this.masterRule == null) || (this.slaveRule == null)) {
            return false;
        }
        if (!this.masterRule.equals(masterRule)) {
            return false;
        }
        if (!this.slaveRule.equals(slaveRule)) {
            return false;
        }
        return true;
    }
}