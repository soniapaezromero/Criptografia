package com.company.CifradoAsimetrico;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
/**
 * @author :Sonia Paez Romero Creado:11/05/2021
 * Clase para cifrar el mensaje con un metodo Asimetrico
 */

public class Descifrado_Asimetrico {
    protected static PrivateKey privateKey ;
    static String rutaClave="src/com/company/CifradoAsimetrico/keystore/claveprivad.dat";
    static String rutafihero="src/com/company/CifradoAsimetrico/fichero.cifrado";
    public static void main(String[] args) {
        FileInputStream fe = null;

        Cipher descifradorAsimetrico= null;


        try {
            privateKey=loadPrivateKey(rutaClave);
            //llamamos al descifrador
            descifradorAsimetrico = Cipher.getInstance("RSA");
            descifradorAsimetrico.init(Cipher.DECRYPT_MODE,privateKey);
            try {

                fe=new FileInputStream(rutafihero);
                byte[] bufferClaro = new byte[0];
                byte[] buffer = new byte[1000]; //array de bytes
                //lee el fichero de 1k en 1k y pasa los fragmentos leidos al cifrador
                int bytesLeidos = fe.read(buffer, 0, 1000);
                while (bytesLeidos != -1) {//mientras no se llegue al final del fichero
                    //pasa texto cifrado al cifrador y lo descifra, asignándolo a bufferClarobufferClaro = descifradorAsimetrico.update(buffer, 0, bytesLeidos);
                    bufferClaro=descifradorAsimetrico.update(buffer, 0, bytesLeidos);
                    bytesLeidos = fe.read(buffer, 0, 1000);
                }
                bufferClaro = descifradorAsimetrico.doFinal(); //Completa el descifrado
                System.out.println("Mensaje descifrado:");
                // cierra archivos
                System.out.println(new String(bufferClaro));
                fe.close();
            } catch (FileNotFoundException e) {
                System.out.println("Fichero no encontrado:"+ e.getMessage());
            } catch (IOException e) {
                System.out.println("Fichero nose ha podido leer:"+ e.getMessage());
            } catch (IllegalBlockSizeException e) {
                System.out.println("El tamaño es erróneso:"+ e.getMessage());;
            } catch (BadPaddingException e) {
                System.out.println("Clave errónea:"+ e.getMessage());
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Clave errónea:"+ e.getMessage());
        } catch (NoSuchPaddingException e) {
            System.out.println("No se ha posido descifrar elfichero:"+ e.getMessage());
        } catch (InvalidKeyException e) {
            System.out.println("Clave no válida:"+ e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo para obtener la clave desde el keystore
     * @param fileName
     * @return
     * @throws Exception
     */
    private static PrivateKey loadPrivateKey(String fileName) throws Exception {
        FileInputStream fis = new FileInputStream(fileName);
        int numBtyes = fis.available();
        byte[] bytes = new byte[numBtyes];
        fis.read(bytes);
        fis.close();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey keyFromBytes = keyFactory.generatePrivate(keySpec);
        return keyFromBytes;
    }
}
