package calculator;

import java.util.Stack;
import java.util.StringTokenizer;

public class Infix {
	
	/**
	 * this function take expression delete any space and add = at last expression
	 * path this expression to brackets function
	 * make object from StringTokenizer path to expression and operator delimiter
	 * expression divided to two stack one for operand and other for operator
	 * push operand to valueStack and push operator to operatorStack
	 * path there two stack to resolve function
	 * 
	 * @param expression
	 * @return result of expression
	 */
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

    /**
     * call by resolve function
     * 
     * @param op
     * @return priority for any operator
     */
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

    /**
     * call by infix function
     * rearrange operatorStack by descending
     * path two value and operator with low priority to getResults function
     * push return value from this function to valuesStack again
     * 
     * @param values
     * @param operators
     */
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

    /**
     * call by resolve function
     * do suitable arithmetic on two operand
     * 
     * @param operand1
     * @param operator
     * @param operand2
     * @return result of 'operand1 operator operand2'
     */
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
    
    /**
     * call by infix function
     * take expression between brackets and path it to infix function
     * to get last result and return it to concatenate with original expression
     * delete brackets
     * 
     * 
     * @param exp
     * @return exp without brackets
     */
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
    	for(int i=0;i<exp.length();i++)
    	{
    		if(exp.charAt(i)=='-'&&exp.charAt(i+1)=='-')
    			exp=exp.replace("--", "+");
    		if(exp.charAt(i)=='+'&&exp.charAt(i+1)=='-')
    			exp=exp.replace("+-", "-");
    		if(exp.charAt(i)=='*'&&exp.charAt(i+1)=='-')
    		{
    			exp=exp.replace("*-", "*");
    			for(int k=i-1;k>=0;k--)
    			{
    				if(exp.charAt(k)=='+'||exp.charAt(k)=='-'||exp.charAt(k)=='*'
    						||exp.charAt(k)=='/')
    				{
    					exp=exp.substring(0,k+1)+"-"+exp.substring(k+1,exp.length());
    					i=0;
    					break;
    				}
    				else if(k==0&&exp.charAt(0)!='-')
    					exp="-"+exp;
    			}
    			for(int j=0;j<exp.length();j++)
    				if(j==0)
    				{
    	    			if(exp.charAt(j)=='-'&&exp.charAt(j+1)=='-')
    	        			exp=exp.replace("--", "");
    	    			break;
    				}
    		}
    		if(exp.charAt(i)=='/'&&exp.charAt(i+1)=='-')
    		{
    			exp=exp.replace("/-", "/");
    			for(int k=i-1;k>=0;k--)
    			{
    				if(exp.charAt(k)=='+'||exp.charAt(k)=='-'||exp.charAt(k)=='*'
    						||exp.charAt(k)=='/')
    				{
    					exp=exp.substring(0,k+1)+"-"+exp.substring(k+1,exp.length());
    					i=0;
    					break;
    				}
    				else if(k==0&&exp.charAt(0)!='-')
    					exp="-"+exp;
    			}
    			for(int j=0;j<exp.length();j++)
    				if(j==0)
    				{
    	    			if(exp.charAt(j)=='-'&&exp.charAt(j+1)=='-')
    	        			exp=exp.replace("--", "");
    	    			break;
    				}
    		}
    	}
    	return exp;
    }
    
}
