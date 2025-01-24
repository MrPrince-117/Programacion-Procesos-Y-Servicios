package UD_4;

import java.net.SocketException;

public class ClienteFTP1 {
    public static void main(String[] args)throws SocketException {

        System.out.println(clienteFTP.getReplyString());
        //codigo de respuesta
        int respuesta = clienteFTP.getReplyCode();

        System.out.println("Respuesta: "  + respuesta);
        
    }
}
