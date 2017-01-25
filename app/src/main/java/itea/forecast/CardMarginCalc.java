package itea.forecast;

/**
 * Created by shevc on 21.01.2017.
 */

public class CardMarginCalc {
    float density;
    int partialWidth;
    int pageMargin;

    public CardMarginCalc(float density){
        this.density = density;
    }

    public int getPageMargin(){
        pageMargin = 8 * (int)density;
        return pageMargin;
    }

    public  int getPartialWidth(){
        partialWidth = 16 * (int)density;
        return partialWidth;
    }
}
