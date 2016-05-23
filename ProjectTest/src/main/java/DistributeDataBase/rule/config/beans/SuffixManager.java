/**
 * 
 */
package DistributeDataBase.rule.config.beans;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Binding;

import DistributeDataBase.common.util.TableSuffixTypeEnum;

/** 
* @ClassName: SuffixManager 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:47:23 
*  
*/
public class SuffixManager {
    private List<Suffix> listSuffix = new ArrayList();
    private String       tbSuffix;
    private Binding      binding    = new Binding();
    private GroovyShell  shell      = new GroovyShell(this.binding);

    public SuffixManager() {
        Suffix suf = new Suffix();
        this.listSuffix.add(suf);
    }

    public List<Suffix> getListSuffix() {
        return this.listSuffix;
    }

    public void init(String[] dbIndexes) {
        Suffix suf = (Suffix) this.listSuffix.get(0);
        if (suf.getTbSuffixTo() == -1) {
            suf.setTbSuffixTo(dbIndexes);
        }
        suf.setTbType(TableSuffixTypeEnum.throughAllDB.getValue());
    }

    protected void parseOneRange(String part, Suffix suf, int dbIndexSize)
                                                                          throws TableRule.ParseException {
        if ((!part.startsWith("[")) || (!part.endsWith("]"))) {
            throw new TableRule.ParseException();
        }

        part = part.substring(1, part.length() - 1);

        String[] temp = part.split("-");
        if (temp.length != 2) {
            throw new TableRule.ParseException();
        }
        temp[0] = temp[0].trim();
        temp[1] = temp[1].trim();
        int firstNumFrom = firstNum(temp[0]);
        int firstNot0From = firstNot0(temp[0], firstNumFrom);
        int firstNumTo = firstNum(temp[1]);
        int firstNot0To = firstNot0(temp[1], firstNumTo);
        if ((firstNumFrom == -1) || (firstNumTo == -1)) {
            throw new TableRule.ParseException();
        }
        if (firstNumFrom != firstNumTo) {
            throw new TableRule.ParseException("padding width different");
        }
        if ((temp[0].length() != temp[1].length())
            && ((firstNot0From != -1) || (firstNumFrom != temp[0].length() - 1) || (firstNot0To != firstNumTo))
            && ((firstNot0From != firstNumFrom) || (firstNot0To != firstNumTo))) {
            throw new TableRule.ParseException("tbSuffix width different");
        }
        if (firstNumFrom != 0) {
            String fromPadding = temp[0].substring(0, firstNumFrom);
            String toPadding = temp[1].substring(0, firstNumTo);
            if (!fromPadding.equals(toPadding)) {
                throw new TableRule.ParseException("padding different");
            }
            suf.setTbSuffixPadding(fromPadding);
        } else {
            suf.setTbSuffixPadding("");
        }
        int tbSuffixFrom = firstNot0From == -1 ? 0 : Integer.parseInt(temp[0]
            .substring(firstNot0From));

        suf.setTbSuffixFrom(tbSuffixFrom);
        int tbSuffixTo = Integer.parseInt(temp[1].substring(firstNot0To));
        suf.setTbSuffixTo(tbSuffixTo);
        if (tbSuffixTo <= tbSuffixFrom) {
            throw new TableRule.ParseException();
        }
        int tbSuffixWidth = temp[0].length() != temp[1].length() ? 0 : temp[0].length()
                                                                       - firstNumFrom;

        suf.setTbSuffixWidth(tbSuffixWidth);
        int tbNumForEachDb = -1;
        if (TableSuffixTypeEnum.resetForEachDB.getValue().equals(suf.getTbType()))
            tbNumForEachDb = -1;
        else {
            tbNumForEachDb = (tbSuffixTo - tbSuffixFrom + 1) / dbIndexSize;
        }
        suf.setTbNumForEachDb(tbNumForEachDb);
    }

