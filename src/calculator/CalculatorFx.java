package calculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CalculatorFx extends Application {
	
	private TextField monitor=new TextField();
	/**
	 * dotcount variable used for disable dot button when used at one time
	 */
	private int dotcount=0;
	/**
	 * brcount used for knowing expression between two brackets
	 */
	private int brcount=0;
	/**
	 * bracketsCounter used for knowing number of closed brackets must user enter in expression
	 */
	private int bracketsCounter=0;
	/**
	 * temperer variable 
	 */
	private double num;
	/**
	 * get text on button
	 */
	private String num1;
	/**
	 * get text on monitor
	 */
	private String num2;
	/**
	 * get operation on button
	 */
	private String Op;
	/**
	 * display final result
	 */
	private String display;
	/**
	 * get text which that user want do operation on it such that (sqart,squer,mod,factorial)
	 */
	private String after;
	/**
	 * get text which no change on it when do some operations
	 */
	private String before;
	/**
	 * check number is float or integer
	 */
	private boolean check;
	/**
	 * check monitor is contain text or no
	 */
	private boolean checkMonitor=false;
	/**
	 * object from class Infix
	 */
	private Infix calculation=new Infix();
	
	
	/**
	 * this function used for display numbers on screen
	 * @param e
	 */
	public void btclickedNum(ActionEvent e)
	{
		display=monitor.getText();
		/*if(!display.endsWith(")"))
		{*/
			if(display.contains(Character.toString('I'))||display.contains(Character.toString('N'))
				||display.contains(Character.toString('E'))||display.contains(Character.toString('U'))
				||checkMonitor||(display.startsWith("0")&&display.length()==1))
			{
				monitor.clear();
				dotcount=0;
				checkMonitor=false;
			}
			num1=((Button)e.getSource()).getText();
			num2=monitor.getText();
			if(num2.endsWith(")"))
				num2+="*";
			else if(num2.endsWith("0")&&!num2.contains(Character.toString('.'))
					&&!Character.isDigit(num2.charAt(num2.length()-2)))
				num2=num2.substring(0, num2.length()-1);
			display=num2+num1;
			monitor.setText(display);
			checkMonitor=false;
		//}
	}
	
	/**
	 * this function used for do operations such that clear monitor, clear digit, calculate expression by path
	 * expression to function infix, call two function DoOperation which used for sqart , squer, 
	 * mod, factorial, WriteArthimetic which display simple arithmetic, display dot and brackets
	 * @param e
	 */
	public void btOpclicked(ActionEvent e)
	{
		String s= monitor.getText();
		Op=((Button)e.getSource()).getText();
		switch(Op)
		{
			case "⌂":	monitor.clear();
						dotcount=0;
					 	bracketsCounter=0;
					 	checkMonitor=false;
					 	break;
					 
			case "X²":	DoOperation(Op, s);
					  	break;
			
			case "√":	DoOperation(Op,s);
					  	break;
			
			case "%":	DoOperation(Op,s);
						break;
						
			case "X!":	DoOperation(Op,s);
						break;
						
			case ".":	if(dotcount==0&&!s.endsWith(")")
							&&!s.contains(Character.toString('E'))
							&&!s.contains(Character.toString('I'))
							&&!s.contains(Character.toString('N')))
						{
							num1=((Button)e.getSource()).getText();
							num2=monitor.getText();
							if(num2.length()==0||num2.endsWith("+")||
									num2.endsWith("-")||num2.endsWith("*")||
									num2.endsWith("/")||num2.endsWith("("))
								num2+="0";
							display=num2+num1;
							monitor.setText(display);
							dotcount=1;
							checkMonitor=false;
						}
						break;
			
			case "←":	if(s.length()>0)
						{
							String removeDigit=s.substring(0,s.length()-1);
							monitor.clear();
							monitor.setText(removeDigit);
							if(s.endsWith("."))
								if(!removeDigit.endsWith("."))
									dotcount=0;
							if(s.endsWith(")"))
								if(!removeDigit.endsWith(")"))
									bracketsCounter++;
							if(s.endsWith("("))
								if(!removeDigit.endsWith("("))
									bracketsCounter--;
							checkMonitor=false;
						}
						break;
			
			case "+":	WriteArithmetic(Op);
						break;
			
			case "-":	WriteArithmetic(Op);
						break;
			
			case "*":	WriteArithmetic(Op);
						break;
			
			case "/":	WriteArithmetic(Op);
						break;
						
			case "=":	if(s.length()>0&&bracketsCounter==0&&!s.endsWith("-")
							&&!s.endsWith("+")&&!s.endsWith("*")&&!s.endsWith("/")
							&&!s.contains(Character.toString('E'))
							&&!s.contains(Character.toString('I'))
							&&!s.contains(Character.toString('N')))
						{
							double result=calculation.infix(s);
							if(!checkNum(result))
							{
								display=""+result;
								display=display.substring(0,display.length()-2);
								dotcount=0;
							}
							else
							{
								display=""+result;
								dotcount=1;
							}
							if(display.contains(Character.toString('N')))
								display="Undefined result";
							if(display.contains(Character.toString('I')))
								display="Infinity";
							monitor.clear();
							monitor.setText(display);
							checkMonitor=true;
						}
						break;
						
			case "(":	if(!s.endsWith(".")&&!s.contains(Character.toString('E'))
							&&!s.contains(Character.toString('I'))
							&&!s.contains(Character.toString('N')))
						{
							num1=((Button)e.getSource()).getText();
							num2=monitor.getText();
							if(num2.length()>0&&(num2.endsWith(")")
									||Character.isDigit(num2.charAt(num2.length()-1))))
								num2+="*";
							display=num2+num1;
							monitor.setText(display);
							bracketsCounter++;
							checkMonitor=false;
						}
						break;
			
			case ")":	if(bracketsCounter>0&&!s.endsWith("(")&&!s.endsWith("-")
							&&!s.endsWith("+")&&!s.endsWith("/")&&!s.endsWith("*"))
						{
							num1=((Button)e.getSource()).getText();
							num2=monitor.getText();
							display=num2+num1;
							monitor.setText(display);
							bracketsCounter--;
							checkMonitor=false;
						}
						break;
			
			default:	monitor.setText("Invalid Operation");
						break;
		}
	}
	
	/**
	 * calculate factorial
	 * @param num
	 * @return f factorial number
	 */
	public double fact(double num)
	{
		if(!checkNum(num))
			return num;
		double f=1;
		for(double i=num;i>0;i--)
			f*=i;
		return f;
	}
	
	/**
	 * @param Num
	 * @return true if Num float and false if Num integer
	 */
	public boolean checkNum(double Num)
	{
		String decimal=""+Num;
		int dec=0;
		for(int j=0;j<decimal.length();j++)
		{
			if(decimal.charAt(j)=='.')
			{
				decimal=decimal.substring(j+1,decimal.length());
				if(decimal.contains(Character.toString('E')))
					return true;
				if(decimal.length()<10)
					dec=Integer.parseInt(decimal);
				else
				{
					int k=decimal.length()-9;
					decimal=decimal.substring(0,decimal.length()-k);
					dec=Integer.parseInt(decimal);
				}
				break;
			}
		}
		if(dec>0)
			return true;
		return false;
	}
	
	/**
	 * find (sqart, squer, mod, factorial)
	 * @param op operation (sqart, squer, mod, factorial)
	 * @param s expression
	 */
	public void DoOperation(String op,String s)
	{
		if(s.length()>0&&!s.endsWith("(")&&!s.endsWith("-")
				&&!s.endsWith("+")&&!s.endsWith("*")&&!s.endsWith("/")
				&&!s.contains(Character.toString('E'))
				&&!s.contains(Character.toString('I'))
				&&!s.contains(Character.toString('N'))
				&&!s.contains(Character.toString('U')))
			{
				if(s.endsWith(")"))
				{
					for(int i=(s.length()-1);i>=0;i--)
					{
						if(s.charAt(i)==')')
							brcount++;
						if(s.charAt(i)=='(')
							brcount--;
						if(brcount==0)
						{
							after=s.substring(i,s.length());
							before=s.substring(0,i);
							num=calculation.infix(after);
							if(op=="X²")
							{
								after=""+(double)Math.pow(num, 2);
								num=Double.parseDouble(after);
								if(!checkNum(num))
								{
									after=after.substring(0,after.length()-2);
									display=before+after;
									dotcount=0;
								}
								else
								{
									display=before+after;
									dotcount=1;
								}
								break;
							}
							else if(op=="√")
							{
								if(num<0)
									display="Invalid Input";
								else
								{
									after=""+(double)Math.sqrt(num);
									num=Double.parseDouble(after);
									if(!checkNum(num))
									{
										after=after.substring(0,after.length()-2);
										display=before+after;
										dotcount=0;
									}
									else
									{
										display=before+after;
										dotcount=1;
									}
								}
								break;
							}
							else if(op=="%")
							{
								after=""+(num/100);
								display=before+after;
								dotcount=1;
								break;
							}
							else if(op=="X!")
							{
								check=checkNum(num);
								if(num<0||check)
									display="Invalid Input";
								else
								{
									after=""+fact(num);
									num=Double.parseDouble(after);
									if(!checkNum(num))
									{
										after=after.substring(0,after.length()-2);
										display=before+after;
										dotcount=0;
									}
									else
									{
										display=before+after;
										dotcount=1;
									}
								}
								break;
							}
						}
					}
				}
				else if(!s.endsWith(")"))
				{
					for(int i=(s.length()-1);i>=0;i--)
					{
						if(s.charAt(i)=='-'&&s.charAt(i-1)=='(')
						{
							after=s.substring(i-1,s.length())+")";
							before=s.substring(0,i);
							num=calculation.infix(after);
							if(op=="X²")
							{
								after=""+(double)Math.pow(num, 2);
								num=Double.parseDouble(after);
								if(!checkNum(num))
								{
									after=after.substring(0,after.length()-2);
									display=before+after;
									dotcount=0;
								}
								else
								{
									display=before+after;
									dotcount=1;
								}
								break;
							}
							else if(op=="√")
							{
								if(num<0)
									display="Invalid Input";
								else
								{
									after=""+(double)Math.sqrt(num);
									num=Double.parseDouble(after);
									if(!checkNum(num))
									{
										after=after.substring(0,after.length()-2);
										display=before+after;
										dotcount=0;
									}
									else
									{
										display=before+after;
										dotcount=1;
									}
								}
								break;
							}
							else if(op=="%")
							{
								after=""+(num/100);
								display=before+after;
								dotcount=1;
								break;
							}
							else if(op=="X!")
							{
								check=checkNum(num);
								if(num<0||check)
									display="Invalid Input";
								else
								{
									after=""+fact(num);
									num=Double.parseDouble(after);
									if(!checkNum(num))
									{
										after=after.substring(0,after.length()-2);
										display=before+after;
										dotcount=0;
									}
									else
									{
										display=before+after;
										dotcount=1;
									}
								}
								break;
							}
						}
						else if(s.charAt(i)=='(')
						{
							after=s.substring(i+1,s.length());
							before=s.substring(0,i+1);
							num=Double.parseDouble(after);
							if(op=="X²")
							{
								after=""+(double)Math.pow(num, 2);
								num=Double.parseDouble(after);
								if(!checkNum(num))
								{
									after=after.substring(0,after.length()-2);
									display=before+after;
									dotcount=0;
								}
								else
								{
									display=before+after;
									dotcount=1;
								}
								break;
							}
							else if(op=="√")
							{
								if(num<0)
									display="Invalid Input";
								else
								{
									after=""+(double)Math.sqrt(num);
									num=Double.parseDouble(after);
									if(!checkNum(num))
									{
										after=after.substring(0,after.length()-2);
										display=before+after;
										dotcount=0;
									}
									else
									{
										display=before+after;
										dotcount=1;
									}
								}
								break;
							}
							else if(op=="%")
							{
								after=""+(num/100);
								display=before+after;
								dotcount=1;
								break;
							}
							else if(op=="X!")
							{
								check=checkNum(num);
								if(num<0||check)
									display="Invalid Input";
								else
								{
									after=""+fact(num);
									num=Double.parseDouble(after);
									if(!checkNum(num))
									{
										after=after.substring(0,after.length()-2);
										display=before+after;
										dotcount=0;
									}
									else
									{
										display=before+after;
										dotcount=1;
									}
								}
								break;
							}
						}
						else if(s.charAt(i)=='+'||s.charAt(i)=='-'||s.charAt(i)=='*'
								||s.charAt(i)=='/')
						{
							after=s.substring(i+1,s.length());
							if(after.startsWith("("))
							{
								after=after.substring(1, after.length());
								before=s.substring(0,i+1)+"(";
								num=Double.parseDouble(after);
								if(op=="X²")
								{
									after=""+(double)Math.pow(num, 2);
									num=Double.parseDouble(after);
									if(!checkNum(num))
									{
										after=after.substring(0,after.length()-2);
										display=before+after;
										dotcount=0;
									}
									else
									{
										display=before+after;
										dotcount=1;
									}
									break;
								}
								else if(op=="√")
								{
									if(num<0)
										display="Invalid Input";
									else
									{
										after=""+(double)Math.sqrt(num);
										num=Double.parseDouble(after);
										if(!checkNum(num))
										{
											after=after.substring(0,after.length()-2);
											display=before+after;
											dotcount=0;
										}
										else
										{
											display=before+after;
											dotcount=1;
										}
									}
									break;
								}
								else if(op=="%")
								{
									after=""+(num/100);
									display=before+after;
									dotcount=1;
									break;
								}
								else if(op=="X!")
								{
									check=checkNum(num);
									if(num<0||check)
										display="Invalid Input";
									else
									{
										after=""+fact(num);
										num=Double.parseDouble(after);
										if(!checkNum(num))
										{
											after=after.substring(0,after.length()-2);
											display=before+after;
											dotcount=0;
										}
										else
										{
											display=before+after;
											dotcount=1;
										}
									}
									break;
								}
							}
							else
							{
								before=s.substring(0,i+1);
								num=Double.parseDouble(after);
								if(op=="X²")
								{
									after=""+(double)Math.pow(num, 2);
									num=Double.parseDouble(after);
									if(!checkNum(num))
									{
										after=after.substring(0,after.length()-2);
										display=before+after;
										dotcount=0;
									}
									else
									{
										display=before+after;
										dotcount=1;
									}
									break;
								}
								else if(op=="√")
								{
									if(num<0)
										display="Invalid Input";
									else
									{
										after=""+(double)Math.sqrt(num);
										num=Double.parseDouble(after);
										if(!checkNum(num))
										{
											after=after.substring(0,after.length()-2);
											display=before+after;
											dotcount=0;
										}
										else
										{
											display=before+after;
											dotcount=1;
										}
									}
									break;
								}
								else if(op=="%")
								{
									after=""+(num/100);
									display=before+after;
									dotcount=1;
									break;
								}
								else if(op=="X!")
								{
									check=checkNum(num);
									if(num<0||check)
										display="Invalid Input";
									else
									{
										after=""+fact(num);
										num=Double.parseDouble(after);
										if(!checkNum(num))
										{
											after=after.substring(0,after.length()-2);
											display=before+after;
											dotcount=0;
										}
										else
										{
											display=before+after;
											dotcount=1;
										}
									}
									break;
								}
							}
						}
						else if((!s.contains(Character.toString('+'))
								&&!s.contains(Character.toString('-'))
								&&!s.contains(Character.toString('*'))
								&&!s.contains(Character.toString('/'))
								&&!s.contains(Character.toString('(')))
								||s.startsWith("-"))
						{
							num=calculation.infix(monitor.getText());
							if(op=="X²")
							{
								display=""+(double)Math.pow(num, 2);
								num=Double.parseDouble(display);
								if(!checkNum(num))
								{
									display=""+num;
									display=display.substring(0,display.length()-2);
									dotcount=0;
								}
								else
									dotcount=1;
								break;
							}
							else if(op=="√")
							{
								if(num<0)
									display="Invalid Input";
								else
								{
									display=""+(double)Math.sqrt(num);
									num=Double.parseDouble(display);
									if(!checkNum(num))
									{
										display=""+num;
										display=display.substring(0,display.length()-2);
										dotcount=0;
									}
									else
										dotcount=1;
								}
								break;
							}
							else if(op=="%")
							{
								display=""+(num/100);
								dotcount=1;
								break;
							}
							else if(op=="X!")
							{
								check=checkNum(num);
								if(num<0||check)
									display="Invalid Input";
								else
								{
									display=""+fact(num);
									num=Double.parseDouble(display);
									if(!checkNum(num))
									{
										display=""+num;
										display=display.substring(0,display.length()-2);
										dotcount=0;
									}
								}
								break;
							}
						}
					}
				}
				if(display.contains(Character.toString('N')))
					display="Undefined result";
				monitor.setText(display);
				checkMonitor=false;
			}
	}
	
	/**
	 * display (*-+/) on monitor
	 * @param op
	 */
	public void WriteArithmetic(String op)
	{
		display=monitor.getText();
		if(display.length()>0
			&&!display.contains(Character.toString('E'))
			&&!display.contains(Character.toString('I'))
			&&!display.contains(Character.toString('N')))
		{
			if((display.endsWith("+")||display.endsWith("-")
					||display.endsWith("*")||display.endsWith("/")
					||display.endsWith(".")))
			{
				display=display.substring(0, display.length()-1);
				if(display.endsWith("("))
					display+="-";
				else
					display+=op;
				monitor.setText(display);
				dotcount=0;
				checkMonitor=false;
			}
			else if(!display.endsWith("("))
			{
				display+=op;
				monitor.setText(display);
				dotcount=0;
				checkMonitor=false;
			}
			else if(display.endsWith("(")&&op=="-")
			{
				display+=op;
				monitor.setText(display);
				dotcount=0;
				checkMonitor=false;
			}
		}
		else if(display.length()==0)
		{
			monitor.setText("0");
			display=monitor.getText();
			display+=op;
			monitor.setText(display);
			dotcount=0;
			checkMonitor=false;
		}
	}
	
	/**
	 * set information to buttons
	 * @param bt button
	 * @param v container
	 */
	public void btInfo(Button bt,VBox v)
	{
		if(bt.getText()=="=")
		{
			bt.setStyle("-fx-font-size:14pt;-fx-background-radius:20;"
					+ "-fx-color:orange");
			bt.prefWidthProperty().bind(v.widthProperty().divide(4));
			bt.prefHeightProperty().bind(v.heightProperty().divide(4));
		}
		else
		{
			bt.setStyle("-fx-font-size:14pt;-fx-background-radius:20");
			bt.prefWidthProperty().bind(v.widthProperty().divide(4));
			bt.prefHeightProperty().bind(v.heightProperty().divide(4));
		}
	}
	
	/**
	 * set information to monitor
	 * @param screen monitor
	 * @param v container
	 */
	public void monitorInfo(TextField screen,VBox v)
	{
		screen.prefWidthProperty().bind(v.widthProperty());
		screen.prefHeightProperty().bind(v.heightProperty().divide(2));
		screen.setFont(Font.font(24));
		screen.setEditable(false);
		screen.setStyle("-fx-background-radius:20");
		screen.setAlignment(Pos.CENTER_RIGHT);
	}
	
 	public void start(Stage stage)
	{
		GridPane pane=new GridPane();
		
		GridPane panebt=new GridPane();
		panebt.setHgap(5);
		panebt.setVgap(5);
		
		VBox Vbox=new VBox(pane,panebt);
		Vbox.setStyle("-fx-color:black");
		
		monitorInfo(monitor,Vbox);
		pane.getChildren().add(monitor);
		
		Button bt1=new Button("1");
		btInfo(bt1,Vbox);
		panebt.add(bt1, 0, 3);
		bt1.setOnAction(e->btclickedNum(e));
		
		Button bt2=new Button("2");
		btInfo(bt2,Vbox);
		panebt.add(bt2, 1, 3);
		bt2.setOnAction(e->btclickedNum(e));
		
		Button bt3=new Button("3");
		btInfo(bt3,Vbox);
		panebt.add(bt3, 2, 3);
		bt3.setOnAction(e->btclickedNum(e));
		
		Button bt4=new Button("4");
		btInfo(bt4,Vbox);
		panebt.add(bt4, 0, 2);
		bt4.setOnAction(e->btclickedNum(e));
		
		Button bt5=new Button("5");
		btInfo(bt5,Vbox);
		panebt.add(bt5, 1, 2);
		bt5.setOnAction(e->btclickedNum(e));
		
		Button bt6=new Button("6");
		btInfo(bt6,Vbox);
		panebt.add(bt6, 2, 2);
		bt6.setOnAction(e->btclickedNum(e));
		
		Button bt7=new Button("7");
		btInfo(bt7,Vbox);
		panebt.add(bt7, 0, 1);
		bt7.setOnAction(e->btclickedNum(e));
		
		Button bt8=new Button("8");
		btInfo(bt8,Vbox);
		panebt.add(bt8, 1, 1);
		bt8.setOnAction(e->btclickedNum(e));
		
		Button bt9=new Button("9");
		btInfo(bt9,Vbox);
		panebt.add(bt9, 2, 1);
		bt9.setOnAction(e->btclickedNum(e));
		
		Button bt0=new Button("0");
		btInfo(bt0,Vbox);
		panebt.add(bt0, 1, 4);
		bt0.setOnAction(e->btclickedNum(e));
		
		
		Button btce=new Button("←");
		btInfo(btce,Vbox);
		panebt.add(btce, 4, 1);
		btce.setOnAction(e->btOpclicked(e));
			
		Button btc=new Button("⌂");
		btInfo(btc,Vbox);
		panebt.add(btc, 5, 1);
		btc.setOnAction(e->btOpclicked(e));
		
		
		Button btdiv=new Button("/");
		btInfo(btdiv,Vbox);
		panebt.add(btdiv, 3, 1);
		btdiv.setOnAction(e->btOpclicked(e));
		
		Button btmul=new Button("*");
		btInfo(btmul,Vbox);
		panebt.add(btmul, 3, 2);
		btmul.setOnAction(e->btOpclicked(e));
		
		Button btmin=new Button("-");
		btInfo(btmin,Vbox);
		panebt.add(btmin, 3, 3);
		btmin.setOnAction(e->btOpclicked(e));
		
		Button btplus=new Button("+");
		btInfo(btplus,Vbox);
		panebt.add(btplus, 3, 4);
		btplus.setOnAction(e->btOpclicked(e));
		
		Button bteq=new Button("=");
		btInfo(bteq,Vbox);
		panebt.add(bteq, 4, 4);
		bteq.setOnAction(e->btOpclicked(e));
		
		
		Button btl=new Button("(");
		btInfo(btl,Vbox);
		panebt.add(btl, 4, 2);
		btl.setOnAction(e->btOpclicked(e));
		
		Button btr=new Button(")");
		btInfo(btr,Vbox);
		panebt.add(btr, 5, 2);
		btr.setOnAction(e->btOpclicked(e));
	
		Button btdot=new Button(".");
		btInfo(btdot,Vbox);
		panebt.add(btdot, 0, 4);
		btdot.setOnAction(e->btOpclicked(e));
		
		
		Button btsquar=new Button("X²");
		btInfo(btsquar,Vbox);
		panebt.add(btsquar, 4, 3);
		btsquar.setOnAction(e->btOpclicked(e));
		
		Button btsqrt=new Button("√");
		btInfo(btsqrt,Vbox);
		panebt.add(btsqrt, 5, 3);
		btsqrt.setOnAction(e->btOpclicked(e));
		
		Button btmod=new Button("%");
		btInfo(btmod,Vbox);
		panebt.add(btmod, 2, 4);
		btmod.setOnAction(e->btOpclicked(e));
		
		Button btfact=new Button("X!");
		btInfo(btfact,Vbox);
		panebt.add(btfact, 5, 4);
		btfact.setOnAction(e->btOpclicked(e));
		
		/*Button exit=new Button("exit");
		exit.setPrefSize(40, 40);
		panebt.add(exit, 0, 5);
		exit.setOnAction(e->{
			System.exit(0);
		});*/
		
		Scene scene=new Scene(Vbox,300,400);
		stage.setTitle("Casio");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);

	}

}
