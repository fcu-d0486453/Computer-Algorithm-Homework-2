import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; 
import java.util.Scanner;

public class DrawGraphics extends Canvas {
	
	static int XRANGE = 900;
	static int YRANGE = 900;
	static int POINTSIZE = -1;//點的數量 進入 main 後給定
	 
	
    public static void main(String[] args) {
    	
    	if( args.length == 0 ) {//JAVA 的物件 接收參數
    		Scanner scanner = new Scanner(System.in);
    		
    		while( POINTSIZE < 3 ) {
    			System.out.print("請輸入 點 的數量 N ：");
    			POINTSIZE=scanner.nextInt();
    			
    			if( POINTSIZE >= 3) break;
    			else System.out.println("N 必須大於等於 3 !!");
    		}
    		
    	}else {//用命令列的 args 接收 參數
    		System.out.println("參數數量 = "+ args.length);
    		System.out.println("點的數量 = "+ args[1] );
    		POINTSIZE = Integer.valueOf( args[1] );
    		
    	}
    	
    	System.out.println("POINTSIZE = "+ POINTSIZE );
    	
        Frame frame = new Frame("Convex Hull Demo");
        frame.addWindowListener(new AdapterDemo());
        frame.setSize(XRANGE, YRANGE);
        
         
        DrawGraphics canvas = new DrawGraphics();
        frame.add(canvas, BorderLayout.CENTER);
         
        frame.setVisible(true);
    }
     
    public void paint(Graphics g) {
    	
    	String X_AXIS = "X-axis";
    	String Y_AXIS = "Y-axis";
    	
    	//我自己的 f 小工具(以利畫圖)
    	MyFunction f = new MyFunction(g, XRANGE, YRANGE);
    	
    	//字體設定
    	Font myFont = new Font("axis_font",Font.BOLD,20);
    	Font pointFont = new Font("point_font",Font.PLAIN,20);
    	g.setFont(myFont);
    	
    	//把圖形的原點(0,0)設在中間
    	g.translate(XRANGE/2, YRANGE/2);
    	
    	//畫出  X軸 綠色
    	g.setColor(Color.GREEN);
    	g.drawLine(-XRANGE/2,0,XRANGE/2,0); 
    	//印出  X軸上的 文字
    	g.drawString(X_AXIS+"(+)", XRANGE/2-19*X_AXIS.length(), X_AXIS.length()*-1);
    	g.drawString(X_AXIS+"(-)",-1*(XRANGE/2-2*X_AXIS.length()), X_AXIS.length()*-1);
    	
    	//畫出 Y軸 橘色
    	g.setColor(Color.ORANGE);
    	g.drawLine(0,-YRANGE/2,0,YRANGE/2);
    	//印出  Y軸上的 文字
    	g.drawString(Y_AXIS+"(+)", Y_AXIS.length(),YRANGE/2-5*X_AXIS.length());//寫上 Y軸 文字
    	g.drawString(Y_AXIS+"(-)", Y_AXIS.length(),-1*(YRANGE/2-4*X_AXIS.length()));//寫上 Y軸 文字
    	
    	//利用我修改的方法畫出點
    	f.setPointSize(10);
    	g.setColor(Color.CYAN);
    	f.drawPoint(0, 0);//畫出原點
    	
    	//轉回原色
    	g.setColor(Color.BLACK);
    	//將坐標軸用方框圍住
    	g.drawRect(-XRANGE/2, -YRANGE/2, XRANGE, YRANGE);
    	
    	//生成隨機座標 並印出
    	char[] alphabetCount ={'A'};
    	
    	//設定點的字型
    	g.setFont(pointFont);
    	//隨機生成 (X,Y)點
    	f.generateXYPoint(POINTSIZE);
    	
    	//新建一個 ArrayList 物件
    	ArrayList <Point> PointsList = new ArrayList();
    	
    	//將物件丟給 MyFunction的成員參數做紀錄
    	f.setPointsList(PointsList);
    	 for(int i=0;i<POINTSIZE;i++){
    		 //畫出點
    		 f.drawPoint(f.xPoints[i], f.yPoints[i]);
    		 
    		 //new 一個物件 "點"
    		 Point Onepoint = new Point();
    		 
    		 //用ArrayList<Point> 紀錄 每個點的參數
    		 Onepoint.setName( alphabetCount[0]);
    		 Onepoint.setX(f.xPoints[i]);
    		 Onepoint.setY(f.yPoints[i]);
    		 PointsList.add(Onepoint);
    		 
    		 //在點的旁邊加名字ASCII 
    		 g.drawChars(alphabetCount, 0, 1,f.xPoints[i]+5, f.yPoints[i]+15);
    		 alphabetCount[0]++;
    	 }
    	 
    	 //依照X軸做連線 
    	 g.setColor(Color.GRAY);
    	 f.drawStarPolyLine(f.findStarPolyLine());
    	 
    	 //凸包點找出來
    	 String convexStr = f.findConvexPoint();
    	 
    	 //在 console 印出 每個點
    	 f.showXYPoint();
    	 
    	 //畫出凸包的點
    	 g.setColor(Color.RED);
    	 f.drawConvexHullPoint(convexStr);
    	 
    	 //程式 J束 惹
    }    
}

class AdapterDemo extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}