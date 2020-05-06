# Multi-threaded Group Chat Server

A chatting system is implemented where multiple clients can join the server and chat together like a chat-group. It is just like the facebook messenger group.

When the user run the server, server will wait until a client request comes. When a client program is run, this client request will go to the server and the server will create a new thread for that client. So the server can handle multiple clients by using different threads for each client.

When a new client joins the server, first thing the server will do is to create a separate
thread for him/her. Then the server will ask his/her name by sending a message “Enter
your name:”. After getting this message from the server,  client’s name will be taken as input
from the client side program’s console. The new client will send this name to the server and server will store this information. Let’s assume, the new client’s name is Ironman. Then, server
will also send a server-message to all of the existing clients that, “Ironman has joined the
group chat."

Let’s assume that there are two clients at this moment. Their names are: Ironman and Thor.
Now, another client program can be run and provides 'Hulk' in the console as the input of client’s
name. Other existing clients (in this example: Ironman and Thor) will see in their console
that “Hulk has joined the group chat.” Suppose, Ironman want to send a message in
the group, he wants to say “Hello”. So he will write the message in his console and press
enter. And it will go to server and the server will send this message to all of the existing
clients. As a result, all of the existing clients will see in their windows (consoles) that-
“Ironman: Hello.”

Now suppose, Ironman wants to leave the group-chat, he will have to simply stop his
program by closing it. Then the server will have to notify other existing clients by sending
them a server-message that “Ironman has left the group chat.”

There are many functions for sending and reading data. But here I only used writeBytes() and readLine() functions on both server and client sides for this server. I also used a ClientHandler thread on the Server Side program for handling multiple
clients. I kept a list of existing clients’ names and output-streams on the server side to send any
message to all of them at once. I implemented simultaneous input/output for any client on this server. So
I used two separate threads on the client side: one for input-stream and other for output-stream.

