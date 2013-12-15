package kloudy.mc.bettercommandblocks.util;

import java.util.logging.Logger;

import kloudy.mc.bettercommandblocks.BetterCommandBlocks;

public class CommandParser 
{
	private DataManager dm;
	Logger logger = BetterCommandBlocks.logger;
	
	public CommandParser(){
		dm = DataManager.getInstance();
	}
	
	/*
	 * Return a String array containing the real values of the two arguments
	 */
	public Object parseCommand(String str, DataType dataType)
	{		
		String[] strs = new String[2];
		Object result = null;
		
		if(dataType == DataType.STRING)
		{
			String[] args = new String[2];
			
			//add string
			if(str.matches("\\w*\\+\\*"))
			{
				strs = str.split("\\+");
				
				//first argument is a variable
				if(dm.variables.get(args[0]) != null){
					args[0] = (String)dm.variables.get(strs[0]).getValue();
				}
				
				//first argument is a string literal
				else{
					args[0] = strs[0];
				}
				
				//second argument is a variable
				if(dm.variables.get(args[1]) != null){
					args[1] = (String)dm.variables.get(strs[1]).getValue();
				}
				
				//second argument is a string literal
				else{
					args[1] = strs[1];
				}
				
				result = args[0] + args[1];
				logger.info((String)result);
			}
			
			//assign value to int
			else if(str.matches("\\w*"))
			{
				String arg1 = null;
				
				if(dm.variables.get(strs[0]) != null){
					arg1 = (String)dm.variables.get(strs[0]).getValue();
				}
				
				else{
					arg1 = str;
				}
				
				result = arg1;
				
				logger.info((String)result);
			}
		}
		
		else if(dataType == DataType.INT)
		{
			int arg1 = 0;
			int arg2 = 0;
			
			//add integers
			if(str.matches("\\w*\\+\\w*"))
			{
				strs = str.split("\\+");
				
				//first argument is a variable
				if(dm.variables.get(strs[0]) != null){
					arg1 = (Integer)dm.variables.get(strs[0]).getValue();
				}
				
				//first argument is an integer literal
				else if(strs[0].matches("\\d*")){
					arg1 = Integer.parseInt(strs[0]);
				}
				
				//second argument is a variable
				if(dm.variables.get(strs[1]) != null){
					arg2 = (Integer)dm.variables.get(strs[1]).getValue();
				}
				
				//second argument is an integer literal
				else if(strs[1].matches("\\d*")){
					arg2 = Integer.parseInt(strs[1]);
				}
				
				result = arg1 + arg2;				
			}
			
			//subtract integers
			else if(str.matches("\\w*\\-\\w*"))
			{
				strs = str.split("\\-");
				
				//first argument is a variable
				if(dm.variables.get(strs[0]) != null){
					arg1 = (Integer)dm.variables.get(strs[0]).getValue();
				}
				
				//first argument is an integer literal
				else if(strs[0].matches("\\d*")){
					arg1 = Integer.parseInt(strs[0]);
				}
				
				//second argument is a variable
				if(dm.variables.get(strs[1]) != null){
					arg2 = (Integer)dm.variables.get(strs[1]).getValue();
				}
				
				//second argument is an integer literal
				else if(strs[1].matches("\\d*")){
					arg2 = Integer.parseInt(strs[1]);
				}
				
				result = arg1 - arg2;			
				
			}
			
			//multiply integers
			else if(str.matches("\\w*\\*\\w*"))
			{
				strs = str.split("\\*");
				
				//first argument is a variable
				if(dm.variables.get(strs[0]) != null){
					arg1 = (Integer)dm.variables.get(strs[0]).getValue();
				}
				
				//first argument is an integer literal
				else if(strs[0].matches("\\d*")){
					arg1 = Integer.parseInt(strs[0]);
				}
				
				//second argument is a variable
				if(dm.variables.get(strs[1]) != null){
					arg2 = (Integer)dm.variables.get(strs[1]).getValue();
				}
				
				//second argument is an integer literal
				else if(strs[1].matches("\\d*")){
					arg2 = Integer.parseInt(strs[1]);
				}
				
				result = arg1 * arg2;							
			}
			
			//divide integers
			else if(str.matches("\\w*/\\w*"))
			{
				strs = str.split("/");
				
				//first argument is a variable
				if(dm.variables.get(strs[0]) != null){
					arg1 = (Integer)dm.variables.get(strs[0]).getValue();
				}
				
				//first argument is an integer literal
				else if(strs[0].matches("\\d*")){
					arg1 = Integer.parseInt(strs[0]);
				}
				
				//second argument is a variable
				if(dm.variables.get(strs[1]) != null){
					arg2 = (Integer)dm.variables.get(strs[1]).getValue();
				}
				
				//second argument is an integer literal
				else if(strs[1].matches("\\d*")){
					arg2 = Integer.parseInt(strs[1]);
				}
				
				if(arg2 != 0){			
					result = arg1 / arg2;
				}		
			}
			
			//assign value to int
			else if(str.matches("\\w*"))
			{
				if(dm.variables.get(str) != null){
					arg1 = (Integer)dm.variables.get(str).getValue();
				}
				
				else if(str.matches("\\d*")){
					arg1 = Integer.parseInt(str);
				}
				
				result = arg1;
			}
		}
		
		else if(dataType == DataType.DOUBLE)
		{
			double arg1 = 0;
			double arg2 = 0;
			
			//add doubles
			if(str.matches("\\w*\\+\\w*"))
			{
				strs = str.split("\\+");
				
				//first argument is a variable
				if(dm.variables.get(strs[0]) != null){
					arg1 = (Double)dm.variables.get(strs[0]).getValue();
				}
				
				//first argument is an integer literal
				else if(strs[0].matches("\\d*")){
					arg1 = Double.parseDouble(strs[0]);
				}
				
				//second argument is a variable
				if(dm.variables.get(strs[1]) != null){
					arg2 = (Double)dm.variables.get(strs[1]).getValue();
				}
				
				//second argument is an integer literal
				else if(strs[1].matches("\\d*")){
					arg2 = Double.parseDouble(strs[1]);
				}
				
				result = arg1 + arg2;
				
				logger.info(Double.toString((Double)result));
			}
			
			//subtract doubles
			else if(str.matches("\\w*\\-\\w*"))
			{
				strs = str.split("\\-");
				
				//first argument is a variable
				if(dm.variables.get(strs[0]) != null){
					arg1 = (Double)dm.variables.get(strs[0]).getValue();
				}
				
				//first argument is an integer literal
				else if(strs[0].matches("\\d*")){
					arg1 = Double.parseDouble(strs[0]);
				}
				
				//second argument is a variable
				if(dm.variables.get(strs[1]) != null){
					arg2 = (Double)dm.variables.get(strs[1]).getValue();
				}
				
				//second argument is an integer literal
				else if(strs[1].matches("\\d*")){
					arg2 = Double.parseDouble(strs[1]);
				}
			
				result = arg1 - arg2;
				
				logger.info(Double.toString((Double)result));
			}
			
			//multiply doubles
			else if(str.matches("\\w*\\*\\w*"))
			{
				strs = str.split("\\*");
				
				//first argument is a variable
				if(dm.variables.get(strs[0]) != null){
					arg1 = (Double)dm.variables.get(strs[0]).getValue();
				}
				
				//first argument is an integer literal
				else if(strs[0].matches("\\d*")){
					arg1 = Double.parseDouble(strs[0]);
				}
				
				//second argument is a variable
				if(dm.variables.get(strs[1]) != null){
					arg2 = (Double)dm.variables.get(strs[1]).getValue();
				}
				
				//second argument is an integer literal
				else if(strs[1].matches("\\d*")){
					arg2 = Double.parseDouble(strs[1]);
				}				
				
				result = arg1 * arg2;
				
				logger.info(Double.toString((Double)result));
			}
			
			else if(str.matches("\\w*/\\w*"))
			{
				strs = str.split("/");
				
				//first argument is a variable
				if(dm.variables.get(strs[0]) != null){
					arg1 = (Double)dm.variables.get(strs[0]).getValue();
				}
				
				//first argument is an integer literal
				else if(strs[0].matches("\\d*")){
					arg1 = Double.parseDouble(strs[0]);
				}
				
				//second argument is a variable
				if(dm.variables.get(strs[1]) != null){
					arg2 = (Double)dm.variables.get(strs[1]).getValue();
				}
				
				//second argument is an integer literal
				else if(strs[1].matches("\\d*")){
					arg2 = Double.parseDouble(strs[1]);
				}
				
				//assign value to double
				else if(str.matches("\\w*"))
				{
					if(dm.variables.get(strs[0]) != null){
						arg1 = (Double)dm.variables.get(strs[0]).getValue();
					}
					
					else if(strs[0].matches("\\d*")){
						arg1 = Double.parseDouble(strs[0]);
					}
					
					result = arg1;
				}
				
				if(arg2 != 0){
					result = arg1 / arg2;
				}
			}
		}
		
		else if(dataType == DataType.BOOLEAN)
		{
			if(str.matches("\\w*"))
			{
				if(dm.variables.get(str) != null)
				{
					result = dm.variables.get(str).getValue();
				}
				
				else if(str.equalsIgnoreCase("true")){
					result = "true";
				}
				
				else if(str.equalsIgnoreCase("faslse")){
					result = "false";
				}				
			}
		}
		
		return result;
	}
	
