/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang;

import net.ozias.rad.lang.asm.ASMField;
import net.ozias.rad.lang.asm.adapter.AbstractChainableAdapter;
import net.ozias.rad.lang.asm.adapter.AddFieldAdapter;
import net.ozias.rad.lang.asm.adapter.AddOpsAdapter;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * Invoke stuff on R@d objects.
 */
public class RadInvoker {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** Unable to access field string. */
  private static final String UNABLE_TO_ACCESS_FIELD = "Unable to access field.";
  /** Error loading state string. */
  private static final String ERROR_LOADING_STATE = "Error loading state.";
  /** log4j Logger. */
  private static final Logger LOG = Logger.getLogger( RadInvoker.class );

  //~ Instance fields ------------------------------------------------------------------------------------------------------------------------------------------

  /** The field adapters that have been added to the factory chain. */
  private final transient List<AbstractChainableAdapter> fieldAdapters = new ArrayList<AbstractChainableAdapter>();
  /** The map of R@d objects to their associated RadFactory. */
  private final transient RadFactory radFactory;
  /** The AddOpsAdapter. */
  private transient AddOpsAdapter addOpsAdapter = null;
  /** The current object. */
  private transient Object currentObject = null;

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new RadInvoker object.
   *
   * @param  objectName  The name of the object this invoker is associated with.
   */
  public RadInvoker( final String objectName ) {
    this.radFactory = new RadFactory( objectName );
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Add a field (plus getter/setter) to the base R@d object.
   *
   * @param   object      The object to add the field to.
   * @param   identifier  The field identifier.
   * @param   type        The field type.
   * @param   value       The field value.
   *
   * @return  The result of the field being set.
   */
  public Object addField( final String object, final String identifier, final Class<?> type, final Object value ) {
    Object retobj = null;
    boolean create = true;

    if ( currentObject != null ) {
      final Class<?> clazz = currentObject.getClass();

      try {
        clazz.getDeclaredField( identifier );
        create = false;
      } catch ( final NoSuchFieldException e ) {
        LOG.debug( UNABLE_TO_ACCESS_FIELD, e );
      } catch ( final SecurityException e ) {
        LOG.error( UNABLE_TO_ACCESS_FIELD, e );
        create = false;
      }
    }

    try {

      if ( create ) {
        // Field doesn't exist, so create it.
        final AddFieldAdapter addFieldAdapter = new AddFieldAdapter( object, identifier, type.getName().replace( '.', '/' ) );
        fieldAdapters.add( addFieldAdapter );
        radFactory.addAdapter( addFieldAdapter );
        final Map<String, Object> fieldMap = saveState();
        currentObject = radFactory.newInstance();
        loadState( fieldMap );
      }

      final Method getCN = currentObject.getClass().getDeclaredMethod( ASMField.getter( identifier ), ( Class<?>[] ) null );
      retobj = getCN.invoke( currentObject, ( Object[] ) null );

      if ( ( retobj == null ) || ( value != null ) ) {
        final Method setCN = currentObject.getClass().getDeclaredMethod( ASMField.setter( identifier ), type );
        setCN.invoke( currentObject, value );
        retobj = value;
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

    return retobj;
  }

  /**
   * Invoke the given operation on the given numbers.
   *
   * @param   op       The operation to invoke.
   * @param   numbers  The number to invoke the operation with.
   *
   * @return  The result of the operation on the numbers.
   */
  public Number evalOp( final String op, final List<Number> numbers ) {
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

      if ( addOpsAdapter == null ) {
        addOpsAdapter = new AddOpsAdapter();
        radFactory.addAdapter( addOpsAdapter );
        final Map<String, Object> fieldMap = saveState();
        currentObject = radFactory.newInstance();
        loadState( fieldMap );
      }
      final Method evalOp = currentObject.getClass().getDeclaredMethod( "evalOp", String.class, List.class );
      retnum = ( Number ) evalOp.invoke( currentObject, new Object[] { localOp.toUpperCase( Locale.US ), numbers } );
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
   * Read a field in the current object.
   *
   * @param   identifier  The field identifier.
   *
   * @return  The current value of the field.
   */
  public Object readField( final String identifier ) {
    Object retobj = null;

    if ( currentObject != null ) {
      final Class<?> clazz = currentObject.getClass();

      try {
        final Field field = clazz.getDeclaredField( identifier );
        final boolean origAccessible = field.isAccessible();
        field.setAccessible( true );
        retobj = field.get( currentObject );
        field.setAccessible( origAccessible );
      } catch ( final NoSuchFieldException e ) {
        LOG.error( UNABLE_TO_ACCESS_FIELD, e );
      } catch ( final SecurityException e ) {
        LOG.error( UNABLE_TO_ACCESS_FIELD, e );
      } catch ( IllegalArgumentException e ) {
        LOG.error( UNABLE_TO_ACCESS_FIELD, e );
      } catch ( IllegalAccessException e ) {
        LOG.error( UNABLE_TO_ACCESS_FIELD, e );
      }
    }

    return retobj;
  }

  /**
   * Load the field map information into the current base object.
   *
   * @param  fieldMap  The state information from the previous base object incarnation.
   */
  private void loadState( final Map<String, Object> fieldMap ) {

    for ( final String fieldName : fieldMap.keySet() ) {

      try {
        final Field field = currentObject.getClass().getDeclaredField( fieldName );
        final boolean accessible = field.isAccessible();
        field.setAccessible( true );
        field.set( currentObject, fieldMap.get( fieldName ) );
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
  private Map<String, Object> saveState() {
    final Map<String, Object> fieldMap = new TreeMap<String, Object>();

    if ( currentObject != null ) {

      for ( final Field field : currentObject.getClass().getDeclaredFields() ) {
        final boolean accessible = field.isAccessible();
        field.setAccessible( true );

        try {
          fieldMap.put( field.getName(), field.get( currentObject ) );
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
