package com.company.CifradoSimetrico;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author :Sonia Paez Romero Creado:11/05/2021
 * Clase par descifrar el mensaje con los distintos métodos 
 */
public class Descifrar {
   static String ruta="src/com/company/CifradoSimetrico/fichero.cifrado";
   static String rutaKeystore="src/com/company/CifradoSimetrico/keystore/clave.dat";
   static String rutaKeystoreAES="src/com/company/CifradoSimetrico/ficheroclave.dat";
   public static void main(String[] args){
      Scanner scan =new Scanner(System.in);
      boolean  salir= false;

         int opcion;
         System.out.println(" Dime el que método que quieres  usar para descifrar:");
         System.out.println(" 1.DES");
         System.out.println(" 2.AES");
         System.out.println(" 3.Blowfish");
         try {
            opcion = scan.nextInt();
            switch (opcion) {
               case 1:
                  descifrarDES(rutaKeystore);
                  break;
               case 2:
                  descifrarAES(rutaKeystore);
                  break;
               case 3:
                  descifrarBlowfish(rutaKeystore);
                  break;
               default:
                  System.out.println("Las opciones van de 1 a 3. ");
                  break;

            }
         } catch (InputMismatchException ex) {
            System.out.println("Debes introducir un número .");
         }


   }

   private static void descifrarBlowfish(String rutaKeystore) {

         try {
            SecretKey claveBlowfish = loadKeyBlowfish(rutaKeystore);
            Cipher descifradorBlowfish = Cipher.getInstance("Blowfish");
            descifradorBlowfish.init(Cipher.DECRYPT_MODE, claveBlowfish);
            FileInputStream fe = new FileInputStream(ruta);
            byte[] bufferClaro = new byte[0];
            byte[] buffer = new byte[1000]; //array de bytes
            //lee el fichero de 1k en 1k y pasa los fragmentos leidos al cifrador
            int bytesLeidos = fe.read(buffer, 0, 1000);
            while (bytesLeidos != -1) {//mientras no se llegue al final del fichero
               //pasa texto cifrado al cifrador y lo descifra, asignándolo a bufferClarobufferClaro = descifradorAsimetrico.update(buffer, 0, bytesLeidos);
               bufferClaro = descifradorBlowfish.update(buffer, 0, bytesLeidos);
               bytesLeidos = fe.read(buffer, 0, 1000);
            }
            bufferClaro = descifradorBlowfish.doFinal(); //Completa el descifrado
            System.out.println("Mensaje descifrado:");
            // cierra archivos
            System.out.println(new String(bufferClaro));
            fe.close();


         } catch (Exception e) {
            System.out.println("No se ha podido obtener la clave:" + e.getMessage());
         }

   }



   private static void descifrarAES(String rutaKeystore) {
         try {
            SecretKey claveAES = loadKeyAES(rutaKeystoreAES);
            Cipher descifradorAES = Cipher.getInstance("AES");
            descifradorAES.init(Cipher.DECRYPT_MODE, claveAES);
            FileInputStream fe = new FileInputStream(ruta);
            byte[] bufferClaro = new byte[0];
            byte[] buffer = new byte[1000]; //array de bytes
            //lee el fichero de 1k en 1k y pasa los fragmentos leidos al cifrador
            int bytesLeidos = fe.read(buffer, 0, 1000);
            while (bytesLeidos != -1) {//mientras no se llegue al final del fichero
               //pasa texto cifrado al cifrador y lo descifra, asignándolo a bufferClarobufferClaro = descifradorAsimetrico.update(buffer, 0, bytesLeidos);
               bufferClaro = descifradorAES.update(buffer, 0, bytesLeidos);
               bytesLeidos = fe.read(buffer, 0, 1000);
            }
            bufferClaro = descifradorAES.doFinal(); //Completa el descifrado
            System.out.println("Mensaje descifrado:");
            // cierra archivos
            System.out.println(new String(bufferClaro));
            fe.close();

         } catch (Exception e) {
            System.out.println("No se ha podido obtener la clave:" + e.getMessage());
         }

   }




   private static void descifrarDES(String rutaKeystore) {
      boolean descifardo =false;
         try {
            SecretKey claveDES = loadKeyDES(rutaKeystore);
            //desciframos el mensaje
            Cipher descifradorDES = Cipher.getInstance("DES");
            descifradorDES.init(Cipher.DECRYPT_MODE, claveDES);
            FileInputStream fe = new FileInputStream(ruta);
            byte[] bufferClaro = new byte[0];
            byte[] buffer = new byte[1000]; //array de bytes
            //lee el fichero de 1k en 1k y pasa los fragmentos leidos al cifrador
            int bytesLeidos = fe.read(buffer, 0, 1000);
            while (bytesLeidos != -1) {//mientras no se llegue al final del fichero
               //pasa texto cifrado al cifrador y lo descifra, asignándolo a bufferClarobufferClaro = descifradorAsimetrico.update(buffer, 0, bytesLeidos);
               bufferClaro = descifradorDES.update(buffer, 0, bytesLeidos);
               bytesLeidos = fe.read(buffer, 0, 1000);
            }
            bufferClaro = descifradorDES.doFinal(); //Completa el descifrado
            System.out.println("Mensaje descifrado:");
            // cierra archivos
            System.out.println(new String(bufferClaro));
            fe.close();
            descifardo=true;

         } catch (Exception e) {
            System.out.println("No se ha podido obtener la clave:" + e.getMessage());
         }

   }
   /**
    * Metodo para obtener la clave DES
    * @return
    * @throws IOException
    * @throws NoSuchPaddingException
    * @throws NoSuchAlgorithmException
    */

   private static SecretKey loadKeyDES(String rutaKeystore) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
      FileInputStream fis = new FileInputStream(rutaKeystore);
      int numBtyes = fis.available();
      byte[] bytes = new byte[numBtyes];
      fis.read(bytes);
      DESKeySpec dks = new DESKeySpec(bytes);
      // Cree una fábrica de claves y utilícela para convertir DESKeySpec en un objeto SecretKey
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
      SecretKey key1 = keyFactory.generateSecret(dks);
      fis.close();
      return key1;
   }

   /**
    * Metodo para obtener la clave AES
    * @return
    * @throws IOException
    * @throws NoSuchPaddingException
    * @throws NoSuchAlgorithmException
    */
   private static SecretKey loadKeyAES(String rutaKeystore) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException {
      FileInputStream fis = new FileInputStream(rutaKeystore);
      int numBtyes = fis.available();
      byte[] bytes = new byte[numBtyes];
      System.out.println(numBtyes);
      fis.read(bytes);
      // Cree una fábrica de claves par descifrar la clave
      SecretKeySpec secretKeySpec = new SecretKeySpec(bytes,"AES");

      fis.close();
      return secretKeySpec;
   }

   /**
    * Metodo para obtener la clave Blowfish
    * @param rutaKeystore
    * @return
    * @throws IOException
    * @throws InvalidKeyException
    * @throws NoSuchAlgorithmException
    * @throws InvalidKeySpecException
    */
   private static SecretKey loadKeyBlowfish(String rutaKeystore) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
      FileInputStream fis = new FileInputStream(rutaKeystore);
      int numBtyes = fis.available();
      byte[] bytes = new byte[numBtyes];
      fis.read(bytes);
      // Cree una fábrica de claves par descifrar la clave
      SecretKeySpec secretKeySpec = new SecretKeySpec(bytes,"Blowfish");
      fis.close();
      return secretKeySpec;
   }

}