    protected void parseTwoColumn(String part2, int dbIndexSize) throws TableRule.ParseException {
        Suffix suf = (Suffix) this.listSuffix.get(0);
        //twoColumnForEachDB
        String[] parts = part2.split(",");
        if (parts.length != 2) {
            throw new TableRule.ParseException("twoColumnForEachDB must have two range");
        }
        int tbNumForEachDb = -1;
        parseOneRange(parts[0], suf, dbIndexSize);
        suf.setTbNumForEachDb(tbNumForEachDb);
        Suffix suf2 = new Suffix();
        parseOneRange(parts[1], suf2, dbIndexSize);
        suf2.setTbNumForEachDb(tbNumForEachDb);
        this.listSuffix.add(suf2);
    }

    protected void parseDbIndex(String part2, int dbIndexSize) throws TableRule.ParseException {
        Suffix suf = (Suffix) this.listSuffix.get(0);
        parseOneRange(part2, suf, dbIndexSize);
        suf.setTbNumForEachDb(-1);
    }

    protected void parseTbSuffix(String[] dbIndexes) throws TableRule.ParseException {
        Suffix suf = (Suffix) this.listSuffix.get(0);

        String[] temp = this.tbSuffix.split(":");
        if (TableSuffixTypeEnum.groovyAdjustTableList.getValue().equals(temp[0].trim())) {
            if (temp.length != 3) {
                throw new TableRule.ParseException();
            }
        } else if (temp.length != 2) {
            throw new TableRule.ParseException();
        }

        String type = temp[0].trim();
        suf.setTbType(type);

        //resetForEachDB:
        //值操作
        //[_0000-_0001]
        String part2 = temp[1].trim();
        if (TableSuffixTypeEnum.twoColumnForEachDB.getValue().equals(type)) {
            //传入的值
            parseTwoColumn(part2, dbIndexes.length);
        } else if (TableSuffixTypeEnum.dbIndexForEachDB.getValue().equals(type)) {
            parseDbIndex(part2, dbIndexes.length);
        } else if (TableSuffixTypeEnum.groovyTableList.getValue().equals(type)) {
            parseGroovyDbindex(part2);

            suf.setTbNumForEachDb(-1);
        } else if (TableSuffixTypeEnum.groovyThroughAllDBTableList.getValue().equals(type)) {
            parseGroovyDbindex(part2);
        } else if (TableSuffixTypeEnum.groovyAdjustTableList.getValue().equals(type)) {
            String groovyExpression = temp[2].trim();
            parseGroovyDbindex(groovyExpression);
        } else if (!TableSuffixTypeEnum.none.getValue().equals(type)) {
            parseOneRange(part2, suf, dbIndexes.length);
        }
    }

    private void parseGroovyDbindex(String part2) throws TableRule.ParseException {
        try {
            this.shell.parse(part2);
        } catch (CompilationFailedException e) {
            throw new TableRule.ParseException("groovy script with syntax error!");
        }
    }

    public String getExpression() throws TableRule.ParseException {
        String[] temp = this.tbSuffix.split(":");
        if (TableSuffixTypeEnum.groovyAdjustTableList.getValue().equals(temp[0].trim())) {
            if (temp.length != 3) {
                throw new TableRule.ParseException();
            }
            return temp[1].trim() + ":" + temp[2].trim();
        }
        if (temp.length != 2) {
            throw new TableRule.ParseException();
        }
        return temp[1].trim();
    }

    private static int firstNum(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((c >= '0') && (c <= '9')) {
                return i;
            }
        }
        return -1;
    }

    private static int firstNot0(String str, int start) {
        for (int i = start; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c != '0') {
                return i;
            }
        }
        return -1;
    }

    public Suffix getSuffix(int index) {
        return (Suffix) this.listSuffix.get(index);
    }

    public String getTbSuffix() {
        return this.tbSuffix;
    }

    public void setTbSuffix(String tbSuffix) {
        this.tbSuffix = tbSuffix;
    }
}
