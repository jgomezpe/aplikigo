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
package aplikigo.awt.canvas;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * <p>Utility for scaling paint objects to dependent/independent device coordinates</p>
 */
public class Graphics2DScaler {
	/**
	 * Scale factor in the x axe
	 */
	protected double sx;
   
	/**
	 * Scale factor in the y axe
	 */
	protected double sy;

	/**
	 * Creates a scaling utility (dependent/independent device coordinates) with scale factor of 1.0
	 */
	public Graphics2DScaler() {
		sx = 1.0;
		sy = 1.0;
	}

	/**
	 * Creates a scaling utility (dependent/independent device coordinates) with the given scale factor
	 * @param sx Scale factor in the x axe
	 * @param sy Scale factor in the y axe
	 */
	public Graphics2DScaler(double sx, double sy) {
		this.sx = sx;
		this.sy = sy;
	}

	/**
	 * Scales the given paint object (according to the scale factor) to dependent device coordinates
	 * @param g Graphics2D to be scaled
	 */
	public void scale(Graphics2D g) { 
		g.setTransform(AffineTransform.getScaleInstance(sx, sy));
	}

	/**
	 * Scales the given paint object (according to the scale factor) to independent device coordinates
	 * @param g Graphics2D to be scaled
	 */
	public void scalePI(Graphics2D g) {
		AffineTransform tr = g.getDeviceConfiguration().getNormalizingTransform();
		tr.scale(sx, sy);
		g.setTransform(tr);
	}
}