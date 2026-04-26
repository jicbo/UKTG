import java.io.*;

public class SimpleIO {

    public static void printStackTrace(){
        // force exception
        try{Class.forName("unknown").newInstance();}
        catch(Exception e){e.printStackTrace();}
    }

	//-------------------------------------------------------
	//
	// ASCII (8 bit) file/string input
	// Java podrzava unicode (16-bit reprezentacija), pa
	// nam za rad sa ASCII fajlovima preba malo programiranja
	//

	public static DataInputStream in = null;

	private static char look = ' ';

	public static char peek() {
		return look;
	}

	public static void openFileInput(String fname) throws Exception {
		in = new DataInputStream(new FileInputStream(fname));
		look = rdCh();
	}

	public static void openStringInput(String s) throws Exception {
		in = new DataInputStream(new StringBufferInputStream(s));
		look = rdCh();
	}

	public static void closeInput() {
		if(in != null) {
			try { in.close(); } catch (IOException ignored) {}
		}
	}

	public static char rdCh() throws Exception {
		return (char)in.readByte();
	}

	public static boolean whiteSpace(char c) {
		return c <= ' ';
	}

	public static boolean digit(char c) {
		return '0' <= c && c <= '9';
	}

	public static int decValue(char c) {
		return c - '0';
	}

	public static void skipItem() throws Exception {
		while(whiteSpace(look)) look = rdCh();
		while(!whiteSpace(look)) look = rdCh();
	}

	public static void skipLine() throws Exception {
		while(look != (char)13) look = rdCh();
		look = rdCh();
	}

	public static int rdInt() throws Exception {
		int n = 0;
		boolean neg = false;
		try {
			while(whiteSpace(look)) look = rdCh();
			// znak
			if(look == '-') {
				neg = true;
				look = rdCh();
			} else if(look == '+') {
				look = rdCh();
			}
			// cifre
			while(digit(look)) {
				n = 10*n + decValue(look);
				look = rdCh();
			}
		} catch(EOFException ex) {}

		if(neg) return -n;
		else return n;
	}

	public static double rdDouble() throws Exception {
		double s = 0.0;
		double f = 1.0;
		int e, i;
		boolean neg = false;
		try {
			while(whiteSpace(look)) look = rdCh();
			// znak
			if(look == '-') {
				neg = true;
				look = rdCh();
			} else if(look == '+') {
				look = rdCh();
			}
			// deo ispred decimalne tacke
			if(!digit(look)) throw new NumberFormatException();
			while(digit(look)) {
				s = 10.0*s + decValue(look);
				look = rdCh();
			}
			// decimale
			if(look == '.') {
				look = rdCh();
				while(digit(look)){
					f /= 10.0;
					s += f * decValue(look);
					look = rdCh();
				}
			}
			// eksponent
			if(look == 'e' || look == 'E') {
				look = rdCh();
				e = rdInt();
				if(e > 0)
					for(i = 0; i < e; i++) s *= 10.0;
				else if(e < 0)
					for(i = 0; i < -e; i++) s /= 10.0;
			}
		} catch(EOFException ex) {}

		if(neg) return -s;
		else return s;
	}
}