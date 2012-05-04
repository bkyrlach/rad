/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang;

import net.ozias.rad.lang.asm.ASMConstants;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * The R@d Invoker. Use this to invoke actions on your objects.
 */
public final class Invoker {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Error invoking message. */
  private static final String ERROR_INVOKING = "Error invoking.";
  /** log4j Logger. */
  private static final Logger LOG = Logger.getLogger( Invoker.class );
  /** The list of current invokers. */
  private static final Map<String, RadInvoker> INVOKERS = new HashMap<String, RadInvoker>();
  /** The current namespace. */
  private static String currentNamespace;
  /** Lock to support changing the current object. */
  private static final Object NAME_LOCK = new Object();

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Private to ensure this can't be instantiated through normal means.
   */
  private Invoker() {
    // Private to ensure this can't be instantiated through normal means.
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Add a RadInvoker to the list of invokers.
   *
   * @param  objectName  The object name the invoker backs.
   * @param  radInvoker  The RadInvoker.
   */
  public static void addInvoker( final String objectName, final RadInvoker radInvoker ) {

    if ( !INVOKERS.containsKey( objectName ) ) {
      INVOKERS.put( objectName, radInvoker );
    }
  }

  /**
   * Get the current base object.
   *
   * @return  The current base object.
   */
  public static String getCurrentBase() {

    synchronized ( NAME_LOCK ) {
      return new StringBuilder( currentNamespace ).append( ".Base" ).toString();
    }
  }

  /**
   * Get the current namespace.
   *
   * @return  The current execution namespace.
   */
  public static String getCurrentNamespace() {

    synchronized ( NAME_LOCK ) {
      return currentNamespace;
    }
  }

  /**
   * Invoke the given method.
   *
   * @param   methodName      The method name.
   * @param   parameterTypes  The method parameter types.
   * @param   parameters      The method parameters.
   *
   * @return  The result of invoking the method with the given parameters.
   */
  public static Object invoke( final String methodName, final Class<?>[] parameterTypes, final Object[] parameters ) {
    return invoke( ASMConstants.BASE_CN, methodName, parameterTypes, parameters );
  }

  /**
   * Invoke the given method.
   *
   * @param   objectName      The object to invoke this method on.
   * @param   methodName      The method name.
   * @param   parameterTypes  The method parameter types.
   * @param   parameters      The method parameters.
   *
   * @return  The result of invoking the method with the given parameters.
   */
  public static Object invoke( final String objectName, final String methodName, final Class<?>[] parameterTypes, final Object[] parameters ) {
    Object retobj = null;

    synchronized ( NAME_LOCK ) {

      try {
        final RadInvoker radInvoker = INVOKERS.get( objectName );
        final Method method = radInvoker.getClass().getDeclaredMethod( methodName, parameterTypes );
        retobj = method.invoke( radInvoker, parameters );
      } catch ( NoSuchMethodException e ) {
        LOG.error( ERROR_INVOKING, e );
      } catch ( SecurityException e ) {
        LOG.error( ERROR_INVOKING, e );
      } catch ( IllegalAccessException e ) {
        LOG.error( ERROR_INVOKING, e );
      } catch ( IllegalArgumentException e ) {
        LOG.error( ERROR_INVOKING, e );
      } catch ( InvocationTargetException e ) {
        LOG.error( ERROR_INVOKING, e );
      }
    }

    return retobj;
  }

  /**
   * Set the current namespace.
   *
   * @param  namespace  The namespace.
   */
  public static void setCurrentNamespace( final String namespace ) {

    synchronized ( NAME_LOCK ) {
      currentNamespace = namespace;
    }
  }
}
