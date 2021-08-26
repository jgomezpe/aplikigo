package aplikigo.gui;

import kompari.Order;

/**
 * <p>Lexicographical order of colors</p>
 */
public class ColorOrder implements Order<Color>{
	/**
	 * Determines if color one is less, equal or greater than color two (lexicographical order)
	 * @param one First color to be compared
	 * @param two Second color to be compared
	 * @return A value less than 0 indicates that <i>one</i> is less than <i>two</i>, a value equal to 0 indicates
	 * that <i>one</i> is equal to <i>two</i> and a value greater than 0 indicates that <i>one</i> is greater than <i>two</i>
	 */
	public int compare(int[] one, int[] two) {
		if( one.length!=two.length ) return one.length-two.length;
		int k=0;
		while( k<one.length && one[k]==two[k] ) k++;
		if(k==one.length) return 0;
		else return one[k]-two[k];
	}
	
	/**
	 * Determines if color one is less, equal or greater than color two (lexicographical order)
	 * @param one First color to be compared
	 * @param two Second color to be compared
	 * @return A value less than 0 indicates that <i>one</i> is less than <i>two</i>, a value equal to 0 indicates
	 * that <i>one</i> is equal to <i>two</i> and a value greater than 0 indicates that <i>one</i> is greater than <i>two</i>
	 */
	@Override
	public int compare(Color one, Color two) {
		return compare(one.values(),two.values());
	}
}