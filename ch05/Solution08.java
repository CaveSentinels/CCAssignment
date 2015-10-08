import java.util.*;


public class Solution08 {

    private static byte fill_left(final int N) {
        return (N > 0 ? (byte)((byte)(0x80) >> (N-1)) : (byte)(0));
    }

    private static byte fill_right(final int N) {
        return (byte)((0xFF) >> (8 - N));
    }

    private static byte fill_full() {
        return (byte)(0xFF);
    }

    private static byte fill_partial(final int L_zero, final int R_zero) {
        byte mask_left = ((byte)((0xFF) >> L_zero));
        byte mask_right = ((byte)((0xFF) << R_zero));
        return (byte)(mask_left & mask_right);
    }

    private static void draw_horiontal_line(int[] scr, int width, int x1, int x2, int y) {
        if (x1 > x2) {
            draw_horiontal_line(scr, width, x2, x1, y);
            return;
        }

        final int SCR_SIZE = scr.length;
        final int COL = width / 8;
        final int ROW = SCR_SIZE / COL;

        final int base_start = (y - 1) * COL;
        final int start_byte = x1 / 8;
        final int start_bit = x1 % 8;
        final int end_byte = x2 / 8;
        final int end_bit = x2 % 8;

        if (start_byte == end_byte) {
            scr[base_start + start_byte] = fill_partial(start_bit - 1, 8 - end_bit);
        } else {
            scr[base_start + start_byte] = fill_right(8 - start_bit + 1);
            for (int i = start_byte + 1; i < end_byte; ++i) {
                scr[base_start + i] = fill_full();
            }
            scr[base_start + end_byte] = fill_left(end_bit);
        }
    }

    private static boolean test_fill_left(final int N, byte expected) {
        return (fill_left(N) == expected);
    }

    private static boolean test_fill_right(final int N, byte expected) {
        return (fill_right(N) == expected);
    }

    private static boolean test_fill_partial(final int L, final int R, byte expected) {
        return (fill_partial(L, R) == expected);
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            // Test to make sure the helper functions work fine.
            pass = pass
                && test_fill_left(0, (byte)(0x00))
                && test_fill_left(1, (byte)(0x80))
                && test_fill_left(2, (byte)(0xC0))
                && test_fill_left(3, (byte)(0xE0))
                && test_fill_left(4, (byte)(0xF0))
                && test_fill_left(5, (byte)(0xF8))
                && test_fill_left(6, (byte)(0xFC))
                && test_fill_left(7, (byte)(0xFE))
                && test_fill_left(8, (byte)(0xFF))
                ;
            pass = pass
                && test_fill_right(0, (byte)(0x00))
                && test_fill_right(1, (byte)(0x01))
                && test_fill_right(2, (byte)(0x03))
                && test_fill_right(3, (byte)(0x07))
                && test_fill_right(4, (byte)(0x0F))
                && test_fill_right(5, (byte)(0x1F))
                && test_fill_right(6, (byte)(0x3F))
                && test_fill_right(7, (byte)(0x7F))
                && test_fill_right(8, (byte)(0xFF))
                ;
            pass = pass
                && test_fill_partial(0, 0, (byte)(0xFF))
                && test_fill_partial(0, 1, (byte)(0xFE))
                && test_fill_partial(0, 2, (byte)(0xFC))
                && test_fill_partial(0, 3, (byte)(0xF8))
                && test_fill_partial(0, 4, (byte)(0xF0))
                && test_fill_partial(0, 5, (byte)(0xE0))
                && test_fill_partial(0, 6, (byte)(0xC0))
                && test_fill_partial(0, 7, (byte)(0x80))
                && test_fill_partial(0, 8, (byte)(0x00))
                && test_fill_partial(1, 0, (byte)(0x7F))
                && test_fill_partial(2, 0, (byte)(0x3F))
                && test_fill_partial(3, 0, (byte)(0x1F))
                && test_fill_partial(4, 0, (byte)(0x0F))
                && test_fill_partial(5, 0, (byte)(0x07))
                && test_fill_partial(6, 0, (byte)(0x03))
                && test_fill_partial(7, 0, (byte)(0x01))
                && test_fill_partial(8, 0, (byte)(0x00))
                && test_fill_partial(3, 3, (byte)(0x18))
                && test_fill_partial(4, 4, (byte)(0x00))
                ;
        }

        {
            int [] scr = { 0 };
            draw_horiontal_line(scr, 8, 2, 4, 1);
            pass = pass
                && (scr[0] == (byte)(0x70))
                ;
        }

        {
            int [] scr = { 0, 0, 0, 0, 0 };
            draw_horiontal_line(scr, 40, 10, 29, 1);
            pass = pass
                && (scr[0] == (byte)(0x00))
                && (scr[1] == (byte)(0x7F))
                && (scr[2] == (byte)(0xFF))
                && (scr[3] == (byte)(0xF8))
                && (scr[4] == (byte)(0x00))
                ;
        }

        {
            int [] scr = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            draw_horiontal_line(scr, 40, 10, 29, 2);
            pass = pass
                && (scr[0] == (byte)(0x00))
                && (scr[1] == (byte)(0x00))
                && (scr[2] == (byte)(0x00))
                && (scr[3] == (byte)(0x00))
                && (scr[4] == (byte)(0x00))
                && (scr[5] == (byte)(0x00))
                && (scr[6] == (byte)(0x7F))
                && (scr[7] == (byte)(0xFF))
                && (scr[8] == (byte)(0xF8))
                && (scr[9] == (byte)(0x00))
                ;
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
