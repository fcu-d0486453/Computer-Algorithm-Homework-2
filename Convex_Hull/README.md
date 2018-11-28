# 題目
![](https://i.imgur.com/UM6etNl.png)


# UML
> DrawGraphics : 主程式 負責畫出 圖形介面 繼承 canvas 類別(可以用來畫出座標、線、等其他圖案...)
> MyFunction : 簡單的函式，畫點、點對點連線、設定點數量、數學計算、等等......。
> compare : 實作 **Point類別** 的 **compareTo()** 方法，用以比較 Point 物件。
>  AdapterDemo : 繼承 JAVA 的抽象方法 WindowAdapter，以建立視窗。
```java=
public abstract class WindowAdapter
```

![](https://i.imgur.com/0xk7FnV.png)

# 參考演算法

## Andrew's Monotone Chain
![](https://i.imgur.com/lrFfJXf.png)
![](https://i.imgur.com/iXlLuNr.png)

## Andrew's Monotone Chain 演算法細部
![](http://images.cnitblog.com/i/571629/201405/042247221115220.png)
![](https://i.imgur.com/pm85pJX.png)

## 我所使用的方法
```java=
//cosX 出來是數值(-1~1),為 P0---P2 && P0---P1 所夾的角
public double cosX(Point P0,Point P1 ,Point P2){
	return innerProduct( P0, P1, P2) / 
	(twoPointsDistance(P0,P1) * twoPointsDistance(P0,P2));
}

//算內積  U*V ,P0->P1 && P0->P3 的內積喔
public double innerProduct(Point P0,Point P1 ,Point P2) {
	int x0=P0.getX(),y0=P0.getY(),
		x1=P1.getX(),y1=P1.getY(),
	    x2=P2.getX(),y2=P2.getY(),
        v1x=x1-x0,v1y=y1-y0,
        v2x=x2-x0,v2y=y2-y0;
		return (v1x*v2x)+(v1y*v2y);
}
	
	//兩點距離  |U|
public double twoPointsDistance(Point P1,Point P2) {
    return Math.sqrt( Math.pow(P1.getX()-P2.getX(),2) +
	    	       Math.pow(P1.getY()-P2.getY(),2));
}
```    
簡單來說就是找出  **角平分線** 

![](https://i.imgur.com/77F0VPC.png)











