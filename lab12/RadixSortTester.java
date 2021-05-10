import static org.junit.Assert.*;
import org.junit.Test;

public class RadixSortTester {

    /**
     * Array that will cause CountingSort.naiveCountingSort to fail, but
     * CountingSort.betterCountingSort can handle.
     **/
    private static String[] unsorted = {"\u0006¥\u0084êG\bødØ\u008C4÷\u0007\u0005L¯\u000Fê©ö-&Õ¶\u0016_\u0090\u001CØÚª\u001A`\u0089òa\u0019É<ÈüýÌ\u0082\u0003ì \u0006bÅÛ=\u0080¨±\u001E\u008DÈø\u008C0U1çýBýPfÉö¦iÈr\u009F¿\u0012(ïÿòL\u0092oFÛ\u0082\u0003H{ÉHÈ\u0081" ,"\u0006bÅÛ=\u0080¨±\u001E\u008DÈø\u008C0U1çýBýPfÉö¦iÈr\u009F¿\u0012(ïÿòL\u0092oFÛ\u0082\u0003H{ÉHÈ\u0081 \u0006¥\u0084êG\bødØ\u008C4÷\u0007\u0005L¯\u000Fê©ö-&Õ¶\u0016_\u0090\u001CØÚª\u001A`\u0089òa\u0019É<ÈüýÌ\u0082\u0003ì"};

    /**
     * Array that both sorts should sort successfully.
     **/
    private static String[] sorted = {"\u0006bÅÛ=\u0080¨±\u001E\u008DÈø\u008C0U1çýBýPfÉö¦iÈr\u009F¿\u0012(ïÿòL\u0092oFÛ\u0082\u0003H{ÉHÈ\u0081 \u0006¥\u0084êG\bødØ\u008C4÷\u0007\u0005L¯\u000Fê©ö-&Õ¶\u0016_\u0090\u001CØÚª\u001A`\u0089òa\u0019É<ÈüýÌ\u0082\u0003ì", "\u0006¥\u0084êG\bødØ\u008C4÷\u0007\u0005L¯\u000Fê©ö-&Õ¶\u0016_\u0090\u001CØÚª\u001A`\u0089òa\u0019É<ÈüýÌ\u0082\u0003ì \u0006bÅÛ=\u0080¨±\u001E\u008DÈø\u008C0U1çýBýPfÉö¦iÈr\u009F¿\u0012(ïÿòL\u0092oFÛ\u0082\u0003H{ÉHÈ\u0081"};


    @Test
    public void testNaiveWithNonNegative() {
        assertArrayEquals(sorted, (RadixSort.sort(unsorted)));
    }

}
