/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad;

import net.ozias.rad.lang.ASTNegativePrimary;
import net.ozias.rad.lang.ASTNumber;
import net.ozias.rad.lang.ASTOpFunction;
import net.ozias.rad.lang.BaseFactory;
import net.ozias.rad.lang.ParseException;
import net.ozias.rad.lang.Parser;
import net.ozias.rad.lang.SimpleNode;
import net.ozias.rad.lang.TokenMgrError;

import org.apache.log4j.Logger;

import java.io.Console;
import java.io.PrintWriter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

          try {
            writer.println( evaluateOpFunction( Parser.parseOpFunction( token ) ) );
          } catch ( ParseException e ) {
            writer.println( e.getMessage() );
          } catch ( TokenMgrError e ) {
            writer.println( e.getMessage() );
          }

          prompt.print();
        }
      }
    }
  }

  /**
   * Evaluate a NegativePrimary node.
   *
   * @param   node  The node to evaluate.
   *
   * @return  The negated value of the primary.
   */
  private Number evaluateNegativePrimary( final SimpleNode node ) {
    Number retnum = evaluatePrimary( ( SimpleNode ) node.jjtGetChild( 0 ) );

    if ( retnum instanceof Double ) {
      retnum = ( Double ) retnum * -1.0d;
    } else if ( retnum instanceof Integer ) {
      retnum = ( Integer ) retnum * -1;
    }

    return retnum;
  }

  /**
   * Evaluate a Number node.
   *
   * @param   node  The node to evaluate. This must be an instance of ASTNumber.
   *
   * @return  Either a Double or an Integer.
   */
  private Number evaluateNumber( final SimpleNode node ) {
    return ( Number ) ( ( ASTNumber ) node ).jjtGetValue();
  }

  /**
   * Evaluate an expression node.
   *
   * @param   node  The node to evaluate.
   *
   * @return  Currently, the sum of all the expression primaries.
   */
  private Number evaluateOpFunction( final SimpleNode node ) {
    final String op = ( String ) node.jjtGetValue();
    final List<Number> numbers = new ArrayList<Number>();

    for ( int i = 0; i < node.jjtGetNumChildren(); i++ ) {
      numbers.add( evaluatePrimary( ( SimpleNode ) node.jjtGetChild( i ) ) );
    }

    return invoke( op, numbers );
  }

  /**
   * Evaluate a Primary node.
   *
   * @param   node  The node to evaluate.
   *
   * @return  The value of the primary.
   */
  private Number evaluatePrimary( final SimpleNode node ) {
    Number retnum = null;
    final SimpleNode childNode = ( SimpleNode ) node.jjtGetChild( 0 );

    if ( childNode instanceof ASTNumber ) {
      retnum = evaluateNumber( childNode );
    } else if ( childNode instanceof ASTNegativePrimary ) {
      retnum = evaluateNegativePrimary( childNode );
    } else if ( childNode instanceof ASTOpFunction ) {
      retnum = evaluateOpFunction( childNode );
    }

    return retnum;
  }

  /**
   * Invoke the given operation on the given numbers.
   *
   * @param   op       The operation to invoke.
   * @param   numbers  The number to invoke the operation with.
   *
   * @return  The result of the operation on the numbers.
   */
  private Number invoke( final String op, final List<Number> numbers ) {
    Number retnum = null;

    try {
      BaseFactory.addFlag( op );
      final Object base = BaseFactory.newInstance();
      final Method evalOp = base.getClass().getDeclaredMethod( "evalOp", String.class, List.class );
      retnum = ( Number ) evalOp.invoke( base, new Object[] { op.toUpperCase( Locale.US ), numbers } ); // numbers.toArray( new Integer[] {} )
    } catch ( ClassNotFoundException e ) {
      LOG.error( e.getMessage() );
    } catch ( NoSuchMethodException e ) {
      LOG.error( e.getMessage() );
    } catch ( InstantiationException e ) {
      LOG.error( e.getMessage() );
    } catch ( IllegalAccessException e ) {
      LOG.error( e.getMessage() );
    } catch ( InvocationTargetException e ) {
      LOG.error( e.getMessage() );
    }

    return retnum;
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