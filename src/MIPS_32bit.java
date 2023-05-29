/**MIPS_32bit
 * @GiuseppeGuarino
 * CS-472
 * Due Date Oct 5th*/

// class containing all bit mask constants and Mips reference codes

public final class MIPS_32bit {

    // bit masks for mips 32 bit
    final int OP_CODE_MASK = 0xFC000000;
    final int RS_MASK = 0x03E00000;
    final int RT_MASK = 0x001F0000;
    final int RD_MASK = 0x0000F800;
    final int SHAMT_MASK = 0x000003A0;
    final int FUNC_MASK = 0x0000003F;
    final short IMMEDIATE_MASK = (short)0xFFFF;

    // bit shift for all the required masks
    final int OP_CODE_SHIFT = 26;
    final int RS_SHIFT = 21;
    final int RT_SHIFT = 16;
    final int RD_SHIFT = 11;

    // R-Format function codes
    final int FUNC_ADD = 0x20;
    final int FUNC_SUB = 0x22;
    final int FUNC_AND = 0x24;
    final int FUNC_OR = 0x25;
    final int FUNC_SLT = 0x2A;

    // I-Format operation codes
    final int OP_CODE_RFUNC = 0x0;
    final int OP_CODE_BEQ = 0x4;
    final int OP_CODE_BNE = 0x5;
    final int OP_CODE_LW = 0x23;
    final int OP_CODE_SW = 0x2b;
} // end class MIPS_32bit