	public boolean parseTestCase(String expression, DataType dataType)
	{
		boolean result = false;
		
		if(dataType == DataType.INT)
		{
			int arg1 = 0;
			int arg2 = 0;
			
			if(expression.matches("\\w+<\\w+"))
			{
				String[] strs = expression.split("<");
				
				if(dm.variables.get(strs[0]) != null){
					arg1 = (Integer)(dm.variables.get(strs[0]).getValue());
				}
				else if(strs[0].matches("\\d+"));{
					arg1 = Integer.parseInt(strs[0]);
				}
				
				if(dm.variables.get(strs[1]) != null){
					arg2 = (Integer)(dm.variables.get(strs[1]).getValue());
				}
				else if(strs[1].matches("\\d+")){
					arg2 = Integer.parseInt(strs[1]);
				}
				
				result = arg1 < arg2;
			}
			
			else if(expression.matches("\\w+>\\w+"))
			{
				try{
					String[] strs = expression.split(">");
					
					if(dm.variables.get(strs[0]) != null){
						logger.info("Making it inside");
						arg1 = (Integer)(dm.variables.get(strs[0]).getValue());
					}
					else if(strs[0].matches("\\d+"));{
						arg1 = Integer.parseInt(strs[0]);
					}
					
					if(dm.variables.get(strs[1]) != null){
						arg2 = (Integer)(dm.variables.get(strs[1]).getValue());
					}
					else if(strs[1].matches("\\d+")){
						arg2 = Integer.parseInt(strs[1]);
					}
					
					result = arg1 > arg2;
					}
				catch(NumberFormatException e){logger.info(e.toString());}
			}
			
			else if(expression.matches("\\w+<=\\w+"))
			{
				String[] strs = expression.split("<=");
				
				if(dm.variables.get(strs[0]) != null){
					arg1 = (Integer)(dm.variables.get(strs[0]).getValue());
				}
				else if(strs[0].matches("\\d+"));{
					arg1 = Integer.parseInt(strs[0]);
				}
				
				if(dm.variables.get(strs[1]) != null){
					arg2 = (Integer)(dm.variables.get(strs[1]).getValue());
				}
				else if(strs[1].matches("\\d+")){
					arg2 = Integer.parseInt(strs[1]);
				}
				
				result = arg1 <= arg2;
			}
			
			else if(expression.matches("\\w+>=\\w+"))
			{
				String[] strs = expression.split(">=");
				
				if(dm.variables.get(strs[0]) != null){
					arg1 = (Integer)(dm.variables.get(strs[0]).getValue());
				}
				else if(strs[0].matches("\\d+"));{
					arg1 = Integer.parseInt(strs[0]);
				}
				
				if(dm.variables.get(strs[1]) != null){
					arg2 = (Integer)(dm.variables.get(strs[1]).getValue());
				}
				else if(strs[1].matches("\\d+")){
					arg2 = Integer.parseInt(strs[1]);
				}
				
				result = arg1 >= arg2;
			}
			
			else if(expression.matches("\\w+==\\w+"))
			{
				String[] strs = expression.split("==");
				
				if(dm.variables.get(strs[0]) != null){
					arg1 = (Integer)(dm.variables.get(strs[0]).getValue());
				}
				else if(strs[0].matches("\\d+"));{
					arg1 = Integer.parseInt(strs[0]);
				}
				
				if(dm.variables.get(strs[1]) != null){
					arg2 = (Integer)(dm.variables.get(strs[1]).getValue());
				}
				else if(strs[1].matches("\\d+")){
					arg2 = Integer.parseInt(strs[1]);
				}
				
				result = arg1 == arg2;
			}
		}
		return result;
	}
}
