package soc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.Socket;
import java.net.ServerSocket;

public class Receiver {

    public static void main(String[] args) throws IOException {
        System.out.println("Server is started!");
        ServerSocket ss = new ServerSocket(9999);

        System.out.println("Waiting for the cipher...");
        Socket s = ss.accept();
        
        BigInteger d;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String ph = br.readLine();
        BigInteger phi = new BigInteger(ph);
        System.out.println("Receiver Data: phi = " + phi);

        String ee = br.readLine();
        BigInteger e = new BigInteger(ee);
        System.out.println("Receiver Data: e = " + e);

        
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {

            e.add(BigInteger.ONE);

        }

        d = e.modInverse(phi);
        System.out.println("Receiver Data: d = " + d);

        String nn = br.readLine();
        BigInteger n = new BigInteger(nn);
        System.out.println("Receiver Data: n = " + n);

        // Recieving the cipher text
        
        String enc = br.readLine();  
        BigInteger encrypted = new BigInteger(enc);
        System.out.println("Client Data: encrypted = " + encrypted);

        
        // decrypt
        byte[] decrypted = encrypted.modPow(d, n).toByteArray();

        System.out.println("Decrypted String in Bytes: " + bytesToString(decrypted));

        System.out.println("Decrypted String: " + new String(decrypted));

    }

    private static String bytesToString(byte[] encrypted) {

        String test = "";

        for (byte b : encrypted) {

            test += Byte.toString(b);

        }

        return test;

    }
}
