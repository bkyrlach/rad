/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang.datatype;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * A R@d decimal.
 */
public class RadDecimal {

  //~ Instance fields ------------------------------------------------------------------------------------------------------------------------------------------

  /** The value of the RadDecimal. */
  private final transient BigDecimal value;

  //~ Constructors ---------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new RadDecimal object.
   *
   * @param  value  The value to assign to the RadDecimal.
   * @param  scale  The scale of the RadDecimal.
   */
  public RadDecimal( final Double value, final int scale ) {
    this.value = new BigDecimal( value, MathContext.UNLIMITED );
    this.value.setScale( scale );
  }

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Get the value of the RadDecimal.
   *
   * @return  The value of the RadDecimal.
   */
  public BigDecimal getValue() {
    return this.value;
  }
}
