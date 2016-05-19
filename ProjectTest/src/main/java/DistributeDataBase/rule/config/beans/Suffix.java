/**
 * 
 */
package DistributeDataBase.rule.config.beans;

/** 
* @ClassName: Suffix 
* @Description: 
* @author LUCKY
* @date 2016年5月17日 下午6:46:51 
*  
*/
public class Suffix {

    private int    tbNumForEachDb;
    private int    tbSuffixFrom    = 0;
    private int    tbSuffixTo      = -1;
    private int    tbSuffixWidth   = 4;
    private String tbSuffixPadding = "_";
    private String tbType;

    public int getTbNumForEachDb() {
        return this.tbNumForEachDb;
    }

    public void setTbNumForEachDb(int tbNumForEachDb) {
        this.tbNumForEachDb = tbNumForEachDb;
    }

    public int getTbSuffixFrom() {
        return this.tbSuffixFrom;
    }

    public void setTbSuffixFrom(int tbSuffixFrom) {
        this.tbSuffixFrom = tbSuffixFrom;
    }

    public int getTbSuffixTo() {
        return this.tbSuffixTo;
    }

    public void setTbSuffixTo(int tbSuffixTo) {
        this.tbSuffixTo = tbSuffixTo;
    }

    public int getTbSuffixWidth() {
        return this.tbSuffixWidth;
    }

    public void setTbSuffixWidth(int tbSuffixWidth) {
        this.tbSuffixWidth = tbSuffixWidth;
    }

    public String getTbSuffixPadding() {
        return this.tbSuffixPadding;
    }

    public void setTbSuffixPadding(String tbSuffixPadding) {
        this.tbSuffixPadding = tbSuffixPadding;
    }

    public void setTbSuffixTo(String[] dbIndexes) {
        this.tbSuffixTo = (dbIndexes.length - 1 + this.tbSuffixFrom);
    }

    public String getTbType() {
        return this.tbType;
    }

    public void setTbType(String tbType) {
        this.tbType = tbType;
    }
}
