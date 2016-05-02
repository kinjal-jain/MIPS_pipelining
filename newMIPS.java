//Project Two

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class Disassembly {
	FileWriter disassemblyStream, simStream;
	BufferedWriter output, simOutput;
	String line;
	final String INST_SET_1 = "000";
	final String INST_SET_2 = "001";
	final String INST_SET_3 = "010";
	final String INST_SET_4 = "011";
	final String INST_SET_5 = "100";
	int category_4_result = 0;
	String category_4_destination = "";
	
	String waiting, execute;
	
	//Buffers Created
	StringQueue TempBuf1 = new StringQueue(4);
	StringQueue Buffer1 = new StringQueue(8);
	StringQueue TempBuffer1 = new StringQueue(8);
	StringQueue1 Buffer2 = new StringQueue1(2);
	StringQueue1 TempBuffer2 = new StringQueue1(2);
	StringQueue1 Buffer3 = new StringQueue1(2);
	StringQueue1 TempBuffer3 = new StringQueue1(2);
	StringQueue1 Buffer4 = new StringQueue1(2);
	StringQueue1 TempBuffer4 = new StringQueue1(2);
	StringQueue1 Buffer5 = new StringQueue1(2);
	StringQueue1 TempBuffer5 = new StringQueue1(2);
	
	// Storing result buffers
	ResultBuffer Buffer6 = new ResultBuffer();
	ResultBuffer TempBuffer6 = new ResultBuffer();
	ResultBuffer Buffer7 = new ResultBuffer();
	ResultBuffer TempBuffer7 = new ResultBuffer();
	ResultBuffer Buffer8 = new ResultBuffer();
	ResultBuffer TempBuffer8 = new ResultBuffer();
	ResultBuffer Buffer9 = new ResultBuffer();	
	ResultBuffer TempBuffer9 = new ResultBuffer();	
	ResultBuffer Buffer10 = new ResultBuffer();	
	ResultBuffer TempBuffer10 = new ResultBuffer();	
	ResultBuffer Buffer11 = new ResultBuffer();	
	ResultBuffer TempBuffer11 = new ResultBuffer();	
	int Buffer12;
	int TempBuffer12;
	
	

	Map<String, String> instructionCatalog = new HashMap<String, String>();

	HashMap<String, String> register = new HashMap<String, String>();
	HashMap<String, Integer> registerValues = new HashMap<String, Integer>();
	HashMap<Integer, String> InstructionSet = new HashMap<Integer, String>();
	TreeMap<Integer, String> dataBank = new TreeMap<Integer, String>();
	HashMap<String, Boolean> registerStatus = new HashMap<String, Boolean>();
	HashMap<String, Boolean> tempRegisterStatus = new HashMap<String, Boolean>();
	HashMap<String, Integer> tempRegisterValues = new HashMap<String, Integer>();

	// int[] Registers = new int[32];

	Disassembly(String input) throws IOException {
		disassemblyStream = new FileWriter("disassembly.txt");
		output = new BufferedWriter(disassemblyStream);
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(input));
		instructionCatalog = initializeInstructionSet();
		register = initializeRegisters();
		registerValues = initializeRegisterValues();
		tempRegisterValues = initializeRegisterValues();
		registerStatus = initializeRegisterStatus();
		tempRegisterStatus = initializeRegisterStatus();
		int counter = 256;
		boolean breakEncountered = false;
		while ((line = reader.readLine()) != null) {
			String instruction = line.substring(0, 6);
			String category = line.substring(0, 3);
			String disassembledOutput = null;
			if (!breakEncountered) {
				switch (category) {
				case INST_SET_1:
					disassembledOutput = categoryOne(line,
							instructionCatalog.get(instruction));
					break;
				case INST_SET_2:
					disassembledOutput = categoryTwo(line,
							instructionCatalog.get(instruction));
					break;
				case INST_SET_3:
					disassembledOutput = categoryThree(line,
							instructionCatalog.get(instruction));
					break;
				case INST_SET_4:
					disassembledOutput = categoryFour(line,
							instructionCatalog.get(instruction));
					break;
				case INST_SET_5:
					disassembledOutput = categoryFive(line,
							instructionCatalog.get(instruction));
					break;
				default:
					break;
				}

				// Writing Output to file
				output.write(line + "\t" + counter + "\t" + disassembledOutput
						+ "\r\n");

				// Putting disassembled output into Instruction set for
				// simulation
				InstructionSet.put(counter, disassembledOutput);

				// Incrementing the Output
				counter += 4;

				// Check if Break encountered and change boolean
				if (disassembledOutput.equalsIgnoreCase("break")) {
					breakEncountered = true;
				}

			} else {
				// Write to Databank
				String result = null;
				int op;
				if (line.charAt(0) == '1') {
					result = twosCompliment(line);
				} else if (line.charAt(0) == '0') {
					op = Integer.parseInt(line, 2);
					result = Integer.toString(op);
				}
				// System.out.println("Putting value to Databank : " + counter
				// + " with value ->" + result);
				disassembledOutput = result;
				dataBank.put(counter, result);
				output.write(line + "\t" + counter + "\t" + disassembledOutput
						+ "\r\n");
				counter += 4;
			}
		}

		output.close();
//		System.out.println(InstructionSet.keySet() + " : " + "\n"
//				+ InstructionSet.values() + "\n");
		// System.out.println(dataBank.keySet() + " : " + "\n" +
		// dataBank.values()
		// + "\n");

		// Creating Simulation File
		Simulation(counter);
	}

	private HashMap<String, Integer> initializeRegisterValues() {
		HashMap<String, Integer> rVal = new HashMap<String, Integer>();
		rVal.put("R0", 0);
		rVal.put("R1", 0);
		rVal.put("R2", 0);
		rVal.put("R3", 0);
		rVal.put("R4", 0);
		rVal.put("R5", 0);
		rVal.put("R6", 0);
		rVal.put("R7", 0);
		rVal.put("R8", 0);
		rVal.put("R9", 0);
		rVal.put("R10", 0);
		rVal.put("R11", 0);
		rVal.put("R12", 0);
		rVal.put("R13", 0);
		rVal.put("R14", 0);
		rVal.put("R15", 0);
		rVal.put("R16", 0);
		rVal.put("R17", 0);
		rVal.put("R18", 0);
		rVal.put("R19", 0);
		rVal.put("R20", 0);
		rVal.put("R21", 0);
		rVal.put("R22", 0);
		rVal.put("R23", 0);
		rVal.put("R24", 0);
		rVal.put("R25", 0);
		rVal.put("R26", 0);
		rVal.put("R27", 0);
		rVal.put("R28", 0);
		rVal.put("R29", 0);
		rVal.put("R30", 0);
		rVal.put("R31", 0);
		rVal.put("LO", 0);
		rVal.put("HI", 0);
		return rVal;
	}
	
	private HashMap<String, Boolean> initializeRegisterStatus() {
		HashMap<String, Boolean> rStatus = new HashMap<String, Boolean>();
		rStatus.put("R0", true);
		rStatus.put("R1", true);
		rStatus.put("R2", true);
		rStatus.put("R3", true);
		rStatus.put("R4", true);
		rStatus.put("R5", true);
		rStatus.put("R6", true);
		rStatus.put("R7", true);
		rStatus.put("R8", true);
		rStatus.put("R9", true);
		rStatus.put("R10", true);
		rStatus.put("R11", true);
		rStatus.put("R12", true);
		rStatus.put("R13", true);
		rStatus.put("R14", true);
		rStatus.put("R15", true);
		rStatus.put("R16", true);
		rStatus.put("R17", true);
		rStatus.put("R18", true);
		rStatus.put("R19", true);
		rStatus.put("R20", true);
		rStatus.put("R21", true);
		rStatus.put("R22", true);
		rStatus.put("R23", true);
		rStatus.put("R24", true);
		rStatus.put("R25", true);
		rStatus.put("R26", true);
		rStatus.put("R27", true);
		rStatus.put("R28", true);
		rStatus.put("R29", true);
		rStatus.put("R30", true);
		rStatus.put("R31", true);
		rStatus.put("LO", true);
		rStatus.put("HI", true);
		return rStatus;
	}

	private String categoryOne(String binaryInst, String operation) {
		String r1, r2, r3;
		int op;
		String operand = binaryInst.substring(6);
		String disassembledInst = null;
		switch (operation) {
		case "J":
			op = Integer.parseInt(operand + "00", 2);
			r1 = Integer.toString(op);
			disassembledInst = operation + " #" + r1;
			// System.out.println(disassembledInst);
			break;
		case "BEQ":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			op = Integer.parseInt(operand.substring(10) + "00", 2);
			r3 = Integer.toString(op);
			disassembledInst = operation + " " + register.get(r1) + ", "
					+ register.get(r2) + ", #" + r3;
			// System.out.println(disassembledInst);
			break;
		case "BNE":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			op = Integer.parseInt(operand.substring(10) + "00", 2);
			r3 = Integer.toString(op);
			disassembledInst = " " + register.get(r1) + ", " + register.get(r2)
					+ ", #" + r3;
			// System.out.println(disassembledInst);
			break;
		case "BGTZ":
			r1 = operand.substring(0, 5);
			op = Integer.parseInt(operand.substring(10) + "00", 2);
			r3 = Integer.toString(op);
			disassembledInst = operation + " " + register.get(r1) + ", #" + r3;
			// System.out.println(disassembledInst);
			break;
		case "SW":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			op = Integer.parseInt(operand.substring(10), 2);
			r3 = Integer.toString(op);
			disassembledInst = operation + " " + register.get(r2) + ", " + r3
					+ "(" + register.get(r1) + ")";
			// System.out.println(disassembledInst);
			break;
		case "LW":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			op = Integer.parseInt(operand.substring(10), 2);
			r3 = Integer.toString(op);
			disassembledInst = operation + " " + register.get(r2) + ", " + r3
					+ "(" + register.get(r1) + ")";
			// System.out.println(disassembledInst);
			break;
		case "BREAK":
			disassembledInst = operation;
			// System.out.println(disassembledInst);
			break;
		default:
			disassembledInst = "Some error occured in Category 1";
			break;
		}
		return disassembledInst;
	}

	private String categoryTwo(String binaryInst, String operation) {
		String r1, r2, r3; // Destination = R1, Src1 = R2, Src2 = R3
		String operand = binaryInst.substring(6);
		String disassembledInst = null;
		switch (operation) {
		case "ADD":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			r3 = operand.substring(10, 15);
			disassembledInst = operation + " " + register.get(r1) + ", "
					+ register.get(r2) + ", " + register.get(r3);
			// System.out.println(disassembledInst);
			break;
		case "SUB":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			r3 = operand.substring(10, 15);
			disassembledInst = operation + " " + register.get(r1) + ", "
					+ register.get(r2) + ", " + register.get(r3);
			// System.out.println(disassembledInst);
			break;
		case "AND":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			r3 = operand.substring(10, 15);
			disassembledInst = operation + " " + register.get(r1) + ", "
					+ register.get(r2) + ", " + register.get(r3);
			// System.out.println(disassembledInst);
			break;
		case "OR":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			r3 = operand.substring(10, 15);
			disassembledInst = operation + " " + register.get(r1) + ", "
					+ register.get(r2) + ", " + register.get(r3);
			// System.out.println(disassembledInst);
			break;
		case "SRL":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			r3 = operand.substring(10, 15);
			disassembledInst = operation + " " + register.get(r1) + ", "
					+ register.get(r2) + ", #" + Integer.parseInt(r3,2);
			//System.out.println(disassembledInst);
			break;
		case "SRA":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			r3 = operand.substring(10, 15);
			disassembledInst = operation + " " + register.get(r1) + ", "
					+ register.get(r2) + ", #" + Integer.parseInt(r3,2);
			//System.out.println(disassembledInst);
			break;
		default:
			disassembledInst = "Some error occured in Category 2";
			break;

		}
		return disassembledInst;
	}

	private String categoryThree(String binaryInst, String operation) {
		String r1, r2, r3; // Destination = R1, Src1 = R2, Immediate Value = R3
		int op;
		String operand = binaryInst.substring(6);
		String disassembledInst = null;
		switch (operation) {
		case "ADDI":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			op = Integer.parseInt(operand.substring(10), 2);
			r3 = Integer.toString(op);
			disassembledInst = operation + " " + register.get(r1) + ", "
					+ register.get(r2) + ", #" + r3;
			// System.out.println(disassembledInst);
			break;
		case "ANDI":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			op = Integer.parseInt(operand.substring(10), 2);
			r3 = Integer.toString(op);
			disassembledInst = operation + " " + register.get(r1) + ", "
					+ register.get(r2) + ", #" + r3;
			// System.out.println(disassembledInst);
			break;
		case "ORI":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			op = Integer.parseInt(operand.substring(10), 2);
			r3 = Integer.toString(op);
			disassembledInst = operation + " " + register.get(r1) + ", "
					+ register.get(r2) + ", #" + r3;
			// System.out.println(disassembledInst);
			break;
		default:
			disassembledInst = "Some error occured in Category 3";
			break;
		}
		return disassembledInst;

	}

	private String categoryFour(String binaryInst, String operation) {
		String r1, r2; // Src1 = R1, Src2 = R2
		String operand = binaryInst.substring(6);
		String disassembledInst = null;
		switch (operation) {
		case "MULT":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			disassembledInst = operation + " " + register.get(r1) + ", "
					+ register.get(r2);
			// System.out.println(disassembledInst);
			break;
		case "DIV":
			r1 = operand.substring(0, 5);
			r2 = operand.substring(5, 10);
			disassembledInst = operation + " " + register.get(r1) + ", "
					+ register.get(r2);
			// System.out.println(disassembledInst);
			break;
		default:
			disassembledInst = "Some error occured in Category 4";
			break;

		}
		return disassembledInst;
	}

	private String categoryFive(String binaryInst, String operation) {
		String r1; // Destination = R1
		String operand = binaryInst.substring(6);
		String disassembledInst = null;
		switch (operation) {
		case "MFHI":
			r1 = operand.substring(0, 5);
			disassembledInst = operation + " " + register.get(r1);
			// System.out.println(disassembledInst);
			break;
		case "MFLO":
			r1 = operand.substring(0, 5);
			disassembledInst = operation + " " + register.get(r1);
			// System.out.println(disassembledInst);
			break;
		default:
			disassembledInst = "Some error occured in Category 5";
			break;

		}
		return disassembledInst;
	}

	//simulation function
	
	public void Simulation(int c) throws IOException {
		simStream = new FileWriter("simulation.txt");
		simOutput = new BufferedWriter(simStream);
		int counter = 256;
		int cycle = 0;
		boolean breakOccured,isStalled;
		breakOccured = false;
		isStalled = false;
		waiting = "";
		execute = "";
		
		while (!breakOccured) {
			//Buffer1
			TempBuffer1.size = Buffer1.size;
			TempBuffer1.NumberOfElements = Buffer1.NumberOfElements;
			for(int i = 0; i < Buffer1.NumberOfElements; i++){
				TempBuffer1.elements[i] = Buffer1.elements[i];
			}
			//Buffer2
			TempBuffer2.size = Buffer2.size;
			TempBuffer2.NumberOfElements = Buffer2.NumberOfElements;
			for(int i = 0; i < Buffer2.NumberOfElements; i++){
				TempBuffer2.elements[i] = Buffer2.elements[i];
				TempBuffer2.op1[i] = Buffer2.op1[i];
				TempBuffer2.op2[i] = Buffer2.op2[i];
			}
			//Buffer3
			TempBuffer3.size = Buffer3.size;
			TempBuffer3.NumberOfElements = Buffer3.NumberOfElements;
			for(int i = 0; i < Buffer3.NumberOfElements; i++){
				TempBuffer3.elements[i] = Buffer3.elements[i];
				TempBuffer3.op1[i] = Buffer3.op1[i];
				TempBuffer3.op2[i] = Buffer3.op2[i];
			}
			//Buffer4
			TempBuffer4.size = Buffer4.size;
			TempBuffer4.NumberOfElements = Buffer4.NumberOfElements;
			for(int i = 0; i < Buffer4.NumberOfElements; i++){
				TempBuffer4.elements[i] = Buffer4.elements[i];
				TempBuffer4.op1[i] = Buffer4.op1[i];
				TempBuffer4.op2[i] = Buffer4.op2[i];
			}
			//Buffer5
			TempBuffer5.size = Buffer5.size;
			TempBuffer5.NumberOfElements = Buffer5.NumberOfElements;
			for(int i = 0; i < Buffer5.NumberOfElements; i++){
				TempBuffer5.elements[i] = Buffer5.elements[i];
				TempBuffer5.op1[i] = Buffer5.op1[i];
				TempBuffer5.op2[i] = Buffer5.op2[i];
			}
			//Buffer6
			TempBuffer6.location = Buffer6.location;
			TempBuffer6.result = Buffer6.result;
			TempBuffer6.result1 = Buffer6.result1;
			
			//Buffer7
			TempBuffer7.location = Buffer7.location;
			TempBuffer7.result = Buffer7.result;
			TempBuffer7.result1 = Buffer7.result1;
			
			//Buffer8
			TempBuffer8.location = Buffer8.location;
			TempBuffer8.result = Buffer8.result;
			TempBuffer8.result1 = Buffer8.result1;
			
			//Buffer9
			TempBuffer9.location = Buffer9.location;
			TempBuffer9.result = Buffer9.result;
			TempBuffer9.result1 = Buffer9.result1;
			
			//Buffer10
			TempBuffer10.location = Buffer10.location;
			TempBuffer10.result = Buffer10.result;
			TempBuffer10.result1 = Buffer10.result1;
			
			//Buffer11
			TempBuffer11.location = Buffer11.location;
			TempBuffer11.result = Buffer11.result;
			TempBuffer11.result1 = Buffer11.result1;
			
			//Buffer12
			TempBuffer12 = Buffer12;
			
			
			cycle += 1;
			//TOCHANGE
			System.out.print("CYCLE:");
			System.out.println(cycle);
			
			// System.out.println("Counter :" + counter);
			// System.out.println("\n Cycle : "+cycle+"\n");
			simOutput.write("--------------------" + "\r\n");
			simOutput.write("Cycle " + cycle + ":");
			// here all the functions will start
			
			WB();
			//System.out.println("WB fifinsh\n\n");
			//TODO
			if(!"".equals(TempBuffer6.location)){
				MEM();
				
			}
			//MEM();
			//TODO
			if(TempBuffer3.NumberOfElements != 0){
				Division();
				//System.out.println("getting heres DIVIDE\n\n");
			}
			//TODO
			if(TempBuffer4.NumberOfElements != 0){
				multiplication1();
				//System.out.println("getting heres MULT 1\n\n");
			}
			//TODO
			if(TempBuffer5.NumberOfElements != 0){
				ALU1();
				//System.out.println("getting heres ALU 1\n\n");
			}
			//TODO
			if(TempBuffer2.NumberOfElements != 0){
				ALU2();
				//System.out.println("getting heres ALU2\n\n");
			}
			//TODO
			if(!"".equals(TempBuffer8.location)){
				multiplication2();
				//System.out.println("getting heres MULT 2\n\n");
			}
			//TODO
			if(!"".equals(TempBuffer11.location)){
				multiplication3();
				//System.out.println("getting heres MULT 3\n\n");
			}
			
			if(!"".equals(waiting)){
				isStalled = true;
				//System.out.println("is stalled waiting = null heres\n\n");
			}
			Issue();
			//System.out.println("ISSUE :/ getting heres\n\n");
	
			counter = InstFetch(isStalled, counter);
			//System.out.println(counter);
			for(int i = 0;i<TempBuf1.NumberOfElements;i++){
				Buffer1.Enqueue(TempBuf1.Dequeue());
			}
			//System.out.println("buffer 1 no of ele"+ Buffer1.NumberOfElements);
			printer(cycle, registerValues, dataBank);
			if(counter == 255){
				breakOccured = true;
			}
		}
		simOutput.close();
	}
	
	// IF Stage
	
	//Fetch Function


