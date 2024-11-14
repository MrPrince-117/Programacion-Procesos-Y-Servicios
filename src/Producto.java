import java.util.ArrayList;

public class Producto {
    public class Inventario(){
        ArrayList<Producto> lista = new ArrayList();

        boolean addProducto(Producto producto){
            return lista.add(producto);
        }

        //boolean eliminarProducto(Producto producto){
           // lista.contains();
        }
    }
}
