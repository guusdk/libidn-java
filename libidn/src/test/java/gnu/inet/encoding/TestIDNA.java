/* Copyright (C) 2004-2016 Free Software Foundation, Inc.
   Author: Oliver Hitz

   This file is part of GNU Libidn.

   GNU Libidn is free software: you can redistribute it and/or
   modify it under the terms of either:

     * the GNU Lesser General Public License as published by the Free
       Software Foundation; either version 3 of the License, or (at
       your option) any later version.

   or

     * the GNU General Public License as published by the Free
       Software Foundation; either version 2 of the License, or (at
       your option) any later version.

   or both in parallel, as here.

   GNU Libidn is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   General Public License for more details.

   You should have received copies of the GNU General Public License and
   the GNU Lesser General Public License along with this program.  If
   not, see <http://www.gnu.org/licenses/>. */

package gnu.inet.encoding;

import gnu.inet.encoding.IDNA;
import gnu.inet.encoding.IDNAException;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

public class TestIDNA
{
    final static int STATE_SCAN = 0;
    final static int STATE_INPUT = 1;

	@Test
	public void testDot() throws Exception
	{
		String[] tests = new String[] {
				"domain\u3002invalid",
				"domain\uFF0Einvalid",
				"domain\uFF61invalid",
		};
		for ( int i = 0; i < tests.length ; i++ ) {
			Assert.assertEquals( "domain.invalid", IDNA.toASCII( tests[i] ));
		}
	}

	@Test
	public void testDraftJosefssonIDNTestVectors() throws Exception
	{
	  final URL url = this.getClass().getResource( "/Nameprep and IDNA Test Vectors.html" );
	  BufferedReader r = new BufferedReader(new InputStreamReader( url.openStream() ));
      int state = STATE_SCAN;

      StringBuilder input = new StringBuilder();
      String out;

      while (true) {
	String l = r.readLine();
	if (null == l) {
	  break;
	}

	switch (state) {
	case STATE_SCAN:
	  if (l.startsWith("input (length ")) {
	    state = STATE_INPUT;
	    input = new StringBuilder();
	  }
	  break;
	case STATE_INPUT:
	  if (l.equals("")) {
	    // Empty line (before "out:")
	  } else if (l.startsWith("out: ")) {
	    out = l.substring(5).trim();

	    try {
	      String ascii = IDNA.toASCII(input.toString());
	      Assert.assertEquals( ascii, out );
	    } catch (IDNAException e) {
	      System.out.println(" exception thrown ("+e+")");
	    }

	    state = STATE_SCAN;
	  } else {
	    StringTokenizer tok = new StringTokenizer(l.trim(), " ");
	    while (tok.hasMoreTokens()) {
	      String t = tok.nextToken();
	      if (t.startsWith("U+")) {
		char u = (char) Integer.parseInt(t.substring(2, 6), 16);
		input.append(u);
	      } else {
		System.err.println("Unknown token: "+t);
	      }
	    }
	  }
	  break;
	} }
  }
}
