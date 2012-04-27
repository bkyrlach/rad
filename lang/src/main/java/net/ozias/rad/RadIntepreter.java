/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad;

import org.apache.log4j.Logger;

import java.io.Console;
import java.io.PrintWriter;

import java.util.Scanner;

/**
 * R@d Command Intepreter.
 */
public class RadIntepreter implements Runnable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** log4j Logger. */
  private static final Logger LOG = Logger.getLogger( RadIntepreter.class );

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Runnable#run()
   */
  @Override public void run() {
    final Console con = System.console();

    if ( con != null ) {
      final PrintWriter writer = con.writer();
      final RadPrompt prompt = new RadPrompt( con.writer() );
      final Scanner scanner = new Scanner( con.reader() );
      scanner.useDelimiter( System.getProperty( "line.separator" ) );

      writer.println( "Welcome to the R@d Intepreter!" );
      prompt.print();

      while ( scanner.hasNext() ) {
        final String token = scanner.next();

        if ( ( token != null ) && token.equalsIgnoreCase( "exit" ) ) {
          writer.println( "Exiting.  This may take a few moments while everything cleans up." );

          break;
        } else {
          writer.println( "You typed: " + token );
          prompt.print();
        }
      }
    } else {
      LOG.error( "Unable to access system console." );
    }
  }

  //~ Inner Classes --------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * TODO: DOCUMENT ME!
   */
  private class RadPrompt {

    //~ Static fields/initializers -----------------------------------------------------------------------------------------------------------------------------

    /** TODO: DOCUMENT ME! */
    private static final String prompt = "r@d: ";

    //~ Instance fields ----------------------------------------------------------------------------------------------------------------------------------------

    /** TODO: DOCUMENT ME! */
    private PrintWriter writer;

    //~ Constructors -------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * TODO: Creates a new RadPrompt object.
     *
     * @param  writer  TODO: DOCUMENT ME!
     */
    public RadPrompt( final PrintWriter writer ) {
      this.writer = writer;
    }

    //~ Methods ------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * TODO: DOCUMENT ME!
     */
    public void print() {
      writer.print( prompt );
      writer.flush();
    }
  }
}
