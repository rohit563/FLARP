import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MiniServer extends Thread{

    private Socket socket = null;

    public MiniServer(Socket socket) {

        super("MiniServer");
        this.socket = socket;

    }

    public void run() {
            //Read input and process here
    	try(
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
	        String inputLine;
	        String outputLine;
	        boolean pairing = true;
	        
	        while ((inputLine = in.readLine()) != null && pairing == true) {
	        	outputLine = pair(inputLine);
	        	out.println(outputLine);
	        	if(outputLine.equals("1")) // This is temporary pairing for swords
	        		pairing = false;
	        }
	        
	        while ((inputLine = in.readLine()) != null) {
	        	outputLine = processInput(inputLine);
	            //out.println(outputLine);
	            //if (outputLine.equals("Bye."))
	                //break;
	        }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
            //implement your methods here
    public String pair(String input) {
    	Scanner scanner = new Scanner(input);
    	boolean newSword = true;
    	Integer flarperNumber = FlarperManager.getInstance().flarpers.size();
    	scanner.nextLine();
    	scanner.nextLine();
    	Integer swordNumber = Integer.parseInt(scanner.nextLine());
    	
    	for(Sword s : FlarperManager.getInstance().swords) {
    		if(s.getId() == swordNumber) {
    			newSword = false;
    		}
    	}
    	if(newSword) {
    		Flarper flarper = new Flarper(flarperNumber);
    		Sword sword = new Sword(swordNumber);
    		flarper.pairSword(sword);
    		FlarperManager.getInstance().flarpers.add(flarper);
    		FlarperManager.getInstance().swords.add(sword);
    		
    	}
    	scanner.close();
    	return flarperNumber.toString();
    }
    public String processInput(String input) {
    	Scanner scanner = new Scanner(input);
    	Integer flarperNumber = Integer.parseInt(scanner.nextLine());
    	Integer flarperDamage = Integer.parseInt(scanner.nextLine());
    	Integer attackingFlarperSwordNumber = Integer.parseInt(scanner.nextLine());
    	damageFlarper(flarperNumber, flarperDamage, attackingFlarperSwordNumber);

    	scanner.close();
    	return "Placeholder String";
    }

	private void damageFlarper(Integer flarperNumber, Integer flarperDamage, Integer attackingFlarperSwordNumber) {
		Flarper flarper = FlarperManager.getInstance().flarpers.get(flarperNumber);
		Flarper attackingFlarper;
		for(Flarper f : FlarperManager.getInstance().flarpers) {
			if(f.getSword().getId() == attackingFlarperSwordNumber) {
				attackingFlarper = f;
				if(flarper.isAlive() && attackingFlarper.isAlive()) {
					flarper.takeDamage(flarperDamage);
				}
				break;
			}
		}
	}
    

}