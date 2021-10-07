package de.uniba.rz.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.naming.NamingException;

public class TicketServerMain {

	public static void main(String[] args) throws IOException, NamingException, TimeoutException {

		List<RemoteAccess> remoteAccessImplementations = getAvailableRemoteAccessImplementations(args);
		// Starting remote access implementations:
		for (RemoteAccess implementation : remoteAccessImplementations) {
			implementation.prepareStartup(MyTicketStore.getInstance());
			new Thread(implementation).start();

		}

		try (BufferedReader shutdownReader = new BufferedReader(new InputStreamReader(System.in))) {
			System.out.println("Press enter to shutdown system.");
			shutdownReader.readLine();
			System.out.println("Shutting down...");
	
			// Shutting down all remote access implementations
			for (RemoteAccess implementation : remoteAccessImplementations) {
				implementation.shutdown();
			}
			System.out.println("completed. Bye!");
		}
	}

	private static List<RemoteAccess> getAvailableRemoteAccessImplementations(String[] args) {
		List<RemoteAccess> implementations = new ArrayList<>();
		implementations.add(new UdpRemoteAccess(args[0], args[1]));
		implementations.add(new AmqpRemoteAccessPush(args[0], args[2], args[3], args[4], args[5]));
		implementations.add(new GRPCRemoteAccess());
		implementations.add(new RestRemoteAccess(args[0], args[1]));

		return implementations;
	}
}
