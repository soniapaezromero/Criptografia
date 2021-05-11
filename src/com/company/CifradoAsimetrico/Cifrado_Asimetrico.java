package com.company.CifradoAsimetrico;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Scanner;

/**
 * @author :Sonia Paez Romero Creado:11/05/2021
 * Clase para cifrar el mensaje con los distintos métodos  simetricos
 */

public class Cifrado_Asimetrico {
    static String ruta="src/com/company/CifradoAsimetrico/fichero";
    static String rutaClave="src/com/company/CifradoAsimetrico/keystore/";
      public static void main(String[] args) {
        FileOutputStream fs = null; //fichero de salida
        Scanner scan = new Scanner(System.in);
        String mensaje = "";
        System.out.println(" Dime el texto que quieres cifrar");
        mensaje= scan.nextLine();
        //Generamos las claves
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(512);//tamaño de la clave
            KeyPair clavesRSA = keyGen.generateKeyPair();
            PrivateKey clavePrivada = clavesRSA.getPrivate();//obtiene clave privada
            PublicKey clavePublica = clavesRSA.getPublic();//obtiene clave pública
            saveKey(clavePrivada,rutaClave+ "claveprivad.dat");
            //texto a encriptar o cifrar
            byte[] bufferClaro = mensaje.getBytes();
            // Creo el cifrador
            Cipher cifradorAsimetrico= Cipher.getInstance("RSA");
            cifradorAsimetrico.init(Cipher.ENCRYPT_MODE,clavePublica);
            byte[] bufferRSA;
            try {
                fs=new FileOutputStream(ruta+".cifrado");
                bufferRSA=cifradorAsimetrico.doFinal(bufferClaro);
                fs.write(bufferRSA);
                fs.close();
            } catch (FileNotFoundException e) {
                System.out.println("Fichero no encontrado:"+ e.getMessage());

            } catch (IOException e) {
                System.out.println("no se puede escribir en el fichero:"+ e.getMessage());

            }

            bufferRSA=cifradorAsimetrico.doFinal(bufferClaro);
            System.out.println( "Mensaje cifrado y guardado");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No se ha podido genera la clave:"+ e.getMessage());
        } catch (NoSuchPaddingException e) {
            System.out.println("No se ha podido cifrar:"+ e.getMessage());
        } catch (InvalidKeyException e) {
            System.out.println("No se ha podido hacer en cifrado:"+ e.getMessage());
        } catch (IllegalBlockSizeException e) {
            System.out.println("No se ha podido hacer en cifrado:"+ e.getMessage());
        } catch (BadPaddingException e) {
            System.out.println("No se ha podido hacer en cifrado:"+ e.getMessage());
        } catch (Exception e) {
            System.out.println("No se ha podido guardar la clave:"+ e.getMessage());
        }

      }

    /**
     * Metodo para guardar  clave secreta en keystore
     * @param key
     * @param fileName
     * @throws Exception
     */
    private static void saveKey(Key key, String fileName) throws Exception {
        byte[] publicKeyBytes = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(publicKeyBytes);
        fos.close();
    }
}
