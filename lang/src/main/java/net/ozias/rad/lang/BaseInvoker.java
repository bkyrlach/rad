/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Locale;

/**
 * Invoke stuff in the base R@d object.
 */
public final class BaseInvoker {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** log4j Logger. */
  private static final Logger LOG = Logger.getLogger( BaseInvoker.class );

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new BaseInvoker object.
   */
  private BaseInvoker() {
    // Ensures this cannot be instantiated through normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Invoke the given operation on the given numbers.
   *
   * @param   op       The operation to invoke.
   * @param   numbers  The number to invoke the operation with.
   *
   * @return  The result of the operation on the numbers.
   */
  public static Number invoke( final String op, final List<Number> numbers ) {
    Number retnum = null;
    String localOp = op;

    if ( "+".equals( op ) ) {
      localOp = "ADD";
    } else if ( "-".equals( op ) ) {
      localOp = "SUB";
    } else if ( "*".equals( op ) ) {
      localOp = "MUL";
    } else if ( "/".equals( op ) ) {
      localOp = "DIV";
    } else if ( "%".equals( op ) ) {
      localOp = "MOD";
    }

    try {
      BaseFactory.addFlag( localOp );
      final Object base = BaseFactory.newInstance();
      final Method evalOp = base.getClass().getDeclaredMethod( "evalOp", String.class, List.class );
      retnum = ( Number ) evalOp.invoke( base, new Object[] { localOp.toUpperCase( Locale.US ), numbers } );
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
}
