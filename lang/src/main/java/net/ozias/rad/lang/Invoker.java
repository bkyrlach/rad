/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang;

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
  /** The current object being invoked on. */
  private static String currentObjectName;
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
    INVOKERS.put( objectName, radInvoker );
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
    Object retobj = null;

    synchronized ( NAME_LOCK ) {

      try {
        final RadInvoker radInvoker = INVOKERS.get( currentObjectName );
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
   * Set the current object name.
   *
   * @param  objectName  The object name.
   */
  public static void setCurrentObjectName( final String objectName ) {

    synchronized ( NAME_LOCK ) {
      currentObjectName = objectName;
    }
  }
}
