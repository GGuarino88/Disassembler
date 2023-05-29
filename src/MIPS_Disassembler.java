/**MIPS Disassembler
 * @GiuseppeGuarino
 * CS-472
 * Due Date Oct 5th*/

// By using the Disassembler class the program will loop through all the instruction output the assembly code
public class MIPS_Disassembler {

    public static void main (String[] args) {

        // array containing all the instructions provided for the project
        int[] instructions = new int[] {

                // pipeline instructions:
                0xa1020000, 0x810AFFFC, 0x00831820, 0x01263820, 0x01224820,
                0x81180000, 0x81510010, 0x00624022, 0x00000000, 0x00000000, 0x00000000, 0x00000000
        };

        // loop iterates through all the instructions and outputs the decoded assembly instruction
        for (int instruction : instructions) {
            System.out.println(new Disassembler(instruction));
        }
    }
}