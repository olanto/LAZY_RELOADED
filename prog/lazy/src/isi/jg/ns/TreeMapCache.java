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
package isi.jg.ns;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.net.*;
import java.lang.reflect.*;



class TreeMapCache extends java.util.TreeMap {
    
    static int globalstatPut=0,globalstatGetFound=0,globalstatGetAsk=0;
    static int globalstatPutSize=0;
    static int globalstatSaveTime=0,globalstatQueryTime=0;
    
    
    int MAXCACHE;
    Object[] circular;
    long[] saveTime;
    int idx = 0;
    int statPut=0,statGetFound=0,statGetAsk=0;
    int statPutSize=0;
    int statSaveTime=0,statQueryTime=0;
    
    String cacheName;
    
    public TreeMapCache(int max, String name) { // length of cache
        super();
        MAXCACHE = max;
        circular = new Object[MAXCACHE];
        saveTime = new long[MAXCACHE];
        cacheName = name;
    }
    
    
    public synchronized void clear() {
        circular = new Object[MAXCACHE];
        idx = 0;
        super.clear();
    }
    
    public synchronized Object put(Object sKey, Object sInfo, long execTime) {
        if(MAXCACHE>0){
            idx = (++idx % MAXCACHE);
            if (NodeServer.verboseCache)
                System.out.println(
                "TreeMapCache:"
                + cacheName
                + " position in cache :"
                + idx
                + " add object:"
                + sKey.toString());
            if (circular[idx] != null) {
                if (NodeServer.verboseCache)
                    System.out.println(
                    "TreeMapCache:" + cacheName + " remove object from cache :" + sKey.toString());
                super.remove(circular[idx]);
            }
            circular[idx] = sKey;
            
            if (execTime==0) execTime=1;
            saveTime[idx]= execTime;
            statPut++;
            statPutSize+=(sInfo.toString()).length();
            statQueryTime+=(int)execTime;
            globalstatPut++;
            globalstatPutSize+=(sInfo.toString()).length();
            globalstatQueryTime+=(int)execTime;
            
            return super.put(sKey, sInfo);
        }
        else { // no cache !!!
            statPut++;
            statPutSize+=(sInfo.toString()).length();
            statQueryTime+=(int)execTime;
            globalstatPut++;
            globalstatPutSize+=(sInfo.toString()).length();
            globalstatQueryTime+=(int)execTime;
            return null;
        }
    }
    
    public synchronized Object get(Object sKey) {
        Object fromcache=super.get(sKey);
        if (fromcache!=null) {
            if (NodeServer.computeSaveTime){
                for (int i=0; i<MAXCACHE; i++){
                    if (circular[i]!=null&&circular[i].toString().equals(sKey.toString())){
                        // System.out.println("OK "+saveTime[i]);
                        statSaveTime+=saveTime[i];
                        globalstatSaveTime+=saveTime[i];
                        break;}
                    
                }
            }
            ++statGetFound;
            ++globalstatGetFound;
        }
        ++statGetAsk;
        ++globalstatGetAsk;
        
        if (NodeServer.verboseCache)
            System.out.println(
            "TreeMapCache:" + cacheName + " look object from cache, key is:" + sKey.toString());
        //System.out.println(getGlobalStatistic());
        
        return fromcache;
    }
    public synchronized String getXMLStatistic(){
        return
        "<CELL>"+MAXCACHE+"</CELL>"+
        "<CELL>"+statPut+"</CELL>"+
        "<CELL>"+statPutSize+"</CELL>"+
        "<CELL>"+statGetAsk+"</CELL>"+
        "<CELL>"+statGetFound+"</CELL>"+
        "<CELL>"+statQueryTime+"</CELL>"+
        "<CELL>"+statSaveTime+"</CELL>"
        ;
    }
    public synchronized String getStatistic(){
        return "cache: "+cacheName+
        " Put: "+MAXCACHE+
        " Put: "+statPut+
        " PutSize: "+statPutSize+
        " Get: "+statGetAsk+
        " Found: "+statGetFound+
        " QueryTime: "+statQueryTime+
        " SaveTime: "+statSaveTime
        ;
    }
    public static synchronized String getXMLGlobalStatistic(){
        return
        "<CELL>"+"-"+"</CELL>"+
        "<CELL>"+globalstatPut+"</CELL>"+
        "<CELL>"+globalstatPutSize+"</CELL>"+
        "<CELL>"+globalstatGetAsk+"</CELL>"+
        "<CELL>"+globalstatGetFound+"</CELL>"+
        "<CELL>"+globalstatQueryTime+"</CELL>"+
        "<CELL>"+globalstatSaveTime+"</CELL>"
        ;
    }
    public static synchronized String getGlobalStatistic(){
        return "For all caches:" +
        " Put: "+globalstatPut+
        " PutSize: "+globalstatPutSize+
        " Get: "+globalstatGetAsk+
        " Found: "+globalstatGetFound+
        " QueryTime: "+globalstatQueryTime+
        " SaveTime: "+globalstatSaveTime
        ;
    }
}

