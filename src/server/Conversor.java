package server;

public class Conversor {

	
public String DronAereo (int vCodigo) {
		
		String vNombre = "";
		
		switch (vCodigo) {
	    	case 1:
	    		vNombre = "Bomba";
	    		break;
	    	case 2:
	    		vNombre = "Camara";
	    		break;
	    	case 3:
	    		vNombre = "Canon";
	    		break;
	    	case 4:
	    		vNombre = "Motores";
	    		break;
	     default:
	  }
		return vNombre;	
	}
	
public String DronTerrestre (int vCodigo) {
	
	String vNombre = "";
	
	switch (vCodigo) {
    	case 1:
    		vNombre = "Blindaje";
    		break;
    	case 2:
    		vNombre = "Camara";
    		break;
    	case 3:
    		vNombre = "Canon";
    		break;
     default:
  }
	return vNombre;	
}

public String Base (int vCodigo) {
	
	String vNombre = "";
	
	switch (vCodigo) {
    	case 1:
    		vNombre = "VidaPolvorin";
    		break;
    	case 2:
    		vNombre = "VidaDespegue";
    		break;
     default:
  }
	return vNombre;	
}
}
