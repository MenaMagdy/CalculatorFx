package calculator;

import java.util.Stack;
import java.util.StringTokenizer;

public class Infix {
	
	public double infix(String expression)
	    {
	        expression=expression.replaceAll("[\t\n ]", "")+"=";
	        expression=brackets(expression);
	        if(expression.startsWith("-"))
	        	expression="0"+expression;
	        String operator="*/+-=";
	        StringTokenizer tokenizer=new StringTokenizer(expression, operator, true);
	        Stack<String> operatorStack=new Stack<String>();
	        Stack<String> valueStack=new Stack<String>();
	        while(tokenizer.hasMoreTokens())
	        {
	            String token=tokenizer.nextToken();
	            if(operator.indexOf(token)<0)
	                valueStack.push(token);
	            else
	                operatorStack.push(token);
	            resolve(valueStack, operatorStack);
	        }
	        String lastOne=(String)valueStack.pop();
	        return Double.parseDouble(lastOne);   
	    }

    public int getPriority(String op)
    {
        if(op.equals("*") || op.equals("/"))
            return 1;
        else if(op.equals("+") || op.equals("-"))
            return 2;
        else if(op.equals("="))
            return 3;
        else
            return Integer.MIN_VALUE;
    }

    public void resolve(Stack<String> values, Stack<String> operators)
    {
        while(operators.size()>=2)
        {
            String first=(String)operators.pop();
            String second=(String)operators.pop();
            if(getPriority(first)<getPriority(second))
            {
                operators.push(second);
                operators.push(first);
                return;
            }
            else
            {
                String firstValue=(String)values.pop();
                String secondValue=(String)values.pop();
                values.push(getResults(secondValue, second, firstValue));
                operators.push(first);
            }
        }
    }

    public String getResults(String operand1, String operator, String operand2)
	    {
	        double op1=Double.parseDouble(operand1);
	        double op2=Double.parseDouble(operand2);
	        if(operator.equals("*"))
	            return ""+(op1*op2);
	        else if(operator.equals("/"))
	            return ""+(op1/op2);
	        else if(operator.equals("+"))
	            return ""+(op1+op2);
	        else if(operator.equals("-"))
	            return ""+(op1-op2);
	        else
	            return null;
	    }
    
    public String brackets(String exp)
    {
    	while(exp.contains(Character.toString('('))&&
    			exp.contains(Character.toString(')')))
    		for(int i=0;i<exp.length();i++)
    			if(exp.charAt(i)==')')
    				for(int j=i;j>=0;j--)
    					if(exp.charAt(j)=='(')
    					{
    						String in=exp.substring(j+1,i);
    						in=""+infix(in);
    						exp=exp.substring(0,j)+in+exp.substring(i+1);
    						i=j=0;
    					}
    	return exp;
    }

}
