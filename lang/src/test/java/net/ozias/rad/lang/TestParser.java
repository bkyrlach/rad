/**
 * Copyright (c) 2011 Oziasnet, LLC. All Rights Reserved.
 */
package net.ozias.rad.lang;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import net.ozias.rad.lang.eval.statement.NamespaceStatement;
import net.ozias.rad.lang.eval.statement.UseStatement;

import org.apache.log4j.Logger;

import org.testng.annotations.Test;

import java.io.FileNotFoundException;

/**
 * Test cases for the Parser class.
 */
@Test( groups = { "DEFAULT" } )
public class TestParser {

  //~ Static fields/initializers -------------------------------------------------------------------------------------------------------------------------------

  /** log4j Logger. */
  private static final Logger LOG = Logger.getLogger( TestParser.class );

  //~ Methods --------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Test the JavaCC generated parser.
   *
   * @throws  ParseException
   * @throws  TokenMgrError
   * @throws  FileNotFoundException
   */
  @Test public void testParser_test() throws ParseException, TokenMgrError, FileNotFoundException {
    LOG.info( "Parsing test.rad" );
    final SimpleNode rootNode = Parser.parseFile( TestParser.class.getResourceAsStream( "test.rad" ) );
    assertTrue( rootNode instanceof ASTRadFile );
    final ASTRadFile rad = ( ASTRadFile ) rootNode;
    assertEquals( NamespaceStatement.eval( ( SimpleNode ) rad.jjtGetChild( 0 ) ), "net.ozias.rad.lang" );
    assertEquals( UseStatement.eval( ( SimpleNode ) rad.jjtGetChild( 1 ) ), "java.util.List" );
    assertEquals( UseStatement.eval( ( SimpleNode ) rad.jjtGetChild( 2 ) ), "java.util.ArrayList" );
  }
}
