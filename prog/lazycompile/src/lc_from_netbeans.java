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

import isi.jg.ns.Compiler;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class lc_from_netbeans {
 static PrintWriter out;
    public static void main(String[] args) throws FileNotFoundException {

       
        out = new PrintWriter(new FileOutputStream("out.sql"));

        compile("C:/LAZY_RELOADED/lazy-schema/myterm.lzy");

//        compile("C:/LAZY_RELOADED/prog/lazycompile/BASIC/icon.lzy");
//        compile("C:/LAZY_RELOADED/prog/lazycompile/BASIC/node.lzy");        
//        compile("C:/LAZY_RELOADED/prog/lazycompile/BASIC/securelogin.lzy");
//        compile("C:/LAZY_RELOADED/prog/lazycompile/BASIC/test.lzy");
//        compile("C:/LAZY_RELOADED/prog/lazycompile/BASIC/util.lzy");
//        
        
        out.close();

    }
    
    static void compile(String filename){
        
        System.out.println("Compile:"+filename);
                
        Compiler.global_init();
                
        Compiler a;
        
        try {
            a = new Compiler(new FileReader(filename), new FileReader(filename), out);
            a.startrule();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(lc_from_netbeans.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
 
    }
    
}
	
