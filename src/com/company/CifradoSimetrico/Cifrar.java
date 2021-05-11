package com.company.CifradoSimetrico;

import javax.crypto.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * @author :Sonia Paez Romero Creado:11/05/2021
 * Clase para cifrar el mensaje con los distintos métodos  simetricos
 */

public class Cifrar {
    static int byteleidos;
    static String ruta="src/com/company/CifradoSimetrico/fichero";
    static String rutaKeystore="src/com/company/CifradoSimetrico/keystore/";
    public static void main(String[] args){
        Scanner scan =new Scanner(System.in);
        String mensaje="";
        int opcion;
        System.out.println(" Dime el texto que quieres cifrar");
        mensaje= scan.nextLine();
        System.out.println(" Dime el que metodo que quieres  usar:");
        System.out.println(" 1.DES");
        System.out.println(" 2.AES");
        System.out.println(" 3.Blowfish");
        try {
            opcion = scan.nextInt();
            switch (opcion){
                case 1:
                   cifrarDES(mensaje);
                    break;
                case 2:
                    cifrarAES(mensaje);
                    break;
                case 3:
                    cifrarBLowfish(mensaje);
                    break;
                default:
                    System.out.println("Las opciones van de 1 a 3. ");
                break;

            }
        }catch (InputMismatchException | IOException ex){
            System.out.println("Debes introducir un número .");
        }


    }