private int InstFetch(boolean stall, int counter){
	int NoOfInst=4, returnVal;
	String currentInst = "", Inst, operation, remOperands, rs, rt,im, r1, r2, r3;
	boolean opCompare, boolCompare;
	String[] registerNames;
	
	opCompare = false;
	boolCompare = false;
	returnVal = counter;

	if(stall==true){
		//System.out.println("Stage is stalled bcoz of Branch or Break");
		//check for registers whether ready or not
		//chk the inst
		currentInst = waiting;
		operation = currentInst.split(" ")[0];
		remOperands = currentInst.substring(operation.length() + 1);
		//System.out.println("getting heres"+ currentInst +"\n\n");
		
		switch(operation){
			case "BEQ":
				registerNames = remOperands.split(", ");
				rs = registerNames[0];
				rt = registerNames[1];
				im = registerNames[2].substring(1);
				//System.out.println("getting heres\n\n");
				if(registerStatus.get(rs) == true && registerStatus.get(rt) == true){
					for(int i = 0; i < Buffer1.NumberOfElements; i++){
						r1 = Buffer1.elements[i].split(" ")[0];
						r2 = Buffer1.elements[i].substring(r1.length() + 1);
						r3 = r2.split(", ")[0];
						//System.out.println("getting heres :/ \n\n");
						if(r3.equals(rs) || r3.equals(rt)){
							//System.out.println("getting heres :)\n\n");
							opCompare = true;
							break;
						}
					}
					
				}
				else{
					opCompare = true;
					//System.out.println("getting heres :P\n\n");
				}
				if(opCompare == false){
					//System.out.println("getting heres =) \n\n");
					System.out.println("Registers are ready");
					stall = false;
					execute = currentInst;
					waiting = "";
					//comparision of registerValues
					if (registerValues.get(rs) == registerValues.get(rt)) {
						returnVal = returnVal + Integer.parseInt(im);
					}
				}
				else{
					stall = true;
				}
				break;
				
			case "BNE":
				registerNames = remOperands.split(", ");
				rs = registerNames[0];
				rt = registerNames[1];
				im = registerNames[2].substring(1);
				if(registerStatus.get(rs) == true && registerStatus.get(rt) == true){
					for(int i = 0; i < Buffer1.NumberOfElements; i++){
						r1 = Buffer1.elements[i].split(" ")[0];
						r2 = Buffer1.elements[i].substring(r1.length() + 1);
						r3 = r2.split(", ")[0];
						if(r3 == rs || r3 == rt){
							opCompare = true;
							break;
						}
					}
				}
				if(opCompare == false){
					System.out.println("Registers are ready");
					stall = false;
					execute = currentInst;
					waiting = "";
					//comparision of registerValues
					if (registerValues.get(rs) != registerValues.get(rt)) {
						returnVal = returnVal + Integer.parseInt(im);
					}
				}
				break;
				
			case "BGTZ":
				registerNames = remOperands.split(", ");
				rs = registerNames[0];
				im = registerNames[1].substring(1);
				if(registerStatus.get(rs) == true){
					for(int i = 0; i < Buffer1.NumberOfElements; i++){
						r1 = Buffer1.elements[i].split(" ")[0];
						r2 = Buffer1.elements[i].substring(r1.length() + 1);
						r3 = r2.split(", ")[0];
						if(r3.equals(rs)){
							opCompare = true;
							break;
						}
					}
				}
				if(opCompare == false){
					System.out.println("Registers are ready");
					stall = false;
					execute = currentInst;
					waiting = "";
					//comparision of registerValues
					if (registerValues.get(rs) > 0) {
						returnVal = returnVal + Integer.parseInt(im);
					}
				}
				else{
					stall = true;
				}
				break;
				
				
			case "BREAK":
				//check all the registerStatus to true.
				for(int i = 0; i < 32; i++){
					if(registerStatus.get(i) == false){
						boolCompare = true;
						break;
					}
				}
				if(boolCompare == false){
					//check buffer1 is empty.
					if(Buffer1.NumberOfElements == 0){
						System.out.println("Execute BREAK");
						execute = "BREAK";
						waiting = "";
						returnVal = 255;
					}
				}
		}
	}
	else{
		//check emptiness of buffer1
		if(Buffer1.NumberOfElements > 4){
			System.out.println("Buffer doesn't have enough space");
		}
		else{
			for(int z = counter; z < counter+16; z=z+4){ 
				Inst = InstructionSet.get(z);
				//System.out.println(Inst);
				//System.out.println("\n\nblah\n\n");
				//check instructions
				//System.out.println(Inst);
				operation = Inst.split(" ")[0];
				remOperands = Inst.substring(operation.length() + 1);
				
				switch(operation){
					case "J":
						im = remOperands.substring(1);
						returnVal = Integer.parseInt(im);
						break;
						
					case "BEQ":
						registerNames = remOperands.split(", ");
						rs = registerNames[0];
						rt = registerNames[1];
						im = registerNames[2].substring(1);
						opCompare = false;
						//checking if reg are ready for used by branch
						if(registerStatus.get(rs) == true && registerStatus.get(rt) == true){
							for(int i = 0; i < Buffer1.NumberOfElements; i++){
								r1 = Buffer1.elements[i].split(" ")[0];
								r2 = Buffer1.elements[i].substring(r1.length() + 1);
								r3 = r2.split(", ")[0];
								if(r3.equals(rs) || r3.equals(rt)){
									opCompare = true;
									System.out.println("Registers are not ready");
									break;
								}
								else{
									opCompare = false;
								}
							}
							for(int i = 0; i < TempBuf1.NumberOfElements; i++){
								r1 = TempBuf1.elements[i].split(" ")[0];
								r2 = TempBuf1.elements[i].substring(r1.length() + 1);
								r3 = r2.split(", ")[0];
								//System.out.println("Inside the for loop");
								//System.out.println("rs = " + rs + ", rt = " + rt + " r3 = " + r3);
								if(r3.equals(rs) || r3.equals(rt)){
									//System.out.println("Inside the if condition");
									opCompare = true;
									System.out.println("Registers are not ready");
									break;
								}
								else{
									//System.out.println("In the else part");
									opCompare = false;
								}
							}
						}
						else{
							opCompare = true;
						}
						
						//compare the operands of tempBuffer1 to branch inst
						
						if(opCompare == false){
							System.out.println("Registers are ready");
							stall = false;
							execute = currentInst;
							waiting = "";
							//comparision of registerValues
							if (registerValues.get(rs) == registerValues.get(rt)) {
								returnVal = returnVal + 4 + Integer.parseInt(im);
							}
						}
						else{
							stall = true;
							waiting = Inst;
							returnVal = returnVal + 4;
							break;
						}
						break;
					
					case "BNE":
						registerNames = remOperands.split(", ");
						rs = registerNames[0];
						rt = registerNames[1];
						im = registerNames[2].substring(1);
						//checking if reg are ready for used by branch
						if(registerStatus.get(rs) == true && registerStatus.get(rt) == true){
							for(int i = 0; i < Buffer1.NumberOfElements; i++){
								r1 = Buffer1.elements[i].split(" ")[0];
								r2 = Buffer1.elements[i].substring(r1.length() + 1);
								r3 = r2.split(", ")[0];
								if(r3.equals(rs) || r3.equals(rt)){
									opCompare = true;
									System.out.println("Registers are not ready");
									break;
								}
								else{
									opCompare = false;
								}
							}
							for(int i = 0; i < TempBuf1.NumberOfElements; i++){
								r1 = TempBuf1.elements[i].split(" ")[0];
								r2 = TempBuf1.elements[i].substring(r1.length() + 1);
								r3 = r2.split(", ")[0];
								if(r3.equals(rs) || r3.equals(rt)){
									opCompare = true;
									System.out.println("Registers are not ready");
									break;
								}
								else{
									opCompare = false;
								}
							}
						}
						else{
							opCompare = true;
						}
						
						if(opCompare == false){
							System.out.println("Registers are ready");
							stall = false;
							execute = currentInst;
							waiting = "";
							//comparision of registerValues
							if (registerValues.get(rs) != registerValues.get(rt)) {
								returnVal = returnVal + 4 + Integer.parseInt(im);
							}
						}
						else{
							stall = true;
							waiting = Inst;
							returnVal = returnVal + 4;
							break;
						}
						break;
							
					case "BGTZ":
						registerNames = remOperands.split(", ");
						rs = registerNames[0];
						im = registerNames[1].substring(1);
						//checking if reg are ready for used by branch
						if(registerStatus.get(rs) == true){
							for(int i = 0; i < Buffer1.NumberOfElements; i++){
								r1 = Buffer1.elements[i].split(" ")[0];
								r2 = Buffer1.elements[i].substring(r1.length() + 1);
								r3 = r2.split(", ")[0];
								if(r3.equals(rs)){
									opCompare = true;
									System.out.println("Registers are not ready");
									break;
								}
								else{
									opCompare = false;
								}
							}
							for(int i = 0; i < TempBuf1.NumberOfElements; i++){
								r1 = TempBuf1.elements[i].split(" ")[0];
								r2 = TempBuf1.elements[i].substring(r1.length() + 1);
								r3 = r2.split(", ")[0];
								if(r3.equals(rs)){
									opCompare = true;
									System.out.println("Registers are not ready");
									break;
								}
								else{
									opCompare = false;
								}
							}
						}
						else{
							opCompare = true;
						}
						
						if(opCompare == false){
							System.out.println("Registers are ready");
							stall = false;
							execute = currentInst;
							waiting = "";
							//comparision of registerValues
							if (registerValues.get(rs) > 0) {
								returnVal = returnVal + 4 + Integer.parseInt(im);
							}
						}
						else{
							stall = true;
							waiting = Inst;
							returnVal = returnVal + 4;
							break;
						}
						break;
					
					case "BREAK":
						//check all the registerStatus to true.
						for(int i = 0; i < 32; i++){
							if(registerStatus.get(i) == false){
								boolCompare = true;
								break;
							}
						}
						
						if(boolCompare == false){
							//check TempBuffer1 is empty.
							if(Buffer1.NumberOfElements == 0 && TempBuf1.NumberOfElements == 0){
								System.out.println("Execute BREAK");
								execute = "BREAK";
								waiting = "";
								returnVal = 255;
							}
							else{
								stall = true;
								waiting = Inst;
								returnVal = returnVal + 4;
							}
						}
						else{
							stall = true;
							waiting = Inst;
							returnVal = returnVal + 4;
						}
						break;
						
					default:
						//Dealing with default when there is no Branch, Jump, Break.
						//System.out.println("1");
						TempBuf1.Enqueue(Inst);
						returnVal = returnVal + 4;
						
				}
			}
			
		}
		
	}
	return (returnVal);
}

