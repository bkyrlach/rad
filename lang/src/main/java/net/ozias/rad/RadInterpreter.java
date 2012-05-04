/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad;

import jline.console.ConsoleReader;

import net.ozias.rad.lang.Parser;
import net.ozias.rad.lang.eval.Statement;

import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * R@d Command Interpreter.
 */
public class RadInterpreter implements Runnable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** log4j Logger. */
  private static final Logger LOG = Logger.getLogger( RadInterpreter.class );

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Runnable#run()
   */
  @Override public void run() {

    try {
      final ConsoleReader con = new ConsoleReader();
      con.setPrompt( "r@d: " );
      con.println( "Welcome to the R@d Intepreter!" );
      con.println( "Enter help to get started." );

      String token = con.readLine();

      while ( token != null ) {
        token = token.trim();

        if ( token.isEmpty() ) {
          con.println( "Empty token." );
        } else if ( token.equalsIgnoreCase( "exit" ) ) {
          con.println( "Exiting.  This may take a few moments while everything cleans up." );

          break;
        } else {
          con.println( Statement.eval( Parser.parseStatement( token ) ) );
        }
        con.flush();
        token = con.readLine();
      }
      con.flush();
    } catch ( IOException e ) {
      LOG.error( "Error creating console reader.", e );
    }
  }
}
