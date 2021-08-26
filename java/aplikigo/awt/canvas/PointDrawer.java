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
import java.awt.Point;
import java.awt.geom.AffineTransform;

/**
 * <p>Utility for drawing points</p>
 */
public class PointDrawer {
	/**
	 * Constant (Type of object to draw)
	 */
	public static final int SIMPLE_POINT = 0;
	/**
	 * Constant (Type of object to draw)
	 */
	public static final int X_POINT = 1;
	/**
	 * Constant (Type of object to draw)
	 */
	public static final int PLUS_POINT = 2;
	/**
	 * Constant (Type of object to draw)
	 */
	public static final int TRIANGLE_POINT = 3;
	/**
	 * Constant (Type of object to draw)
	 */
	public static final int OVAL_POINT = 4;
	/**
	 * Constant (Type of object to draw)
	 */
	public static final int SQUARE_POINT = 5;

	protected static AffineTransform id = new AffineTransform();

	/**
	 * Draws a point according to the desired style, color and size
	 * @param g Graphics2D
	 * @param x int
	 * @param y int
	 * @param pointType int
	 * @param pointSize int
	 */
	public static void drawPoint(Graphics2D g, int x, int y, int pointType,
								int pointSize) {
		AffineTransform t = g.getTransform();
		g.setTransform(id);
		Point p = new Point(x, y);
		Point fp = new Point(x, y);
		t.transform(p, fp);
		x = fp.x;
		y = fp.y;
		switch (pointType) {
			case SIMPLE_POINT: g.drawLine(x, y, x, y); break;
			case X_POINT:
				g.drawLine(x - pointSize, y + pointSize, x + pointSize, y - pointSize);
				g.drawLine(x - pointSize, y - pointSize, x + pointSize, y + pointSize);
			break;
			case PLUS_POINT:
				g.drawLine(x, y + pointSize, x, y - pointSize);
				g.drawLine(x - pointSize, y, x + pointSize, y);
			break;
			case TRIANGLE_POINT:
				g.drawLine(x - pointSize, y + pointSize, x, y - pointSize);
				g.drawLine(x, y - pointSize, x + pointSize, y + pointSize);
				g.drawLine(x + pointSize, y + pointSize, x - pointSize, y + pointSize);
			break;
			case OVAL_POINT:
				g.drawOval(x - pointSize, y - pointSize, 2 * pointSize, 2 * pointSize);
			break;
			case SQUARE_POINT:
				g.drawLine(x - pointSize, y + pointSize, x - pointSize, y - pointSize);
				g.drawLine(x - pointSize, y + pointSize, x + pointSize, y + pointSize);
				g.drawLine(x + pointSize, y + pointSize, x + pointSize, y - pointSize);
				g.drawLine(x + pointSize, y - pointSize, x - pointSize, y - pointSize);
			break;
		}
		g.setTransform(t);
	}
}