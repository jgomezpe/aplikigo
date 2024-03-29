/**
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * <h3>License</h3>
 *
 * Copyright (c) 2019 by Jonatan Gomez-Perdomo. <br>
 * All rights reserved. <br>
 *
 * <p>Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <ul>
 * <li> Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * <li> Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <li> Neither the name of the copyright owners, their employers, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * </ul>
 * <p>THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 *
 *
 * @author <A HREF="http://disi.unal.edu.co/profesores/jgomezpe"> Jonatan Gomez-Perdomo </A>
 * (E-mail: <A HREF="mailto:jgomezpe@unal.edu.co">jgomezpe@unal.edu.co</A> )
 * @version 1.0
 */
package aplikigo.gui;

import speco.object.JXONfyable;
import speco.jxon.JXON;
import speco.object.Configurable;

/**
 * <p>Abstract color</p>
 *
 */
public class Color implements JXONfyable, Configurable{
	/**
	 * Color TAG
	 */
	public static final String TAG="color";
	/**
	 * Red color component TAG
	 */
	public static final String RED="red";
	
	/**
	 * Green color component TAG
	 */
	public static final String GREEN="green";

	/**
	 * Blue color component TAG
	 */
	public static final String BLUE="blue";

	/**
	 * Alpha color component TAG
	 */
	public static final String ALPHA="alpha";

	protected int r;
	protected int g;
	protected int b;
	protected int a;
	
	/**
	 * Creates a aplikigo color
	 * @param R Red component
	 * @param G Green component
	 * @param B Blue component
	 * @param A Alpha component
	 */
	public Color(int R, int G, int B, int A){
		this.r = R;
		this.g = G;
		this.b = B;
		this.a = A;
	}
	
	/**
	 * Creates an aplikigo component
	 * @param jxon Color information
	 */
	public Color(JXON jxon) { this.config(jxon); }

	public int red(){ return r; }
	public int green(){ return g; }
	public int blue(){ return b; }
	public int alpha(){ return a; }
	
	/**
	 * Gets the components of the color as an array [red,green,blue,alpha]
	 * @return Components of the color as an array [red,green,blue,alpha]
	 */
	public int[] values(){ return new int[]{r,g,b,a}; }
	
	/**
	 * Creates a JXON version of the color
	 * @return JXON version of the color
	 */
	@Override
	public JXON jxon() {
		JXON jxon = new JXON();
		jxon.set(Color.RED, red());
		jxon.set(Color.GREEN, green());
		jxon.set(Color.BLUE, blue());
		jxon.set(Color.ALPHA, alpha());
		return jxon;
	}
	    
	/**
	 * Configures the color with the information provided by the JXON object
	 * @param jxon Configuration information
	 */
	@Override
	public void config(JXON jxon){ 
		r = jxon.integer(RED);
		g = jxon.integer(GREEN);
		b = jxon.integer(BLUE);
		a = jxon.integer(ALPHA);
	}
}