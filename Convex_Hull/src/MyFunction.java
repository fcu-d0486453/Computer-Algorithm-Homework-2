import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Stack;
import java.util.ArrayList; 
import java.util.Arrays;

public class MyFunction {
	
	private Graphics g;
	private int size;
	private int XRANGE;
	private int YRANGE;
	public int [] xPoints;//for generateXYPoint,X value
	public int [] yPoints;//for generateXYPoint,Y value
	public int [][] setXY;//coordinate (X,Y)   ,XY value
	private ArrayList<Point> PointsList;//所有點的參數
	
	MyFunction(Graphics g,int XRange,int YRange){
		this.g= g;
		this.size = 1;
		this.XRANGE = XRange;
		this.YRANGE = YRange;
		this.xPoints = null;
		this.yPoints = null;
		PointsList = null;
	}
	
	public void setPointsList(ArrayList<Point> PointsList) {
		this.PointsList = PointsList;
	}
	
	
	//利用 端點上下切 找尋 星狀折線
	public String findStarPolyLine(){
		//先依 X 排序大小
		java.util.Collections.sort(PointsList, new compare());
		//依照 X軸  把線連起來 會畫出刺刺的圖案
				String lineOrder="";
				for(int i=0;i<this.PointsList.size();i++) {
					lineOrder += PointsList.get(i).getName();
				}
				System.out.println("lineOrder:"+lineOrder);
				return lineOrder;
	}
	
	/*
	 * 找出所有凸包點, 回傳 ArrayList<Point>
	 * 用 Andrew's Monotone Chain 演算法
	 */
	public String findConvexPoint(){
		//findStarPolyLine() 必須先執行過才行
		
		//當stack 資料結構用
		ArrayList<Point> PointsStack = new ArrayList(); 
		
		//找下凸包　vvvvvv
		System.out.println("vvvvvv找下凸包vvvvvv");
		System.out.print("剔除:");
		for(int i=0;i<this.PointsList.size();i++) {
			//1.加入一個點
			PointsStack.add(PointsList.get(i));
			
			//2.檢查 是否要檢查,when 有三個點存在
			if( PointsStack.size() >= 3 ) 
			{
				/* 要找 下凸包 那 "趨勢向量" 應該要是 "負的",
				 * 跟 座標系 有關 這邊設的坐標系, "下凸" 必須 向量為 負的
				 * 故 向量 為"正"須排除
				 */
				while( PointsStack.size() >= 3 
						&& 
					   calcLastThreePointsVectorTrend( PointsStack ) > 0 ) 
				{
					//則 移除 "倒數第二項"
					System.out.print(" "+
						PointsStack.get(PointsStack.size()-2).getName()
					);
					PointsStack.remove( PointsStack.size()-2 );
					
					//繼續檢查
				}
			}
		}System.out.println();
		
		//PointsStack 已經檢查完畢 紀錄 下凸包的點名
		String underConvex="";
		for( int i=0;i<PointsStack.size();i++) {
			underConvex += PointsStack.get(i).getName();
		}
		//======================================
		//清空Stack
		PointsStack.clear();
		//======================================
		System.out.println("ʌʌʌʌʌʌ找上凸包ʌʌʌʌʌʌ");
		
		//找上凸包   ʌʌʌʌʌʌ
		System.out.print("剔除:");
		for(int i=0;i<this.PointsList.size();i++) {
			//1.加入一個點
			PointsStack.add(PointsList.get(i));
			
			//2.檢查 是否要檢查,when 有三個點存在
			if( PointsStack.size() >= 3 ) 
			{
				/* 要找 "上"凸包 那 "趨勢向量" 應該要是 "正的",
				 * 跟 座標系 有關 這邊設的坐標系, "上凸" 必須 向量為 "正"的
				 * 故 向量 為"負"須排除
				 */
				while( PointsStack.size() >= 3 
						&& 
					   calcLastThreePointsVectorTrend( PointsStack ) < 0 ) 
				{
					//則 移除 "倒數第二項"
					System.out.print(" "+
						PointsStack.get(PointsStack.size()-2).getName()
					);
					
					PointsStack.remove( PointsStack.size()-2 );
					
					//繼續檢查
				}
			}
		}System.out.println("\n---檢查完畢---");
		//PointsStack 已經檢查完畢 紀錄 "上"凸包的點名
		String upperConvex="";
		for( int i=0;i<PointsStack.size();i++) {
			upperConvex += PointsStack.get(i).getName();
		}
		
		//印出點名
		//======================================
		System.out.println("下凸包: " + underConvex);
		System.out.println("上凸包: " + upperConvex);
		String convexStr = "";
		convexStr += underConvex;
		
		//反向 上凸包點
		upperConvex  = new StringBuilder(upperConvex)
				.reverse().toString();
		
		//上凸只有兩個點, 代表 只有 左右兩個端點是 上凸包
		if( upperConvex.length() > 2 ) {
			//去除 upperConvex 首項 and 尾項 再 合併
			convexStr += upperConvex.
					substring(1, upperConvex.length());
			System.out.println("凸包點: " + convexStr);
		}else { //直接把原點 加回去
			convexStr += upperConvex.substring(upperConvex.length()-1);
			System.out.println("凸包點: " + convexStr);
		}
		//======================================
		
		return convexStr;
	}
	
