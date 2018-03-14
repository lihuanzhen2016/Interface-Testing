package com.apitest.check.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;





public class ResultSetHandler {
	
	static Logger log = LogManager.getLogger(ResultVerifier.class.getName());
    public static List<Map<String, String>> toMapList(ResultSet rs)
    {
        List<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        ResultSetMetaData md;
        try
        {
            md = rs.getMetaData();
            String[] fieldNames = new String[md.getColumnCount()];
            for(int i=1;i<=fieldNames.length;i++)
            {
                fieldNames[i-1] = md.getColumnLabel(i).toLowerCase();
            }
            while(rs.next())
            {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 1; i <= fieldNames.length; i++) 
                {
                	if(8==md.getColumnType(i))
                	{
                		map.put(fieldNames[i-1],String.valueOf(rs.getDouble(i)));
                	}else
                	{
                		map.put(fieldNames[i-1],rs.getString(i));
                	}
                    
                }
                lst.add(map);
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage(),e);
            //throw(new BaseException(Errors.ERRORS_COMMON,new String[]{Errors.DBOPERATION_FORMATRESULT},e.getMessage(),e));
        }
        return lst;
    }
    
    //private static Logger log = Logger.getLogger(ResultSetHandler.class);

    public static Map<String, String> toMap(ResultSet rs)
    {
    	Map<String, String> map = new HashMap<String, String>();
        ResultSetMetaData md;
        try
        {
            md = rs.getMetaData();
            String[] fieldNames = new String[md.getColumnCount()];
            for(int i=1;i<=fieldNames.length;i++)
            {
                fieldNames[i-1] = md.getColumnLabel(i).toLowerCase();
            }
            if(rs.next())
            {
                for (int i = 1; i <= fieldNames.length; i++) 
                {
                	if(8==md.getColumnType(i))
                	{
                		map.put(fieldNames[i-1],String.valueOf(rs.getDouble(i)));
                	}else
                	{
                		map.put(fieldNames[i-1],rs.getString(i));
                	}
                }
            }
        }
        catch (Exception e)
        {
            log.error(e.getMessage(),e);
            //throw(new BaseException(Errors.ERRORS_COMMON,new String[]{Errors.DBOPERATION_FORMATRESULT},e.getMessage(),e));
        }
        return map;
    }
 
    public static List toStringArrayList(ResultSet rs) throws Exception
    {
        List<String[]> lst = new ArrayList<String[]>();
        ResultSetMetaData md = rs.getMetaData();
        int fieldCount = md.getColumnCount();
        while(rs.next())
        {
            String[] arr = new String[fieldCount];
            for (int i = 1; i <= fieldCount; i++) 
            {
                arr[i-1] = rs.getString(i);
            }
            lst.add(arr);
        }
        return lst;
    }
    
  
    public static String toString(ResultSet rs) throws Exception
    {
    	String str = "";
        ResultSetMetaData md = rs.getMetaData();
        int fieldCount = md.getColumnCount();
        if(rs.next()&&fieldCount>=1)
        {           
        	if(8==md.getColumnType(1))
        	{
        		str = String.valueOf(rs.getDouble(1));
        	}else
        	{
        		str = rs.getString(1);
        	}
        	
        }
        return str;
    }
  
    public static List<Object[]> toObjectArrayList(ResultSet rs)
    {
        List<Object[]> lst = new ArrayList<Object[]>();
        ResultSetMetaData md;
        try
        {
            md = rs.getMetaData();
            int fieldCount = md.getColumnCount();
            while(rs.next())
            {
                Object[] arr = new Object[fieldCount];
                for (int i = 1; i <= fieldCount; i++) 
                {
                    arr[i-1] = rs.getObject(i);
                }
                lst.add(arr);
            }        }
        catch (SQLException e)
        {
            //log.error(e.getMessage(),e);
            //throw(new BaseException(Errors.ERRORS_COMMON,new String[]{Errors.DBOPERATION_FORMATRESULT},e.getMessage(),e));
        }
        return lst;
    }
    
    public List<String> getFieldList(ResultSet rs) 
    {
        ResultSetMetaData md;
        List<String> list = null;
        try
        {
            md = rs.getMetaData();
            list = new ArrayList<String>();
            for(int i=1;i<=md.getColumnCount();i++)
            {
                list.add(md.getColumnLabel(i).toLowerCase());
            }
        }
        catch (SQLException e)
        {
            //log.error(e.getMessage(),e);
            //throw(new BaseException(Errors.ERRORS_COMMON,new String[]{Errors.DBOPERATION_FORMATRESULT},e.getMessage(),e));
        }
        
        return list;
    }
}

