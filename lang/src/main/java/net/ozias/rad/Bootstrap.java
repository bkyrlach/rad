/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad;

import static net.ozias.rad.lang.asm.ASMConstants.BASE_CN;
import static net.ozias.rad.lang.asm.ASMConstants.BASE_NS;

import net.ozias.rad.lang.Invoker;
import net.ozias.rad.lang.RadInvoker;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The R@d Bootstrap class.
 */
public class Bootstrap implements Runnable {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** log4j logger. */
  private static final Logger LOG = Logger.getLogger( Bootstrap.class );
  /** Executor Service for threads. */
  private static final ExecutorService EXECSERVICE;
  /** Futures list. */
  private static final Collection<Future<?>> FUTURES = new LinkedList<Future<?>>();
  /** Core ThreadPoolExecutor size. */
  private static final int CORE_SIZE = 10;
  /** Maximum ThreadPoolExecutor size. */
  private static final int MAX_SIZE = 50;
  /** Thread timeout (in seconds). */
  private static final int TIMEOUT = 5;
  /** Maximum ThreadPoolExecutor work queue size. */
  private static final int QUEUE_SIZE = 20;

  static {
    EXECSERVICE = new ThreadPoolExecutor( CORE_SIZE, MAX_SIZE, TIMEOUT, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>( QUEUE_SIZE ) );
    ( ( ThreadPoolExecutor ) EXECSERVICE ).allowCoreThreadTimeOut( true );
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Add a runnable to the futures list and submit it for execution.
   *
   * @param  runnable  The runnable to add.
   */
  public static void addRunnable( final Runnable runnable ) {
    FUTURES.add( EXECSERVICE.submit( runnable ) );
  }

  /**
   * main method.
   *
   * @param  args  Command line arguments.
   */
  public static void main( final String... args ) {
    ( new Thread( new Bootstrap() ) ).start();
  }

  /**
   * @see  java.lang.Runnable#run()
   */
  @Override public void run() {
    Invoker.addInvoker( BASE_CN, new RadInvoker( BASE_CN ) );
    Invoker.setCurrentNamespace( BASE_NS );

    addRunnable( new RadInterpreter() );

    for ( final Future<?> future : FUTURES ) {

      try {
        future.get();
      } catch ( InterruptedException e ) {
        LOG.error( "InterruptedException", e );
      } catch ( ExecutionException e ) {
        LOG.error( "ExecutionException", e );
      }
    }
  }
}