//ISSUE STAGE

private void Issue(){
	String currentInst = "", operation = "", remOperands = "";
	String previousInst = "", operation1 = "", remOperands1 = "", rd = "", rs = "", rt = "", rs1 = "", rd1 = "", rt1 = "";
	String offset, base ="", offset1, base1 = "";
	boolean WAWhazard = false, WARhazard = false, RAWhazard = false;
	String[] secondInst;
	String[] registerNames, registerNames1, secondInst1;
	//System.out.println("entering issue getting heres\n\n");
	//System.out.println(Buffer1.NumberOfElements);
	//TODO
	System.out.println(TempBuffer1.NumberOfElements);
	for(int i = 0; i < TempBuffer1.NumberOfElements; i++){
		//TODO
		currentInst = TempBuffer1.elements[i];
		System.out.println(currentInst);
		//System.out.println("checking");
		
		//System.out.println("entering issue for loop getting heres\n\n" + currentInst + "blah");
		operation = currentInst.split(" ")[0];
		remOperands = currentInst.substring(operation.length() + 1);
		
		switch(operation){
			case "SW":
				registerNames = remOperands.split(", ");
				secondInst = registerNames[1].split("\\(");
				rt = registerNames[0];
				offset = secondInst[0];
				base = secondInst[1].substring(0, secondInst[1].length() - 1);
				//TODO
				if(TempBuffer2.NumberOfElements == 2){
					// Buffer2 is full
					System.out.println("Structural Hazard");
				}
				else{
					//check the operands are ready or not
					if(registerStatus.get(rt) == true && registerStatus.get(base) == true){
						//check for WAW WAR RAW
						for(int y = 0; y < i; y++){
							//TODO
							previousInst = TempBuffer1.elements[y];
							//check for empty string, if not empty do all this
							if(!"".equals(previousInst)){
								operation1 = currentInst.split(" ")[0];
								remOperands1 = previousInst.substring(operation1.length() + 1);
							}
							else{
								operation1 = "";
							}
							
							switch(operation1){
								case "SW":
									WARhazard = true;
									break;
									
								case "LW":
									registerNames1 = remOperands1.split(", ");
									secondInst1 = registerNames1[1].split("\\(");
									rt1 = registerNames1[0];
									offset1 = secondInst1[0];
									base1 = secondInst1[1].substring(0, secondInst1[1].length() - 1);
									//check for RAW
									if(rt.equals(rt1) || base.equals(rt1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									break;
									
								case "ADD":
								case "SUB":
								case "AND":
								case "OR":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									rt1 = registerNames1[2];
									if(rt.equals(rd1) || base.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									break;
									
								case "SRA":
								case "SRL":
								case "ADDI":
								case "ANDI":
								case "ORI":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									if(rt.equals(rd1) || base.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									break;
									
								case "MULT":
								case "DIV":
									registerNames1 = remOperands1.split(", ");
									rs1 = registerNames1[0];
									rt1 = registerNames1[1];
									break;
									
								case "MFHI":	
								case "MFLO":
									rd1 = remOperands1;
									//RAW Hazard
									if(rt.equals(rt1) || base.equals(rt1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									break;
									
								default:
									System.out.println("Instruction is an empty string");
							}
							// if there is any hazard.
							if(WAWhazard == true || RAWhazard == true || WARhazard == true){
								break;
							}
						}
						if(WAWhazard == false && RAWhazard == false && WARhazard == false){
							//issue intructions
							//TODO
							TempBuffer2.Enqueue(currentInst, registerValues.get(rt), registerValues.get(base));
							//TODO
							Buffer1.elements[i] = "";
						}
					}
				}
				break;
				
			case "LW":
				registerNames = remOperands.split(", ");
				secondInst = registerNames[1].split("\\(");
				rt = registerNames[0];
				offset = secondInst[0];
				base = secondInst[1].substring(0, secondInst[1].length() - 1);
				//TODO
				if(TempBuffer2.NumberOfElements == 2){
					// Buffer2 is full
					System.out.println("Structural Hazard");
				}
				else{
					//check the operands are ready or not
					if(registerStatus.get(rt) == true && registerStatus.get(base) == true){
						//check for WAW WAR RAW
						for(int y = 0; y < i; y++){
							//TODO
							previousInst = TempBuffer1.elements[y];
							//check for empty string, if not empty do all this
							if(previousInst != ""){
								operation1 = currentInst.split(" ")[0];
								remOperands1 = previousInst.substring(operation1.length() + 1);
							}
							else{
								operation1 = "";
							}
							
							switch(operation1){
								case "SW":
									WARhazard = true;
									break;
									
								case "LW":
									registerNames1 = remOperands1.split(", ");
									secondInst1 = registerNames1[1].split("\\(");
									rt1 = registerNames1[0];
									base1 = secondInst1[1].substring(0, secondInst1[1].length() - 1);
									//check for RAW
									if(base.equals(rt1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									// check for WAW
									if(rt.equals(rt1)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									//check for WAR
									if(rt.equals(base1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "ADD":
								case "SUB":
								case "AND":
								case "OR":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									rt1 = registerNames1[2];
									//RAW
									if(base.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									//WAW
									if(rt.equals(rd1)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									// WAR
									if(rt.equals(rs1) || rt.equals(rt1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "SRA":
								case "SRL":
								case "ADDI":
								case "ANDI":
								case "ORI":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									// WAR
									if(rt.equals(rs1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									//RAW
									if(base.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									//WAW
									if(rt.equals(rd1)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									break;
									
								case "MULT":
								case "DIV":
									registerNames1 = remOperands1.split(", ");
									rs1 = registerNames1[0];
									rt1 = registerNames1[1];
									//WAR
									if(rt.equals(rs1) || rt.equals(rt1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "MFHI":	
								case "MFLO":
									rd1 = remOperands1;
									//RAW Hazard
									if(base.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									//WAW
									if(rt.equals(rd1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								default:
									System.out.println("Instruction is an empty string");
							}
							// if there is any hazard.
							if(WAWhazard == true || RAWhazard == true || WARhazard == true){
								break;
							}
						}
						if(WAWhazard == false && WARhazard == false && RAWhazard == false){
							//issue load inst to buffer 2.
							//TODO
							TempBuffer2.Enqueue(currentInst, registerValues.get(rt), registerValues.get(base));
							registerStatus.put(rt, false);
							//TODO
							Buffer1.elements[i] = "";
						}
					}
				}
				break;
				
			case "ADD":
			case "SUB":
			case "AND":
			case "OR":
				registerNames = remOperands.split(", ");
				rd = registerNames[0];
				rs = registerNames[1];
				rt = registerNames[2];
				//TODO
				if(TempBuffer5.NumberOfElements == 2){
					// Buffer5 is full
					System.out.println("Structural Hazard");
				}
				else{
					//check the operands are ready or not
					if(registerStatus.get(rd) == true && registerStatus.get(rs) == true && registerStatus.get(rt) == true){
						// compare instructions with all above instructions
						// check WAW, WAR, RAW
						for(int y = 0; y < i; y++){
							//TODO
							previousInst = TempBuffer1.elements[y];
							//check for empty string, if not empty do all this
							if(previousInst != ""){
								operation1 = currentInst.split(" ")[0];
								remOperands1 = previousInst.substring(operation1.length() + 1);
							}
							else{
								operation1 = "";
							}
							
							switch(operation1){
								case "SW":
									registerNames1 = remOperands1.split(", ");
									secondInst1 = registerNames1[1].split("\\(");
									rt1 = registerNames1[0];
									offset1 = secondInst1[0];
									base1 = secondInst1[1].substring(0, secondInst1[1].length() - 1);
									// for WAR
									if(rd.equals(rt1) || rd.equals(base1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "LW":
									registerNames1 = remOperands1.split(", ");
									secondInst1 = registerNames1[1].split("\\(");
									rt1 = registerNames1[0];
									offset1 = secondInst1[0];
									base1 = secondInst1[1].substring(0, secondInst1[1].length() - 1);
									//check for WAW
									if(rt1.equals(rd)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									//check for RAW
									if(rs.equals(rt1) || rt.equals(rt1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									// for WAR
									if(rd.equals(base1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "ADD":
								case "SUB":
								case "AND":
								case "OR":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									rt1 = registerNames1[2];
									// for WAW
									if(rd1.equals(rd)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									// for RAW
									if(rs.equals(rd1) || rt.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									// for WAR
									if(rd.equals(rs1) || rd.equals(rt1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "SRA":
								case "SRL":
								case "ADDI":
								case "ANDI":
								case "ORI":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									//check for WAW
									if(rd1.equals(rd)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									//check for RAW
									if(rs.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									// for WAR
									if(rd.equals(rs1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									
									break;
									
								case "MULT":
								case "DIV":
									registerNames1 = remOperands1.split(", ");
									rs1 = registerNames1[0];
									rt1 = registerNames1[1];
									// for WAR
									if(rd.equals(rs1) || rd.equals(rt1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "MFHI":	
								case "MFLO":
									rd1 = remOperands1;
									//check for WAW
									if(rd1.equals(rd)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									//check for RAW
									if(rs.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									break;
									
								default:
									System.out.println("Instruction is an empty string");
							}
							// if there is any hazard.
							if(WAWhazard == true || RAWhazard == true || WARhazard == true){
								break;
							}
						}
						if(WAWhazard == false && RAWhazard == false && WARhazard == false){
							//issue ADD SUB .. .. .. inst
							//TODO
							TempBuffer5.Enqueue(currentInst, registerValues.get(rs), registerValues.get(rt));
							registerStatus.put(rd, false);
							//TODO
							Buffer1.elements[i] = "";
						}
					}
				}
				break;
				
			case "SRA":	
			case "SRL":	
			case "ADDI":	
			case "ANDI":	
			case "ORI":
				registerNames = remOperands.split(", ");
				rd = registerNames[0];
				rs = registerNames[1];
				//im = registerNames[2].substring(1);
				//TODO
				if(TempBuffer5.NumberOfElements == 2){
					// Buffer5 is full
					System.out.println("Structural Hazard");
				}
				else{
					//check the operands are ready or not
					if(registerStatus.get(rd) == true && registerStatus.get(rs) == true && registerStatus.get(rt) == true){
						// compare instructions with all above instructions
						// check WAW, WAR, RAW
						for(int y = 0; y < i; y++){
							//TODO
							previousInst = TempBuffer1.elements[y];
							//check for empty string, if not empty do all this
							if(!"".equals(previousInst)){
								operation1 = currentInst.split(" ")[0];
								remOperands1 = previousInst.substring(operation1.length() + 1);
							}
							else{
								operation1 = "";
							}
							
							switch(operation1){
								case "SW":
									registerNames1 = remOperands1.split(", ");
									secondInst1 = registerNames1[1].split("\\(");
									rt1 = registerNames1[0];
									offset1 = secondInst1[0];
									base1 = secondInst1[1].substring(0, secondInst1[1].length() - 1);
									// for WAR
									if(rd.equals(rt1) || rd.equals(base1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "LW":
									registerNames1 = remOperands1.split(", ");
									secondInst1 = registerNames1[1].split("\\(");
									rt1 = registerNames1[0];
									offset1 = secondInst1[0];
									base1 = secondInst1[1].substring(0, secondInst1[1].length() - 1);
									//check for WAW
									if(rt1.equals(rd)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									//check for RAW
									if(rs.equals(rt1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									// for WAR
									if(rd.equals(base1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "ADD":
								case "SUB":
								case "AND":
								case "OR":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									rt1 = registerNames1[2];
									// for WAW
									if(rd1.equals(rd)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									// for RAW
									if(rs.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									// for WAR
									if(rd.equals(rs1) || rd.equals(rt1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "SRA":
								case "SRL":
								case "ADDI":
								case "ANDI":
								case "ORI":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									//check for WAW
									if(rd1.equals(rd)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									//check for RAW
									if(rs.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									// for WAR
									if(rd.equals(rs1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									
									break;
									
								case "MULT":
								case "DIV":
									registerNames1 = remOperands1.split(", ");
									rs1 = registerNames1[0];
									rt1 = registerNames1[1];
									// for WAR
									if(rd.equals(rs1) || rd.equals(rt1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "MFHI":	
								case "MFLO":
									rd1 = remOperands1;
									//check for WAW
									if(rd1.equals(rd)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									//check for RAW
									if(rs.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									break;
									
								default:
									System.out.println("Instruction is an empty string");
							}
							// if there is any hazard.
							if(WAWhazard == true || RAWhazard == true || WARhazard == true){
								break;
							}
						}
						if(WAWhazard == false && RAWhazard == false && WARhazard == false){
							//issue SRL SRA .. .. .. inst
							//TODO
							TempBuffer5.Enqueue(currentInst, registerValues.get(rs), registerValues.get(rs));
							registerStatus.put(rd, false);
							//TODO
							Buffer1.elements[i] = "";
						}
					}
				}
				break;
				
			case "MULT":
				registerNames = remOperands.split(", ");
				rs = registerNames[0];
				rt = registerNames[1];
				//TODO
				if(TempBuffer4.NumberOfElements == 2){
					// Buffer4 is full
					System.out.println("Structural Hazard");
				}
				else{
					//check the operands are ready or not
					if(registerStatus.get(rt) == true && registerStatus.get(rt) == true && registerStatus.get("LO")){
						//check for WAW WAR RAW
						for(int y = 0; y < i; y++){
							//TODO
							previousInst = TempBuffer1.elements[y];
							//check for empty string, if not empty do all this
							if(!"".equals(previousInst)){
								operation1 = currentInst.split(" ")[0];
								remOperands1 = previousInst.substring(operation1.length() + 1);
							}
							else{
								operation1 = "";
							}
							
							switch(operation1){
								case "SW":
									break;
									
								case "LW":
									registerNames1 = remOperands1.split(", ");
									secondInst1 = registerNames1[1].split("\\(");
									rt1 = registerNames1[0];
									base1 = secondInst1[1].substring(0, secondInst1[1].length() - 1);
									//check for RAW
									if(rs.equals(rt1) || rt.equals(rt1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									break;
									
								case "ADD":
								case "SUB":
								case "AND":
								case "OR":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									rt1 = registerNames1[2];
									//RAW
									if(rd1.equals(rs) || rd1.equals(rt)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									break;
									
								case "SRA":
								case "SRL":
								case "ADDI":
								case "ANDI":
								case "ORI":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									//RAW
									if(rt.equals(rd1) || rs.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									break;
									
								case "MULT":
								case "DIV":
									registerNames1 = remOperands1.split(", ");
									rs1 = registerNames1[0];
									rt1 = registerNames1[1];
									//WAW
									WAWhazard = true;
									break;
									
								case "MFHI":	
								case "MFLO":
									rd1 = remOperands1;
									//WAR
									WARhazard = true;
									
									//RAW Hazard
									if(rs.equals(rd1) || rt.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									break;
									
								default:
									System.out.println("Instruction is an empty string");
							}
							// if there is any hazard.
							if(WAWhazard == true || RAWhazard == true || WARhazard == true){
								break;
							}
						}
						if(WAWhazard == false && WARhazard == false && RAWhazard == false){
							//issue load inst to buffer 2.
							//TODO
							TempBuffer4.Enqueue(currentInst, registerValues.get(rs), registerValues.get(rt));
							registerStatus.put("LO", false);
							//TODO
							Buffer1.elements[i] = "";
						}
					}
				}
				break;
				
			case "DIV":
				registerNames = remOperands.split(", ");
				rs = registerNames[0];
				rt = registerNames[1];
				//TODO
				if(TempBuffer3.NumberOfElements == 2){
					// Buffer3 is full
					System.out.println("Structural Hazard");
				}
				else{
					//check the operands are ready or not
					if(registerStatus.get(rt) == true && registerStatus.get(rt) == true && registerStatus.get("HI") == true && registerStatus.get("LO")){
						//check for WAW WAR RAW
						for(int y = 0; y < i; y++){
							//TODO
							previousInst = TempBuffer1.elements[y];
							//check for empty string, if not empty do all this
							if(!"".equals(previousInst)){
								operation1 = currentInst.split(" ")[0];
								remOperands1 = previousInst.substring(operation1.length() + 1);
							}
							else{
								operation1 = "";
							}
							
							switch(operation1){
								case "SW":
									break;
									
								case "LW":
									registerNames1 = remOperands1.split(", ");
									secondInst1 = registerNames1[1].split("\\(");
									rt1 = registerNames1[0];
									base1 = secondInst1[1].substring(0, secondInst1[1].length() - 1);
									//check for RAW
									if(rs.equals(rt1) || rt.equals(rt1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									break;
									
								case "ADD":
								case "SUB":
								case "AND":
								case "OR":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									rt1 = registerNames1[2];
									//RAW
									if(rs.equals(rd1) || rt.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									//WAW
									if(rt.equals(rd1)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									break;
									
								case "SRA":
								case "SRL":
								case "ADDI":
								case "ANDI":
								case "ORI":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									//RAW
									if(rs.equals(rd1) || rt.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									break;
									
								case "MULT":
								case "DIV":
									registerNames1 = remOperands1.split(", ");
									rs1 = registerNames1[0];
									rt1 = registerNames1[1];
									//WAW
									WAWhazard = true;
									break;
									
								case "MFHI":	
								case "MFLO":
									rd1 = remOperands1;
									//RAW Hazard
									if(rs.equals(rd1) || rt.equals(rd1)){
										System.out.println("RAW Hazard");
										RAWhazard = true;
									}
									//WAR
									WARhazard = true;
									break;
									
								default:
									System.out.println("Instruction is an empty string");
							}
							// if there is any hazard.
							if(WAWhazard == true || RAWhazard == true || WARhazard == true){
								break;
							}
						}
						if(WAWhazard == false && WARhazard == false && RAWhazard == false){
							//issue load inst to buffer 2.
							//TODO
							TempBuffer3.Enqueue(currentInst, registerValues.get(rs), registerValues.get(rt));
							registerStatus.put("HI", false);
							registerStatus.put("LO", false);
							//TODO
							Buffer1.elements[i] = "";
						}
					}
				}
				break;
				
			case "MFHI":
				rd = remOperands;
				//TODO
				if(TempBuffer5.NumberOfElements == 2){
					// Buffer5 is full
					System.out.println("Structural Hazard");
				}
				else{
					//check the operands are ready or not
					if(registerStatus.get(rd) == true && registerStatus.get("HI") == true){
						//check for WAW WAR RAW
						for(int y = 0; y < i; y++){
							//TODO
							previousInst = TempBuffer1.elements[y];
							//check for empty string, if not empty do all this
							if(!"".equals(previousInst)){
								operation1 = currentInst.split(" ")[0];
								remOperands1 = previousInst.substring(operation1.length() + 1);
							}
							else{
								operation1 = "";
							}
							
							switch(operation1){
								case "SW":
									registerNames1 = remOperands1.split(", ");
									secondInst1 = registerNames1[1].split("\\(");
									rt1 = registerNames1[0];
									base1 = secondInst1[1].substring(0, secondInst1[1].length() - 1);
									//check for WAR
									if(rd.equals(base1) || rd.equals(rt1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "LW":
									registerNames1 = remOperands1.split(", ");
									secondInst1 = registerNames1[1].split("\\(");
									rt1 = registerNames1[0];
									base1 = secondInst1[1].substring(0, secondInst1[1].length() - 1);
									// check for WAW
									if(rd.equals(rt1)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									//check for WAR
									if(rd.equals(base1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "ADD":
								case "SUB":
								case "AND":
								case "OR":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									rt1 = registerNames1[2];
									//WAW
									if(rd.equals(rd1)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									// WAR
									if(rd.equals(rs1) || rd.equals(rt1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "SRA":
								case "SRL":
								case "ADDI":
								case "ANDI":
								case "ORI":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									// WAR
									if(rd.equals(rs1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									//WAW
									if(rd.equals(rd1)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									break;
									
								case "MULT":
									registerNames1 = remOperands1.split(", ");
									rs1 = registerNames1[0];
									rt1 = registerNames1[1];
									//WAR
									if(rd.equals(rs1) || rd.equals(rt1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
								
								case "DIV":
									registerNames1 = remOperands1.split(", ");
									rs1 = registerNames1[0];
									rt1 = registerNames1[1];
									//RAW
									RAWhazard = true;
									//WAR
									if(rd.equals(rs1) || rd.equals(rt1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "MFHI":	
								case "MFLO":
									rd1 = remOperands1;
									//WAW
									if(rd.equals(rd1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								default:
									System.out.println("Instruction is an empty string");
							}
							// if there is any hazard.
							if(WAWhazard == true || RAWhazard == true || WARhazard == true){
								break;
							}
						}
						if(WAWhazard == false && WARhazard == false && RAWhazard == false){
							//issue load inst to buffer 2.
							//TODO
							TempBuffer5.Enqueue(currentInst, registerValues.get("HI"), registerValues.get("HI"));
							registerStatus.put(rd, false);
							//TODO
							Buffer1.elements[i] = "";
						}
					}
				}
				break;
				
			case "MFLO":
				rd = remOperands;
				//TODO
				if(TempBuffer5.NumberOfElements == 2){
					// Buffer5 is full
					System.out.println("Structural Hazard");
				}
				else{
					//check the operands are ready or not
					if(registerStatus.get(rd) == true && registerStatus.get("LO") == true){
						//check for WAW WAR RAW
						for(int y = 0; y < i; y++){
							//TODO
							previousInst = TempBuffer1.elements[y];
							//check for empty string, if not empty do all this
							if(previousInst != ""){
								operation1 = currentInst.split(" ")[0];
								remOperands1 = previousInst.substring(operation1.length() + 1);
							}
							else{
								operation1 = "";
							}
							
							switch(operation1){
								case "SW":
									registerNames1 = remOperands1.split(", ");
									secondInst1 = registerNames1[1].split("\\(");
									rt1 = registerNames1[0];
									base1 = secondInst1[1].substring(0, secondInst1[1].length() - 1);
									//check for WAR
									if(rd.equals(base1) || rd.equals(rt1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "LW":
									registerNames1 = remOperands1.split(", ");
									secondInst1 = registerNames1[1].split("\\(");
									rt1 = registerNames1[0];
									base1 = secondInst1[1].substring(0, secondInst1[1].length() - 1);
									// check for WAW
									if(rd.equals(rt1)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									//check for WAR
									if(rd.equals(base1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "ADD":
								case "SUB":
								case "AND":
								case "OR":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									rt1 = registerNames1[2];
									//WAW
									if(rd.equals(rd1)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									// WAR
									if(rd.equals(rs1) || rd.equals(rt1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "SRA":
								case "SRL":
								case "ADDI":
								case "ANDI":
								case "ORI":
									registerNames1 = remOperands1.split(", ");
									rd1 = registerNames1[0];
									rs1 = registerNames1[1];
									// WAR
									if(rd.equals(rs1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									//WAW
									if(rd.equals(rd1)){
										System.out.println("WAW Hazard");
										WAWhazard = true;
									}
									break;
									
								case "MULT":
									registerNames1 = remOperands1.split(", ");
									rs1 = registerNames1[0];
									rt1 = registerNames1[1];
									//WAR
									if(rd.equals(rs1) || rd.equals(rt1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									//RAW
									RAWhazard = true;
									break;
								
								case "DIV":
									registerNames1 = remOperands1.split(", ");
									rs1 = registerNames1[0];
									rt1 = registerNames1[1];
									//RAW
									RAWhazard = true;
									//WAR
									if(rd.equals(rs1) || rd.equals(rt1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								case "MFHI":	
								case "MFLO":
									rd1 = remOperands1;
									//WAW
									if(rd.equals(rd1)){
										System.out.println("WAR Hazard");
										WARhazard = true;
									}
									break;
									
								default:
									System.out.println("Instruction is an empty string");
							}
							// if there is any hazard.
							if(WAWhazard == true || RAWhazard == true || WARhazard == true){
								break;
							}
						}
						if(WAWhazard == false && WARhazard == false && RAWhazard == false){
							//issue load inst to buffer 2.
							//TODO
							TempBuffer5.Enqueue(currentInst, registerValues.get("LO"), registerValues.get("LO"));
							registerStatus.put(rd, false);
							//TODO
							Buffer1.elements[i] = "";
						}
					}
				}
				break;
				
		}
	}
	//TODO
	for(int z = 0; z < TempBuffer1.NumberOfElements; z++){
		//TODO
		currentInst = TempBuffer1.Dequeue();
		if(!"".equals(currentInst)){
			//TODO ??
			Buffer1.Enqueue(currentInst);
		}
	}
}

//ALU 1 STAGE

private void ALU1(){
	String currentInst, operation, remOperands, rd, sa;
	int op1, op2, result;
	String[] registerNames;
	
	
	//TODO
	currentInst = TempBuffer5.Dequeue();
	//TODO
	op1 = TempBuffer5.getop1();
	//TODO
	op2 = TempBuffer5.getop2();
	operation = currentInst.split(" ")[0];
	remOperands = currentInst.substring(operation.length() + 1);
	
	switch(operation){
		case "ADD":
			registerNames = remOperands.split(", ");
			rd = registerNames[0];
			result = op1 + op2;
			Buffer9.result = result;
			Buffer9.location = rd;
			break;
			
		case "SUB":
			registerNames = remOperands.split(", ");
			rd = registerNames[0];
			result = op1 - op2;
			Buffer9.result = result;
			Buffer9.location = rd;
			break;
			
		case "AND":
			registerNames = remOperands.split(", ");
			rd = registerNames[0];
			result = op1 & op2;
			Buffer9.result = result;
			Buffer9.location = rd;
			break;
			
		case "OR":
			registerNames = remOperands.split(", ");
			rd = registerNames[0];
			result = op1 | op2;
			Buffer9.result = result;
			Buffer9.location = rd;
			break;
			
		case "SRL":
			registerNames = remOperands.split(", ");
			rd = registerNames[0];
			sa = registerNames[2].substring(1);
			result = op1 >>> Integer.parseInt(sa);
			Buffer9.result = result;
			Buffer9.location = rd;
			break;
			
		case "SRA":
			registerNames = remOperands.split(", ");
			rd = registerNames[0];
			sa = registerNames[2].substring(1);
			result = op1 >> Integer.parseInt(sa);
			Buffer9.result = result;
			Buffer9.location = rd;
			break;
			
		case "ANDI":
			registerNames = remOperands.split(", ");
			rd = registerNames[0];
			sa = registerNames[2].substring(1);
			result = op1 & Integer.parseInt(sa);
			Buffer9.result = result;
			Buffer9.location = rd;
			break;
			
		case "ADDI":
			registerNames = remOperands.split(", ");
			rd = registerNames[0];
			sa = registerNames[2].substring(1);
			result = op1 + Integer.parseInt(sa);
			Buffer9.result = result;
			Buffer9.location = rd;
			break;
			
		case "ORI":
			registerNames = remOperands.split(", ");
			rd = registerNames[0];
			sa = registerNames[2].substring(1);
			result = op1 | Integer.parseInt(sa);
			Buffer9.result = result;
			Buffer9.location = rd;
			break;
			
		case "MFHI":
		case "MFLO":
			registerNames = remOperands.split(", ");
			rd = registerNames[0];
			Buffer9.result = op1;
			Buffer9.location = rd;
			break;
		
	}
	
	
}

//ALU 2 STAGE

private void ALU2(){
	String currentInst, operation, remOperands, rd, sa;
	int op1, op2, result;
	String[] registerNames, secondInst;
	//TODO
	currentInst = TempBuffer2.Dequeue();
	//TODO
	op1 = TempBuffer2.getop1();
	//TODO
	op2 = TempBuffer2.getop2();
	operation = currentInst.split(" ")[0];
	remOperands = currentInst.substring(operation.length() + 1);
	
	switch(operation){
		case "LW":
			registerNames = remOperands.split(", ");
			secondInst = registerNames[1].split("\\(");
			rd = registerNames[0];
			sa = secondInst[0];
			result = op2 + Integer.parseInt(sa);
			Buffer6.location = currentInst;
			Buffer6.result = result;
			break;
				
		case "SW":
			registerNames = remOperands.split(", ");
			secondInst = registerNames[1].split("\\(");
			rd = registerNames[0];
			sa = secondInst[0];
			result = op2 + Integer.parseInt(sa);
			Buffer6.location = currentInst;
			Buffer6.result = result;
			Buffer6.result1 = Integer.parseInt(rd);
			break;
	}
}

//MULTIPLICATION STAGE

// mult stage 1
private void multiplication1(){
	String currentInst;
	int op1, op2;
	//TODO
	currentInst = TempBuffer4.Dequeue();
	//TODO
	op1 = TempBuffer4.getop1();
	//TODO
	op2 = TempBuffer4.getop2();
	Buffer8.location = currentInst;
	Buffer8.result = op1;
	Buffer8.result1 = op2;
}

// mult stage 2
private void multiplication2(){
	//TODO
	Buffer11.location = TempBuffer8.location;
	//TODO
	Buffer11.result = TempBuffer8.result;
	//TODO
	Buffer11.result1 = TempBuffer8.result1;
	Buffer8.location = "";
}

// mult stage 3
private void multiplication3(){
	//TODO
	Buffer12 = TempBuffer11.result * TempBuffer11.result1;
	Buffer11.location = "";
}

//DIVISION STAGE

private void Division(){
	String currentInst;
	int op1, op2;
	//TODO
	currentInst = TempBuffer3.Dequeue();
	//TODO
	op1 = TempBuffer3.getop1();
	//TODO
	op2 = TempBuffer3.getop2();
	Buffer7.result = op1 / op2;
	Buffer7.result1 = op1 % op2;
}

//MEM STAGE

private void MEM(){
	//check for instructions
	System.out.println("HIiiiiiiiiii");
	String currentInst, operation,rt, remOperands;
	int op1, op2, result;
	String[] registerNames, secondInst;
	System.out.println("hello");
	//TODO
	currentInst = TempBuffer6.location;
	System.out.println(currentInst);
	//TODO
	op1 = TempBuffer6.result;
	//TODO
	op2 = TempBuffer6.result1;
	operation = currentInst.split(" ")[0];
	
	
	System.out.println("check for error\n"+currentInst);
	remOperands = currentInst.substring(operation.length() + 1);
	
	System.out.println("check for error");
	switch(operation){
		case "LW":
			registerNames = remOperands.split(", ");
			secondInst = registerNames[1].split("\\(");
			rt = registerNames[0];
			result = Integer.parseInt(dataBank.get(op1));
			Buffer10.result = result;
			Buffer10.location = rt;
			break;
				
		case "SW":
			dataBank.put(op1, Integer.toString(op2));
			Buffer10.location = "SW";
			break;
	}
}

//WRITE BACK STAGE

private void WB(){
	//Buffer10
	if(!"SW".equals(Buffer10.location)){
		//wb the value in temp buffer
		//TODO
		tempRegisterValues.put(TempBuffer10.location, TempBuffer10.result);
		//TODO
		tempRegisterStatus.put(TempBuffer10.location, true);
		Buffer10.location = "SW";
	}
	// buffer 7
	//TODO
	if(!"".equals(TempBuffer7.location)){
		//TODO
		tempRegisterValues.put("LO", TempBuffer7.result);
		//TODO
		tempRegisterValues.put("HI", TempBuffer7.result1);
		Buffer7.location = "";
		//TODO
		if(TempBuffer3.NumberOfElements == 0 && TempBuffer4.NumberOfElements == 0 && TempBuffer8.location == "" && TempBuffer11.location == ""){
			tempRegisterStatus.put("HI",  true);
			tempRegisterStatus.put("LO",  true);
		}
	}
	//buffer 12
	//TODO
	if(TempBuffer12 != -1){
		//TODO
		tempRegisterValues.put("LO", TempBuffer12);
		Buffer12 = -1;
		//TODO
		if(TempBuffer3.NumberOfElements == 0 && TempBuffer4.NumberOfElements == 0 && TempBuffer8.location == "" && TempBuffer11.location == ""){
			tempRegisterStatus.put("LO",  true);
		}
	}
	//Buffer 9
	//TODO
	if(!"".equals(TempBuffer9.location)){
		//TODO
		tempRegisterValues.put(TempBuffer9.location, TempBuffer9.result);
		//TODO
		tempRegisterStatus.put(TempBuffer9.location, true);
		Buffer9.location = "";
		
	}
}


	private void printer(int cycle,
			HashMap<String, Integer> rVal, TreeMap<Integer, String> dataVal)
			throws IOException {
		int counter = (int) dataVal.firstKey();
		
		simOutput.write("\r\n\r\n");
		simOutput.write("IF:\n");
		simOutput.write("\tWaiting: ["+ waiting +"]\n");
		simOutput.write("\tExecuted: ["+ execute +"]\n");
		simOutput.write("Buf1:\n");
		for(int i = 0; i < 8; i++){
		simOutput.write("\tEntry "+ String.valueOf(i) +": [" + Buffer1.elements[i] +"]\n");
		}
		simOutput.write("Buf2:\n");
		for(int i = 0; i < 2; i++){
		simOutput.write("\tEntry "+ String.valueOf(i) +": [" + Buffer2.elements[i] +"]\n");
		}
		simOutput.write("Buf3:\n");
		for(int i = 0; i < 2; i++){
		simOutput.write("\tEntry "+ String.valueOf(i) +": [" + Buffer3.elements[i] +"]\n");
		}
		simOutput.write("Buf4:\n");
		for(int i = 0; i < 2; i++){
		simOutput.write("\tEntry "+ String.valueOf(i) +": [" + Buffer4.elements[i] +"]\n");
		}
		simOutput.write("Buf5:\n");
		for(int i = 0; i < 2; i++){
		simOutput.write("\tEntry "+ String.valueOf(i) +": [" + Buffer5.elements[i] +"]\n");
		}
		simOutput.write("Buf6: ");
		if(!"".equals(Buffer6.location)){
			simOutput.write("["+ Buffer6.location +"]\n");
		}
		else{
			simOutput.write("\n");
		}
		simOutput.write("Buf7: ");
		if(!"".equals(Buffer7.location)){
			simOutput.write("["+ String.valueOf(Buffer7.result) + " ,"+ String.valueOf(Buffer7.result1) +"]\n");
		}
		else{
			simOutput.write("\n");
		}
		simOutput.write("Buf8: ");
		if(!"".equals(Buffer8.location)){
			simOutput.write("["+ Buffer8.location +"]\n");
		}
		else{
			simOutput.write("\n");
		}
		simOutput.write("Buf9: ");
		if(!"".equals(Buffer9.location)){
			simOutput.write("["+ Buffer9.location + " ,"+ String.valueOf(Buffer9.result) +"]\n");
		}
		else{
			simOutput.write("\n");
		}
		simOutput.write("Buf10: ");
		if(!"SW".equals(Buffer10.location)){
			simOutput.write("["+ Buffer10.location + " ,"+ String.valueOf(Buffer10.result) +"]\n");
		}
		else{
			simOutput.write("\n");
		}
		simOutput.write("Buf11: ");
		if(!"".equals(Buffer11.location)){
			simOutput.write("["+ Buffer11.location +"]\n");
		}
		else{
			simOutput.write("\n");
		}
		simOutput.write("Buf12: ");
		if(Buffer12 != -1){
			simOutput.write("["+String.valueOf(Buffer12) +"]\n");
		}
		else{
			simOutput.write("\n");
		}
		simOutput.write("Registers" + "\r\n");
		simOutput.write("R00:\t" + rVal.get("R0") + "\t" + rVal.get("R1")
				+ "\t" + rVal.get("R2") + "\t" + rVal.get("R3") + "\t"
				+ rVal.get("R4") + "\t" + rVal.get("R5") + "\t"
				+ rVal.get("R6") + "\t" + rVal.get("R7") + "\r\n");
		simOutput.write("R08:\t" + rVal.get("R8") + "\t" + rVal.get("R9")
				+ "\t" + rVal.get("R10") + "\t" + rVal.get("R11") + "\t"
				+ rVal.get("R12") + "\t" + rVal.get("R13") + "\t"
				+ rVal.get("R14") + "\t" + rVal.get("R15") + "\r\n");
		simOutput.write("R16:\t" + rVal.get("R16") + "\t" + rVal.get("R17")
				+ "\t" + rVal.get("R18") + "\t" + rVal.get("R19") + "\t"
				+ rVal.get("R20") + "\t" + rVal.get("R21") + "\t"
				+ rVal.get("R22") + "\t" + rVal.get("R23") + "\r\n");
		simOutput.write("R24:\t" + rVal.get("R24") + "\t" + rVal.get("R25")
				+ "\t" + rVal.get("R26") + "\t" + rVal.get("R27") + "\t"
				+ rVal.get("R28") + "\t" + rVal.get("R29") + "\t"
				+ rVal.get("R30") + "\t" + rVal.get("R31") + "\r\n");
		simOutput.write("HI:\t" + rVal.get("HI") + "\n" + "LO:\t" + rVal.get("LO") + "\n");
		simOutput.write("\r\n");
		simOutput.write("Data" + "\r\n");
		for (counter = (int) dataVal.firstKey(); counter <= (int) dataVal
				.lastKey(); counter = counter + 4) {
			simOutput.write(counter + ":\t" + dataVal.get(counter));
			for (int i = 0; i < 7; i++) {
				counter += 4;
				simOutput.write("\t" + dataVal.get(counter));
			}
			simOutput.write("\r\n");
		}
		simOutput.write("\r\n");
	}

	private String twosCompliment(String binaryString) {
		char[] b = binaryString.toCharArray();
		for (int i = 0; i < b.length; i++) {
			if (b[i] == '1') {
				b[i] = '0';
			} else
				b[i] = '1';
		}
		String S1 = new String(b);
		String S = addBinary(S1, "1");
		int resultInt = Integer.parseInt(S, 2);
		String result = "-" + Integer.toString(resultInt);
		return result;
	}

	private String addBinary(String a, String b) {
		if (b.indexOf('1') == -1)
			return a.indexOf('1') == -1 ? a : a.substring(a.indexOf('1'));
		int diff = Math.abs(a.length() - b.length());
		if (a.length() > b.length()) {
			for (int i = 0; i < diff; ++i)
				b = '0' + b;
		} else {
			for (int i = 0; i < diff; ++i)
				a = '0' + a;
		}

		String sum = new String();
		String carry = "0";
		for (int i = a.length() - 1; i >= 0; --i) {
			if ((a.charAt(i) == '1' && b.charAt(i) == '1')
					|| (a.charAt(i) == '0' && b.charAt(i) == '0'))
				sum = '0' + sum;
			else
				sum = '1' + sum;
			if (a.charAt(i) == '1' && b.charAt(i) == '1')
				carry = '1' + carry;
			else
				carry = '0' + carry;
		}
		String B = addBinary(sum, carry);
		if (B.length() > 32) {
			B = B.substring(1);
		}
		while (B.length() < 32) {
			B = "0" + B;
		}
		return B;
	}

	private HashMap<String, String> initializeRegisters() {
		HashMap<String, String> R = new HashMap<String, String>();
		R.put("00000", "R0");
		R.put("00001", "R1");
		R.put("00010", "R2");
		R.put("00011", "R3");
		R.put("00100", "R4");
		R.put("00101", "R5");
		R.put("00110", "R6");
		R.put("00111", "R7");
		R.put("01000", "R8");
		R.put("01001", "R9");
		R.put("01010", "R10");
		R.put("01011", "R11");
		R.put("01100", "R12");
		R.put("01101", "R13");
		R.put("01110", "R14");
		R.put("01111", "R15");
		R.put("10000", "R16");
		R.put("10001", "R17");
		R.put("10010", "R18");
		R.put("10011", "R19");
		R.put("10100", "R20");
		R.put("10101", "R21");
		R.put("10110", "R22");
		R.put("10111", "R23");
		R.put("11000", "R24");
		R.put("11001", "R25");
		R.put("11010", "R26");
		R.put("11011", "R27");
		R.put("11100", "R28");
		R.put("11101", "R29");
		R.put("11110", "R30");
		R.put("11111", "R31");
		return R;
	}

	private HashMap<String, String> initializeInstructionSet() {
		HashMap<String, String> instruction = new HashMap<String, String>();
		// Instruction Set 1
		instruction.put("000000", "J");
		instruction.put("000001", "BEQ");
		instruction.put("000010", "BNE");
		instruction.put("000011", "BGTZ");
		instruction.put("000100", "SW");
		instruction.put("000101", "LW");
		instruction.put("000110", "BREAK");

		// Instruction Set 2
		instruction.put("001000", "ADD");
		instruction.put("001001", "SUB");
		instruction.put("001010", "AND");
		instruction.put("001011", "OR");
		instruction.put("001100", "SRL");
		instruction.put("001101", "SRA");

		// Instruction Set 3
		instruction.put("010000", "ADDI");
		instruction.put("010001", "ANDI");
		instruction.put("010010", "ORI");

		// Instruction Set 4
		instruction.put("011000", "MULT");
		instruction.put("011001", "DIV");

		// Instruction Set 5
		instruction.put("100000", "MFHI");
		instruction.put("100001", "MFLO");

		return instruction;
	}

}

public class newMIPS {
	public static void main(String[] args) throws IOException {
		
		Disassembly D = new Disassembly(args[0]);
	}

}

//create result buffer

class ResultBuffer{
	String location;
	int result, result1;
	
	ResultBuffer(){
		location = "";
		result = 0;
		result1 = 0;
	}
}

// Class String Queue

class StringQueue{
	int size, NumberOfElements;
	String[] elements = new String[8];
	
	StringQueue(int s){
		size = s;
		NumberOfElements = 0;
		for(int x=0; x<8; x++){
			elements[x] = "";
		}
	}
	
	void Enqueue(String u){
		if(NumberOfElements < size){
			elements[NumberOfElements] = u;
			NumberOfElements = NumberOfElements + 1;
		}
		else{
			System.out.println("Buffer is Full1");
		}
	}
	
	String Dequeue(){
		if(NumberOfElements == 0){
			System.out.println("Buffer is Empty");
			return("");
		}
		else{
			String temp = elements[0];
			for(int y=0; y<=NumberOfElements; y++){
				elements[y] = elements[y+1];
			}
			NumberOfElements =NumberOfElements-1;
			return(temp);
		}
	}
	
	void PrintQ(){
		for(int i=0; i< NumberOfElements; i++){
			System.out.println("Elements in buffer1 are:"+ elements[i]);
		}
	}
}

//String Queue 1 class



class StringQueue1{
	int size, NumberOfElements;
	String[] elements = new String[8];
	int[] op1 = new int[8];
	int[] op2 = new int[8];
	
	StringQueue1(int s){
		size = s;
		NumberOfElements = 0;
		for(int x=0; x<8; x++){
			elements[x] = "";
			op1[x] = 0;
			op2[x] = 0;
		}
	}
	
	void Enqueue(String u,int o1, int o2){
		if(NumberOfElements < size){
			elements[NumberOfElements] = u;
			op1[NumberOfElements] = o1;
			op2[NumberOfElements] = o2;
			NumberOfElements = NumberOfElements + 1;
		}
		else{
			System.out.println("Buffer is Full2");
		}
	}
	
	String Dequeue(){
		if(NumberOfElements == 0){
			System.out.println("Buffer is Empty");
			return("");
		}
		else{
			String temp = elements[0];
			for(int y=0; y<=NumberOfElements; y++){
				elements[y] = elements[y+1];
			}
			NumberOfElements =NumberOfElements-1;
			return(temp);
		}
	}
	
	int getop1(){
		int currentNumberOfElements = NumberOfElements + 1;
		if(currentNumberOfElements == 0){
			System.out.println("Buffer is Empty");
			return(-1);
		}
		else{
			int temp = op1[0];
			for(int y=0; y<=currentNumberOfElements; y++){
				op1[y] = op1[y+1];
			}
			NumberOfElements =NumberOfElements-1;
			return(temp);
		}
	}
	
	int getop2(){
		int currentNumberOfElements = NumberOfElements + 1;
		if(currentNumberOfElements == 0){
			System.out.println("Buffer is Empty");
			return(-1);
		}
		else{
			int temp = op2[0];
			for(int y=0; y<=currentNumberOfElements; y++){
				op2[y] = op2[y+1];
			}
			NumberOfElements =NumberOfElements-1;
			return(temp);
		}
	}
	
	void PrintQ(){
		for(int i=0; i< NumberOfElements; i++){
			System.out.println("Elements in buffer1 are:"+ elements[i]);
		}
	}
}





