
## Run steps that worked for us:
### Starting the server
1. There should be a "shared\src\generated [main]" folder. If not, do a re-build.
1. The server needs to be started first, b/c the client makes a getAllTickets request on startup
1. Import the Project in IntelliJ/Eclipse, with root directory **ticketservice**
1. If your JDK version is less than 11 you need to set 'sourceCompatibility' in **build.gradle** to that number
1. Open TicketServerMain.java
    1. Eclipse:
        1. click Run > Run Configurations > Java Application > New Configuration
        1. The config will be generated, you only need to set "Program arguments" in the "Arguments" tab (see last paragraph for program argument examples)
    2. IntelliJ
        1. click on green trinagle next to the main-method of TicketServerMain > create 'TicketServerMain.main()'
        2. the config is generated, you only need to set 'Program arguments' (see last paragraph for program argument examples)
1. Run/Debug the configuration

The command-line method gave us the error that not all necessary classes(from shared) were included in the jar.
Steps left here as documentation:
1. Within the _idistrsys_ folder start a CMD.
2. Run the server via **java -jar server\build\libs\server-1.0.jar**

The _shared_ project is automatically build before the server is started.
Look at the dependencies block in the server/build.gradle.

### Starting the client
1. Create a Run-Configuration for Main.java with the necessary program arguments, the same way as for the server (see last paragraph for program argument examples)
2. Run/Debug the configuration
3. For testing purposes the client configuration can be run multiple times from the same machine(at least in IntelliJ). When closing one client all of close.

The command-line method gave us errors when running the code. Steps left here for documentation:
1. Within the _idistrsys_ folder start a CMD.
2. Run the server via **gradle client:run**

The _shared_ project is automatically included in the dependencies of the client and server.
If you are interested inspect the `idistrsys/build.gradle` to see all the relevant settings, configurations and tasks.

### Example Program Arguments
1. Client:
    1. UDP:  "udp localhost 1337"
    1. AMQP: "amqp localhost TicketQueue UpdateQueue ListRequestQueue ticketUpdateFanout"
    1. gRPC: "grpc"
    1. REST: "rest localhost 1337"
1. Server:
    "localhost 1337 TicketQueue UpdateQueue ListRequestQueue ticketUpdateFanout"
# DSG Project- UDP - AMQP - REST - gRPC 

