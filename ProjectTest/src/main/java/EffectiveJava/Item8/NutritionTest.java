/**     
 * @FileName: NutritionTest.java   
 * @Package:EffectiveJava.Item8   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 下午5:02:33   
 * @version V1.0     
 */
package EffectiveJava.Item8;

import org.junit.Test;

/**  
 * @ClassName: NutritionTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 下午5:02:33     
 */
public class NutritionTest {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    
    public static class Builder{
        private final int servingSize;
        private final int servings;
        
        //初始化默认的值
        private  int calories=0;
        private  int fat=0;
        private  int sodium=0;
        private  int carbohydrate=0;
        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }
        
        
        public Builder calories(int val){
            calories=val;return this;
        }
        
        
        public Builder fat(int val){
            fat=val;return this;
        }
        
        
        public NutritionTest build(){
            return new NutritionTest(this);
        }
        
    }
    
    
    private NutritionTest(Builder builder){
        servings=builder.servings;
        servingSize=builder.servingSize;
        calories=builder.calories;
        fat=builder.fat;
        sodium=builder.sodium;
        carbohydrate=builder.carbohydrate;
    }
    
    
    
}
