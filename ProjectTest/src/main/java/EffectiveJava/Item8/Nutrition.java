/**     
 * @FileName: Nutrition.java   
 * @Package:EffectiveJava.Item8   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年3月29日 下午4:58:25   
 * @version V1.0     
 */
package EffectiveJava.Item8;

/**  
 * @ClassName: Nutrition   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年3月29日 下午4:58:25     
 */
public class Nutrition {

    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public Nutrition(int servingsize, int servings) {
        this(servingsize, servings, 0);
    }

    public Nutrition(int servingSize, int servings, int calories) {
        this(servingSize, servings, calories, 0);
    }

    public Nutrition(int servingSize, int servings, int calories, int fat) {
        this(servingSize, servings, calories, fat, 0);
    }

    public Nutrition(int servingSize, int servings, int calories, int fat, int sodium) {
        this(servingSize, servings, calories, fat, sodium, fat);

    }

    public Nutrition(int servingSize, int servings, int calories, int fat, int sodium,
                     int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }

}
