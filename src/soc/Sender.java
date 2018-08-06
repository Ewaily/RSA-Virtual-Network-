package soc;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Random;

public class Sender {

    public static void main(String[] args) throws IOException {

        Socket s = new Socket("localhost", 9999);
        OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
        PrintWriter out = new PrintWriter(os);

        BigInteger p;
        BigInteger q;
        BigInteger n;
        BigInteger e;
        BigInteger phi;

        Random r = new Random();

        p = BigInteger.probablePrime(1024, r);   // large prime number < 1024
        System.out.println("Receiver Data: p = " + p);

        q = BigInteger.probablePrime(1024, r);  // large prime number < 1024
        System.out.println("Receiver Data: q = " + q);

        n = p.multiply(q);

        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        out.println(phi);
        os.flush();

        e = BigInteger.probablePrime(1024 / 2, r);
        out.println(e);
        os.flush();

        out.println(n);
        os.flush();

        System.out.println("Enter the Plain Text");


        DataInputStream in = new DataInputStream(System.in);

        String teststring;

        teststring = in.readLine();

        System.out.println("Encrypting String: " + teststring);

        System.out.println("String in Bytes: " + bytesToString(teststring.getBytes()));

        // encrypt
        byte[] encrypted = (new BigInteger(teststring.getBytes())).modPow(e, n).toByteArray();

        System.out.println("Encrypted String in Bytes: " + bytesToString(encrypted));
        
        

        out.println(new BigInteger(encrypted));  // Sending the cipher text to the end user
        os.flush();

        

        // decrypt
        /*	byte[] decrypted = (new BigInteger(encrypted)).modPow(d, n).toByteArray();
                
                
		System.out.println("Decrypted String in Bytes: " +  bytesToString(decrypted));
 
		System.out.println("Decrypted String: " + new String(decrypted)); */
    }

    
    private static String bytesToString(byte[] encrypted) {

        String test = "";

        for (byte b : encrypted) {

            test += Byte.toString(b);

        }

        return test;

    }
}
