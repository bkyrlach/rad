/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad;

import net.ozias.rad.lang.Parser;
import net.ozias.rad.lang.eval.Statement;

import org.apache.log4j.Logger;

import java.io.Console;
import java.io.PrintWriter;

import java.util.Scanner;

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
    final Console con = System.console();

    if ( con == null ) {
      LOG.error( "Unable to access system console." );
    } else {
      final PrintWriter writer = con.writer();
      final RadPrompt prompt = new RadPrompt( con.writer() );
      final Scanner scanner = new Scanner( con.reader() );
      scanner.useDelimiter( System.getProperty( "line.separator" ) );

      writer.println( "Welcome to the R@d Intepreter!" );
      prompt.print();

      while ( scanner.hasNext() ) {
        final String token = scanner.next();

        if ( token == null ) {
          writer.println( "Null token." );
          prompt.print();
        } else if ( token.isEmpty() ) {
          writer.println( "Empty token." );
          prompt.print();
        } else if ( token.equalsIgnoreCase( "exit" ) ) {
          writer.println( "Exiting.  This may take a few moments while everything cleans up." );

          break;
        } else {
          writer.println( Statement.eval( Parser.parseStatement( token ) ) );
          prompt.print();
        }
      }
    }
  }

  //~ Inner Classes --------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Generate the Rad prompt.
   */
  private class RadPrompt {

    //~ Static fields/initializers -----------------------------------------------------------------------------------------------------------------------------

    /** The prompt value. */
    private static final String PROMPT = "r@d: ";

    //~ Instance fields ----------------------------------------------------------------------------------------------------------------------------------------

    /** The writer associated with the console. */
    private final transient PrintWriter writer;

    //~ Constructors -------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new RadPrompt object.
     *
     * @param  writer  The writer associated with the console.
     */
    public RadPrompt( final PrintWriter writer ) {
      this.writer = writer;
    }

    //~ Methods ------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Spit out the prompt.
     */
    public void print() {
      writer.print( PROMPT );
      writer.flush();
    }
  }
}
