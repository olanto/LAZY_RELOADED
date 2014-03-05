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

/** 
* Une classe pour déclencher un chronomètre et pour mesurer facilement l'efficacité du code.
* Par exemple: 
* <pre> 
* Timer t1 = new Timer("section A du code");  // le chrono a démarrer!
*    ...section A ... 
* t1.stop(); // affiche le temps en milliseconde ...
* </pre> 
* On peut réutiliser le même chronomètre plusieurs fois, avec la méthode restart()
* @author Jacques Guyot 
* @version 1.1 
*/ 
public class Timer{

private long start;
private String activity;

/**
* crée un chronomètre. Et puis le démarre et affiche dans 
* la console le commentaire associé
* 
* @param s le commentaire associé au chrono. 
*/
  public Timer(String s){
	   activity=s;
	   start=System.currentTimeMillis();
	   System.out.println("START: "+activity);
	}

/**
* stope le chronomètre. Et affiche dans la console le commentaire associé
* et le temps mesuré en milliseconde
* 
*/ 

   public void  stop() {
		start=System.currentTimeMillis()-start;
		 System.out.println("STOP: "+activity+" - "+start+" ms");
	}
   /**
    * redémarre le chronomètre. Et affiche dans la console le commentaire associé
    * @param s le commentaire associé avec le chronomètre
    */ 
	public void  restart(String s) {
	   activity=s;
	   start=System.currentTimeMillis();
	   System.out.println("START: "+activity);
	}
	}