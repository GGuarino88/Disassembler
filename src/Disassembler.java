/**Disassembler
 * @GiuseppeGuarino
 * CS-472
 * Due Date Oct 5th*/

// The Disassembler class takes the hex instruction and decodes the instruction into assembly language

public class Disassembler {

    private int hexInstruction = 0x00000000;
    private int opCode = 0x00000000;
    private int rsReg = 0x00000000;
    private int rtReg = 0x00000000;
    private int rdReg = 0x00000000;
    private int funkCode = 0x00000000;
    private short immediate = (short)0x0000;
    private static int programCounter = 0x8000;
    private int address = 0;
    private String opString = "";


    private final MIPS_32bit mipsReference = new MIPS_32bit();

    //constructor
    public Disassembler(int hexInstruction) {

        this.hexInstruction = hexInstruction;
        setAddress();                               // address for the instruction is set to the program counter
        setOpCode(hexInstruction);                  // decoding the opcode for the instruction
        incrementPc();                              // incrementing the program counter, by default the program counter gets incremented by 4

        if (getOpCode() == mipsReference.OP_CODE_RFUNC) {   // if the opcode equals to zero than we need to fetch the instruction for an R-FORMAT
            setRdCode(hexInstruction);                      // decode the destination register
            setFunkCode(hexInstruction);                    // decode the function code
            setOpStringRformat(getFunkCode());              // from the function code decode the instruction

        } else {                                    // if we instead need to decode an I-FORMAT instruction
            setImmediate(hexInstruction);           // decode the immediate
            setOpStringIformat(getOpCode());        // from the op code determine the instruction
        }

        setRsCode(hexInstruction);          // for both R-Instruction and I-Instruction we decode the
        setRtCode(hexInstruction);          // Source Register and the Target Register
    } // end constructor

    // sets and gets
    // all sets are private and decode the instruction based on their specific bitmask and shift needed
    private void setOpCode(int hexInstruction) {

        this.opCode = (hexInstruction & mipsReference.OP_CODE_MASK) >>> mipsReference.OP_CODE_SHIFT;
    }

    public int getOpCode() {

        return opCode;
    }

    private void setRsCode(int hexInstruction) {

        this.rsReg = (hexInstruction & mipsReference.RS_MASK) >>> mipsReference.RS_SHIFT;
    }

    public int getRsReg() {

        return rsReg;
    }

    private void setRtCode(int hexInstruction) {

        this.rtReg = (hexInstruction & mipsReference.RT_MASK) >>> mipsReference.RT_SHIFT;
    }

    public int getRtReg() {

        return rtReg;
    }

    private void setRdCode(int hexInstruction) {

        this.rdReg = (hexInstruction & mipsReference.RD_MASK) >>> mipsReference.RD_SHIFT;
    }

    public int getRdReg() {

        return rdReg;
    }

    private void setFunkCode(int hexInstruction) {

        this.funkCode = hexInstruction & mipsReference.FUNC_MASK;
    }

    public int getFunkCode() {

        return funkCode;
    }

    private void setImmediate(int hexInstruction) {

        this.immediate = (short)(hexInstruction & mipsReference.IMMEDIATE_MASK);
    }

    public short getImmediate() {

        return immediate;
    }

    private void incrementPc() {

        programCounter += 4;
    }

    public int getProgramCounter() {

        return programCounter;
    }

    private void setAddress() {

        this.address = programCounter;
    }

    public int getAddress() {

        return address;
    }

    // If the instruction is an R-FORMAT than we decode the instruction based on
    // the function code
    private void setOpStringRformat(int func) {

        String operation = "";

        if (func == mipsReference.FUNC_ADD)
            operation = "add";
        else if (func == mipsReference.FUNC_SUB)
            operation = "sub";
        else if (func == mipsReference.FUNC_AND)
            operation = "and";
        else if (func == mipsReference.FUNC_OR)
            operation = "or";
        else if (func == mipsReference.FUNC_SLT)
            operation = "slt";

        this.opString = operation;
    }

    // If the instruction is an I-FORMAT than we decode the instruction based on
    // the op code
    private void setOpStringIformat(int opCode) {

        String operation = "";

        if (opCode == mipsReference.OP_CODE_LW)
            operation = "lw";
        else if (opCode == mipsReference.OP_CODE_SW)
            operation = "sw";
        else if (opCode == mipsReference.OP_CODE_BEQ)
            operation = "beq";
        else if (opCode == mipsReference.OP_CODE_BNE)
            operation = "bne";

        this.opString = operation;
    }

    public String getOpString() {

        return opString;
    }
    // end sets and gets

    // to calculate the branch target we shift the immediate by 2 bits, and we add the difference from the
    // program counter (already incremented) and the address of the branch (current address)
    public int getBranchTarget() {

        return getAddress() + ((getImmediate() << 2) + (getProgramCounter() - getAddress()));
    }

    // R-Format instructions that decoded are add, sub, and, or, slt and will be outputted as:
    // Current Address (hex) - Operation - Destination Register - Source Register - Target Register
    public String getRFormatInst() {

        return String.format("%X %s $%d, $%d, $%d", getAddress(), getOpString(), getRdReg(), getRsReg(), getRtReg());
    }

    // For I-FORMAT instruction we are only decoding branches, save word, load word
    public String getIformatInst() {

        // if the instruction decoded is a branch than the instruction format will be:
        // Current Address (hex) - Operation - Source Register - Target Register - target address (hex)

        if (getOpCode() == mipsReference.OP_CODE_BEQ || getOpCode() == mipsReference.OP_CODE_BNE)
            return String.format("%X %s $%d, $%d, Immediate: %d, address: %X", getAddress(), getOpString(), getRsReg(), getRtReg(), getImmediate(), getBranchTarget());

        // if it is not a branch we have either a save word or a load word and the format is:
        // Current Address (hex) - Operation - Target Register - Immediate - (Source Register)

        else
            return String.format("%X %s $%d, %d ($%d)", getAddress(),  getOpString(), getRtReg(), getImmediate(), getRsReg());
    }

    // the toString function will check if the instruction is an R-Format or an I-Format
    // by checking if the opCode equals to zero determine that the instruction is an R-FORMAT instruction
    // otherwise the instruction is an I-FORMAT instruction
    @Override
    public String toString() {

        if (getOpCode() == mipsReference.OP_CODE_RFUNC)
            return getRFormatInst();
        else
            return getIformatInst();
    }
} // end Disassembler class