    private static void cifrarBLowfish(String mensaje) {
        FileOutputStream fs = null; //fichero de salida
        // Generamos el algorritmo y la cla ve metodo AES
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
                keyGenerator.init(128);
                SecretKey claveBlowfish = keyGenerator.generateKey();
                saveClave(claveBlowfish, rutaKeystore + "clave.dat"); // pasamos  key a store
                // ciframos el mensaje
                Cipher cifradorBlowfish = Cipher.getInstance("Blowfish");
                cifradorBlowfish.init(Cipher.ENCRYPT_MODE, claveBlowfish);
                System.out.println("Mensaje cifrado");
                // Creamnos array de bytes y pasamos al fichero pasamos a fichero
                byte[] buffer = new byte[1000]; //array de bytes
                byte[] bufferAES;
                byteleidos = buffer.length;
                try {
                    fs = new FileOutputStream(ruta + ".cifrado");
                    bufferAES = cifradorBlowfish.doFinal(mensaje.getBytes()); //Completa el cifrado
                    fs.write(bufferAES); //Graba el final del texto cifrado, si lo hay
                    //Cierra ficheros
                    System.out.println("Mensaje  cifrado escrito en fichero");
                    System.out.println("Clave Escrita:" + cifradorBlowfish);

                } catch (FileNotFoundException e) {
                    System.out.println("Fichero no encontrado:" + e.getMessage());
                } catch (IOException e) {
                    System.out.println("Fichero no se ha podido escribir:" + e.getMessage());
                } catch (IllegalBlockSizeException e) {
                    System.out.println("No se ha podido cifrar:" + e.getMessage());
                } catch (BadPaddingException e) {
                    System.out.println("No se ha podido cifrar:" + e.getMessage());
                }

            } catch (NoSuchAlgorithmException e) {
                System.out.println("No se ha podido genera la clave:" + e.getMessage());
            } catch (NoSuchPaddingException e) {
                System.out.println("No se ha podido cifrar:" + e.getMessage());
            } catch (InvalidKeyException e) {
                System.out.println("La clave no es válida para el cifrado:" + e.getMessage());
            } catch (InvalidParameterException e) {
                System.out.println("La clave no es válida por su tamaño:" + e.getMessage());
            } catch (Exception e) {
                System.out.println("No se ha podido guardar la clave:" + e.getMessage());
            }

    }


    private static void cifrarAES(String mensaje) {
        FileOutputStream fs = null; //fichero de salida
        // Generamos el algorritmo y la cla ve metodo AES
        try {
            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey claveAES=keyGenerator.generateKey();
            saveClave(claveAES,ruta+"clave.dat");// pasamos  variable  a store
            // ciframos el mensaje
            Cipher cifradorAES =Cipher.getInstance("AES");
            cifradorAES.init(Cipher.ENCRYPT_MODE,claveAES);
            System.out.println("Mensaje cifrado");
            // Creamnos array de bytes y pasamos al fichero pasamos a fichero
            byte[] buffer = new byte[1000]; //array de bytes
            byte[] bufferAES;
            try {
                fs=new FileOutputStream(ruta+".cifrado");
                bufferAES = cifradorAES.doFinal(mensaje.getBytes()); //Completa el cifrado
                fs.write(bufferAES); //Graba el final del texto cifrado, si lo hay
                //Cierra ficheros
                fs.close();
                System.out.println("Mensaje  cifrado escrito en fichero");
                System.out.println("Clave Escrita:" +claveAES);
            } catch (FileNotFoundException e) {
                System.out.println("Fichero no encontrado:"+ e.getMessage());
            } catch (IOException e) {
                System.out.println("Fichero no se ha podido escribir:"+ e.getMessage());
            } catch (IllegalBlockSizeException e) {
                System.out.println("No se ha podido cifrar:"+ e.getMessage());
            } catch (BadPaddingException e) {
                System.out.println("No se ha podido cifrar:"+ e.getMessage());
            }

        } catch (NoSuchAlgorithmException e) {
            System.out.println("No se ha podido genera la clave:"+ e.getMessage());
        } catch (NoSuchPaddingException e) {
            System.out.println("No se ha podido cifrar:"+ e.getMessage());
        } catch (InvalidKeyException e) {
            System.out.println("La clave no es válida para el cifrado:"+ e.getMessage());
        }catch (InvalidParameterException e){
            System.out.println("La clave no es válida por su tamaño:"+ e.getMessage());
        } catch (Exception e) {
            System.out.println("No se ha podido guardar la clave:"+ e.getMessage());
        }
    }

    private static void cifrarDES(String mensaje) throws IOException {
        FileOutputStream fs = null; //fichero de salida
        // Generamos el algorritmo y la cla ve metodo DES
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
                keyGenerator.init(56);
                SecretKey claveDES = keyGenerator.generateKey();
                saveClave(claveDES, rutaKeystore + "clave.dat");
                // ciframos el mensaje
                Cipher cifradorDES = Cipher.getInstance("DES");
                cifradorDES.init(Cipher.ENCRYPT_MODE, claveDES);
                System.out.println("Mensaje cifrado");
                // Creamnos array de bytes y pasamos al fichero pasamos a fichero
                byte[] buffer = new byte[1000]; //array de bytes
                byte[] bufferDES;
                try {
                    fs = new FileOutputStream(ruta + ".cifrado");
                    bufferDES = cifradorDES.doFinal(mensaje.getBytes()); //Completa el cifrado
                    fs.write(bufferDES); //Graba el final del texto cifrado, si lo hay
                    //Cierra ficheros
                    fs.close();
                    System.out.println("Mensaje  cifrado escrito en fichero");
                    System.out.println("Clave Escrita:" + cifradorDES);
                } catch (FileNotFoundException e) {
                    System.out.println("Fichero no encontrado:" + e.getMessage());
                } catch (IOException e) {
                    System.out.println("Fichero no se ha podido escribir:" + e.getMessage());
                } catch (IllegalBlockSizeException e) {
                    System.out.println("No se ha podido cifrar:" + e.getMessage());
                } catch (BadPaddingException e) {
                    System.out.println("No se ha podido cifrar:" + e.getMessage());
                }
            } catch (NoSuchAlgorithmException e) {
                System.out.println("No se ha podido genera la clave:" + e.getMessage());
            } catch (NoSuchPaddingException e) {
                System.out.println("No se ha podido cifrar:" + e.getMessage());
            } catch (InvalidKeyException e) {
                System.out.println("La clave no es válida para el cifrado:" + e.getMessage());
            } catch (Exception e) {
                System.out.println("No se ha podido guardar la clave:" + e.getMessage());
            }
    }

    /**
     * Método para guardar la clave en keu store
     * @param key
     * @param fileName
     * @throws Exception
     */
    private static void saveClave(Key key, String fileName) throws Exception {
        byte[] keyBytes = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(keyBytes);
        fos.close();
    }

}
