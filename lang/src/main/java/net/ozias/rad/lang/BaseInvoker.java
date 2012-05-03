/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * Invoke stuff in the base R@d object.
 */
public final class BaseInvoker {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Error loading state string. */
  private static final String ERROR_LOADING_STATE = "Error loading state.";
  /** log4j Logger. */
  private static final Logger LOG = Logger.getLogger( BaseInvoker.class );
  /** The current base object. */
  private static Object currentBase = null;

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new BaseInvoker object.
   */
  private BaseInvoker() {
    // Ensures this cannot be instantiated through normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Get the current namespace.
   *
   * @return  The current namespace as a String.
   */
  public static String currentNamespace() {
    String retstr = null;

    try {

      if ( BaseFactory.addFlag( "NS" ) ) {
        final Map<String, Object> fieldMap = saveState();
        currentBase = BaseFactory.newInstance();
        loadState( fieldMap );
      }
      final Method getCN = currentBase.getClass().getDeclaredMethod( "getCurrentNamespace", ( Class<?>[] ) null );
      retstr = ( String ) getCN.invoke( currentBase, ( Object[] ) null );

      if ( retstr == null ) {
        retstr = currentBase.getClass().getPackage().getName();
        final Method setCN = currentBase.getClass().getDeclaredMethod( "setCurrentNamespace", String.class );
        setCN.invoke( currentBase, retstr );
      }
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

    return retstr;
  }

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

      if ( BaseFactory.addFlag( localOp ) ) {
        final Map<String, Object> fieldMap = saveState();
        currentBase = BaseFactory.newInstance();
        loadState( fieldMap );
      }
      final Method evalOp = currentBase.getClass().getDeclaredMethod( "evalOp", String.class, List.class );
      retnum = ( Number ) evalOp.invoke( currentBase, new Object[] { localOp.toUpperCase( Locale.US ), numbers } );
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

  /**
   * Load the field map information into the current base object.
   *
   * @param  fieldMap  The state information from the previous base object incarnation.
   */
  private static void loadState( final Map<String, Object> fieldMap ) {

    for ( final String fieldName : fieldMap.keySet() ) {

      try {
        final Field field = currentBase.getClass().getDeclaredField( fieldName );
        final boolean accessible = field.isAccessible();
        field.setAccessible( true );
        field.set( currentBase, fieldMap.get( fieldName ) );
        field.setAccessible( accessible );
      } catch ( IllegalArgumentException e ) {
        LOG.error( ERROR_LOADING_STATE, e );
      } catch ( IllegalAccessException e ) {
        LOG.error( ERROR_LOADING_STATE, e );
      } catch ( NoSuchFieldException e ) {
        LOG.error( ERROR_LOADING_STATE, e );
      } catch ( SecurityException e ) {
        LOG.error( ERROR_LOADING_STATE, e );
      }
    }

  }

  /**
   * Save the state of the current base object for loading in the new incarnation of the base object.
   *
   * @return  A map of field names to field values.
   */
  private static Map<String, Object> saveState() {
    final Map<String, Object> fieldMap = new TreeMap<String, Object>();

    if ( currentBase != null ) {

      for ( final Field field : currentBase.getClass().getDeclaredFields() ) {
        final boolean accessible = field.isAccessible();
        field.setAccessible( true );

        try {
          fieldMap.put( field.getName(), field.get( currentBase ) );
        } catch ( IllegalArgumentException e ) {
          LOG.error( "Error saving state.", e );
        } catch ( IllegalAccessException e ) {
          LOG.error( "Error saving state.", e );
        } finally {
          field.setAccessible( accessible );
        }
      }
    }

    return fieldMap;
  }
}
