package eighteen;

import java.lang.*;
import java.awt.Color;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

import eighteen.Board.BadBoardException;
import eighteen.Board.BadMoveException;
import eighteen.Board.GameOverException;

public class Server {
	static Board board;
	static AI ai;
	static PrintWriter out;
	static BufferedReader in;
	static boolean gameOver = false;
	static long time;
	static long maxTime;
	static String victoryState;
	static boolean isServer;
	
	public static void main(String args[]) throws IOException {
		//Input server check
		System.out.println("Is this the server?");
		Scanner sysIn = new Scanner(System.in);
		String inToken = sysIn.nextLine();
		if(inToken.equalsIgnoreCase("yes")) {
			isServer = true;
		}
		else {
			isServer = false;
		}
		try {
			//Open up the connection
			Socket socket = null;
			ServerSocket server = null;
			if(isServer) {
				System.out.println("Starting");
				server = new ServerSocket(4001);
				System.out.println("Waiting for client");
				socket = server.accept();
				System.out.println("Server has connected.");
			}
			else {
				System.out.println("Client connecting");
				socket = new Socket("Travis-PC", 4001);
			}
			
			//Create the writer and reader
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if(isServer) {
				System.out.println("Enter rows");
				int rows = sysIn.nextInt();
				System.out.println("Enter columns");
				int columns = sysIn.nextInt();
				System.out.println("Enter max time");
				maxTime = sysIn.nextInt();
				sysIn.nextLine();
				System.out.println("Enter B or W");
				inToken = sysIn.next();
				send("WELCOME");
				send("INFO " + columns + " " + rows + " " + inToken + " " + maxTime);
				board = new Board(rows, columns);
				if(inToken.equals("W")) {
					ai = new AI(Color.WHITE, maxTime);
				}
				else {
					ai = new AI(Color.BLACK, maxTime);
				}
			}
			
			//The execution loop
			while(!gameOver) {
				readAndExecute();
			}
			System.out.println(victoryState);
			
			//Close the sockets
			out.close();
			socket.close();
			if(isServer) {
				server.close();
			}
		} catch (UnknownHostException e) {
			System.out.println("Don't know about host");
		} catch (IOException e) {
			System.out.println("Could get IO connection");
		} catch (BadBoardException e) {
			e.printStackTrace();
		}
	}
	
	public static void send(String message) {
		System.out.println("Sending: " + message);
		out.write(message);
	}
	
	//The loop call that takes in the input and sends the output
	public static void readAndExecute() throws IOException {
		//Read input
		String input = in.readLine();
		System.out.println("Received: " + input);
		Scanner reader = new Scanner(input);
		
		//Look through the input
		while(reader.hasNext()) {
			String token = reader.next();
			if(token.equals("WELCOME")) {
				break;
			}
			else if(token.equals("INFO")) {
				int rows = reader.nextInt();
				int columns = reader.nextInt();
				try {
					board = new Board(rows, columns);
				} catch (BadBoardException e) {
					send("ILLEGAL");
					send("LOSER");
					victoryState = "WE WIN!";
					gameOver = true;
					break;
				}
				String color = reader.next();
				if(color.equals(" ") || color.equals("")) {
					color = reader.next();
				}
				long time = reader.nextLong();
				if(color.equals("B")) {
					ai = new AI(Color.BLACK, time);
				}
				else {
					ai = new AI(Color.WHITE, time);
				}
				maxTime = time;
				send("READY");
			}
			else if(token.equals("READY")) {
				send("BEGIN");
				getAndSendMove();
			}
			else if(token.equals("OK")) {
				time = System.currentTimeMillis();
			}
			else if(token.equals("TIME") || token.equals("ILLEGAL")) {
				if(isServer) {
					send("WINNER");
					victoryState = "We lose :(";
				}
				gameOver = true;
			}
			else if(token.equals("WINNER")) {
				victoryState = "WE WIN!";
				gameOver = true;
			}
			else if(token.equals("LOSER")) {
				victoryState = "We lose :(";
				gameOver = true;
			}
			else if(token.equals("TIE")) {
				victoryState = "WE TIE";
				gameOver = true;
			}
			else {
				//Parse the given move and execute it
				if(System.currentTimeMillis() - time > maxTime) {
					send("TIME");
					send("LOSER");
					gameOver = true;
				}
				while(reader.hasNext()) {
					String stateToken = reader.next();
					AttackState state;
					int columnStart = reader.nextInt() - 1;
					int rowStart = reader.nextInt() - 1;
					int columnEnd = 0;
					int rowEnd = 0;
					if(reader.hasNext()) {
						reader.next();
					}
					if(stateToken.equals("A")) {
						state = AttackState.ADVANCING;
						columnEnd = reader.nextInt() - 1;
						rowEnd = reader.nextInt() - 1;
					}
					else if(stateToken.equals("W")) {
						state = AttackState.WITHDRAWING;
						columnEnd = reader.nextInt() - 1;
						rowEnd = reader.nextInt() - 1;
					}
					else if(stateToken.equals("P")) {
						state = AttackState.NEITHER;
						columnEnd = reader.nextInt() - 1;
						rowEnd = reader.nextInt() - 1;
					}
					else {
						state = AttackState.SACRIFICE;
					}
					try {
						board.move(new Move(board.getPiece(rowStart, columnStart), new Piece.adjLoc(rowEnd, columnEnd), state));
					} catch (BadMoveException e) {
						send("ILLEGAL");
						send("LOSER");
						victoryState = "WE TIE";
						gameOver = true;
					} catch (GameOverException e) {
						if(e.getMessage().contains("tie")) {
							send("TIE");
							victoryState = "WE WIN!";
						}
						else if(e.getMessage().contains("white")) {
							if(ai.getColor() == Color.WHITE) {
								send("LOSER");
								victoryState = "WE WIN!";
							}
							else {
								send("WINNER");
								victoryState = "We lose :(";
							}
						}
						else {
							if(ai.getColor() == Color.WHITE) {
								send("WINNER");
								victoryState = "We lose :(";
							}
							else {
								send("LOSER");
								victoryState = "WE WIN!";
							}
						}
						gameOver = true;
					}
				}
				//After parsin, send our move
				send("OK");
				getAndSendMove();
			}
		}
	}
	
	//Uses the AI to compute the next move
	public static void getAndSendMove() {
		try {
			ai.opponentMove(board);
			ai.getNewLevel();
			ai.getNewLevel();
			ai.getNewLevel();
			ArrayList<Move> moves = ai.alphaBetaSearch();
			String message = "";
			for(int i = 0; i < moves.size() - 1; i++) {
				board.move(moves.get(i));
				message += moves.get(i).toString() + " + ";
			}
			message += moves.get(moves.size() - 1);
			send(message);
		} catch (BadMoveException e) {
			send("ILLEGAL");
			send("LOSER");
		} catch (GameOverException e) {
			if(e.getMessage().contains("tie")) {
				send("TIE");
				victoryState = "WE TIE";
			}
			else if(e.getMessage().contains("white")) {
				if(ai.getColor() == Color.WHITE) {
					send("LOSER");
					victoryState = "WE WIN!";
				}
				else {
					send("WINNER");
					victoryState = "We lose :(";
				}
			}
			else {
				if(ai.getColor() == Color.WHITE) {
					send("WINNER");
					victoryState = "We lose :(";
				}
				else {
					send("LOSER");
					victoryState = "WE WIN!";
				}
			}
			gameOver = true;
		}
	}
}
