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
package aplikigo.awt;

import java.io.File;
import java.util.Hashtable;

/**
 * <p>A File Filter for open/save dialog boxes</p>
 */
public class FileFilter extends javax.swing.filechooser.FileFilter {
	/**
	 * Collection of valid file extensions
	 */
	protected Hashtable<String,String> extensions = new Hashtable<String, String>();

	/**
	 * File filer description
	 */
	protected String description;

	/**
	 * Creates a FileFilter with the given description
	 * @param description Description for the given file filtering
	 */
	public FileFilter(String description) { this.description = description; }

	/**
	 * Adds a new file extension for filtering files in dialog boxes
	 * @param extension file extension for filtering files in dialog boxes that will be added
	 */
	public void add(String extension) { extensions.put(extension, extension); }

	/**
	 * Determines if a File has one valid extension
	 * @param file File to be analized
	 * @return true if the file has a valid extension, false otherwise
	 */
	public boolean accept(File file) {
		if (file.isDirectory()) return true;

		String ext = null;
		String s = file.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) ext = s.substring(i + 1).toLowerCase();

		return (ext != null && extensions.get(ext) != null);
	}

	/**
	 * Gets the Description for the given file filtering
	 * @return String containing the the Description for the given file filtering
	 */
	public String getDescription() { return description; }
}