	//檢查 ArrayList 最後三項 的Y軸向量趨勢,維護 convexList 保持 凸包
	public double calcLastThreePointsVectorTrend(ArrayList<Point> convexList){
		//注意順序 
		Point P2 = convexList.get(convexList.size()-1);
		Point P0 = convexList.get(convexList.size()-2);
		Point P1 = convexList.get(convexList.size()-3);
		/*
		System.out.println("P0 = "+ P0.getName());
		System.out.println("P1 = "+ P1.getName());
		System.out.println("P2 = "+ P2.getName());
		System.out.println("=================");
		*/
		// **向量/兩點距離 = 單為向量**
		double v1 = (P1.getY()-P0.getY())/twoPointsDistance(P0,P1),
			   v2 = (P2.getY()-P0.getY())/twoPointsDistance(P0,P2),
			   a=v1+v2;
		/*
		System.out.println(P0.getName()+"->"+P1.getName()+'\n'+
				P0.getName()+"->"+P2.getName()+" = "+
				a);
		*/
		//回傳  V(p0->p1) * V(p0->p2) 的 Y軸 向量趨勢
		return v1+v2;
	}
	
	//只供 此類別的方法 自行調用,找出該名稱的索引數值
	private int findPointIndexForItName(char c) {
		
		for(int i=0;i<PointsList.size();i++) {
			if(c == PointsList.get(i).getName()) {
				return i;
			}
		}
		
		//failed
		return -1;
	}
	
	//畫出 凸包  其實跟 drawStarPolyLine() 一樣 就名字不同而已XD
	public void drawConvexHullPoint(String convexOrder) {
		this.drawStarPolyLine(convexOrder);
	}
	
	//畫出 凸凹折線
	public void drawStarPolyLine(String cycleOrder) {
		int index,nextIndex;//該點的 索引數值
		
		
		//中間的點點們手牽手~
 		for(int i=0;i<cycleOrder.length()-1;i++){
			// cycleOrder[i](該點名字) 在 PointsList 的第幾個呢?
			index = findPointIndexForItName(cycleOrder.charAt(i));
			nextIndex = findPointIndexForItName(cycleOrder.charAt(i+1));
			this.g.drawLine(PointsList.get(index).getX(),
					PointsList.get(index).getY(),
					PointsList.get(nextIndex).getX(),
					PointsList.get(nextIndex).getY());
		}
		
	}
	
	//設定點的 大小
	public void setPointSize(int size) {
		this.size = size;
	}
	
	
	//自己流 drawPoint()
	public void drawPoint(int x,int y){
		//需要 往左上位移 中心點才會剛好 在 指定的x,y上
		g.fillOval(x-(size/2),y-(size/2),size,size);
	}
	
	//隨機生成 座標點 有給定範圍
	public void generateXYPoint(int size){
		Random ran = new Random();
		xPoints = new int[size];
		yPoints = new int[size];
		for(int i=0;i<size;i++) {
        	xPoints[i] = ((ran.nextInt(XRANGE))+1)-(XRANGE/2);
        	yPoints[i] = ((ran.nextInt(YRANGE))+1)-(YRANGE/2);
       }
	}
	public void showXYPoint() {
		for(int i=0;i<xPoints.length;i++) {
			System.out.println
				(this.PointsList.get(i).getName() + 
				"( "+ this.PointsList.get(i).getX() +
				", "+ this.PointsList.get(i).getY() +" )");
		}
		System.out.println("=======拖曳視窗大小改變點的分布=======");
	}
	
	//以下數學計算 
	//cosX 出來是數值(-1~1),為 P0---P2 && P0---P1 所夾的角
		public double cosX(Point P0,Point P1 ,Point P2){
			return innerProduct( P0, P1, P2) / 
			(twoPointsDistance(P0,P1) * twoPointsDistance(P0,P2));
			/*
			 *           U  *  V
			 *  cosX = ------------
			 *          |U| * |V|
			 *  
			 * */
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
	
}
