/* Lazy hypertext view system
Copyright (C) 2002-2003,  ISI Research Group, CUI, University of Geneva
 
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
You can also find the GNU GPL at http://www.gnu.org/copyleft/gpl.html
 
You can contact the ISI research group at http://cui.unige.ch/isi
 */
import isi.jg.ns.*;
import java.util.*;
import java.text.*;

public class Cumul{

static Hashtable cumul=new Hashtable();
// static DecimalFormat df=new DecimalFormat("###,###,###,##0.00");
static DecimalFormat df=new DecimalFormat("###,###,###,###.##");


public Cumul(){}

public static String set(Object o){
	/*
	__Cumul.set[cumulName,initialValue] return empty
	*/
	if(NodeServer.verboseExternal) System.out.println("ok Cumul.setCumul");
	String[] u=(String [])o;
	cumul.put(u[0],new Double(Double.parseDouble(u[1])));
	return "";
	}

public static String get(Object o){
	/*
	__Cumul.get[cumulName] return value
	*/
	if(NodeServer.verboseExternal) System.out.println("ok Cumul.getCumul");
	String[] u=(String [])o;
	Double d = (Double)cumul.get(u[0]);
	if (d == null) {return "must be init";}
	  else {DecimalFormat fff=new DecimalFormat("############################.############");
	  	return fff.format(d.doubleValue());}
	}  

public static String getnice(Object o){
	/*
	__Cumul.getnice[cumulName] return value formated
	*/
	if(NodeServer.verboseExternal) System.out.println("ok Cumul.getnice");
	String[] u=(String [])o;
	Double d = (Double)cumul.get(u[0]);
	if (d == null) {return "must be init";}
	  else {return df.format(d.doubleValue());}
	}  

public static String getAndFormat(Object o){
	/*
	__Cumul.getAndFormat[cumulName,formatstring] return value formated "###,###,###,###.##"
	*/	
	if(NodeServer.verboseExternal) System.out.println("ok Cumul.getAndFormat ");
	String[] u=(String [])o;
	Double d = (Double)cumul.get(u[0]);		
	if (d == null) {return "must be init";}
	  else {DecimalFormat fff=new DecimalFormat(u[1]);
	  	return fff.format(d.doubleValue());}
	}  


public static String add(Object o){
	/*
	__Cumul.add[cumulName, addedValue] return value
	*/
	if(NodeServer.verboseExternal) System.out.println("ok Cumul.add");
	String[] u=(String [])o;
	Double d = (Double)cumul.get(u[0]);
	if (d == null) {return "must be init";}
	  else {cumul.put(u[0],new Double(d.doubleValue()+Double.parseDouble(u[1])));
	  	return "";}
	}  
	
public static String getPercent(Object o){
	/*
	__Cumul.getPercent[cumulName,totalValue] return value
	*/
	if(NodeServer.verboseExternal) System.out.println("ok Cumul.getnice");
	String[] u=(String [])o;
	Double d = (Double)cumul.get(u[0]);	
	if (d == null) {return "must be init";}
	  else {
	  	DecimalFormat fff=new DecimalFormat("###########################0.#############################");
	  	return fff.format(d.doubleValue()/Double.parseDouble(u[1]));}
	}  
	
public static String getPercentNice(Object o){
	/*
	__Cumul.getPercent[cumulName,totalValue] return value
	*/
	if(NodeServer.verboseExternal) System.out.println("ok Cumul.getnice");
	String[] u=(String [])o;
	Double d = (Double)cumul.get(u[0]);	
	if (d == null) {return "must be init";}
	  else {return df.format((d.doubleValue()/Double.parseDouble(u[1]))*100);}
	}  
		
	
}